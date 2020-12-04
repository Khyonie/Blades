package com.yukiemeralis.blogspot.blades.customspecials.healer;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.PlayerAccount;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_healer_water_3 extends PlayerBladeSpecial {

    public PBS_healer_water_3() {
        super("RedeemingStrike", "Redeeming strike", "Heals 30% health.", Element.WATER, Role.HEALER);
    }

    @Override
    public void runEffect(Entity target, Player driver) {
        PlayerAccount account = BladeData.getAccount(driver);

        int delta = (int) Math.round(account.getMaxVirtualHP() * .30);
        account.healVirtualHP(delta);

        if (BladeData.getAccount(driver).getParty() != null)
            healParty(driver, BladeData.getAccount(driver).getParty(), delta);
    }
    
}
