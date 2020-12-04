package com.yukiemeralis.blogspot.blades.affinity.skills;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_WarmthSap extends AffinitySkill {

    public Skill_WarmthSap(int level) 
    {
        super("Warmth sap", "Occasionally steal the target's health.", level, AffinitySkillType.BATTLE);

        icon = Material.ENDER_EYE;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        BladeData.getAccount(driver).healVirtualHP(3.0 + level);

        ((Damageable) target).damage(3.0 + level);
    }
    
}
