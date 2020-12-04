package com.yukiemeralis.blogspot.blades.customspecials;

import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.Party;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class PlayerBladeSpecial implements Cloneable
{
    String name;
    String displayName;
    String description;

    Element element;
    Role role;

    public PlayerBladeSpecial(String name, String displayName, String description, Element element, Role role)
    {
        this.name = name;
        this.description = description;
        this.displayName = displayName;

        this.element = element;
        this.role = role;

        SpecialRegister.register(this);
    }

    public abstract void runEffect(Entity target, Player driver);

    public String getName()
    {
        return this.name;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public String getDescription()
    {
        return this.description;
    }

    public Element getElement()
    {
        return this.element;
    }

    protected void healParty(Player healer, Party party, double amount)
    {
        party.getMembers().forEach(player -> {
            if (player != healer)
            {
                if (healer.getLocation().distanceSquared(player.getLocation()) <= (30 * 30))
                {
                    BladeData.getAccount(player).healVirtualHP(amount);
                }
            }
        });
    }

    @Override
    public Object clone()
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException error) {
            error.printStackTrace();
            return null;
        }
    }
}
