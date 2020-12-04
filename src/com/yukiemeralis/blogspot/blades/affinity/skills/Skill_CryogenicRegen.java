package com.yukiemeralis.blogspot.blades.affinity.skills;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_CryogenicRegen extends AffinitySkill 
{
    // For use in ice-type blades
    public Skill_CryogenicRegen(int level) 
    {
        super("Cryogenic regeneration", "Restores HP in cold biomes.", level, AffinitySkillType.FIELD);

        icon = Material.PACKED_ICE;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (driver.getLocation().getBlock().getTemperature() <= 0.05) // Cold biomes are less than 0.05
            BladeData.getAccount(driver).healVirtualHP(1.0);
    }
    
}
