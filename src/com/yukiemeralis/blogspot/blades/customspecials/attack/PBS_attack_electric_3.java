package com.yukiemeralis.blogspot.blades.customspecials.attack;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.*;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_attack_electric_3 extends PlayerBladeSpecial 
{
    public PBS_attack_electric_3() 
    {
        super("Magnet", "Magnet", "Pulls nearby targets close.", Element.ELECTRIC, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        // TODO Do this
    }
}
