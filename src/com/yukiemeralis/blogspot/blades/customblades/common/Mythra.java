package com.yukiemeralis.blogspot.blades.customblades.common;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.Main;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_BlazingSpirit;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_CriticalAmp;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra.Skill_Glint;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra.Skill_LightningBuster;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra.Skill_LightspeedFlurry;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra.Skill_PhotonEdge;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra.Skill_RayOfPunishment;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;
import com.yukiemeralis.blogspot.blades.utils.LocationUtils;
import com.yukiemeralis.blogspot.blades.utils.Particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_16_R3.EntityTypes;

public class Mythra extends Blade 
{
    public Mythra() 
    {
        super("Mythra", Element.LIGHT, Role.ATTACK, WeaponType.SWORD, 4, EntityTypes.ZOMBIE, true);

        chart = AffinityUtils.createAffinityChart(this, 
            AffinityUtils.createAffinityRow(new Skill_CriticalAmp(1), new Skill_BlazingSpirit(1), new Skill_LightspeedFlurry(1), new Skill_Glint(1), new Skill_RayOfPunishment(1), new Skill_PhotonEdge(1), new Skill_LightningBuster(1)),
            AffinityUtils.createAffinityRow(new Skill_CriticalAmp(2), new Skill_BlazingSpirit(2), new Skill_LightspeedFlurry(2), new Skill_Glint(2), new Skill_RayOfPunishment(2), new Skill_PhotonEdge(2), new Skill_LightningBuster(2)),
            AffinityUtils.createAffinityRow(new Skill_CriticalAmp(3), new Skill_BlazingSpirit(3), new Skill_LightspeedFlurry(3), new Skill_Glint(3), new Skill_RayOfPunishment(3), new Skill_PhotonEdge(3), new Skill_LightningBuster(3)),
            AffinityUtils.createAffinityRow(new Skill_CriticalAmp(4), new Skill_BlazingSpirit(4), new Skill_LightspeedFlurry(4), new Skill_Glint(4), new Skill_RayOfPunishment(4), new Skill_PhotonEdge(4), new Skill_LightningBuster(4)),
            AffinityUtils.createAffinityRow(new Skill_CriticalAmp(5), new Skill_BlazingSpirit(5), new Skill_LightspeedFlurry(5), new Skill_Glint(5), new Skill_RayOfPunishment(5), new Skill_PhotonEdge(5), new Skill_LightningBuster(5))
        );

        chart.setOwner(this);

        // Set special data
        setSpecialInfo(0, "Ray of punishment", "Inflicts medium damage.", Element.LIGHT);
        setSpecialInfo(1, "Photon edge", "Inflicts light damage multiple times. +Smash", Element.LIGHT);
        setSpecialInfo(2, "Lightning buster", "Inflicts heavy damage.", Element.LIGHT);
        setSpecialInfo(3, "Sacred arrow", "Inflicts medium damage multiple times.", Element.LIGHT);

        // Set other blade data
        base_critrate = 15;
    }

    @Override
    public void performSpecial1(Entity target, Player driver) 
    {
        chart.getHighestSkillInColumn(4).runEffect(target, driver);

        ((Damageable) target).damage(6.0 * chart.getHighestSkillInColumn(4).getModifierValue());

        // Target nearby. No exceptions
        target.getNearbyEntities(3, 3, 3).forEach(enemy -> {
            if (enemy instanceof LivingEntity)
            {
                ((LivingEntity) enemy).damage(6.0 * chart.getHighestSkillInColumn(4).getModifierValue());
            }
        });

        Particles.drawSphere(
            target.getLocation().subtract(0, 0.5, 0), 3, 30, 30, 
            Particle.REDSTONE, 
            new Particle.DustOptions
            (
                Color.YELLOW.setGreen(250).setBlue(184), 
                1
            )
        );
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        chart.getHighestSkillInColumn(5).runEffect(target, driver);

        for (int i = 0; i < 7; i++)
        {
            new BukkitRunnable(){
                @Override
                public void run()
                {
                    ((Damageable) target).damage(1.0 * chart.getHighestSkillInColumn(5).getModifierValue());
                }
            }.runTaskLater(Main.getInstance(), (i * 5) + 5);
        }
        
        target.setVelocity(new Vector(0, -5, 0));

        Particles.drawSphere(
            target.getLocation().subtract(0, 0.5, 0), 5, 30, 30, 
            Particle.REDSTONE, 
            new Particle.DustOptions
            (
                Color.YELLOW.setGreen(250).setBlue(184), 
                1
            )
        );
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        chart.getHighestSkillInColumn(6).runEffect(target, driver);

        ((Damageable) target).damage(14.0 * chart.getHighestSkillInColumn(6).getModifierValue());

        // Visual
        Particles.drawCylinder(target.getLocation(), 1, 15, 12, 0.16, 
            Particle.REDSTONE, 
            new Particle.DustOptions
            (
                Color.YELLOW.setGreen(250).setBlue(184), 
                1
            )
        );
    }

    @Override
    public void performSpecial4(Entity target, Player driver) 
    {
        ((Damageable) target).damage(6.0);

        new BukkitRunnable() {
            @Override
            public void run() 
            {
                for (int i = 0; i < 5; i++)
                    new BukkitRunnable() {
                        @Override
                        public void run() 
                        {
                            ((Damageable) target).damage(2.0);
                        }
                    }.runTaskLater(Main.getInstance(), 0L + (i*5L));
            }
            
        }.runTaskLater(Main.getInstance(), 10L);

        for (Location l : LocationUtils.getLocationCloud(target.getLocation().add(0, 12, 0), 6, 0, 6, 14))
        {
            Location lb = l.subtract(0, -12, 0);

            Particles.drawLine(l, lb, 0.2, 
                Particle.REDSTONE, 
                new Particle.DustOptions
                (
                    Color.YELLOW.setGreen(250).setBlue(184), 
                    1
                )
            );
        }

    }
}
