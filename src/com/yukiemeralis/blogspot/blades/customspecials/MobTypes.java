package com.yukiemeralis.blogspot.blades.customspecials;

import java.util.ArrayList;

import org.bukkit.entity.EntityType;

@SuppressWarnings("serial")
public class MobTypes 
{
    public static ArrayList<EntityType> passive = new ArrayList<>() {{
        add(EntityType.BAT);
        add(EntityType.BEE);
        add(EntityType.CAT);
        add(EntityType.CHICKEN);
        add(EntityType.COD);
        add(EntityType.COW);
        add(EntityType.DOLPHIN);
        add(EntityType.DONKEY);
        add(EntityType.FOX);
        add(EntityType.HORSE);
        add(EntityType.IRON_GOLEM);
        add(EntityType.LLAMA);
        add(EntityType.MULE);
        add(EntityType.MUSHROOM_COW);
        add(EntityType.OCELOT);
        add(EntityType.PANDA);
        add(EntityType.PARROT);
        add(EntityType.PIG);
        add(EntityType.POLAR_BEAR);
        add(EntityType.PUFFERFISH);
        add(EntityType.RABBIT);
        add(EntityType.SALMON);
        add(EntityType.SHEEP);
        add(EntityType.SNOWMAN);
        add(EntityType.SQUID);
        add(EntityType.TRADER_LLAMA);
        add(EntityType.TROPICAL_FISH);
        add(EntityType.TURTLE);
        add(EntityType.VILLAGER);
        add(EntityType.WANDERING_TRADER);
        add(EntityType.WOLF);
    }};

    public static ArrayList<EntityType> nether = new ArrayList<>() {{
        add(EntityType.BLAZE);
        add(EntityType.GHAST);
        add(EntityType.HOGLIN);
        add(EntityType.MAGMA_CUBE);
        add(EntityType.PIGLIN);
        add(EntityType.PIGLIN_BRUTE);
        add(EntityType.ZOMBIFIED_PIGLIN);
        add(EntityType.WITHER_SKELETON);
        add(EntityType.WITHER);
        add(EntityType.ZOGLIN);
    }};

    public static ArrayList<EntityType> end = new ArrayList<>() {{
        add(EntityType.ENDERMAN);
        add(EntityType.ENDERMITE);
        add(EntityType.ENDER_DRAGON);
        add(EntityType.SHULKER);
    }};

    public static ArrayList<EntityType> boss = new ArrayList<>() {{
        add(EntityType.ENDER_DRAGON);
        add(EntityType.WITHER);
        add(EntityType.ELDER_GUARDIAN);
    }};

    public static ArrayList<EntityType> undead = new ArrayList<>() {{
        add(EntityType.DROWNED);
        add(EntityType.SKELETON);
        add(EntityType.STRAY);
        add(EntityType.WITHER);
        add(EntityType.WITHER_SKELETON);
        add(EntityType.ZOGLIN);
        add(EntityType.ZOMBIE);
        add(EntityType.ZOMBIE_VILLAGER);
        add(EntityType.ZOMBIFIED_PIGLIN);
    }};
}
