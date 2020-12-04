package com.yukiemeralis.blogspot.blades.affinity.skills.specials.agate;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Skill_BlastMining extends AffinitySkill 
{
    public Skill_BlastMining(int level) 
    {
        super("Blast mining", "Blast the enemy away at the start of battle.", level, AffinitySkillType.BATTLE);

        icon = Material.TNT;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        Vector direction = target.getLocation().toVector().subtract(driver.getLocation().toVector());
        target.setVelocity(direction.multiply(0.5));
    }
    
}
