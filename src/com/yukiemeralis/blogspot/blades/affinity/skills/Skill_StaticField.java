package com.yukiemeralis.blogspot.blades.affinity.skills;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Skill_StaticField extends AffinitySkill 
{
    public Skill_StaticField(int level) 
    {
        super("Static field", "Slows target at the beginning of battle.", level, AffinitySkillType.BATTLE);
        icon = Material.GRAY_DYE;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, level + 1));
    }
    /**
     * Player blades are broken (battles fail to start)
     * Implement proper driver combos
     * 
     * Affinity charts:
     * Agate - Complete
     * Freya - Complete
     * Lionel - Needs specials
     * Lumi - Needs specials
     * Lux - Needs full chart
     * Malos - Needs field and battle specials
     * Mythra - Needs specials
     * Nia - Needs field, 2 battle, and all special skills
     * Pyra - Needs field
     */
}
