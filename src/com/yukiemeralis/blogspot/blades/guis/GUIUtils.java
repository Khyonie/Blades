package com.yukiemeralis.blogspot.blades.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUIUtils 
{
    public static void paintBlack(Inventory inv)
    {
        for (int i = 0; i < inv.getSize(); i++)
        {
            inv.setItem(i, black);
        }
    }

    private static ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

    static {
        ItemMeta meta = black.getItemMeta();

        meta.setDisplayName(" ");
        black.setItemMeta(meta);
    }
}
