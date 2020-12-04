package com.yukiemeralis.blogspot.blades.affinity.skills.specials.freya;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_RadiantCall extends AffinitySkill 
{
    public Skill_RadiantCall(int level) 
    {
        super("Radiant call", "Increases target's burn time by " + (level - 1) + " second(s).", level, AffinitySkillType.SPECIAL);

        icon = Material.GLOWSTONE;
        modifier = level - 1;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {

    }
    
}
