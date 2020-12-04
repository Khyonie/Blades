package com.yukiemeralis.blogspot.blades.affinity.skills.specials.agate;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;
import com.yukiemeralis.blogspot.blades.customspecials.MobTypes;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_RhodochrositeMight extends AffinitySkill 
{
    public Skill_RhodochrositeMight(int level) 
    {
        super("Rhodochrosite might", "Increases damage done to nether mobs by " + (level + 1) + "x.", level, AffinitySkillType.SPECIAL);

        icon = Material.NETHERITE_PICKAXE;
        modifier = 1.0;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (MobTypes.nether.contains(target.getType()))
        {
            modifier = 1 + level;
        } else {
            modifier = 1;
        }
    }
}
