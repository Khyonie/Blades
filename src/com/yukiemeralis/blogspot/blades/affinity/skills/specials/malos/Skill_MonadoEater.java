package com.yukiemeralis.blogspot.blades.affinity.skills.specials.malos;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_MonadoEater extends AffinitySkill 
{
    public Skill_MonadoEater(int level) 
    {
        super("Monado Jail", "Increases damage dealt to humans by " + level + "♥.", level, AffinitySkillType.SPECIAL);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        ((Player) target).damage(level);
    }
    
}
