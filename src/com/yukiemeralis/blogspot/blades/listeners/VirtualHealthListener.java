package com.yukiemeralis.blogspot.blades.listeners;

import java.util.Random;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.PlayerAccount;
import com.yukiemeralis.blogspot.blades.affinity.AffinityActivationType;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class VirtualHealthListener implements Listener
{
    Random random = new Random();
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamageByEntity(EntityDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        // Attempt to block damage
        PlayerAccount acnt = BladeData.accounts.get(((Player) event.getEntity()).getUniqueId().toString());
        
        // If the player is a blade, block all damage
        if (!acnt.isDriver())
        {
            event.setCancelled(true);
            return;
        } else {
            // Otherwise roll for block
            if (BladeData.getAccount((Player) event.getEntity()).getCurrentBlade() == null)
                return;

            int blockrate = acnt.getCurrentBlade().getBlockRate();

            if (random.nextInt(100) <= blockrate)
            {
                event.getEntity().sendMessage("§aBlade blocked damage!");
                event.setCancelled(true);
                return;
            }
            
            // If the blade's second battle skill is about taking hits...
            if (acnt.getCurrentBlade().getAffinityChart().getHighestSkillInColumn(1).getActivationType().equals(AffinityActivationType.ON_DAMAGE))
            {
                if (random.nextInt(15) == 0)
                {
                    AffinitySkill skill = acnt.getCurrentBlade().getAffinityChart().getHighestSkillInColumn(1);

                    skill.runEffect(null, (Player) event.getEntity());
                }
            }

            acnt.dealVirtualHPDamage(event.getDamage());
        }

        event.setDamage(0d); // Nullify actual damage
    }

    @EventHandler
    public void onHealGeneric(EntityRegainHealthEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        PlayerAccount acnt = BladeData.accounts.get(((Player) event.getEntity()).getUniqueId().toString());
        acnt.healVirtualHP(event.getAmount());
    }

    @EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent event)
    {
        BladeData.getAccount(event.getPlayer()).setVirtualHP(
            BladeData.getAccount(event.getPlayer()).getMaxVirtualHP()
        );

        BladeData.getAccount(event.getPlayer()).setIsDead(false);

        try {
            BladeData.addBladeWeapon(BladeData.getAccount(event.getPlayer()).getCurrentBlade());
        } catch (Exception error) {}
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        PlayerAccount account = BladeData.getAccount(event.getEntity());

        account.setIsDead(true);
    }
}
