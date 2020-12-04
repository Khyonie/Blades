package com.yukiemeralis.blogspot.blades.customblades.common;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.agate.*;
import com.yukiemeralis.blogspot.blades.combat.CombatData;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Agate extends Blade 
{
    public Agate() 
    {
        super("Agate", Element.EARTH, Role.TANK, WeaponType.AXE, 4);

        chart = AffinityUtils.createAffinityChart(this, 
            AffinityUtils.createAffinityRow(null,                         new Skill_BlastMining(1), null,                   new Skill_SharpPickaxe(1), new Skill_HeliodorPassion(1), new Skill_StarBerylCourage(1), new Skill_RhodochrositeMight(1)),
            AffinityUtils.createAffinityRow(new Skill_MiningExpertise(1), null,                     new Skill_RockSmash(1), null,                      new Skill_HeliodorPassion(2), new Skill_StarBerylCourage(2), new Skill_RhodochrositeMight(2)),
            AffinityUtils.createAffinityRow(null,                         null,                     null,                   null,                      new Skill_HeliodorPassion(3), new Skill_StarBerylCourage(3), new Skill_RhodochrositeMight(3)),
            AffinityUtils.createAffinityRow(null,                         null,                     null,                   null,                      new Skill_HeliodorPassion(4), new Skill_StarBerylCourage(4), new Skill_RhodochrositeMight(4)),
            AffinityUtils.createAffinityRow(new Skill_MiningExpertise(2), null,                     null,                   null,                      new Skill_HeliodorPassion(5), new Skill_StarBerylCourage(5), new Skill_RhodochrositeMight(5))
        );
        chart.setOwner(this);

        // Set special data
        setSpecialInfo(0, "Heliodor passion", "Inflicts light damage.", Element.EARTH);
        setSpecialInfo(1, "Star beryl courage", "Inflicts medium damage. +Aggro draw", Element.EARTH);
        setSpecialInfo(2, "Rhodochrosite might", "Launch the enemy into the air.", Element.EARTH);
        setSpecialInfo(3, "Invincible moldavite", "Heavy damage and launches nearby targets.", Element.EARTH);

        base_blockrate = 30;
        setBlockRate(30);
        setBaseCritRate(10);
    }

    @Override
    public void performSpecial1(Entity target, Player driver) 
    {
        chart.getHighestSkillInColumn(4).runEffect(target, driver);

        ((Damageable) target).damage(6.0 * chart.getHighestSkillInColumn(4).getModifierValue());
        target.setVelocity(new Vector(0, -5, 0));
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        if (target instanceof Mob)
            ((Mob) target).setTarget(driver);

        chart.getHighestSkillInColumn(5).runEffect(target, driver);
        ((Damageable) target).damage(10.0 * chart.getHighestSkillInColumn(5).getModifierValue());
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        chart.getHighestSkillInColumn(6).runEffect(target, driver);
        ((Damageable) target).damage(6.0 * chart.getHighestSkillInColumn(6).getModifierValue());

        target.setVelocity(new Vector(0, 1.75, 0));
    }

    @Override
    public void performSpecial4(Entity target, Player driver) 
    {
        ((Damageable) target).damage(15.0);

        CombatData.getCombatInfo(driver).getCombatants().forEach(enemy -> {
            Vector direction = enemy.getLocation().toVector().subtract(driver.getLocation().toVector());
            enemy.setVelocity(direction);
        });
        
    }
    
}
