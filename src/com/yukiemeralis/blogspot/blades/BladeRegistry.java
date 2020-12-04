package com.yukiemeralis.blogspot.blades;

import java.util.HashMap;

public class BladeRegistry 
{
    private static HashMap<String, Blade> registry = new HashMap<>();

    public static HashMap<String, Blade> getRegistry()
    {
        return registry;
    }
}
