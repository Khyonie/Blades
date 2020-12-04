package com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_Glint extends AffinitySkill 
{
    public Skill_Glint(int level) 
    {
        super("Glint", "Occasionally increases critical hit rate.", level, AffinitySkillType.BATTLE);

        icon = Material.DIAMOND;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        Blade target_blade = BladeData.getAccount(driver).getCurrentBlade(); // Again, only mythra

        if (target_blade.getCritRate() < 93)
            target_blade.setCritRate(target_blade.getCritRate() + 2);
    }
    
}
