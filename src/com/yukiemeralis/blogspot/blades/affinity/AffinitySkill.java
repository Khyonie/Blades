package com.yukiemeralis.blogspot.blades.affinity;

import java.util.Arrays;

import com.yukiemeralis.blogspot.blades.utils.TextUtils;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class AffinitySkill 
{
    protected int level;
    protected String name;
    protected String description;

    // Specials
    protected boolean isModifierActive = false;
    protected double modifier;

    protected Material icon;

    protected AffinitySkillType skillType;
    protected AffinityActivationType activationType = null;

    protected boolean unlocked = false;

    public AffinitySkill(String name, String description, int level, AffinitySkillType type)
    {
        this.skillType = type;
        this.description = description;
        this.name = name;
        this.level = level;
    }

    public abstract void runEffect(Entity target, Player driver);

    public int getLevel()
    {
        return level;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public AffinityActivationType getActivationType()
    {
        if (activationType == null)
        {
            return AffinityActivationType.RANDOM;
        } else {
            return activationType;
        }
    }

    // Special data
    public double getModifierValue()
    {
        return this.modifier;
    }

    public boolean isModifierActive()
    {
        return this.isModifierActive;
    }

    public void setModifierState(boolean value)
    {
        this.isModifierActive = value;
    }

    public void setModifierValue(double value)
    {
        this.modifier = value;
    }

    public ItemStack getIcon()
    {
        ItemStack item = new ItemStack(icon);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§e" + name);
        meta.setLore(Arrays.asList(new String[] {"§f" + description, "§fType: " + TextUtils.formatNicely(skillType.toString())}));

        item.setItemMeta(meta);

        //item.setAmount(level);

        return item;
    }
}
