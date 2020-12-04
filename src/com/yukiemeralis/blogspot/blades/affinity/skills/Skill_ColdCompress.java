package com.yukiemeralis.blogspot.blades.affinity.skills;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_ColdCompress extends AffinitySkill {

    public Skill_ColdCompress(int level) 
    {
        super("Cold compress", "Occasionally heal " + ((level * 2.0) + 4.0) + "♥ during battle.", level, AffinitySkillType.BATTLE);

        icon = Material.LIGHT_BLUE_DYE;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        BladeData.getAccount(driver).healVirtualHP((level * 2.0) + 4.0);
    }
}
