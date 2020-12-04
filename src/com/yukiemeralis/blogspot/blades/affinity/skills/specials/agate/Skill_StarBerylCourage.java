package com.yukiemeralis.blogspot.blades.affinity.skills.specials.agate;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Skill_StarBerylCourage extends AffinitySkill 
{
    public Skill_StarBerylCourage(int level) 
    {
        super("Star beryl courage", "Increases damage to enemies status afflicted enemies by " + (40 + (20 * level)) + "%.", level, AffinitySkillType.SPECIAL);

        icon = Material.REDSTONE_ORE;
        modifier = 1.4 + (level * 0.2);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (((LivingEntity) target).getActivePotionEffects().size() != 0)
        {
            modifier = 1.4 + (level * 0.2);
        } else {
            modifier = 1.0;
        }
    }
    
}
