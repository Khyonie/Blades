package com.yukiemeralis.blogspot.blades.affinity.skills.specials.nia;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Skill_WaterMastery extends AffinitySkill 
{
    public Skill_WaterMastery(int level) 
    {
        super("Water mastery", "Increases swim speed.", level, AffinitySkillType.FIELD);

        icon = Material.WATER_BUCKET;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        driver.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 15*20, 0));
    }
    
}
