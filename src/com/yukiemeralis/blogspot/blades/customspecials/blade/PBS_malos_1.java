package com.yukiemeralis.blogspot.blades.customspecials.blade;

import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PBS_malos_1 extends PlayerBladeSpecial 
{
    public PBS_malos_1() 
    {
        super("MonadoEnchant", "Monado Enchant", "Temporarily increases damage dealt.", Element.DARK, Role.ATTACK);
    }

    @Override
    public void runEffect(Entity target, Player driver) 
    {
        driver.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*20, 0));
    }
    
}
