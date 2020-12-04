package com.yukiemeralis.blogspot.blades.affinity.skills.specials.agate;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_SharpPickaxe extends AffinitySkill 
{
    public Skill_SharpPickaxe(int level) 
    {
        super("Sharp pickaxe", "Occasionally increase crit rate.", level, AffinitySkillType.BATTLE);

        icon = Material.CLAY_BALL;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        Blade target_blade = BladeData.getAccount(driver).getCurrentBlade();

        if (target_blade.getCritRate() < 93)
            target_blade.setCritRate(target_blade.getCritRate() + 2);
    }
    
}
