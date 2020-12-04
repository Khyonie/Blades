package com.yukiemeralis.blogspot.blades.guis;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.PlayerAccount;
import com.yukiemeralis.blogspot.blades.utils.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EngageGUI implements Listener
{
    private final Inventory inv;

    public EngageGUI()
    {
        inv = Bukkit.createInventory(null, 27, "Engage blades");
    }

    public void init(Player player)
    {
        GUIUtils.paintBlack(inv);

        // Get player account
        PlayerAccount account = BladeData.getAccount(player);

        ItemStack first = new ItemStack(Material.AIR);
        ItemStack second = new ItemStack(Material.AIR);
        ItemStack third = new ItemStack(Material.AIR);

        try {
            first = account.getCurrentBlade().getIcon();
        } catch (NullPointerException error) {
        } catch (IndexOutOfBoundsException error) {}
        
        try {
            second = account.getEngagedBlades().get(1).getIcon();
        } catch (NullPointerException error) {
        } catch (IndexOutOfBoundsException error) {}

        try {
            third = account.getEngagedBlades().get(2).getIcon();
        } catch (NullPointerException error) {
        } catch (IndexOutOfBoundsException error) {}

        // Engaged blades
        inv.setItem(0, first);
        inv.setItem(9, second);
        inv.setItem(18, third);

        int data_index = 0;

        Blade buffer;

        for (int i = 0; i < 27; i++)
        {
            if (i % 9 >= 2)
            {
                try {
                    buffer = account.getBladeStock().get(data_index);
                    inv.setItem(i, buffer.getIcon());
                } catch (IndexOutOfBoundsException error) {
                    inv.setItem(i, new ItemStack(Material.AIR));
                } catch (NullPointerException error) {
                    inv.setItem(i, new ItemStack(Material.AIR));
                }

                data_index++;
            }
        }
    }

    @EventHandler
    public void onInteract(InventoryClickEvent event)
    {
        if (event.getClickedInventory() == null)
            return;

        if (!event.getView().getTitle().equals("Engage blades"))
            return;

        event.setCancelled(true);

        int slot = event.getRawSlot();
        PlayerAccount account = BladeData.getAccount((Player) event.getWhoClicked());

        if (slot == -1)
            return;

        if (event.getClickedInventory().getItem(slot).getType().equals(null))
            return;

        if (event.getClickedInventory().getItem(slot).getType().equals(Material.AIR) || event.getClickedInventory().getItem(slot) == null)
            return;

        if (slot % 9 == 0)
        {
            // Decide how to move around engaged blades
            Blade firstPosition, secondPosition, thirdPosition;

            switch (slot / 9)
            {
                // Do nothing
                case 0: break;

                // 0 becomes 1, 1 becomes 0
                case 1: 
                    firstPosition = account.getCurrentBlade();
                    secondPosition = account.getEngagedBlades().get(1);

                    account.getEngagedBlades().set(0, secondPosition);
                    account.getEngagedBlades().set(1, firstPosition);
                
                    break;

                // 2 becomes 0, 0 becomes 1, 1 becomes 2
                case 2: 
                    firstPosition = account.getCurrentBlade();
                    secondPosition = account.getEngagedBlades().get(1);
                    thirdPosition = account.getEngagedBlades().get(2);

                    account.getEngagedBlades().set(0, thirdPosition);
                    account.getEngagedBlades().set(1, firstPosition);
                    account.getEngagedBlades().set(2, secondPosition);

                    break;
            }

            account.setEquippedBlade(account.getEngagedBlades().get(0));

            reloadInventory((Player) event.getWhoClicked());
            return;
        } else if (slot % 9 == 1) {
            event.setCancelled(true);
        } else if (slot % 9 > 1) {
            // For now we're being lazy and making this work like so:
            // Clicking on a blade in stock immediately engages that blade as the player's current blade.
            // The currently engaged blade gets shifted down to slot 1, and slot 1 to slot 2
            // A blade in slot 3 gets put back in stock.

            ItemStack item = event.getClickedInventory().getItem(slot);

            Blade blade = null;

            // Find the blade in stock
            for (int i = 0; i < account.getBladeStock().size(); i++)
            {
                blade = account.getBladeStock().get(i);

                try {
                    if (blade.getName().equals(nameFromItem(item)))
                        break;
                } catch (NullPointerException error) {}
            }

            if (blade == null)
            {
                throw new NullPointerException("Could not find requested blade in stock.");
            }

            // Shift downwards
            try {
                account.getBladeStock().add(account.getEngagedBlades().get(2));
                account.getEngagedBlades().remove(2);
            } catch (IndexOutOfBoundsException error) {}

            try {
                account.getEngagedBlades().set(2, account.getEngagedBlades().get(1));
            } catch (IndexOutOfBoundsException error) {
                account.getEngagedBlades().add(account.getEngagedBlades().get(1)); // This'll create a new slot for blade 2
            }
                
            try {
                account.getEngagedBlades().set(1, account.getEngagedBlades().get(0));
            } catch (IndexOutOfBoundsException error) {
                account.getEngagedBlades().add(account.getEngagedBlades().get(0)); // This'll create a new slot for blade 1
            }

            account.setEquippedBlade(blade);
            account.getEngagedBlades().set(0, blade);
            account.getBladeStock().remove(blade);

            account.getBladeStock().removeIf(blade_tmp -> blade_tmp == null);

            reloadInventory((Player) event.getWhoClicked());
        }
    }

    // Helper methods

    public void openInventory(Player player)
    {
        player.openInventory(inv);
    }

    public void reloadInventory(Player player)
    {
        EngageGUI buffer = new EngageGUI();
        buffer.init(player);

        player.openInventory(buffer.getInventory());
    }

    public Inventory getInventory()
    {
        return this.inv;
    }

    private String nameFromItem(ItemStack in)
    {
        return TextUtils.trimFormatting(in.getItemMeta().getDisplayName());
    }
}
