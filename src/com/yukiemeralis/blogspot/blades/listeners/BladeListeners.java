package com.yukiemeralis.blogspot.blades.listeners;

import java.util.Random;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.Main;
import com.yukiemeralis.blogspot.blades.PlayerAccount;
import com.yukiemeralis.blogspot.blades.combat.CombatData;
import com.yukiemeralis.blogspot.blades.combat.CombatInfo;
import com.yukiemeralis.blogspot.blades.combat.SpecialBar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class BladeListeners implements Listener
{
    Random random = new Random();
    // Attempt to perform a blade special
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        // Check if the action is a right click in the air
        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR))
            return;

        // Filter out the offhand event
        if (!event.getHand().equals(EquipmentSlot.HAND))
            return;

        // Check if the player is crouched
        Player player = event.getPlayer();
        if (!player.isSneaking())
            return;

        ItemStack held = player.getInventory().getItemInMainHand();

        if (!BladeData.isBladeWeapon(held))
            return;

        event.setCancelled(true);

        PlayerAccount account = BladeData.getAccount(player);
        Blade blade = account.getCurrentBlade();

        // If the blade is a player, both parties must be crouched in order to execute a special.
        // For teamplay and all that
        if (blade.isPlayerBlade())
        {
            // Check proximity
            Player player_blade = Bukkit.getPlayerExact(blade.getName());

            if (player.getLocation().distanceSquared(player_blade.getLocation()) > 42) 
            {
                player.sendMessage("§c" + blade.getName() + " must be closer to use a special!");
                Bukkit.getPlayerExact(blade.getName()).sendMessage("§cYou are not close enough for your driver to use a special!");
                return;
            }

            if (!Bukkit.getPlayerExact(blade.getName()).isSneaking())
            {
                player.sendMessage("§c" + blade.getName() + " must be crouched to use a special!");
                Bukkit.getPlayerExact(blade.getName()).sendMessage("§cYou must be crouched for your driver to use a special!");
                return;
            }
        }

        // Check what level of blade special to use
        int special_level = account.getBladeSpecialProgress() / 100;

        if (special_level == 0)
        {
            player.sendMessage("§c" + blade.getName() + " is not ready to perform a special yet!");
            return;
        }

        // Set block rate to 100% for 5 seconds
        blade.setBlockRate(100);

        // "Pass" the weapon to the blade
        player.getInventory().remove(held);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!account.getIsDead())
                {
                    player.getInventory().addItem(held);
                }
                
                blade.resetBlockRate();

                if (blade.isPlayerBlade())
                {
                    Bukkit.getPlayerExact(blade.getName()).getInventory().remove(held);
                    BladeData.getAccount(Bukkit.getPlayerExact(blade.getName())).setSpecialState(false);
                }
            }
        }.runTaskLater(Main.getInstance(), 80L);

        if (blade.isPlayerBlade())
        {
            Bukkit.getPlayerExact(blade.getName()).getInventory().addItem(held);
            Bukkit.getPlayerExact(blade.getName()).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 80, 2));
            BladeData.getAccount(Bukkit.getPlayerExact(blade.getName())).setSpecialState(true);
        }
        // Also give the blade a massive bonus to damage, and run the blade special effect

        // Fire off the blade special
        Entity target = CombatData.getCombatInfo(player).getTarget();

        switch (special_level)
        {
            case 1: blade.performSpecial1(target, player); break;
            case 2: blade.performSpecial2(target, player); break;
            case 3: blade.performSpecial3(target, player); break;
            case 4: blade.performSpecial4(target, player); break;
        }

        // Reset special progress
        account.setBladeSpecialProgress(0);

        blade.raiseTrust(1);

        // Update bar
        try {
            CombatInfo data = CombatData.getCombatInfo(player);
            data.getSpecialBar().setProgress((BladeData.getAccount(player).getBladeSpecialProgress() % 100) / 100d);
            
            BarColor color = null;

            if (random.nextInt(7) == 0)
                BladeData.getAccount(player).raiseAffinity();

            switch (BladeData.getAccount(player).getAffinity())
            {
                case 0: color = BarColor.BLUE; break;
                case 1: color = BarColor.GREEN; break;
                case 2: color = BarColor.YELLOW; break;
            }

            data.getSpecialBar().setColor(color);
            data.getSpecialBar().setTitle(SpecialBar.generateDisplayName(player));

            // Update board
            data.updateBoard();
        } catch (NullPointerException error) {}
    }
}
