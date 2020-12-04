package com.yukiemeralis.blogspot.blades.customblades.common;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.combat.CombatData;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Lux extends Blade {

    public Lux() 
    {
        super("Lux", Element.LIGHT, Role.TANK, WeaponType.AXE, 1);

        chart = AffinityUtils.multi_color.clone();
        chart.setOwner(this);

        // Set special data
        setSpecialInfo(0, "Piercing glare", "Inflicts light damage. +Aggro draw", Element.LIGHT);
        setSpecialInfo(1, "Bright limelight", "Inflicts medium damage. +Aggro draw", Element.LIGHT);
        setSpecialInfo(2, "Illusory shade", "Inflicts medium damage. +Target weakness, aggro draw", Element.LIGHT);
        setSpecialInfo(3, "Radiance diversion", "Forces all nearby enemies to target you. +Heavy damage", Element.LIGHT);

        base_blockrate = 30;
    }

    @Override
    public void performSpecial1(Entity target, Player driver) 
    {
        if (target instanceof Mob)
            ((Mob) target).setTarget(driver);

        ((Damageable) target).damage(4.0);
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        if (target instanceof Mob)
            ((Mob) target).setTarget(driver);

        ((Damageable) target).damage(6.0);
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        if (target instanceof Mob)
            ((Mob) target).setTarget(driver);

        ((Damageable) target).damage(8.0);
        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 160, 1));
    }

    @Override
    public void performSpecial4(Entity target, Player driver) 
    {
        CombatData.getCombatInfo(driver).getCombatants().forEach(enemy -> {
            if (enemy instanceof Mob)
                ((Mob) enemy).setTarget(driver);
        });

        ((Damageable) target).damage(8.0);
        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 160, 1));
    }
    
}
