package com.yukiemeralis.blogspot.blades.affinity.skills.specials.nia;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_SeaOfPlenty extends AffinitySkill {

    public Skill_SeaOfPlenty(int level) 
    {
        super("Sea of Plenty", "Occasionally heals " + (3 + level) + "% HP in battle.", level, AffinitySkillType.BATTLE);

        icon = Material.HEART_OF_THE_SEA;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (BladeData.getAccount(driver).getAffinity() == 2)
        {
            double hpToHeal = BladeData.getAccount(driver).getPercentHp(0.03 + (level * 0.01));
            BladeData.getAccount(driver).healVirtualHP(hpToHeal);
        }
    }
}
