package com.yukiemeralis.blogspot.blades.entities.npcs;

import java.util.HashMap;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.utils.PacketManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;
import net.minecraft.server.v1_16_R3.WorldServer;

public class NPCManager 
{
    //
    // Buffer layer between the local NPC and the server NPC
    // All NPC actions should come from NPC, but they all reflect actions in here
    //

    public static HashMap<Blade, NPC> npcs = new HashMap<>();

    public static EntityPlayer createNPC(Entity host, String name)
    {
        Location location = host.getLocation();

        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorld(host.getWorld().getName())).getHandle(); // Change "world" to the world the NPC should be spawned in.
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name); // Change "playername" to the name the NPC should have, max 16 characters.
        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld)); // This will be the EntityPlayer (NPC) we send with the sendNPCPacket method.
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        return npc;
    }

    public static void destroyNPC(Blade name, boolean removeFromMemory)
    {
        Bukkit.getOnlinePlayers().forEach(target -> {
            PacketManager.hideEntity(npcs.get(name).getAvatarData(), target);
        });

        if (removeFromMemory)
            npcs.remove(name);
    }

    public static void showNPC(Blade name, boolean addPlayer)
    {
        Bukkit.getOnlinePlayers().forEach(target -> {
            PacketManager.showPlayerEntity(npcs.get(name).getAvatarData(), target, addPlayer);
        });
    }

    public static void showSkinnedNPC(Blade name)
    {
        Bukkit.getOnlinePlayers().forEach(target -> {
            PacketManager.spawnSkinnedNPC(target, npcs.get(name));
            PacketManager.hideEntity(name.getAvatar(), target);
        });
    }

    public static void teleportNPC(Blade name, Location l)
    {
        npcs.get(name).getAvatarData().setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());

        Bukkit.getOnlinePlayers().forEach(target -> {
            PacketManager.teleportPlayerEntity(npcs.get(name).getAvatarData(), target);
            PacketManager.hideEntity(name.getAvatar(), target);
        });
    }
}
