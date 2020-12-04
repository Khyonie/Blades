package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.MobTypes;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_dark_1 extends PlayerBladeSpecial 
{
    public PBS_attack_dark_1() 
    {
        super("Hellfire", "Hellfire", "Inflicts light damage and sets target ablaze. +Passive mob damage", Element.DARK, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        target.setFireTicks(60);

        if (MobTypes.passive.contains(target.getType()))
        {
            ((Damageable) target).damage(10.0);
        } else {
            ((Damageable) target).damage(6.0);
        }
    }
}
