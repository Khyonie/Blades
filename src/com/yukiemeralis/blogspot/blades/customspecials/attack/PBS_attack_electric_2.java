package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.combat.CombatData;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_electric_2 extends PlayerBladeSpecial 
{
    public PBS_attack_electric_2() 
    {
        super("ShortCircuit", "Short circuit", "Inflict light damage to nearby targets.", Element.ELECTRIC, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        CombatData.getCombatInfo(driver).getCombatants().forEach(enemy -> {
            ((Damageable) enemy).damage(4.0);
        });
    }
}
