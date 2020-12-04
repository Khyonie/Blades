package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PBS_attack_fire_2 extends PlayerBladeSpecial 
{
    public PBS_attack_fire_2() 
    {
        super("HeatUpdraft", "Heat updraft", "Create a firey vortex that launches the target.", Element.FIRE, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        target.setVelocity(new Vector(0, 1, 0));
        target.setFireTicks(60);
    }
}
