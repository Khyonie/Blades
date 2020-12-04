package com.yukiemeralis.blogspot.blades.affinity.skills.specials.freya;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_RagingEmber extends AffinitySkill {

    public Skill_RagingEmber(int level) 
    {
        super("Raging ember", "Increases damage dealt to undead by " + (1 + ((level - 1) * 0.5)) + "x.", level, AffinitySkillType.SPECIAL);

        icon = Material.DRIED_KELP;
        modifier = 1 + ((level -1) * 0.5);
    }

    @Override
    public void runEffect(Entity target, Player driver) {

    }
    
}
