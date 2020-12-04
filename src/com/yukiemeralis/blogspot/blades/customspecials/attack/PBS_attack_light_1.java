package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.combat.CombatData;
import com.yukiemeralis.blogspot.blades.customspecials.MobTypes;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_light_1 extends PlayerBladeSpecial 
{
    public PBS_attack_light_1() 
    {
        super("SearingLight", "Searing light", "Inflict light damage to nearby enemies. +Nether/End mob damage", Element.LIGHT, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        CombatData.getCombatInfo(driver).getCombatants().forEach(enemy -> {
            if (MobTypes.end.contains(enemy.getType()) || MobTypes.nether.contains(enemy.getType()))
            {
                ((Damageable) target).damage(12.0);
            } else {
                ((Damageable) target).damage(6.0);
            }
        });
    }
}
