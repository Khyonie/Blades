package com.yukiemeralis.blogspot.blades.affinity.skills;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_BlazingSpirit extends AffinitySkill {

    public Skill_BlazingSpirit(int level) 
    {
        super("Blazing spirit", "Start battles with " + (level * 20) + "% special charge during the day.", level, AffinitySkillType.BATTLE);

        icon = Material.NETHERITE_SWORD;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        BladeData.getAccount(driver).addBladeSpecialProgress(20*level);
    }
    
}
