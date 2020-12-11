package com.yukiemeralis.blogspot.blades.listeners.events;

import com.yukiemeralis.blogspot.blades.Blade;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BladeEngageEvent extends Event 
{
    private static final HandlerList handlers = new HandlerList();

    Player driver;
    Blade blade;

    public BladeEngageEvent(Player driver, Blade blade)
    {
        this.driver = driver;
        this.blade = blade;
    }

    public Player getDriver()
    {
        return this.driver;
    }

    public Blade getBlade()
    {
        return this.blade;
    }

    @Override
    public HandlerList getHandlers() 
    {
        return handlers;
    }

    public static HandlerList getHandlerList() 
    {
        return handlers;
    }
}
