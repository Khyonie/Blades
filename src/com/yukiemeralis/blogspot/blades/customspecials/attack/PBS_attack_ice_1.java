package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.Main;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PBS_attack_ice_1 extends PlayerBladeSpecial 
{
    public PBS_attack_ice_1() 
    {
        super("BelowZero", "Below 0", "Slows the target and deals DoT.", Element.ICE, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        ((LivingEntity) target).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 70*20, 2));

        // Half a heart each second
        for (int i = 0; i < 7; i++)
            new BukkitRunnable(){
                @Override
                public void run() 
                {
                    ((Damageable) target).damage(1.0);
                }
            }.runTaskLater(Main.getInstance(), 20*i);
    }
}
