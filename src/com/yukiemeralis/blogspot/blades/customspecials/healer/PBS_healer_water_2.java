package com.yukiemeralis.blogspot.blades.customspecials.healer;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_healer_water_2 extends PlayerBladeSpecial 
{
    public PBS_healer_water_2() {
        super("MercifulHeart", "Merciful heart", "Saps health from the target.", Element.WATER, Role.HEALER);
    }

    @Override
    public void runEffect(Entity target, Player driver) {
        BladeData.getAccount(driver).healVirtualHP(8);
        ((Damageable) target).damage(8);

        if (BladeData.getAccount(driver).getParty() != null)
            healParty(driver, BladeData.getAccount(driver).getParty(), 8);
    }
}
