package com.yukiemeralis.blogspot.blades.affinity.skills.specials.agate;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Skill_MiningExpertise extends AffinitySkill {

    public Skill_MiningExpertise(int level) 
    {
        super("Mining expertise", "Faster mining speed.", level, AffinitySkillType.FIELD);

        icon = Material.IRON_PICKAXE;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        driver.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 15*20, level-1));
    }
    
}
