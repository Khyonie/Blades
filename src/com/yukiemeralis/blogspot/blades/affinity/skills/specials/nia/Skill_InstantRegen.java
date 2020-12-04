package com.yukiemeralis.blogspot.blades.affinity.skills.specials.nia;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_InstantRegen extends AffinitySkill 
{
    public Skill_InstantRegen(int level) 
    {
        super("Instant regeneration", "Chance of restoring " + level + "% HP upon taking a hit.", level, AffinitySkillType.BATTLE);

        icon = Material.KELP;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        BladeData.getAccount(driver).healVirtualHP(BladeData.getAccount(driver).getPercentHp(level * 0.01));
    }
    
}
