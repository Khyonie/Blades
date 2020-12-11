package com.yukiemeralis.blogspot.blades.listeners.events;

import com.yukiemeralis.blogspot.blades.Blade;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TrustGradeRaiseEvent extends Event 
{
    private static final HandlerList handlers = new HandlerList();

    private Blade blade;
    private Player driver;
    private String grade;
    private int row;

    public TrustGradeRaiseEvent(Blade blade, Player driver, String grade, int row)
    {
        this.blade = blade;
        this.driver = driver;
        this.grade = grade;
        this.row = row;
    }

    public Blade getBlade()
    {
        return this.blade;
    }

    public Player getDriver()
    {
        return this.driver;
    }

    public String getGrade()
    {
        return this.grade;
    }

    public int getRow()
    {
        return this.row;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() 
    {
        return handlers;
    }
    
}
