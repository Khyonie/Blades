package com.yukiemeralis.blogspot.blades.affinity.skills.specials.agate;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_HeliodorPassion extends AffinitySkill 
{
    public Skill_HeliodorPassion(int level) 
    {
        super("Heliodor passion", "Increases damage at max affinity by " + (20 + (level * 5)) + "%.", level, AffinitySkillType.SPECIAL);

        icon = Material.GOLD_ORE;

        modifier = 1.2 + (level * 0.05);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (BladeData.getAccount(driver).getAffinity() == 2)
        {
            modifier = 1.2 + (level * 0.05);
        } else {
            modifier = 1.0;
        }
    }
    
}
