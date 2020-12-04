package com.yukiemeralis.blogspot.blades.affinity.skills;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_Placeholder extends AffinitySkill
{
    public Skill_Placeholder(int level) 
    {
        super("Placeholder Skill", "Just be patient, we'll get there eventually.", level, AffinitySkillType.SPECIAL);
        this.icon = Material.ENDER_EYE;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {

    }
}
