package com.yukiemeralis.blogspot.blades.utils;

import com.yukiemeralis.blogspot.blades.entities.npcs.NPC;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.DataWatcher;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class PacketManager 
{
    //
    // Purely server-side things to make and manage packets
    //

    /**
    * Hide an NMS entity from a player
    * @param entity
    */
    public static void hideEntity(Entity entity, Player target)
    {
        ((CraftPlayer) target).getHandle().playerConnection
            .sendPacket(new PacketPlayOutEntityDestroy(entity.getId()));
    }

    /**
     * Show an NMS NPC to a player
     * @param npc
     * @param target
     */
    public static void showPlayerEntity(EntityPlayer npc, Player target, boolean addPlayer)
    {
        PlayerConnection connection = ((CraftPlayer) target).getHandle().playerConnection;

        if (addPlayer)
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc)); // "Adds the player data for the client to use when spawning a player" - https://wiki.vg/Protocol#Spawn_Player
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc)); // Spawns the NPC for the player client.
    }

    public static void teleportPlayerEntity(EntityPlayer npc, Player target)
    {
        PlayerConnection connection = ((CraftPlayer) target).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityTeleport(npc));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
    }

    /**
     * Updates an NPC's skin for all players
     */
    public static void setNPCSkin(Player target, NPC npc)
    {
        DataWatcher watcher = npc.getWatcher();
        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(npc.getAvatarData().getId(), watcher, true);
        ((CraftPlayer) target).getHandle().playerConnection.sendPacket(packet);
    }

    public static void spawnSkinnedNPC(Player target, NPC npc)
    {
        // Skin data
        PacketPlayOutEntityMetadata skinLayer = new PacketPlayOutEntityMetadata(npc.getAvatarData().getId(), npc.getWatcher(), true);

        PlayerConnection connection = ((CraftPlayer) target).getHandle().playerConnection;

        connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] {npc.getAvatarData()}));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc.getAvatarData()));
        connection.sendPacket(skinLayer);
    }
}
