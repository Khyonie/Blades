package com.yukiemeralis.blogspot.blades.affinity;

import java.util.HashMap;

import com.yukiemeralis.blogspot.blades.Blade;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class AffinityChart implements Listener, Cloneable
{
    // Blades will have three special trees, three battle skill trees, and one field skill tree
    // For battle skill trees, the first one activates when a battle begins
    // The second one activates randomly during normal attacks
    // The third one activates randomly during battle
    Blade owner;

    HashMap<Integer, AffinityRow> affinity = new HashMap<>();

    int unlock_level = 0;

    /**
     * Don't use this. It's so I can register the listener in Main
     */
    public AffinityChart() {}

    /**
     * Affinity chart default constructor.
     * @param skills An array of arrays of affinity skills. Each array represents one row, starting at level
     * 1 and going up. Arrays may not be null but their values may be, a null affinity skill represents a
     * blank space.
     */
    public AffinityChart(Blade owner, AffinityRow... skills)
    {
        this.owner = owner;

        // Hook up a given affinity chart
        for (int i = 0; i < skills.length; i++)
            affinity.put(i, skills[i]);

        affinity.get(0).setUnlockedState(true);
    }

    /**
     * Pull a skill's info out of an affinity chart. Reminder that arrays start at 0.
     * @param column
     * @param row
     * @return
     */
    public AffinitySkill get(int column, int row)
    {
        if (column < 0 || column > 4)
            throw new IllegalArgumentException("Illegal column " + column + " for affinity chart, acceptable values are 0-4.");
        return affinity.get(column).get(row);
    }

    public AffinityRow getRow(int column)
    {
        if (column < 0 || column > 4)
            throw new IllegalArgumentException("Illegal column " + column + " for affinity chart, acceptable values are 0-4.");
        return affinity.get(column);
    }

    public Blade getOwner()
    {
        return this.owner;
    }

    @EventHandler
    public void onInteract(InventoryClickEvent event)
    {
        if (!event.getView().getTitle().equals("Affinity chart"))
            return;

        event.setCancelled(true);
        if (event.getRawSlot() != 18)
            return;
    }

    public Inventory toInventory()
    {
        return AffinityUtils.toInventory(this);
    }

    public AffinityChart clone()
    {
        try {
            return (AffinityChart) super.clone();
        } catch (CloneNotSupportedException error) {
            return null;
        }
    }

    public void setUnlockValue(int value)
    {
        unlock_level = value;
    }

    public void raiseUnlockLevel()
    {
        unlock_level++;
    }

    public void reset()
    {
        affinity.forEach((id, row) -> {
            row.setUnlockedState(false);
        });
        affinity.get(0).setUnlockedState(true);
    }

    public void setOwner(Blade owner)
    {
        this.owner = owner;
    }

    /**
     * Get the highest unlocked version of an affinity skill in a column
     */
    public AffinitySkill getHighestSkillInColumn(int column)
    {
        for (int i = unlock_level; i > -1; i--)
        {
            if (getRow(i).isUnlocked() && getRow(i).get(column) != null)
                return getRow(i).get(column);
        }

        return null;
    }
}
