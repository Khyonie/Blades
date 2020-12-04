package com.yukiemeralis.blogspot.blades.affinity.skills;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Skill_SunnyMiner extends AffinitySkill 
{
    public Skill_SunnyMiner(int level) 
    {
        super("Bright miner", "Increases mining speed during the day.", level, AffinitySkillType.FIELD);

        icon = Material.GOLDEN_PICKAXE;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (driver.getWorld().getTime() <= 13000)
            driver.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 15*20, level-1));
    }
    
}
