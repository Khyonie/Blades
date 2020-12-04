package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_earth_1 extends PlayerBladeSpecial 
{
    public PBS_attack_earth_1() 
    {
        super("Faultline", "Faultline", "Shatter the ground at the target's location.", Element.EARTH, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        target.getWorld().createExplosion(target.getLocation(), 2);
    }
}
