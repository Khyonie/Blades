package com.yukiemeralis.blogspot.blades.entities;

import com.yukiemeralis.blogspot.blades.Blade;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;

public class BladeEntity extends EntityCreature 
{
    Player driver;
    Blade linked;

    boolean isHumanoid = false;
    EntityPlayer npc;

    public BladeEntity(EntityTypes<? extends EntityCreature> entitytypes, Location location) 
    {
        super(entitytypes, ((CraftWorld) location.getWorld()).getHandle());
        this.setPosition(location.getX(), location.getY(), location.getZ());

        this.setInvulnerable(true);
    }

    public void link(Blade blade)
    {
        this.linked = blade;
    }

    public Blade getLinked()
    {
        return linked;
    }

    public void linkEntityPlayer(EntityPlayer npc)
    {
        this.npc = npc;
    }

    public EntityPlayer getEntityPlayer()
    {
        return this.npc;
    }

    public void setDriver(Player driver)
    {
        this.driver = driver;
        this.setGoalTarget(((CraftPlayer) driver).getHandle(), TargetReason.CUSTOM, false);
    }

    public void setIsHumanoid(boolean value)
    {
        isHumanoid = value;
    }

    public boolean getIsHumanoid()
    {
        return isHumanoid;
    }
    
    @Override
    protected void initPathfinder()
    {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.goalSelector.a(1, new PathfinderGoalBladeFollow(this, 1.5, 20));
    }

    public Location getBukkitLocation()
    {
        return getBukkitEntity().getLocation();
    }
}
