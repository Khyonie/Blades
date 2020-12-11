package com.yukiemeralis.blogspot.blades.entities;

import java.util.EnumSet;

import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PathfinderGoal;
import net.minecraft.server.v1_16_R3.RandomPositionGenerator;
import net.minecraft.server.v1_16_R3.Vec3D;

public class PathfinderGoalBladeFollow extends PathfinderGoal
{
    private final EntityInsentient a; 
    private EntityLiving driver;

    private final double speed;
    private final float distance;

    private double x, y, z;

    public PathfinderGoalBladeFollow(EntityInsentient a, double speed, float distance)
    {
        this.a = a;
        this.speed = speed;
        this.distance = distance;
        this.a(EnumSet.of(Type.MOVE));
    }

    @Override
    public boolean a() 
    {
        this.driver = this.a.getGoalTarget();
        if (this.driver == null) {
            return false;
        }
        else if (this.a.getDisplayName() == null) {
            return false;
        }
        else if (this.driver.h(this.a) > (double) (this.distance * this.distance)) {
            a.setPosition(this.driver.locX(), this.driver.locY(), this.driver.locZ());
            return false;
        }
        else if (this.driver.h(this.a) < (double) (5 * 5)) {
            return false;
        }
        else {
            Vec3D vec = RandomPositionGenerator.a((EntityCreature) this.a, 16, 7, this.driver.getPositionVector());
            if (vec == null)
                return false;
            this.x = this.driver.locX(); 
            this.y = this.driver.locY(); 
            this.z = this.driver.locZ();
            return true;
        }
    }
    
    // Runs when a() is true
    public void c()
    {
        this.a.getNavigation().a(this.x, this.y, this.z, this.speed);
    }

    // Runs after c(), makes sure we're in range and haven't completed navigation yet
    public boolean b()
    {
        return !this.a.getNavigation().m() && this.driver.h(this.a) < (double) (distance * distance);
    }

    // Runs when c() is false
    public void d()
    {
        this.driver = null;
    }
}
