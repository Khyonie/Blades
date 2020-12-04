package com.yukiemeralis.blogspot.blades.combat;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CombatData 
{
    private static HashMap<Player, CombatInfo> active_fights = new HashMap<>();

    public static void beginCombat(Player player, Entity target)
    {
        CombatInfo info = new CombatInfo(player, target);

        active_fights.put(player, info);
        info.resetTimer(); // Begin the 45 second timer
    }

    public static boolean isInCombat(Player player)
    {
        if (active_fights.containsKey(player))
            return true;
        return false;
    }

    public static CombatInfo getCombatInfo(Player player)
    {
        return active_fights.get(player);
    }

    public static void removeCombatInfo(Player player)
    {
        active_fights.remove(player);
    }
}
