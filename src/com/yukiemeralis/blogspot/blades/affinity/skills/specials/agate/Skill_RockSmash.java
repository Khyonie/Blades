package com.yukiemeralis.blogspot.blades.affinity.skills.specials.agate;

import com.yukiemeralis.blogspot.blades.affinity.AffinityActivationType;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;
import com.yukiemeralis.blogspot.blades.affinity.AffinitySkillType;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Skill_RockSmash extends AffinitySkill {

    public Skill_RockSmash(int level) 
    {
        super("Rock smash", "Criticals inflict dizziness on the target.", level, AffinitySkillType.BATTLE);

        icon = Material.DIAMOND_PICKAXE;
        activationType = AffinityActivationType.CRITICAL;
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5*20, 0));
    }
}
