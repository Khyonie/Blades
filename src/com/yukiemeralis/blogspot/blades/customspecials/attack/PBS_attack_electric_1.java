package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_electric_1 extends PlayerBladeSpecial 
{
    public PBS_attack_electric_1() 
    {
        super("Spark", "Spark", "Strike the target with lightning.", Element.ELECTRIC, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        target.getWorld().strikeLightning(target.getLocation());
    }
}
