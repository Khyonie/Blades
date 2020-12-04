package com.yukiemeralis.blogspot.blades.affinity.skills.specials.malos;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Skill_MonadoEnchant extends AffinitySkill {

    public Skill_MonadoEnchant(int level) {
        super("Monado Enchant", "Increases damage bonus.", level, AffinitySkillType.SPECIAL);

        icon = Material.MAGENTA_DYE;
        modifier = level;
    }

    @Override
    public void runEffect(Entity target, Player driver) {

    }
    
}
