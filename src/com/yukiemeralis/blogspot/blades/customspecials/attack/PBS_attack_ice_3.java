package com.yukiemeralis.blogspot.blades.customspecials.attack;

import java.util.List;

import com.yukiemeralis.blogspot.blades.combat.CombatData;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_ice_3 extends PlayerBladeSpecial 
{
    public PBS_attack_ice_3() 
    {
        super("Blizzard", "Blizzard", "Inflicts medium damage to nearby targets.", Element.ICE, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        List<Entity> targets = CombatData.getCombatInfo(driver).getCombatants();

        targets.forEach(enemy -> {
            ((Damageable) enemy).damage(8.0);
        });
    }
}
