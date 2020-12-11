package com.yukiemeralis.blogspot.blades.listeners;

import java.util.HashMap;
import java.util.Random;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.utils.EntityAccount;
import com.yukiemeralis.blogspot.blades.utils.TextUtils;

import org.bukkit.World.Environment;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntityListeners implements Listener
{
    public static HashMap<Entity, EntityAccount> accounts = new HashMap<>();

    Random random = new Random();

    // On entity spawn
    @EventHandler
    public void onSpawn(EntitySpawnEvent event)
    {
        if (!(event.getEntity() instanceof LivingEntity))
            return;

        if (event.getEntity() instanceof Player)
            return;

        if (accounts.containsKey(event.getEntity()))
            return;

        if (!(event.getEntity() instanceof Attributable))
            return;

        // Assign an account
        int level = biomeToLevel(event.getLocation().getBlock());
        String name = TextUtils.formatNicely(event.getEntityType().name()) + " | level " + level;
        EntityAccount account = new EntityAccount(name, level, event.getEntity());

        accounts.put(event.getEntity(), account);

        // Set up health based on the level
        Attributable target = (Attributable) event.getEntity();

        event.getEntity().setCustomName(name);
        event.getEntity().setCustomNameVisible(true);

        // hp = maxhp * (level / 4) * difficulty
        double hp =  (target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (level / 4))*account.getDifficulty();

        target.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hp);

        ((Damageable) event.getEntity()).setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        // Set up damage the entity can deal (level + base)
        try {
            target.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(target.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() + level);
        } catch (NullPointerException error) {}
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player)
            return;
        if (!(event.getEntity() instanceof LivingEntity))
            return;

        try {
            accounts.get(event.getEntity()).getCombatInfo().showEnemyHealthBar();
        } catch (NullPointerException error) {}
    }

    // On entity death
    @EventHandler
    public void onDeath(EntityDeathEvent event)
    {
        if (event.getEntity() instanceof LivingEntity && !(event.getEntity() instanceof Player))
            return;

        if (!accounts.containsKey(event.getEntity()))
            return;

        event.getEntity().getNearbyEntities(20, 20, 20).forEach(entity -> {
            if (entity instanceof Player)
                BladeData.getAccount((Player) entity).addExperience(accounts.get(event.getEntity()).getExpValue());
        });
        
        accounts.remove(event.getEntity());
    }

    // Helper methods
    public static HashMap<Entity, EntityAccount> getAccounts()
    {
        return accounts;
    }

    private int biomeToLevel(Block b)
    {
        int section;

        if (b.getTemperature() <= 0.0)
        {
            section = 0; // Very cold biomes, high levels (60-90 + uniques)
        } else if (b.getTemperature() > 0.0 && b.getTemperature() <= 0.2) {
            section = 1; // Cold/hot biome, medium levels (40-70);
        } else if (b.getTemperature() >= 0.25 && b.getTemperature() <= 0.3) {
            section = 2; // Cold forest biome, medium-low levels (30-60)
        } else if (b.getTemperature() > 0.3 && b.getTemperature() <= 0.7) {
            section = 3; // Temperate forest biome, low levels (1-15)
        } else if (b.getTemperature() == 0.7) {
            section = 4; // Safe common biome, variable low levels (1-30);
        } else if (b.getTemperature() == 0.8) {
            section = 5; // Dangerous warm biome, variable levels (10-70 + uniques)
        } else if (b.getTemperature() >= 0.9) {
            section = 6; // Jungle, high levels
        } else {
            section = 7; // Unknown, level 50
        }

        if (b.getLocation().getWorld().getEnvironment().equals(Environment.NETHER))
        {
            return 50 + random.nextInt(40);
        }

        if (b.getLocation().getWorld().getEnvironment().equals(Environment.THE_END))
        {
            return 60 + random.nextInt(30);
        }

        switch (section)
        {
            case 0:
                return 60 + random.nextInt(30);
            case 1:
                return 40 + random.nextInt(30);
            case 2:
                return 30 + random.nextInt(30);
            case 3:
                return 1 + random.nextInt(15);
            case 4:
                return 1 + random.nextInt(30);
            case 5:
                return 10 + random.nextInt(60);
            case 6:
                return 60 + random.nextInt(30);
            case 7:
                return 50;
            default:
                return 0;
        }
    }
}
