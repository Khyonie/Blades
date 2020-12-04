package com.yukiemeralis.blogspot.blades.customblades.common;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.Main;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_BlazingSpirit;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_CriticalAmp;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra.Skill_Glint;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra.Skill_LightspeedFlurry;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Mythra extends Blade 
{
    public Mythra() 
    {
        super("Mythra", Element.LIGHT, Role.ATTACK, WeaponType.SWORD, 4);

        chart = AffinityUtils.createAffinityChart(this, 
            AffinityUtils.createAffinityRow(new Skill_CriticalAmp(1), new Skill_BlazingSpirit(1), new Skill_LightspeedFlurry(1), new Skill_Glint(1), null, null, null),
            AffinityUtils.createAffinityRow(new Skill_CriticalAmp(2), new Skill_BlazingSpirit(2), new Skill_LightspeedFlurry(2), new Skill_Glint(2), null, null, null),
            AffinityUtils.createAffinityRow(new Skill_CriticalAmp(3), new Skill_BlazingSpirit(3), new Skill_LightspeedFlurry(3), new Skill_Glint(3), null, null, null),
            AffinityUtils.createAffinityRow(new Skill_CriticalAmp(4), new Skill_BlazingSpirit(4), new Skill_LightspeedFlurry(4), new Skill_Glint(4), null, null, null),
            AffinityUtils.createAffinityRow(new Skill_CriticalAmp(5), new Skill_BlazingSpirit(5), new Skill_LightspeedFlurry(5), new Skill_Glint(5), null, null, null)
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
        ((Damageable) target).damage(6.0);
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        for (int i = 0; i < 7; i++)
        {
            new BukkitRunnable(){
                @Override
                public void run()
                {
                    ((Damageable) target).damage(1.0);
                }
            }.runTaskLater(Main.getInstance(), (i * 5) + 5);
        }
        
        target.setVelocity(new Vector(0, -5, 0));
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        ((Damageable) target).damage(14.0);
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
    }
}
