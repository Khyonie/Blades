package com.yukiemeralis.blogspot.blades.customspecials.attack;

import java.util.Random;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_dark_3 extends PlayerBladeSpecial 
{
    Random random = new Random();
    public PBS_attack_dark_3() 
    {
        super("Reaperbound", "Reaperbound", "Small chance to instantly kill target.", Element.DARK, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        if (random.nextInt(5) == 0)
            ((Damageable) target).setHealth(0.0);
    }
}