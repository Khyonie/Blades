package com.yukiemeralis.blogspot.blades;

import java.util.Arrays;

import com.yukiemeralis.blogspot.blades.affinity.AffinityChart;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.customspecials.SpecialData;
import com.yukiemeralis.blogspot.blades.entities.BladeEntity;
import com.yukiemeralis.blogspot.blades.entities.npcs.NPC;
import com.yukiemeralis.blogspot.blades.entities.npcs.NPCManager;
import com.yukiemeralis.blogspot.blades.entities.npcs.skins.Skins;
import com.yukiemeralis.blogspot.blades.enums.*;
import com.yukiemeralis.blogspot.blades.listeners.events.TrustGradeRaiseEvent;
import com.yukiemeralis.blogspot.blades.utils.PacketManager;
import com.yukiemeralis.blogspot.blades.utils.Particles;
import com.yukiemeralis.blogspot.blades.utils.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.WorldServer;

public abstract class Blade implements Cloneable {
    // Blade stats
    protected String name;
    protected Element element;
    protected Role role;
    protected WeaponType type;
    protected boolean isHumanNPC;
    protected NPC npc_entity;

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
    BladeEntity entity;
    EntityTypes<? extends EntityCreature> entitytype;

    public Blade(String name, Element element, Role role, WeaponType type, int rarity, EntityTypes<? extends EntityCreature> entitytype, boolean isHumanNPC) {
        this.name = name;
        this.element = element;
        this.role = role;
        this.type = type;
        this.rarity = rarity;
        this.entitytype = entitytype;
        this.isHumanNPC = isHumanNPC;

        register();
    }

    public void setMinecraftProperties() {
        entity = new BladeEntity(entitytype, driver.getLocation());

        if (isHumanNPC)
        {
            entity.setIsHumanoid(true); // Mark the NPC as having a human avatar
            npc_entity = new NPC(this, NPCManager.createNPC(entity.getBukkitEntity(), name));
            npc_entity.setHost(this); // Set the NPC to have this blade as its host
            npc_entity.linkSkin(Skins.data.get(name)); // Link the skin for the blade
        }
            
    }

    public BladeEntity getAvatar()
    {
        return entity;
    }

    public void spawn()
    {
        if (isPlayer)
            return;
        entity.setDriver(driver);
        WorldServer world = ((CraftWorld) ((Player) driver).getWorld()).getHandle();

        world.addEntity(entity); // Spawn the entity in the world
        entity.link(this); // Link this blade and the blade's avatar together

        if (isHumanNPC) {
            NPCManager.npcs.put(this, npc_entity);

            // Spawn the NPC on the client and server side
            npc_entity.spawn(entity.getBukkitLocation()); 
            
            // Hide the base zombie avatar
            Bukkit.getOnlinePlayers().forEach(target -> {
                PacketManager.hideEntity(entity, target);
            });

            npc_entity.getAvatarData().setCustomName(new ChatComponentText(elementToColor(element) + name + " | " + driver.getDisplayName()));
        } else {
            BladeData.blade_entities.add(entity);
            entity.setCustomName(new ChatComponentText(elementToColor(element) + name + " | " + driver.getDisplayName()));
        }
        
    }

    public void despawn() 
    {
        if (isPlayer)
            return;
        WorldServer world = ((CraftWorld) ((Player) driver).getWorld()).getHandle();

        if (entity.getIsHumanoid())
        {
            npc_entity.despawn(false);
            world.removeEntity(entity);
        } else {
            world.removeEntity(entity);
        }
    }

    public void linkNPC()
    {
        getAvatar().setIsHumanoid(true);
    }

    public NPC getLinkedNPC()
    {
        return npc_entity;
    }

    public boolean isHumanoid()
    {
        return this.isHumanNPC;
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
            Bukkit.getPluginManager().callEvent(new TrustGradeRaiseEvent(this, driver, AffinityUtils.trustToGrade(trust / trustMultiplier * 100), 1));
        }
        if (trust >= trustMultiplier * 175 && !chart.getRow(1).isUnlocked())
        {
            chart.getRow(1).setUnlockedState(true);
            chart.raiseUnlockLevel();
            Bukkit.getPluginManager().callEvent(new TrustGradeRaiseEvent(this, driver, AffinityUtils.trustToGrade(trust / trustMultiplier * 175), 2));
        }
        if (trust >= trustMultiplier * 300 && !chart.getRow(2).isUnlocked())
        {
            chart.getRow(2).setUnlockedState(true);
            chart.raiseUnlockLevel();
            Bukkit.getPluginManager().callEvent(new TrustGradeRaiseEvent(this, driver, AffinityUtils.trustToGrade(trust / trustMultiplier * 300), 3));
        }
        if (trust >= trustMultiplier * 400 && !chart.getRow(3).isUnlocked())
        {
            chart.getRow(3).setUnlockedState(true);
            chart.raiseUnlockLevel();
            Bukkit.getPluginManager().callEvent(new TrustGradeRaiseEvent(this, driver, AffinityUtils.trustToGrade(trust / trustMultiplier * 400), 4));
        }
        if (trust >= trustMultiplier * 750 && !chart.getRow(4).isUnlocked())
        {
            chart.getRow(4).setUnlockedState(true);
            chart.raiseUnlockLevel();
            Bukkit.getPluginManager().callEvent(new TrustGradeRaiseEvent(this, driver, AffinityUtils.trustToGrade(trust / trustMultiplier * 750), 5));
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

    public void drawAffinityLine()
    {
        PlayerAccount account = BladeData.getAccount(driver);

        Color color = Color.RED;

        switch (account.getAffinity())
        {
            case 0: color = Color.fromRGB(0, 150, 255); break;
            case 1: color = Color.fromRGB(64, 224, 208); break;
            case 2: color = Color.YELLOW; break;
        }

        if (isHumanNPC)
        {
            Particles.drawLine
            (
                ((LivingEntity) entity.getBukkitEntity()).getLocation().add(0, 1.2, 0), 
                driver.getLocation().add(0, 1, 0), 
                0.1, Particle.REDSTONE, 
                new Particle.DustOptions(color, 1)
            );
        } else {
            Particles.drawLine
            (
                ((LivingEntity) entity.getBukkitEntity()).getEyeLocation(), 
                driver.getLocation().add(0, 1, 0), 
                0.1, Particle.REDSTONE, 
                new Particle.DustOptions(color, 1)
            );
        }

        
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
