package com.yukiemeralis.blogspot.blades.affinity.skills.specials.pyra;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_BlazingEnd extends AffinitySkill 
{
    public Skill_BlazingEnd(int level) 
    {
        super("Blazing end", "Increases damage dealt by special III by " + (1 + ((level - 1)*0.5)) + "x.", level, AffinitySkillType.SPECIAL);

        icon = Material.REDSTONE;
        modifier = 1 + ((level - 1)*0.5);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {

    }
    
}
