package com.yukiemeralis.blogspot.blades;

import com.yukiemeralis.blogspot.blades.customblades.PlayerBlade;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerBladeAccount 
{
    Player driver; // The driver this player will act as a blade for

    PlayerBlade blade;

    ItemStack[] inventoryBackup;

    public PlayerBladeAccount(String name, Element element, Role role, WeaponType type)
    {
        blade = new PlayerBlade(name, element, role, type);
        blade.setPlayerBlade(true);
    }

    public void setDriver(Player player)
    {
        this.driver = player;
    }

    public Blade getBladeData()
    {
        return blade;
    }

    public void setInventory(Inventory inv)
    {
        this.inventoryBackup = inv.getContents();
    }

    public ItemStack[] getDriverInventory()
    {
        return this.inventoryBackup;
    }
}
