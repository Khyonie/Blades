package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.combat.CombatData;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_earth_3 extends PlayerBladeSpecial 
{
    public PBS_attack_earth_3() 
    {
        super("Earthquake", "Earthquake", "Heavy damage to nearby targets.", Element.EARTH, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        CombatData.getCombatInfo(driver).getCombatants().forEach(enemy -> {
            ((Damageable) enemy).damage(10.0);
        });
    }
}
