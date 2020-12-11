package com.yukiemeralis.blogspot.blades.combat;

import java.util.ArrayList;
import java.util.Random;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.Main;
import com.yukiemeralis.blogspot.blades.affinity.AffinityActivationType;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.guis.EnemyHPBar;
import com.yukiemeralis.blogspot.blades.guis.PartyScoreboard;
import com.yukiemeralis.blogspot.blades.listeners.EntityListeners;
import com.yukiemeralis.blogspot.blades.listeners.events.AffinitySkillTriggerEvent;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CombatInfo 
{
    private ArrayList<Entity> enemies = new ArrayList<>();
    private Entity current_enemy = null;
    private Player player;

    private boolean isActive = false;

    private BukkitTask combat_timer;
    private BukkitTask cleaning_timer;

    private BukkitTask battle_skill_timer;

    private BossBar special_bar = null;
    private PartyScoreboard board;

    private Random random = new Random();

    public CombatInfo(Player player, Entity target)
    {
        this.player = player;

        if (this.player == null)
            System.out.println("Player is null!");
        isActive = true;

        cleaning_timer = new BukkitRunnable() {
            @Override
            public void run() 
            {
                if (!this.isCancelled())
                {
                    checkIfEnemyIsAlive();
                    BladeData.getAccount(player).getCurrentBlade().drawAffinityLine();
                }
                    
            }
        }.runTaskTimer(Main.getInstance(), 1L, 5L);

        if (BladeData.getAccount(player).getCurrentBlade() != null)
        {
            special_bar = SpecialBar.generate(player);
            special_bar.addPlayer(player);

            // Also set up a player blade's boss bar
            if (BladeData.getAccount(player).getCurrentBlade().isPlayerBlade())
            {
                Player blade = Bukkit.getPlayerExact(BladeData.getAccount(player).getCurrentBlade().getName());
                special_bar.addPlayer(blade);
            }

            // Finally start up the party board, if applicable
            if (BladeData.getAccount(player).getParty() != null)
            {
                board = new PartyScoreboard(BladeData.getAccount(player).getParty());
                board.update();
                board.show();
            } else {
                board = new PartyScoreboard(player);
                board.update();
                board.show();
            }
        }

        // Run the battle start affinity skill
        try {
            AffinitySkill skill = BladeData.getAccount(player).getCurrentBlade().getAffinityChart().getHighestSkillInColumn(1);

            if (skill.getActivationType().equals(AffinityActivationType.START))
            {
                skill.runEffect(target, player);
                Bukkit.getPluginManager().callEvent(new AffinitySkillTriggerEvent(skill, player, target));
            }
            
        } catch (Exception error) {}
        
        // Battle affinity skill timer
        battle_skill_timer = new BukkitRunnable(){
            @Override
            public void run() 
            {
                if (random.nextInt(15 / (BladeData.getAccount(player).getAffinity() + 1)) == 0)
                    try {
                        AffinitySkill skill = BladeData.getAccount(player).getCurrentBlade().getAffinityChart().getHighestSkillInColumn(3);
                        skill.runEffect(target, player);
                        Bukkit.getPluginManager().callEvent(new AffinitySkillTriggerEvent(skill, player, null));
                    } catch (Exception error) {}
            }
        }.runTaskTimer(Main.getInstance(), 100L, 100L);
    }

    public ArrayList<Entity> getCombatants()
    {
        return this.enemies;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public void addTarget(Entity target)
    {
        enemies.add(target);
        setTarget(target);

        try {
            if (!(target instanceof Player))
                EntityListeners.accounts.get(target).linkCombatInfo(this);
        } catch (NullPointerException error) {}
        
    }

    public void setTarget(Entity target)
    {
        this.current_enemy = target;
    }

    public Entity getTarget()
    {
        return this.current_enemy;
    }

    public void checkIfEnemyIsAlive()
    {
        if (!isActive)
            return;
        enemies.removeIf(enemy -> enemy.isDead());

        if (enemies.size() == 0)
        {
            deactivate("All targets died");
            return;
        }
            
        // Technically should create another method for this but screw it
        // This checks if the player is out of range of any enemies
        enemies.removeIf(enemy -> {
            return !enemy.getNearbyEntities(30, 30, 30).contains(player);
        });

        if (enemies.size() == 0)
        {
            deactivate("Out of range");
            return;
        }
    }

    public boolean getIsActive()
    {
        return this.isActive;
    }

    public void updateBoard()
    {
        try {
            board.update();
        } catch (NullPointerException error) {}
    }

    public void deactivate(String reason)
    {
        if (this.player == null)
        {
            System.out.println("Ending combat for unknown player (reason: " + reason + ")");
        } else {
            System.out.println("Ending combat for " + this.player.getDisplayName() + " (reason: " + reason + ")");
        }
        
        stopTimer();
        cleaning_timer.cancel();
        battle_skill_timer.cancel();
        this.isActive = false;

        EnemyHPBar.show(0, player, current_enemy);

        try {
            special_bar.removeAll();
        } catch (NullPointerException error) {}

        try {
            board.destroy();
        } catch (NullPointerException error) {}

        BladeData.getAccount(this.player).getCurrentBlade().resetCritRate();

        BladeData.getAccount(this.player).resetAffinity();
        BladeData.getAccount(this.player).setBladeSpecialProgress(0);
        
        CombatData.removeCombatInfo(this.player);

        destroy();
    }

    public BossBar getSpecialBar()
    {
        return this.special_bar;
    }

    public void resetTimer()
    {
        stopTimer();
        combat_timer = new BukkitRunnable(){
            @Override
            public void run() 
            {
                // If nothing happens for 45 seconds, declare the combat over
                if (!this.isCancelled())
                    deactivate("Timed out");
            }
        }.runTaskLater(Main.getInstance(), 45*20);
    }

    public void showEnemyHealthBar()
    {
        EnemyHPBar.show(player, current_enemy);
    }

    public void stopTimer()
    {
        try {
            combat_timer.cancel();
        } catch (NullPointerException error) {}
    }

    /**
     * Releases memory held by this object
     */
    private void destroy()
    {
        enemies.clear();
        this.player = null;
    }
}
