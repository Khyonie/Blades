package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.MobTypes;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

public class PBS_attack_dark_2 extends PlayerBladeSpecial 
{
    public PBS_attack_dark_2() 
    {
        super("DeathScythe", "Death scythe", "Inflicts medium damage on target. +Passive mob damage", Element.DARK, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (MobTypes.passive.contains(target.getType()))
        {
            ((Damageable) target).damage(15.0);
        } else {
            ((Damageable) target).damage(10.0);
        }
    }
}
