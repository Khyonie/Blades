package com.yukiemeralis.blogspot.blades;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PotionItem 
{
    private static ItemStack potion = new ItemStack(Material.POTION);
    private static Random random = new Random();

    static {
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 1, 0), false);
        meta.setDisplayName("§aPotion");
        potion.setItemMeta(meta);
    }

    /**
     * Spawns an HP potion in the world.
     * @param location
     * @param size 0 = 5%, 1 = 10%, 2 = 20%
     * @param spawner
     * @param count
     */
    public static void spawn(Location location, int size, Player spawner, int count)
    {
        for (int i = 0; i < count; i++)
        {
            ItemStack p = potion.clone();

            setPotionData(p, size, spawner);
            spawner.getWorld().dropItemNaturally(makeLocation(spawner).toLocation(spawner.getWorld()), p);
        }
    }

    private static void setPotionData(ItemStack p, int size, Player spawner)
    {
        ItemMeta meta = p.getItemMeta();

        meta.setLore(Arrays.asList(new String[] {size + "", spawner.getDisplayName()}));

        p.setItemMeta(meta);
    }

    private static Vector makeLocation(Player player)
    {
        Vector buffer = player.getLocation().getDirection();

        buffer.setX(-1 + (random.nextDouble()*2));
        buffer.setZ(-1 + (random.nextDouble()*2));

        buffer.setY(0.5);
        

        return buffer;
    }
}
