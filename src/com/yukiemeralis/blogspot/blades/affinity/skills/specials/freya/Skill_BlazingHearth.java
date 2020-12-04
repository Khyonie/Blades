package com.yukiemeralis.blogspot.blades.affinity.skills.specials.freya;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_BlazingHearth extends AffinitySkill 
{
    public Skill_BlazingHearth(int level) 
    {
        super("Blazing hearth", "Increases damage dealt to undead by " + (1 + ((level-1) * 1)) + "x.", level, AffinitySkillType.SPECIAL);

        icon = Material.CHARCOAL;
        modifier = 1 + ((level-1) * 1);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        
    }
    
}
