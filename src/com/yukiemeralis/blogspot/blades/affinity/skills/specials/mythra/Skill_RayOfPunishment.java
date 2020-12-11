package com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;
import com.yukiemeralis.blogspot.blades.customspecials.MobTypes;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_RayOfPunishment extends AffinitySkill 
{
    public Skill_RayOfPunishment(int level) 
    {
        super("Ray of punishment", "Increases damage to end enemies by " + (1 + ((level - 1) * 0.1)) + "x.", level, AffinitySkillType.SPECIAL);

        icon = Material.WHITE_DYE;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (MobTypes.end.contains(target.getType()))
        {
            modifier = 1 + ((level - 1) * 0.1);
        } else {
            modifier = 1.0;
        }
    }
}
