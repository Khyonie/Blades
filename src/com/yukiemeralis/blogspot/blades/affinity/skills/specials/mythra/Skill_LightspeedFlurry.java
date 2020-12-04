package com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinityActivationType;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_LightspeedFlurry extends AffinitySkill 
{
    public Skill_LightspeedFlurry(int level) 
    {
        super("Lightspeed flurry", "Landing a critical charges special.", level, AffinitySkillType.BATTLE);

        activationType = AffinityActivationType.CRITICAL;
        icon = Material.GOLD_INGOT;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        BladeData.getAccount(driver).addBladeSpecialProgress(30*level);
    }
}
