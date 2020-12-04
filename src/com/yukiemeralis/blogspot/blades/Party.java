package com.yukiemeralis.blogspot.blades;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Party 
{
    private ArrayList<Player> members = new ArrayList<>();

    public Party(Player creator)
    {
        join(creator);
    }

    public Player getLeader()
    {
        return members.get(0);
    }

    public ArrayList<Player> getMembers()
    {
        return members;
    }

    public void join(Player p)
    {
        BladeData.getAccount(p).setParty(this);
        members.add(p);
    }

    public void leave(Player p)
    {
        BladeData.getAccount(p).setParty(null);
        members.remove(p);
    }
}
