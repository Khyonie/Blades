package com.yukiemeralis.blogspot.blades.customspecials.healer;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_healer_water_1 extends PlayerBladeSpecial {

    public PBS_healer_water_1() {
        super("LastHope", "Last hope", "Restores a medium amount of health.", Element.WATER, Role.HEALER);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        BladeData.getAccount(driver).healVirtualHP(6);

        if (BladeData.getAccount(driver).getParty() != null)
            healParty(driver, BladeData.getAccount(driver).getParty(), 6);
    }
    
}
