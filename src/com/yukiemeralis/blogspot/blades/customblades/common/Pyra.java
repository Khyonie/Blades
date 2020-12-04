package com.yukiemeralis.blogspot.blades.customblades.common;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.Main;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_AffinityBoost;
import com.yukiemeralis.blogspot.blades.affinity.skills.Skill_BlazingSpirit;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.pyra.Skill_BlazingEnd;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.pyra.Skill_FlameNova;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.pyra.Skill_ProminenceRevolt;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.pyra.Skill_PurifyingFlames;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Pyra extends Blade 
{
    public Pyra() 
    {
        super("Pyra", Element.FIRE, Role.ATTACK, WeaponType.SWORD, 4);

        chart = AffinityUtils.createAffinityChart(this, 
            AffinityUtils.createAffinityRow(null, new Skill_BlazingSpirit(1), new Skill_PurifyingFlames(1), new Skill_AffinityBoost(1), new Skill_FlameNova(1), new Skill_ProminenceRevolt(1), new Skill_BlazingEnd(1)),
            AffinityUtils.createAffinityRow(null, new Skill_BlazingSpirit(2), new Skill_PurifyingFlames(2), new Skill_AffinityBoost(2), new Skill_FlameNova(2), new Skill_ProminenceRevolt(2), new Skill_BlazingEnd(2)),
            AffinityUtils.createAffinityRow(null, new Skill_BlazingSpirit(3), new Skill_PurifyingFlames(3), new Skill_AffinityBoost(3), new Skill_FlameNova(3), new Skill_ProminenceRevolt(3), new Skill_BlazingEnd(3)),
            AffinityUtils.createAffinityRow(null, new Skill_BlazingSpirit(4), new Skill_PurifyingFlames(4), new Skill_AffinityBoost(4), new Skill_FlameNova(4), new Skill_ProminenceRevolt(4), new Skill_BlazingEnd(4)),
            AffinityUtils.createAffinityRow(null, new Skill_BlazingSpirit(5), new Skill_PurifyingFlames(5), new Skill_AffinityBoost(5), new Skill_FlameNova(5), new Skill_ProminenceRevolt(5), new Skill_BlazingEnd(5))
        );
        chart.setOwner(this);

        // Set special data
        setSpecialInfo(0, "Flame nova", "Inflicts light damage and sets target ablaze.", Element.FIRE);
        setSpecialInfo(1, "Prominence revolt", "Inflicts medium damage and sets target ablaze.", Element.FIRE);
        setSpecialInfo(2, "Blazing end", "Inflicts heavy damage and sets target ablaze.", Element.FIRE);
        setSpecialInfo(3, "Burning sword", "Inflicts heavy damage multiple times. +Launch", Element.FIRE);
    }

    @Override
    public void performSpecial1(Entity target, Player driver) 
    {
        target.setFireTicks(60);
        ((Damageable) target).damage(6.0 * chart.getHighestSkillInColumn(4).getModifierValue());
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        target.setFireTicks(60);
        ((Damageable) target).damage(10.0 * chart.getHighestSkillInColumn(5).getModifierValue());
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        target.setFireTicks(60);
        ((Damageable) target).damage(14.0 * chart.getHighestSkillInColumn(6).getModifierValue());
    }

    @Override
    public void performSpecial4(Entity target, Player driver) 
    {
        ((Damageable) target).damage(6.0);
        target.setFireTicks(60);

        new BukkitRunnable() {
            @Override
            public void run() 
            {
                target.setFireTicks(100);

                for (int i = 0; i < 5; i++)
                    new BukkitRunnable() {
                        @Override
                        public void run() 
                        {
                            ((Damageable) target).damage(2.0 * chart.getHighestSkillInColumn(4).getModifierValue());
                        }
                    }.runTaskLater(Main.getInstance(), 0L + (i*5L));
            }
            
        }.runTaskLater(Main.getInstance(), 10L);

        target.setVelocity(new Vector(0, 1.75, 0));
    }
}
