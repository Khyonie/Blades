package com.yukiemeralis.blogspot.blades.affinity.skills.specials.pyra;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_ProminenceRevolt extends AffinitySkill {

    public Skill_ProminenceRevolt(int level) 
    {
        super("Prominence revolt", "Increase damage dealt by special II by " + (1 + ((level - 1)*0.5)) + "x.", level, AffinitySkillType.SPECIAL);

        icon = Material.RED_DYE;
        modifier = 1 + ((level - 1)*0.5);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {

    }
}
