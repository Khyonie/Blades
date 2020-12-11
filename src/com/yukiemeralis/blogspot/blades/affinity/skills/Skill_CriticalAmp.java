package com.yukiemeralis.blogspot.blades.affinity.skills;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_CriticalAmp extends AffinitySkill 
{
    public Skill_CriticalAmp(int level) 
    {
        super("Critical amp", "Increases base crit rate by " + (5 + (10 * level)) + "%.", level, AffinitySkillType.FIELD);

        icon = Material.BOOK;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        BladeData.getAccount(driver).getCurrentBlade().setBaseCritRate(5 + (10 * level)); // Only mythra
    }
    
}
