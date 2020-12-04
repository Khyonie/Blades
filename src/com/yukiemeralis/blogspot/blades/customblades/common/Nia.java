package com.yukiemeralis.blogspot.blades.customblades.common;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.affinity.AffinityUtils;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.nia.Skill_CellularStimulus;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.nia.Skill_InstantRegen;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.nia.Skill_SeaOfPlenty;
import com.yukiemeralis.blogspot.blades.affinity.skills.specials.nia.Skill_WaterMastery;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Nia extends Blade 
{
    public Nia() 
    {
        super("WaterStory", Element.WATER, Role.HEALER, WeaponType.SWORD, 4);
        chart = AffinityUtils.createAffinityChart(this, 
            AffinityUtils.createAffinityRow(new Skill_WaterMastery(1), new Skill_InstantRegen(1), new Skill_CellularStimulus(1), new Skill_SeaOfPlenty(1), null, null, null),
            AffinityUtils.createAffinityRow(null,                      new Skill_InstantRegen(2), new Skill_CellularStimulus(2), new Skill_SeaOfPlenty(2), null, null, null),
            AffinityUtils.createAffinityRow(null,                      new Skill_InstantRegen(3), new Skill_CellularStimulus(3), new Skill_SeaOfPlenty(3), null, null, null),
            AffinityUtils.createAffinityRow(null,                      new Skill_InstantRegen(4), new Skill_CellularStimulus(4), new Skill_SeaOfPlenty(4), null, null, null),
            AffinityUtils.createAffinityRow(null,                      new Skill_InstantRegen(5), new Skill_CellularStimulus(5), new Skill_SeaOfPlenty(5), null, null, null)
        );
        chart.setOwner(this);

        // Set special data
        setSpecialInfo(0, "Last hope", "Drain enemies' HP, and distribute it to allies.", Element.WATER);
        setSpecialInfo(1, "Merciful heart", "Attack enemies with a water shock wave. +Blowdown", Element.WATER);
        setSpecialInfo(2, "Redeeming strike", "Heals a large amount of HP.", Element.WATER);
        setSpecialInfo(3, "Divine sword", "Heal a large amount and become invincible.", Element.WATER);
    }

    @Override
    public void performSpecial1(Entity target, Player driver) 
    {
        double percent = BladeData.getAccount(driver).getPercentHp(0.2);
        BladeData.getAccount(driver).healVirtualHP(percent);

        if (BladeData.getAccount(driver).getParty() != null)
            healParty(driver, BladeData.getAccount(driver).getParty(), percent);
    }

    @Override
    public void performSpecial2(Entity target, Player driver) 
    {
        double percent = BladeData.getAccount(driver).getPercentHp(0.3);
        BladeData.getAccount(driver).healVirtualHP(percent);

        Vector direction = target.getLocation().toVector().subtract(driver.getLocation().toVector());
        target.setVelocity(direction.multiply(0.5));
    }

    @Override
    public void performSpecial3(Entity target, Player driver) 
    {
        double percent = BladeData.getAccount(driver).getPercentHp(0.4);
        BladeData.getAccount(driver).healVirtualHP(percent);

        if (BladeData.getAccount(driver).getParty() != null)
            healParty(driver, BladeData.getAccount(driver).getParty(), percent);
    }

    @Override
    public void performSpecial4(Entity target, Player driver) 
    {
        double percent = BladeData.getAccount(driver).getPercentHp(0.5);
        BladeData.getAccount(driver).healVirtualHP(percent);

        if (BladeData.getAccount(driver).getParty() != null)
            healParty(driver, BladeData.getAccount(driver).getParty(), percent);

        driver.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10*20, 15));
    }
    
}
