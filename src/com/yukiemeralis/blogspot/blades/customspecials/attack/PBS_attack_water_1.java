package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PBS_attack_water_1 extends PlayerBladeSpecial 
{
    public PBS_attack_water_1() 
    {
        super("Riptide", "Riptide", "Pushes the target back and inflicts slowness.", Element.WATER, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        Vector direction = target.getLocation().toVector().subtract(driver.getLocation().toVector());
        target.setVelocity(direction);

        ((Damageable) target).damage(6.0);
    }
    
}
