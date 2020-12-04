package com.yukiemeralis.blogspot.blades.customblades.common;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.Main;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_AffinityBoost;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_Conductor;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_StaticField;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_SwiftStep;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Lionel extends Blade 
{
    public Lionel() 
    {
        super("Lionel", Element.ELECTRIC, Role.ATTACK, WeaponType.TRIDENT, 1);

        chart = AffinityUtils.createAffinityChart(this, 
            AffinityUtils.createAffinityRow(new Skill_SwiftStep(1), new Skill_StaticField(1), null,                   new Skill_AffinityBoost(1), null, null, null),
            AffinityUtils.createAffinityRow(null,                   null,                     null,                   new Skill_AffinityBoost(2), null, null, null),
            AffinityUtils.createAffinityRow(null,                   null,                     new Skill_Conductor(1), new Skill_AffinityBoost(3), null, null, null),
            AffinityUtils.createAffinityRow(null,                   new Skill_StaticField(2), null,                   new Skill_AffinityBoost(4), null, null, null),
            AffinityUtils.createAffinityRow(new Skill_SwiftStep(2), new Skill_StaticField(3), null,                   new Skill_AffinityBoost(5), null, null, null)
        );
        chart.setOwner(this);

        // Set special data
        setSpecialInfo(0, "Dynamic volt", "Release built-up static electricity.", Element.ELECTRIC);
        setSpecialInfo(1, "Static overdrive", "Shock the enemy, slowing their movements.", Element.ELECTRIC);
        setSpecialInfo(2, "Voltaic spirit", "Shock the enemy and increase movement speed.", Element.ELECTRIC);
        setSpecialInfo(3, "Electro-magnetic blast", "Launch the target and electrocute them.", Element.ELECTRIC);
    }

    @Override
    public void performSpecial1(Entity target, Player driver) 
    {
        target.getWorld().strikeLightning(target.getLocation());
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        target.getWorld().strikeLightning(target.getLocation());
        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1));
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        target.getWorld().strikeLightning(target.getLocation());
        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 1));
        driver.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
    }

    @Override
    public void performSpecial4(Entity target, Player driver) 
    {
        target.setVelocity(new Vector(0, 1.5, 0));

        new BukkitRunnable(){
            @Override
            public void run() {
                target.getWorld().strikeLightning(target.getLocation());
            }
        }.runTaskLater(Main.getInstance(), 10L);
    }
    

}
