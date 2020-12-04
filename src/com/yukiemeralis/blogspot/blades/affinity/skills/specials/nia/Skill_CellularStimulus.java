package com.yukiemeralis.blogspot.blades.affinity.skills.specials.nia;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_CellularStimulus extends AffinitySkill 
{
    public Skill_CellularStimulus(int level) 
    {
        super("Cellular stimulus", "Occasionally heal " + (3 + (level * 2)) + "% HP when attacking.", level, AffinitySkillType.BATTLE);

        icon = Material.SLIME_BALL;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        double percent = (3 + (level * 2))/100;

        BladeData.getAccount(driver).healVirtualHP(BladeData.getAccount(driver).getPercentHp(percent));
    }
    
}
