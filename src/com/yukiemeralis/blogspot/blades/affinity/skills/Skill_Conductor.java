package com.yukiemeralis.blogspot.blades.affinity.skills;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_Conductor extends AffinitySkill 
{
    public Skill_Conductor(int level) 
    {
        super("Conductor", "Occasionally strikes the enemy with lightning.", level, AffinitySkillType.BATTLE);

        icon = Material.NETHER_STAR;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        target.getWorld().strikeLightning(target.getLocation());
    }
    
}
