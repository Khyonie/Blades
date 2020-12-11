package com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;
import com.yukiemeralis.blogspot.blades.customspecials.MobTypes;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_PhotonEdge extends AffinitySkill 
{
    public Skill_PhotonEdge(int level) 
    {
        super("Photon edge", "Increases damage to bosses by " + level + "x.", level, AffinitySkillType.SPECIAL);

        icon = Material.GOLD_NUGGET;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (MobTypes.boss.contains(target.getType()))
        {
            modifier = level;
        } else {
            modifier = 1.0;
        }
    }
    
}
