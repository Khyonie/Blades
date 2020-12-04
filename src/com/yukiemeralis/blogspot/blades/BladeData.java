package com.yukiemeralis.blogspot.blades;

import java.util.HashMap;

import com.yukiemeralis.blogspot.blades.enums.WeaponType;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BladeData 
{
    public static HashMap<String, PlayerAccount> accounts = new HashMap<>();    

    public static PlayerAccount getAccount(Player player)
    {
        return accounts.get(player.getUniqueId().toString());
    }

    // Define items
    private static ItemStack sword, axe, trident, bow, crossbow;
    private static HashMap<WeaponType, ItemStack> weapons = new HashMap<>();

    static 
    {
        // Definitions
        sword = new ItemStack(Material.DIAMOND_SWORD);
        crossbow = new ItemStack(Material.CROSSBOW);
        axe = new ItemStack(Material.DIAMOND_AXE);
        trident = new ItemStack(Material.TRIDENT);
        bow = new ItemStack(Material.BOW);

        // Unbreakable
        initialize(crossbow);
        initialize(trident);
        initialize(sword);
        initialize(axe);
        initialize(bow);

        // Enchants
        crossbow.addUnsafeEnchantment(Enchantment.QUICK_CHARGE, 3);
        bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        trident.addUnsafeEnchantment(Enchantment.LOYALTY, 3);

        // Finalize
        weapons.put(WeaponType.SWORD, sword);
        weapons.put(WeaponType.AXE, axe);
        weapons.put(WeaponType.CROSSBOW, crossbow);
        weapons.put(WeaponType.BOW, bow);
        weapons.put(WeaponType.TRIDENT, trident);
    }

    public static ItemStack getBladeWeapon(WeaponType type)
    {
        return weapons.get(type).clone();
    }

    public static boolean isBladeWeapon(ItemStack item)
    {
        return (item.getItemMeta().getDisplayName().equals("§eBlade weapon"));
    }

    public static void removeBladeWeapon(Inventory inv)
    {
        weapons.forEach((key, value) -> {
            inv.remove(value);
        });
    }

    public static void addBladeWeapon(Blade blade)
    {
        try {
            blade.getDriver().getInventory().addItem(getBladeWeapon(blade.getWeaponType()));
        } catch (NullPointerException error) {
            System.out.println("NPE at line 76:BladeData.java");
        }
    }

    //
    // Helper methods
    //
    private static void initialize(ItemStack item)
    {
        ItemMeta meta = item.getItemMeta();

        meta.setUnbreakable(true);
        meta.setDisplayName("§r§eBlade weapon");

        item.setItemMeta(meta);

        item.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
    }
}
