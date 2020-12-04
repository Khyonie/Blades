package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_water_3 extends PlayerBladeSpecial 
{
    public PBS_attack_water_3() 
    {
        super("Tsunami", "Tsunami", "Inflicts light damage.", Element.WATER, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        ((Damageable) target).damage(8.0);
    }
    
}
