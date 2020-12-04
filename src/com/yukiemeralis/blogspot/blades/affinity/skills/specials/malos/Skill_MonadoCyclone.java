package com.yukiemeralis.blogspot.blades.affinity.skills.specials.malos;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_MonadoCyclone extends AffinitySkill 
{
    public Skill_MonadoCyclone(int level) 
    {
        super("Monado Cyclone", "Increase damage dealt by special II by " + (1 + ((level - 1) * 0.2)) + "x.", level, AffinitySkillType.SPECIAL);

        icon = Material.PURPLE_DYE;
        modifier = 1 + ((level - 1) * 0.2);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {

    }
    
}
