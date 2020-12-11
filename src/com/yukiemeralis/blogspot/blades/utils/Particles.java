package com.yukiemeralis.blogspot.blades.utils;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Particles 
{

    /**
     * A utility class that handles drawing of particles into the world in various shapes.
     */

    public static <T> void drawLine(Location loc1, Location loc2, double interval, Particle particle, T data)
    {
        // Check to see if both locations are in the same world
        if (!loc1.getWorld().equals(loc2.getWorld()))
            return;
        World world = loc1.getWorld();

        Vector vec1 = loc1.toVector();
        Vector vec2 = loc2.toVector(); 
        Vector vecf = vec2.subtract(vec1).normalize().multiply(interval);

        double distance = loc1.distance(loc2);
        double length = 0;

        for (; length < distance; vec1.add(vecf))
        {
            world.spawnParticle(particle, vec1.getX(), vec1.getY(), vec1.getZ(), 0, 0, 0, 0, data);
            length += interval;
        }
    }

    public static <T> void drawCircle(Location location, double radius, double density, Particle particle, T data)
    {
        World world = location.getWorld();

        double increment = (2 * Math.PI) / density;
        ArrayList<Location> locations = new ArrayList<Location>();

        for (int i = 0; i < density; i++)
        {
            double angle = i * increment;
            double x = location.getX() + (radius * Math.cos(angle));
            double z = location.getZ() + (radius * Math.sin(angle));

            locations.add(new Location(world, x, location.getY(), z));
        }

        locations.forEach(loc -> {
            world.spawnParticle(particle, loc.setDirection(new Vector(0, 0, 0)), 0, 0, 0, 0, data);
        });
    }

    public static <T> void drawCylinder(Location location, double radius, int density, int stacks, double distance, Particle particle, T data)
    {
        Location buffer = location.clone();

        for (int i = 0; i < stacks; i++)
        {
            // Draw circle
            drawCircle(buffer, radius, density, particle, data);

            // Add height to buffer to create a cylinder
            buffer = buffer.add(0, distance, 0);
        }
    }

    public static <T> void drawSphere(Location location, double radius, int rows, int density, Particle particle, T data)
    {
        for (double phi = 0; phi < Math.PI; phi += Math.PI / rows) // Default 15
        {
            // z = z0 + radius cos(phi)
            double y = radius * Math.cos(phi) + 1.5;

            for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / density) // Default 30
            {
                // x = x0 + radius * cos(phi) * sin(theta)
                double x = radius * Math.cos(theta) * Math.sin(phi);
                double z = radius * Math.sin(theta) * Math.sin(phi);

                location.add(x, y, z);
                location.getWorld().spawnParticle(particle, location, 0, 0, 0, 0, data);
                location.subtract(x, y, z);
            }
        }
    }

    public static <T> void drawDisc(Location location, double radius, int layers, int density, Particle particle, T data)
    {
        for (int i = 0; i < layers; i++)
        {
            drawCircle(location, radius + (i/2), density + i, particle, data);
        }
    }
}
