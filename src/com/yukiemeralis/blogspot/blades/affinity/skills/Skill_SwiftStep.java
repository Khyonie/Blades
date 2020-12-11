package com.yukiemeralis.blogspot.blades.affinity.skills;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Skill_SwiftStep extends AffinitySkill 
{
    public Skill_SwiftStep(int level) 
    {
        super("Swift step", "Increase out of battle movement speed in thunder.", level, AffinitySkillType.FIELD);
        this.icon = Material.FEATHER;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (driver.getWorld().isThundering())
            driver.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15*20, level-1));
    }
}
