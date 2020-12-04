package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PBS_attack_wind_1 extends PlayerBladeSpecial 
{
    public PBS_attack_wind_1() 
    {
        super("Turbulence", "Turbulence", "Launches the target high into the air.", Element.WIND, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        target.setVelocity(new Vector(0, 3, 0));
    }
}
