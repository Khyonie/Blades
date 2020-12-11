package com.yukiemeralis.blogspot.blades.affinity.skills.specials.mythra;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;
import com.yukiemeralis.blogspot.blades.customspecials.MobTypes;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_LightningBuster extends AffinitySkill {

    public Skill_LightningBuster(int level) 
    {
        super("Lightning buster", "Increases damage dealt to nether mobs by " + (1 + ((level - 1)*0.2)) + "x.", level, AffinitySkillType.SPECIAL);

        icon = Material.YELLOW_DYE;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (MobTypes.nether.contains(target.getType()))
        {
            modifier = 1 + ((level - 1)*0.2);
        } else {
            modifier = 1.0;
        }
    }
    
}
