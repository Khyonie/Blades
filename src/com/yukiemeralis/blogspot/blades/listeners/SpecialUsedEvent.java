package com.yukiemeralis.blogspot.blades.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpecialUsedEvent extends Event 
{
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() 
    {
        return handlers;
    }
    
}
