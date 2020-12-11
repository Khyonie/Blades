package com.yukiemeralis.blogspot.blades.guis;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.utils.TextUtils;

import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class EnemyHPBar 
{
    public static void show(Player target, Entity entity)
    {
        double max;
        double current;
        long percent;

        // Get the percent of the entity's HP
        if (entity instanceof Player) {
            max = BladeData.getAccount((Player) entity).getMaxVirtualHP();
            current = BladeData.getAccount((Player) entity).getVirtualHP();
            percent = Math.round(10*(current/max));
        } else {
            max = ((Attributable) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            current = ((LivingEntity) entity).getHealth();
            percent = Math.round(10*(current/max)); // Multiply x10 so we get a value from 1-10
        }

        // Build the HP bar section
        StringBuilder builder = new StringBuilder("§f" + Math.round(current) + "/" + Math.round(max) + " §7[" + percentToColor(percent));
        
        for (int i = 0; i < 10; i++)
        {
            if (i <= percent)
                builder.append("█");
            else builder.append("§8░");
        }

        builder.append("§7]");

        // Append the bar to a new string with the entity's name
        String message = "§a" + TextUtils.formatNicely(entity.getType().name()) + " §3| " + builder.toString();

        // Send the bar to the target player
        TextUtils.sendActionBar(target, message);
    }

    /**
     * Same as {@link EnemyHPBar#show(Player, Entity)} except it forces the "displayed" current health to be a certain value
     * @param forced_current
     */
    public static void show(int forced_current, Player target, Entity entity)
    {
        double max;
        double current;
        long percent;

        // Get the percent of the entity's HP
        if (entity instanceof Player) {
            max = BladeData.getAccount((Player) entity).getMaxVirtualHP();
            current = forced_current;
            percent = Math.round(10*(current/max));
        } else {
            max = ((Attributable) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            current = forced_current;
            percent = Math.round(10*(current/max)); // Multiply x10 so we get a value from 1-10
        }

        // Build the HP bar section
        StringBuilder builder = new StringBuilder("§f" + Math.round(current) + "/" + Math.round(max) + " §7[" + percentToColor(percent));
        
        for (int i = 0; i < 10; i++)
        {
            if (i <= percent)
                builder.append("█");
            else builder.append("§8░");
        }

        builder.append("§7]");

        // Append the bar to a new string with the entity's name
        String message = "§a" + TextUtils.formatNicely(entity.getType().name()) + " §3| " + builder.toString();

        // Send the bar to the target player
        TextUtils.sendActionBar(target, message);
    }

    private static String percentToColor(long percent)
    {
        String buffer;

        // Green for 80-100, yellow for 60-79, orange for 40-59, red for 20-39, dark red for 0-19
        if (percent >= 8)
        {
            buffer = "§a";
        } else if (percent < 8 && percent >= 6) {
            buffer = "§e";
        } else if (percent < 6 && percent >= 4) {
            buffer = "§6";
        } else if (percent < 4 && percent >= 2) {
            buffer = "§c";
        } else {
            buffer = "§4";
        }

        return buffer;
    }
}
