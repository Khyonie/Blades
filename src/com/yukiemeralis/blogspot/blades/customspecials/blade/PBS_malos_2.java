package com.yukiemeralis.blogspot.blades.customspecials.blade;

import com.yukiemeralis.blogspot.blades.combat.CombatData;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PBS_malos_2 extends PlayerBladeSpecial 
{
    public PBS_malos_2() 
    {
        super("MonadoCyclone", "Monado Cyclone", "Inflicts medium damage and knockback on nearby enemies.", Element.DARK, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        CombatData.getCombatInfo(driver).getCombatants().forEach(enemy -> {
            Vector direction = target.getLocation().toVector().subtract(driver.getLocation().toVector());
            target.setVelocity(direction);

            ((Damageable) target).damage(8);
        });
    }
    
}
