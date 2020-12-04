package com.yukiemeralis.blogspot.blades.affinity;

public class AffinityRow 
{
    AffinitySkill[] data = new AffinitySkill[7];
    boolean unlocked = false;

    public AffinityRow(AffinitySkill[] input)
    {
        data = input;
    }

    public AffinitySkill get(int value)
    {
        if (value < 0 || value > 6)
            throw new IllegalArgumentException("Illegal row " + value + " for affinity chart, acceptable values are 0-6.");

        return data[value];
    }

    public boolean isUnlocked()
    {
        return this.unlocked;
    }

    public void setUnlockedState(boolean state)
    {
        this.unlocked = state;
    }
}
