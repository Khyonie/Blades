package com.yukiemeralis.blogspot.blades.utils;

import java.util.Random;

import org.bukkit.entity.Entity;

public class EntityAccount 
{
    Entity owner;

    int level;
    String name;
    boolean unique;

    int difficulty; // Difficulty scale for each enemy from 0-20, where 0 is unique

    int experience_value;

    private static Random random = new Random();

    public EntityAccount(String name, int level, Entity entity)
    {
        this.name = name;
        this.level = level;

        this.owner = entity;

        difficulty = random.nextInt(20);

        if (difficulty == 0)
            unique = true;

        experience_value = (level * difficulty) /10;
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

    // Setters
    public void setIsUnique(boolean unique)
    {
        this.unique = unique;
    }
}
