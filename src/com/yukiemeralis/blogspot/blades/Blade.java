package com.yukiemeralis.blogspot.blades;

import java.util.Arrays;

import com.yukiemeralis.blogspot.blades.affinity.AffinityChart;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.customspecials.SpecialData;
import com.yukiemeralis.blogspot.blades.enums.*;
import com.yukiemeralis.blogspot.blades.listeners.TrustGradeRaiseEvent;
import com.yukiemeralis.blogspot.blades.utils.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Blade implements Cloneable {
    // Blade stats
    protected String name;
    protected Element element;
    protected Role role;
    protected WeaponType type;

    protected int rarity;

    protected Player driver;

    protected boolean isPlayer = false;

    // RPG stats
    protected int critRate = 2;
    protected int base_critrate = 2;

    protected int blockRate = 5;
    protected int base_blockrate = 5;

    protected int trust = 0;
    protected int trustMultiplier = 1; // Defaults to 1, higher means harder to level up affinity chart

    protected AffinityChart chart;

    protected SpecialData[] skills = new SpecialData[4];

    // Minecraft stats
    Entity entity;
    EntityType entityType;

    public Blade(String name, Element element, Role role, WeaponType type, int rarity) {
        this.name = name;
        this.element = element;
        this.role = role;
        this.type = type;
        this.rarity = rarity;

        register();
    }

    public void setMinecraftProperties(EntityType entityType) {
        this.entityType = entityType;
    }

    public void spawn(Location location) {
        entity = location.getWorld().spawnEntity(location, entity.getType());
        entity.setInvulnerable(true);
    }

    public void despawn() {
        entity.remove();
    }

    public void setDriver(Player driver) {
        this.driver = driver;
    }

    public Player getDriver() {
        return driver;
    }

    /**
     * Pass in an affinity skill as a parameter to a special attack.
     * @param skill
     */
    public void addSkillParameter(AffinitySkill skill)
    {
        this.skill_parameter = skill;
    }

    AffinitySkill skill_parameter;

    public abstract void performSpecial1(Entity target, Player driver);
    public abstract void performSpecial2(Entity target, Player driver);
    public abstract void performSpecial3(Entity target, Player driver);
    public abstract void performSpecial4(Entity target, Player driver);

    // Getters and setters

    public String getName()
    {
        return name;
    }

    public void setAlias(String alias)
    {
        this.name = alias;
    }

    public Element getElement()
    {
        return element;
    }

    public Role getCombatRole()
    {
        return role;
    }

    public WeaponType getWeaponType()
    {
        return type;
    }

    public int getBlockRate()
    {
        return blockRate;
    }

    public void setBlockRate(int value)
    {
        blockRate = value;
    }

    public void resetBlockRate()
    {
        blockRate = base_blockrate;
    }

    public int getCritRate()
    {
        return critRate;
    }

    public void setCritRate(int value)
    {
        this.critRate = value;
    }

    public void setBaseCritRate(int value)
    {
        this.base_critrate = value;
        if (critRate < base_critrate)
            critRate = base_critrate;
    }

    public void resetCritRate()
    {
        this.critRate = base_critrate;
    }

    public int getTrust()
    {
        return trust;
    }

    public int getTrustMultiplier()
    {
        return trustMultiplier;
    }

    public boolean isPlayerBlade()
    {
        return isPlayer;
    }

    public void setPlayerBlade(boolean state)
    {
        this.isPlayer = state;
    }

    public AffinityChart getAffinityChart()
    {
        return chart;
    }

    public void raiseTrust(int delta)
    {
        trust += delta;

        // Update affinity chart
        updateAffinityChart();
    }

    protected void setSpecialInfo(int index, String name, String desc, Element element)
    {
        skills[index] = new SpecialData(name, desc, element);
    }

    public SpecialData getSpecialInfo(int index)
    {
        return skills[index];
    }

    protected void healParty(Player healer, Party party, double amount)
    {
        party.getMembers().forEach(player -> {
            if (player != healer)
            {
                if (healer.getLocation().distanceSquared(player.getLocation()) <= (30 * 30))
                {
                    BladeData.getAccount(player).healVirtualHP(amount);
                }
            }
        });
    }

    private void updateAffinityChart()
    {
        if (trust >= trustMultiplier * 100 && !chart.getRow(0).isUnlocked())
        {
            chart.getRow(0).setUnlockedState(true);
            chart.raiseUnlockLevel();
            Bukkit.getPluginManager().callEvent(new TrustGradeRaiseEvent(this, driver, AffinityUtils.trustToGrade(trust), 1));
        }
        if (trust >= trustMultiplier * 175 && !chart.getRow(1).isUnlocked())
        {
            chart.getRow(1).setUnlockedState(true);
            chart.raiseUnlockLevel();
            Bukkit.getPluginManager().callEvent(new TrustGradeRaiseEvent(this, driver, AffinityUtils.trustToGrade(trust), 2));
        }
        if (trust >= trustMultiplier * 300 && !chart.getRow(2).isUnlocked())
        {
            chart.getRow(2).setUnlockedState(true);
            chart.raiseUnlockLevel();
            Bukkit.getPluginManager().callEvent(new TrustGradeRaiseEvent(this, driver, AffinityUtils.trustToGrade(trust), 3));
        }
        if (trust >= trustMultiplier * 400 && !chart.getRow(3).isUnlocked())
        {
            chart.getRow(3).setUnlockedState(true);
            chart.raiseUnlockLevel();
            Bukkit.getPluginManager().callEvent(new TrustGradeRaiseEvent(this, driver, AffinityUtils.trustToGrade(trust), 4));
        }
        if (trust >= trustMultiplier * 750 && !chart.getRow(4).isUnlocked())
        {
            chart.getRow(4).setUnlockedState(true);
            chart.raiseUnlockLevel();
            Bukkit.getPluginManager().callEvent(new TrustGradeRaiseEvent(this, driver, AffinityUtils.trustToGrade(trust), 5));
        }
            
    }

    public ItemStack getIcon()
    {
        ItemStack item;
        Material material = null;

        switch (type.toString())
        {
            case "SWORD": material = Material.DIAMOND_SWORD; break;
            case "TRIDENT": material = Material.TRIDENT; break;
            case "AXE": material = Material.DIAMOND_AXE; break;
            case "BOW": material = Material.BOW; break;
            case "CROSSBOW": material = Material.CROSSBOW; break;
        }

        item = new ItemStack(material);

        // Meta
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Blade.elementToColor(element) + name);
        meta.setLore(Arrays.asList(new String[] {
            "§fElement: " + TextUtils.formatNicely(element.toString()),
            "§fRole: " + TextUtils.formatNicely(role.toString()),
            "§fWeapon type: " + TextUtils.formatNicely(type.toString()),
            "§f---=[ §7Blade specials §f]=---",
            "§fSpecial I: " + elementToColor(skills[0].getElement()) +  skills[0].getName(),
            "§7- " + skills[0].getDescription(),
            "§fSpecial II: " + elementToColor(skills[1].getElement()) +  skills[1].getName(),
            "§7- " + skills[1].getDescription(),
            "§fSpecial III: " + elementToColor(skills[2].getElement()) +  skills[2].getName(),
            "§7- " + skills[2].getDescription(),
            "§fSpecial IV: " + elementToColor(skills[3].getElement()) +  skills[3].getName(),
            "§7- " + skills[3].getDescription()
        }));

        item.setItemMeta(meta);

        return item;
    }

    @Override
    public Object clone()
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException error) {
            error.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (!(o instanceof Blade))
            return false;
        if (((Blade) o).getName().equals(this.getName()))
            return true;
        return false;
    }

    protected void register()
    {
        BladeRegistry.getRegistry().put(this.name, this);
    }

    //
    // Text utils
    //

    public static String elementToColor(Element element) 
    {
        String in = "§";

        switch (element.toString())
        {
            case "FIRE": in = in + "c"; break;
            case "WATER": in = in + "9"; break;
            case "WIND": in = in + "a"; break;
            case "ICE": in = in + "b"; break;
            case "EARTH": in = in + "6"; break;
            case "ELECTRIC": in = in + "e"; break;
            case "LIGHT": in = in + "f"; break;
            case "DARK": in = in + "5"; break;
        }

        return in;
    }
}
