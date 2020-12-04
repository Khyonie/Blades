package com.yukiemeralis.blogspot.blades.affinity.skills;

import java.util.Random;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_BurningEdge extends AffinitySkill 
{
    Random random = new Random();
    public Skill_BurningEdge(int level) 
    {
        super("Burning edge", "Normal attacks occasionally light the enemy on fire.", level, AffinitySkillType.BATTLE);

        icon = Material.BLAZE_POWDER;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        target.setFireTicks(100);
    }
    
}
