package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_fire_3 extends PlayerBladeSpecial 
{
    public PBS_attack_fire_3() 
    {
        super("BlazingEnd", "Blazing end", "Inflicts heavy damage to the target.", Element.FIRE, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        ((Damageable) target).damage(8.0);
    }
}
