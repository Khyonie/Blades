package com.yukiemeralis.blogspot.blades.customblades;

import java.util.Arrays;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.customspecials.SpecialRegister;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;
import com.yukiemeralis.blogspot.blades.utils.TextUtils;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerBlade extends Blade 
{
    PlayerBladeSpecial level1 = (PlayerBladeSpecial) SpecialRegister.getSpecial("Flare").clone();
    PlayerBladeSpecial level2 = (PlayerBladeSpecial) SpecialRegister.getSpecial("Hellfire").clone();
    PlayerBladeSpecial level3 = (PlayerBladeSpecial) SpecialRegister.getSpecial("HeatUpdraft").clone();
    PlayerBladeSpecial level4 = (PlayerBladeSpecial) SpecialRegister.getSpecial("BlazingEnd").clone();

    // In case a player themselves wants to act as a blade for someone
    public PlayerBlade(String name, Element element, Role role, WeaponType type) 
    {
        super(name, element, role, type, 5);

        chart = AffinityUtils.blank_chart.clone();
    }

    @Override
    public void performSpecial1(Entity target, Player driver) 
    {
        level1.runEffect(target, driver);
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        level2.runEffect(target, driver);
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        level3.runEffect(target, driver);
    }

    @Override
    public void performSpecial4(Entity target, Player driver) 
    {
        level4.runEffect(target, driver);
    }
    
    public void setBladeSpecial(int slot, PlayerBladeSpecial special)
    {
        switch (slot)
        {
            case 1: this.level1 = special; break;
            case 2: this.level2 = special; break;
            case 3: this.level3 = special; break;
            case 4: this.level4 = special; break;
            default: throw new IllegalArgumentException("Expected number between 1-4, got " + slot);
        }
    }

    @Override
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
            "§a-§e-§6-§c=§7[ §fPlayer blade specials §7]§c=§6-§e-§a-",
            "§fSpecial I: " + elementToColor(level1.getElement()) +  level1.getDisplayName(),
            "§7- " + level1.getDescription(),
            "§fSpecial II: " + elementToColor(level2.getElement()) +  level2.getDisplayName(),
            "§7- " + level2.getDescription(),
            "§fSpecial III: " + elementToColor(level3.getElement()) +  level3.getDisplayName(),
            "§7- " + level3.getDescription(),
            "§fSpecial IV: " + elementToColor(level4.getElement()) +  level4.getDisplayName(),
            "§7- " + level4.getDescription()
        }));

        item.setItemMeta(meta);

        return item;
    }
}
