package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.MobTypes;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_light_2 extends PlayerBladeSpecial 
{
    public PBS_attack_light_2() 
    {
        super("Sniper", "Sniper shot", "Inflicts large damage on target. +Nether/End mob damage", Element.LIGHT, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (MobTypes.end.contains(target.getType()) || MobTypes.nether.contains(target.getType()))
            {
                ((Damageable) target).damage(30.0);
            } else {
                ((Damageable) target).damage(20.0);
            }
    }
}
