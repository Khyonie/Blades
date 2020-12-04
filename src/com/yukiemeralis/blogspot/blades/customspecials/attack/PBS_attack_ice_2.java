package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PBS_attack_ice_2 extends PlayerBladeSpecial 
{
    public PBS_attack_ice_2() 
    {
        super("IceShard", "Ice shard", "Inflicts light damage and increases speed.", Element.ICE, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        ((Damageable) target).damage(4.0);
        driver.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
    }
}
