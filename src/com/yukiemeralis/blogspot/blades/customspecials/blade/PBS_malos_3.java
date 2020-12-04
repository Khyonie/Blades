package com.yukiemeralis.blogspot.blades.customspecials.blade;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PBS_malos_3 extends PlayerBladeSpecial {
    public PBS_malos_3() {
        super("MonadoBuster", "Monado Buster", "Inflicts extreme damage on target.", Element.DARK, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) {
        ((Damageable) target).damage(20);
    }
    
}
