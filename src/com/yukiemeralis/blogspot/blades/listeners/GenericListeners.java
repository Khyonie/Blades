package com.yukiemeralis.blogspot.blades.listeners;

import java.util.ArrayList;
import java.util.Random;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.Main;
import com.yukiemeralis.blogspot.blades.PlayerAccount;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.combat.CombatData;
import com.yukiemeralis.blogspot.blades.combat.CombatInfo;
import com.yukiemeralis.blogspot.blades.combat.SpecialBar;
import com.yukiemeralis.blogspot.blades.utils.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class GenericListeners implements Listener
{
    Random random = new Random();

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        if (BladeData.getAccount(event.getPlayer()) == null)
        {
            PlayerAccount account = new PlayerAccount(event.getPlayer());

            BladeData.accounts.put(event.getPlayer().getUniqueId().toString(), account);
        }

        // Fire up the clock for field skills
        new BukkitRunnable() {
            @Override
            public void run() 
            {
                try {
                    BladeData.getAccount(event.getPlayer()).getCurrentBlade().getAffinityChart().getHighestSkillInColumn(0).runEffect(null, event.getPlayer());
                } catch (Exception error) {}
            }
        }.runTaskTimer(Main.getInstance(), 0L, 100L);
    }

    // Affinity skill listener
    @EventHandler
    public void onAffinitySkill(AffinitySkillTriggerEvent event)
    {
        if (event.getSkill() == null)
            return;

        TextComponent message = new TextComponent("§8[§e★ Skill lvl §6" + event.getSkill().getLevel() + "§8] §7" + event.getSkill().getName());
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§f" + event.getSkill().getDescription())));

        event.getDriver().spigot().sendMessage(message);
    }

    @EventHandler
    public void onTrustRaise(TrustGradeRaiseEvent event)
    {
        event.getDriver().sendMessage(
            "§aRaised trust grade with " + event.getBlade().getName() + "! New grade: " + event.getGrade() 
            + ", unlocking row " + event.getRow() + " of their affinity chart."
        );

        event.getDriver().playSound(event.getDriver().getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1.0f, 1.0f);
    }

    // Melee combat
    @EventHandler
    public void onCombat(EntityDamageByEntityEvent event)
    {
        /**
         * We want to do a few things in this method.
         * 1) Handle all aspects of driver combat, such as incrementing special state on hit.
         * 2) Make sure blades are both invincible and can't deal damage (unless they're attacking with a special)
         */
        if (!(event.getDamager() instanceof Player))
            return;

        Player player = (Player) event.getDamager();
        Entity target = event.getEntity();

        // Check if the player is a blade
        if (!BladeData.getAccount(player).isDriver())
        {
            if (!BladeData.getAccount(player).getSpecialState())
            {
                event.setCancelled(true);
                return;
            }
        }

        if (player.getInventory().getItemInMainHand() == null)
            return;

        if (!BladeData.isBladeWeapon(player.getInventory().getItemInMainHand()))
            return;

        handleCombat(player, target, event.getFinalDamage());
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event)
    {
        if (!(event.getEntity().getShooter() instanceof Player))
            return;
        
        if (event.getHitEntity() == null)
            return;

        Player driver = (Player) event.getEntity().getShooter();
        Entity target = event.getHitEntity();

        if (!BladeData.getAccount(driver).isDriver())
            return;

        if (event.getEntity() instanceof Trident)
        {
            handleCombat(driver, target, 4.0);
            return;
        }

        if (driver.getInventory().getItemInMainHand() == null)
            return;

        if (!BladeData.isBladeWeapon(driver.getInventory().getItemInMainHand()))
            return;

        handleCombat(driver, target, 4.0);
    }
    
    boolean critical = false;
    private void handleCombat(Player player, Entity target, double damage)
    {
        if (BladeData.getAccount(player).getCurrentBlade() == null)
        {
            return;
        }

        if (player.equals(target))
            return;

        if (!CombatData.isInCombat(player))
        {
            CombatData.beginCombat(player, target);
        }

        CombatInfo data = CombatData.getCombatInfo(player);
        BladeData.getAccount(player).addBladeSpecialProgress(20);

        // Apply level-based damage
        damage = (damage + BladeData.getAccount(player).getLevel());

        // Roll for a crit
        if (random.nextInt(100) <= BladeData.getAccount(player).getCurrentBlade().getCritRate())
        {
            critical = true;
            player.sendMessage("§aCritical hit!");
            ((Damageable) target).damage(damage); // Double the damage inflicted
        }  

        // Update bar
        try {
            data.getSpecialBar().setProgress((BladeData.getAccount(player).getBladeSpecialProgress() % 100) / 100d);
            
            BarColor color = null;

            if (random.nextInt(7) == 0)
                BladeData.getAccount(player).raiseAffinity();

            try {
                // Attacking affinity skill
                AffinitySkill skill = BladeData.getAccount(player).getCurrentBlade().getAffinityChart().getHighestSkillInColumn(2);
                switch (skill.getActivationType().toString())
                {
                    case "RANDOM":
                        if (random.nextInt(30 / (BladeData.getAccount(player).getAffinity() + 1)) == 0)
                        {
                            Bukkit.getPluginManager().callEvent(new AffinitySkillTriggerEvent(skill, player, target));
                            skill.runEffect(target, player);
                        }
                        break;
                    case "ALWAYS":
                        Bukkit.getPluginManager().callEvent(new AffinitySkillTriggerEvent(skill, player, target));
                        skill.runEffect(target, player);
                        break;
                    case "CRITICAL":
                        if (critical)
                        {
                            Bukkit.getPluginManager().callEvent(new AffinitySkillTriggerEvent(skill, player, target));
                            skill.runEffect(target, player);
                        }
                        break;
                }
                
            } catch (Exception error) {}

            switch (BladeData.getAccount(player).getAffinity())
            {
                case 0: color = BarColor.BLUE; break;
                case 1: color = BarColor.GREEN; break;
                case 2: color = BarColor.YELLOW; break;
            }

            data.getSpecialBar().setColor(color);
            data.getSpecialBar().setTitle(SpecialBar.generateDisplayName(player));
        } catch (NullPointerException error) {}

        if (!data.getCombatants().contains(target))
        {
            // If we kill the enemy, don't add it as a target
            if (((Damageable) target).getHealth() - damage <= 0.0)
                return;

            data.addTarget(target);
        }

        // Update party board
        data.updateBoard();

        if (!data.getTarget().equals(target))
            data.setTarget(target);
        data.resetTimer();
        critical = false;
    }

    // Crossbow "infinity" listener
    @EventHandler
    public void onCrossbowShoot(ProjectileLaunchEvent event)
    {
        if (event.getEntity().getShooter() instanceof Player)
            return;
        ItemStack held = ((Player) event.getEntity().getShooter()).getInventory().getItemInMainHand();

        if (held.getItemMeta().getDisplayName().equals("§eBlade weapon"));
            ((Player) event.getEntity().getShooter()).getInventory().addItem(new ItemStack(Material.ARROW));
    }

    // Potion listener
    public void onPotionPickup(EntityPickupItemEvent event)
    {
        if (!(event.getEntity() instanceof Player))
            return;

        if (!TextUtils.trimFormatting(event.getItem().getItemStack().getItemMeta().getDisplayName()).equals("Potion"))
            return;

        // Pull the potion data
        ItemMeta meta = event.getItem().getItemStack().getItemMeta();

        int size = Integer.valueOf(TextUtils.trimFormatting(meta.getLore().get(0)));
        Player spawner = Bukkit.getPlayerExact(TextUtils.trimFormatting(meta.getLore().get(1)));

        // Turn the size into a percentage
        double percent = 1.0;
        switch (size)
        {
            case 0: percent = 0.05; break;
            case 1: percent = 0.1; break;
            case 2: percent = 0.2; break;
        }

        // Apply healing
        if (BladeData.getAccount(spawner).getParty() == null)
        {
            // For single player

            // Check if the spawner is the same player
            if (spawner.equals(event.getEntity()))
            {
                double hp = BladeData.getAccount(spawner).getPercentHp(percent);
                BladeData.getAccount(spawner).healVirtualHP(hp);
            } else {
                event.setCancelled(true);
                return;
            }
        } else {
            // For party members

            // Check if the player is in the spawner's party
            if (BladeData.getAccount(spawner).getParty().getMembers().contains((Player) event.getEntity()))
            {
                healParty(BladeData.getAccount(spawner).getParty().getMembers(), percent);
            } else {
                event.setCancelled(true);
            }
        }
    }

    private static void healParty(ArrayList<Player> members, double percent)
    {
        members.forEach(player -> {
            PlayerAccount account = BladeData.getAccount(player);
            double hp = account.getPercentHp(percent);

            account.healVirtualHP(hp);
        });
    }
}
