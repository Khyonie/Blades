package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_fire_1 extends PlayerBladeSpecial 
{
    public PBS_attack_fire_1() 
    {
        super("Flare", "Flare", "Inflicts light damage and sets the target alight.", Element.FIRE, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        target.setFireTicks(120);
        ((Damageable) target).damage(3.0);
    }

    /**
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_ extends PlayerBladeSpecial 
{
    public PBS_attack_() 
    {
        super("internal", "display", "desc", Element.e, Role.r);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        
    }
}
     */
}
