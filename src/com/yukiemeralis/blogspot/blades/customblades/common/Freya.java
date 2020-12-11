package com.yukiemeralis.blogspot.blades.customblades.common;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.affinity.skills.*;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.freya.Skill_BlazingHearth;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.freya.Skill_RadiantCall;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.freya.Skill_RagingEmber;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_16_R3.EntityTypes;

public class Freya extends Blade 
{
    public Freya() 
    {
        super("Freya", Element.FIRE, Role.ATTACK, WeaponType.SWORD, 1, EntityTypes.WITHER_SKELETON, false);

        chart = AffinityUtils.createAffinityChart(this, 
            AffinityUtils.createAffinityRow(null,                    new Skill_BlazingSpirit(1), new Skill_BurningEdge(1), new Skill_AffinityBoost(1), new Skill_RagingEmber(1), new Skill_RadiantCall(1), null),
            AffinityUtils.createAffinityRow(new Skill_SunnyMiner(1), new Skill_BlazingSpirit(2), null,                     null,                       null,                     new Skill_RadiantCall(2), null),
            AffinityUtils.createAffinityRow(null,                    new Skill_BlazingSpirit(3), null,                     null,                       new Skill_RagingEmber(2), new Skill_RadiantCall(3), new Skill_BlazingHearth(2)),
            AffinityUtils.createAffinityRow(null,                    new Skill_BlazingSpirit(4), null,                     new Skill_AffinityBoost(2), null,                     new Skill_RadiantCall(4), null),
            AffinityUtils.createAffinityRow(new Skill_SunnyMiner(2), new Skill_BlazingSpirit(5), null,                     null,                       new Skill_RagingEmber(3), new Skill_RadiantCall(5), new Skill_BlazingHearth(4))

        );
        chart.setOwner(this);

        // Set special data
        setSpecialInfo(0, "Raging ember", "Inflicts light damage and sets target ablaze.", Element.FIRE);
        setSpecialInfo(1, "Radiant call", "Inflicts medium damage and sets target ablaze.", Element.FIRE);
        setSpecialInfo(2, "Blazing hearth", "Inflicts heavy damage and sets target ablaze.", Element.FIRE);
        setSpecialInfo(3, "Devouring pyre", "Inflicts heavy damage and applies regeneration.", Element.FIRE);
    }

    @Override
    public void performSpecial1(Entity target, Player driver) 
    {
        target.setFireTicks(60);
        ((Damageable) target).damage(3.0 * (chart.getHighestSkillInColumn(4).getModifierValue()));
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        target.setFireTicks(120 + (20 * (int) Math.round(chart.getHighestSkillInColumn(5).getModifierValue())));
        ((Damageable) target).damage(3.0);
        driver.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 1));
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        target.setFireTicks(180);
        ((Damageable) target).damage(7.0 * chart.getHighestSkillInColumn(6).getModifierValue());
        driver.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 1));
    }

    @Override
    public void performSpecial4(Entity target, Player driver) 
    {
        target.setFireTicks(180);
        ((Damageable) target).damage(7.0);
        driver.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 1));
        driver.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));

        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 15*20, 1));
    }
}
