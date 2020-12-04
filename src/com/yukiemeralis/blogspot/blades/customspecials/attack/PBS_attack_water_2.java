package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.Main;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PBS_attack_water_2 extends PlayerBladeSpecial 
{
    public PBS_attack_water_2() 
    {
        super("PosWrath", "Poseidon's wrath", "Launches the target and strikes them with lightning.", Element.WATER, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        target.setVelocity(new Vector(0, 1.5, 0));

        new BukkitRunnable(){
            @Override
            public void run() {
                target.getWorld().strikeLightning(target.getLocation());
            }
        }.runTaskLater(Main.getInstance(), 10L);
    }
}
