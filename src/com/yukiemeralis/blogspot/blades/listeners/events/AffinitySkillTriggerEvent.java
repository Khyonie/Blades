package com.yukiemeralis.blogspot.blades.listeners.events;

import com.yukiemeralis.blogspot.blades.affinity.AffinitySkill;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AffinitySkillTriggerEvent extends Event 
{
    private static final HandlerList handlers = new HandlerList();

    private AffinitySkill skill;
    private Player driver;
    private Entity target;

    public AffinitySkillTriggerEvent(AffinitySkill skill, Player driver, Entity target)
    {
        this.skill = skill;
        this.driver = driver;
        this.target = target;
    }

    public static HandlerList getHandlerList() 
    {
        return handlers;
    }

    public AffinitySkill getSkill()
    {
        return this.skill;
    }

    public Player getDriver()
    {
        return driver;
    }

    public Entity getEntityTarget()
    {
        return target;
    }

    @Override
    public HandlerList getHandlers() 
    {
        return handlers;
    }
    
}
