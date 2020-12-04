package com.yukiemeralis.blogspot.blades.affinity.skills.specials.pyra;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_FlameNova extends AffinitySkill 
{
    public Skill_FlameNova(int level) 
    {
        super("Flame nova", "Increase damage dealt by special I by " + (1 + ((level-1) * 0.25)) + "x.", level, AffinitySkillType.SPECIAL);

        icon = Material.GLOWSTONE_DUST;
        modifier = 1 + ((level-1) * 0.25);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {

    }
}
