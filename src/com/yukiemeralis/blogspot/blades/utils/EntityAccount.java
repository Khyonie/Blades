package com.yukiemeralis.blogspot.blades.utils;

import java.util.Random;

import com.yukiemeralis.blogspot.blades.combat.CombatInfo;
import com.yukiemeralis.blogspot.blades.customspecials.MobTypes;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class EntityAccount 
{
    Entity owner;

    int level;
    String name;
    boolean unique;

    int difficulty; // Difficulty scale for each enemy from 0-10, where 0 is unique

    int experience_value;

    CombatInfo combat;

    private static Random random = new Random();

    public EntityAccount(String name, int level, Entity entity)
    {
        this.name = name;
        this.level = level;

        this.owner = entity;

        difficulty = 1 + random.nextInt(9);

        if (difficulty == 0)
            unique = true;

        experience_value = (level * difficulty);
        if (MobTypes.passive.contains(entity.getType()));
            experience_value = experience_value /10;
    }

    // Getters
    public int getLevel()
    {
        return this.level;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isUnique()
    {
        return this.unique;
    }

    public Entity getOwner()
    {
        return this.owner;
    }

    public int getDifficulty()
    {
        return this.difficulty;
    }

    public int getExpValue()
    {
        return experience_value;
    }

    /**
     * Gives us the HP percentage of the entity.
     * @return
     */
    public double getHpPercent()
    {
        double max = ((Attributable) owner).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double current = ((LivingEntity) owner).getHealth();

        return current/max;
    }

    // Setters
    public void setIsUnique(boolean unique)
    {
        this.unique = unique;
    }

    public void linkCombatInfo(CombatInfo info)
    {
        this.combat = info;
    }

    public CombatInfo getCombatInfo()
    {
        return this.combat;
    }
}
