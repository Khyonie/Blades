package com.yukiemeralis.blogspot.blades.entities.npcs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.entities.npcs.skins.SkinData;

import org.bukkit.Location;

import net.minecraft.server.v1_16_R3.DataWatcher;
import net.minecraft.server.v1_16_R3.DataWatcherObject;
import net.minecraft.server.v1_16_R3.DataWatcherRegistry;
import net.minecraft.server.v1_16_R3.EntityPlayer;

public class NPC 
{
    //
    // Implementation of a local NPC
    //

    GameProfile profile;
    String name;
    SkinData skin;

    String alias;

    EntityPlayer avatar;
    Blade host;

    DataWatcher watcher;

    /**
     * An instance of a player NPC
     * @param name
     * @param avatar
     */
    public NPC(Blade host, EntityPlayer avatar)
    {
        this.name = host.getName();
        this.avatar = avatar;
        this.profile = avatar.getProfile();

        watcher = avatar.getDataWatcher();
        watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a),(byte)127);

        NPCManager.npcs.put(host, this);
    }

    public String getName()
    {
        return this.name;
    }

    public GameProfile getGameProfile()
    {
        return this.profile;
    }

    public void setHost(Blade host)
    {
        this.host = host;
    }

    public Blade getHost()
    {
        return this.host;
    }

    public SkinData getSkinData()
    {
        return this.skin;
    }

    public void linkSkin(SkinData skin)
    {
        this.skin = skin;

        avatar.getProfile().getProperties().put("textures", new Property("textures", this.skin.value, this.skin.signature));
    }

    public DataWatcher getWatcher()
    {
        return this.watcher;
    }

    public void spawn(Location loc)
    {
        avatar.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getYaw());
        teleport(loc);
        NPCManager.showSkinnedNPC(host);
    }

    public void despawn(boolean removeFromMemory)
    {
        NPCManager.destroyNPC(host, removeFromMemory);
    }

    public void teleport(Location l)
    {
        NPCManager.teleportNPC(host, l);
    }

    public EntityPlayer getAvatarData()
    {
        return this.avatar;
    }
}
