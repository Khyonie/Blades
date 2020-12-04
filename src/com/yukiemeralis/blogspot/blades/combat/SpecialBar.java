package com.yukiemeralis.blogspot.blades.combat;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.PlayerAccount;
import com.yukiemeralis.blogspot.blades.utils.TextUtils;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class SpecialBar 
{
    public static BossBar generate(Player player) 
    {
        // Pull all the information we need
        PlayerAccount account = BladeData.getAccount(player);
        Blade blade = account.getCurrentBlade();

        //Entity target = CombatData.getCombatInfo(player).getTarget();

        String displayName = 
            blade.getName() + " special: " + TextUtils.toRomanNumeral(account.getBladeSpecialProgress() / 100);// + 
            //" | Target: " + TextUtils.formatNicely(target.getType().name());
        BarColor color = BarColor.BLUE;
        BarStyle style = BarStyle.SEGMENTED_10;

        // Initialize boss bar info
        BossBar bar = Bukkit.createBossBar(displayName, color, style, new BarFlag[] {});

        bar.setProgress((account.getBladeSpecialProgress() % 100) / 100d);

        return bar;
    }

    public static String generateDisplayName(Player player)
    {
        // Pull all the information we need
        PlayerAccount account = BladeData.getAccount(player);
        Blade blade = account.getCurrentBlade();

        //Entity target = CombatData.getCombatInfo(player).getTarget();

        return blade.getName() + " special: " + TextUtils.toRomanNumeral(account.getBladeSpecialProgress() / 100);// + 
            //" | Target: " + TextUtils.formatNicely(target.getType().name());
    }
}
