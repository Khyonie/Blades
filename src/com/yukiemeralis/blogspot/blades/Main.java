package com.yukiemeralis.blogspot.blades;

import com.yukiemeralis.blogspot.blades.guis.EngageGUI;
import com.yukiemeralis.blogspot.blades.listeners.BladeListeners;
import com.yukiemeralis.blogspot.blades.listeners.EntityListeners;
import com.yukiemeralis.blogspot.blades.listeners.GenericListeners;
import com.yukiemeralis.blogspot.blades.listeners.VirtualHealthListener;
import com.yukiemeralis.blogspot.blades.affinity.AffinityChart;
import com.yukiemeralis.blogspot.blades.customblades.common.*;
import com.yukiemeralis.blogspot.blades.customspecials.attack.*;
import com.yukiemeralis.blogspot.blades.customspecials.healer.*;
import com.yukiemeralis.blogspot.blades.customspecials.blade.*;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private static Main instance;

    @Override
    public void onEnable()
    {
        instance = this;

        this.getServer().getPluginManager().registerEvents(new BladeListeners(), this);
        this.getServer().getPluginManager().registerEvents(new GenericListeners(), this);
        this.getServer().getPluginManager().registerEvents(new VirtualHealthListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityListeners(), this);

        this.getServer().getPluginManager().registerEvents(new EngageGUI(), this);
        this.getServer().getPluginManager().registerEvents(new AffinityChart(), this);

        this.getCommand("blade").setExecutor(new Commands());

        // Blade registry
        new Freya();
        new Lionel();
        new Lumi();
        new Lux();

        new Pyra();
        new Mythra();
        new Malos();

        new Nia();

        new Agate();


        // Aliases
        Blade alias_buffer = (Blade) BladeRegistry.getRegistry().get("WaterStory").clone();
        alias_buffer.setAlias("Nia");
        BladeRegistry.getRegistry().put("Nia", alias_buffer);

        // Player special registry
        new PBS_attack_fire_1();
        new PBS_attack_fire_2();
        new PBS_attack_fire_3();
        new PBS_attack_water_1();
        new PBS_attack_water_2();
        new PBS_attack_water_3();
        new PBS_attack_wind_1();
        new PBS_attack_wind_2();
        new PBS_attack_wind_3();
        new PBS_attack_ice_1();
        new PBS_attack_ice_2();
        new PBS_attack_ice_3();
        new PBS_attack_earth_1();
        new PBS_attack_earth_2();
        new PBS_attack_earth_3();
        new PBS_attack_electric_1();
        new PBS_attack_electric_2();
        new PBS_attack_electric_3();
        new PBS_attack_light_1();
        new PBS_attack_light_2();
        new PBS_attack_light_3();
        new PBS_attack_dark_1();
        new PBS_attack_dark_2();
        new PBS_attack_dark_3();

        new PBS_healer_water_1();
        new PBS_healer_water_2();
        new PBS_healer_water_3();

        new PBS_malos_1();
        new PBS_malos_2();
        new PBS_malos_3();
    }

    @Override
    public void onDisable()
    {

    }

    public static Main getInstance()
    {
        return instance;
    }
}
