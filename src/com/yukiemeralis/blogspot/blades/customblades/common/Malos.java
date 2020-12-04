package com.yukiemeralis.blogspot.blades.customblades.common;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.malos.*;
import com.yukiemeralis.blogspot.blades.combat.CombatData;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Malos extends Blade 
{
    public Malos() 
    {
        super("Malos", Element.DARK, Role.ATTACK, WeaponType.SWORD, 4);

        chart = AffinityUtils.createAffinityChart(this, 
            AffinityUtils.createAffinityRow(null, null, null, null, new Skill_MonadoEnchant(1), new Skill_MonadoCyclone(1), new Skill_MonadoEater(1)),
            AffinityUtils.createAffinityRow(null, null, null, null, new Skill_MonadoEnchant(2), new Skill_MonadoCyclone(2), new Skill_MonadoEater(2)),
            AffinityUtils.createAffinityRow(null, null, null, null, new Skill_MonadoEnchant(3), new Skill_MonadoCyclone(3), new Skill_MonadoEater(3)),
            AffinityUtils.createAffinityRow(null, null, null, null, new Skill_MonadoEnchant(4), new Skill_MonadoCyclone(4), new Skill_MonadoEater(4)),
            AffinityUtils.createAffinityRow(null, null, null, null, new Skill_MonadoEnchant(5), new Skill_MonadoCyclone(5), new Skill_MonadoEater(5))
        );
        chart.setOwner(this);

        // Set special info
        setSpecialInfo(0, "Monado Enchant", "Temporarily increases damage dealt.", Element.DARK);
        setSpecialInfo(1, "Monado Cyclone", "Inflicts medium damage and knockback on nearby enemies.", Element.DARK);
        setSpecialInfo(2, "Monado Eater", "Reduces blade affinity if enemy is a driver. +Smash", Element.DARK);
        setSpecialInfo(3, "Monado Buster", "Inflicts extreme damage on target.", Element.DARK);
    }

    @Override
    public void performSpecial1(Entity target, Player driver) 
    {
        int level = (int) Math.round(chart.getHighestSkillInColumn(4).getModifierValue());
        driver.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*20, 0 + level));
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        CombatData.getCombatInfo(driver).getCombatants().forEach(enemy -> {
            Vector direction = target.getLocation().toVector().subtract(driver.getLocation().toVector());
            target.setVelocity(direction);

            ((Damageable) target).damage(8 * chart.getHighestSkillInColumn(5).getModifierValue());
        });
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        ((Damageable) target).damage(8);
        target.setVelocity(new Vector(0, -5, 0));

        if (!(target instanceof Player))
            return;
        if (BladeData.getAccount((Player) target).getCurrentBlade() == null)
            return;
        
        if (CombatData.getCombatInfo((Player) target) == null)
            return;

        chart.getHighestSkillInColumn(6).runEffect(target, driver);
        BladeData.getAccount((Player) target).resetAffinity();
    }

    @Override
    public void performSpecial4(Entity target, Player driver) 
    {
        ((Damageable) target).damage(20);
    }
    
}
