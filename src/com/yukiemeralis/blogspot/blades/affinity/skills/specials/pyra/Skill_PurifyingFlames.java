package com.yukiemeralis.blogspot.blades.affinity.skills.specials.pyra;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Skill_PurifyingFlames extends AffinitySkill 
{
    public Skill_PurifyingFlames(int level) 
    {
        super("Purifying flames", "Occasionally insta-kill enemy with < " + (level*5) + "% HP.", level, AffinitySkillType.BATTLE);
        icon = Material.BLAZE_POWDER;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        double max = ((Attributable) target).getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double percent = ((Damageable) target).getHealth()/max;

        if (percent <= (level * 0.05))
            ((LivingEntity) target).setHealth(0.0);
    }
    
}
