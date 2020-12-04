package com.yukiemeralis.blogspot.blades.guis;

import java.util.ArrayList;

import com.yukiemeralis.blogspot.blades.Blade;
import com.yukiemeralis.blogspot.blades.BladeData;
import com.yukiemeralis.blogspot.blades.Party;
import com.yukiemeralis.blogspot.blades.PlayerAccount;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class PartyScoreboard 
{
    ArrayList<Player> targets = new ArrayList<>();

    Scoreboard board;

    public PartyScoreboard(Party party)
    {
        targets = party.getMembers();

        init();
    }

    public PartyScoreboard(Player creator)
    {
        targets.add(creator);
        init();
    }

    public void init()
    {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("header", "dummy", "§3-[ §bParty §3]-");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        targets.forEach(member -> {
            PlayerAccount account = BladeData.getAccount(member);

            String name = Blade.elementToColor(account.getCurrentBlade().getElement()) + member.getDisplayName();

            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.getScore("§7[" + name + "§7] » ").setScore((int) Math.round(account.getVirtualHP()));;
        });
    }

    public void update()
    {
        Objective obj = board.getObjective("header");

        targets.forEach(member -> {
            PlayerAccount account = BladeData.getAccount(member);

            String name = Blade.elementToColor(account.getCurrentBlade().getElement()) + member.getDisplayName();

            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.getScore("§7[" + name + "§7] » ").setScore((int) Math.round(account.getVirtualHP()));;
        });
    }

    public void show()
    {
        targets.forEach(member -> {
            member.setScoreboard(board);
        });
    }

    public void destroy()
    {
        targets.forEach(member -> {
            member.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        });
    }
}
