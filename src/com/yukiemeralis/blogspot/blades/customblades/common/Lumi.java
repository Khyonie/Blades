package com.yukiemeralis.blogspot.blades.customblades.common;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.PotionItem;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_ColdCompress;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_CryogenicRegen;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_WarmthSap;
import com.yukiemeralis.blogspot.blades.combat.CombatData;
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

public class Lumi extends Blade 
{
    public Lumi() 
    {
        super("Lumi", Element.ICE, Role.HEALER, WeaponType.BOW, 1, EntityTypes.WOLF, false);

        chart = AffinityUtils.createAffinityChart(this,
            AffinityUtils.createAffinityRow(new Skill_CryogenicRegen(1), null,                      new Skill_WarmthSap(1), new Skill_ColdCompress(1), null, null, null),
            AffinityUtils.createAffinityRow(new Skill_CryogenicRegen(2), null,                      null,                   new Skill_ColdCompress(2), null, null, null),
            AffinityUtils.createAffinityRow(null,                        new Skill_ColdCompress(3), null,                   null,                      null, null, null),
            AffinityUtils.createAffinityRow(null,                        null,                      new Skill_WarmthSap(2), new Skill_ColdCompress(3), null, null, null),
            AffinityUtils.createAffinityRow(new Skill_CryogenicRegen(3), null,                      null,                   new Skill_ColdCompress(4), null, null, null)
        );
        chart.setOwner(this);

        // Set special data
        setSpecialInfo(0, "Piercing chill", "Spawns two small HP potions.", Element.ICE);
        setSpecialInfo(1, "Glacial numbness", "Heals a medium amount of health.", Element.ICE);
        setSpecialInfo(2, "Permafrost blast", "Slows all nearby targets. +Medium healing", Element.ICE);
        setSpecialInfo(3, "Everlasting winter", "Heavily slows all nearby targets. +Heavy healing", Element.ICE);
    }

    @Override
    public void performSpecial1(Entity target, Player driver) 
    {
        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1));
    
        PotionItem.spawn(driver.getLocation(), 0, driver, 2);

        if (BladeData.getAccount(driver).getParty() != null)
            healParty(driver, BladeData.getAccount(driver).getParty(), 5);
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1));
        ((Damageable) target).damage(2.0);

        BladeData.getAccount(driver).healVirtualHP(8);

        if (BladeData.getAccount(driver).getParty() != null)
            healParty(driver, BladeData.getAccount(driver).getParty(), 8);
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        ((Damageable) target).damage(10.0);

        CombatData.getCombatInfo(driver).getCombatants().forEach(enemy -> {
            ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 3));
        });

        BladeData.getAccount(driver).healVirtualHP(10);

        if (BladeData.getAccount(driver).getParty() != null)
            healParty(driver, BladeData.getAccount(driver).getParty(), 10);
    }

    @Override
    public void performSpecial4(Entity target, Player driver) 
    {
        ((Damageable) target).damage(12.0);

        CombatData.getCombatInfo(driver).getCombatants().forEach(enemy -> {
            ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 3));
        });

        driver.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));
        BladeData.getAccount(driver).healVirtualHP(10);

        if (BladeData.getAccount(driver).getParty() != null)
            healParty(driver, BladeData.getAccount(driver).getParty(), 10);
    }
}
