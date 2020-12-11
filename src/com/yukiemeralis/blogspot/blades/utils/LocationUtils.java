package com.yukiemeralis.blogspot.blades.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;

public class LocationUtils 
{
    private static Random random = new Random();

    /**
     * Creates a list of locations with <count> locations with variance of vx, vy, and vz
     * @param vx Variance X
     * @param vy Variance Y
     * @param vz Variance Z
     */
    public static List<Location> getLocationCloud(Location origin, double vx, double vy, double vz, int count)
    {
        ArrayList<Location> buffer = new ArrayList<>();

        double[] variance = new double[3];

        // Initialize with zeroes
        for (int i = 0; i < variance.length; i++)
            variance[i] = 0.0;

        // Set values
        for (int i = 0; i < count; i++)
        {
            variance[0] = generateVariance(vx);
            variance[1] = generateVariance(vy);
            variance[2] = generateVariance(vz);

            for (double d : variance)
                if (random.nextBoolean())
                    d = d * -1;

            buffer.add(origin.add(variance[0], variance[1], variance[2]));
        }

        return buffer;
    }

    private static double generateVariance(double variance)
    {
        double buffer = variance * random.nextDouble();
        return buffer;
    }
}
