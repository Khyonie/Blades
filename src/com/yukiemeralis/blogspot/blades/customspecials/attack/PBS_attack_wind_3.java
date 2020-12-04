package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PBS_attack_wind_3 extends PlayerBladeSpecial 
{
    public PBS_attack_wind_3() 
    {
        super("GaleWind", "Gale wind", "Increases speed for 20 seconds.", Element.WIND, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        driver.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 2));
    }
}
