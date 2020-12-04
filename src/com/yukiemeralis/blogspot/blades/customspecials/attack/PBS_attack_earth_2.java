package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_earth_2 extends PlayerBladeSpecial 
{
    public PBS_attack_earth_2() 
    {
        super("Eruption", "Eruption", "Spew molten rocks onto the target, setting them ablaze.", Element.EARTH, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        ((Damageable) target).damage(4.0);
        target.setFireTicks(120);
    }
}
