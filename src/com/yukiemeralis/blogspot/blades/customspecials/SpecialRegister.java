package com.yukiemeralis.blogspot.blades.customspecials;

import java.util.HashMap;

public class SpecialRegister 
{
    private static HashMap<String, PlayerBladeSpecial> registry = new HashMap<>();

    public static HashMap<String, PlayerBladeSpecial> getRegistry()
    {
        return registry;
    }

    public static PlayerBladeSpecial getSpecial(String name)
    {
        return registry.get(name);
    }

    public static void register(PlayerBladeSpecial special)
    {
        registry.put(special.getName(), special);
    }
}
