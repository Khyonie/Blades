package com.yukiemeralis.blogspot.blades.affinity.skills;

import java.util.Random;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_AffinityBoost extends AffinitySkill
{
    Random random = new Random();
    public Skill_AffinityBoost(int level) 
    {
        super("Special recharge", "Occasionally charges special by " + (level * 20) + ".", level, AffinitySkillType.BATTLE);
        this.icon = Material.REDSTONE_LAMP;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        BladeData.getAccount(driver).addBladeSpecialProgress(level*20); 
    }
}
