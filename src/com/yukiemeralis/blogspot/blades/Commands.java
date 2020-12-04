package com.yukiemeralis.blogspot.blades;

import com.yukiemeralis.blogspot.blades.customblades.PlayerBlade;
import com.yukiemeralis.blogspot.blades.customspecials.PlayerBladeSpecial;
import com.yukiemeralis.blogspot.blades.customspecials.SpecialRegister;
import com.yukiemeralis.blogspot.blades.enums.Element;
import com.yukiemeralis.blogspot.blades.enums.Role;
import com.yukiemeralis.blogspot.blades.enums.WeaponType;
import com.yukiemeralis.blogspot.blades.guis.EngageGUI;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor 
{
    /**
     * Just so I can keep my thoughts in line, here's a list of commands we have.
     * /blade - Opens GUI
     * /blade createplayer <element> <role> <weapon> - Creates a player blade account so this player can act as a blade
     * /blade resonate <name> - Resonate with a blade
     * /blade swap - Swap from being a driver to being a blade
     * /blade revert - Revert from a blade to a driver
     */

    /**
     * - Change 1 -
     * /blade create <name> + /blade resonate <name> ->
     * /blade resonate <name>
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) 
    {
        String subcmd;
        String target;

        PlayerAccount account;

        if (arguments.length == 0)
        {
            // Open GUI
            EngageGUI gui = new EngageGUI();
            gui.init((Player) sender);

            ((Player) sender).openInventory(gui.getInventory());
            return true;
        } else {
            // Attempt to develop command request and args

            subcmd = arguments[0];

            // We're assuming the sender is a player. I know. Bad practice.

            switch (subcmd)
            {
                case "createplayer":
                    if (arguments.length < 4)
                    {
                        sender.sendMessage("§cNot enough arguments! Required: element, role, weapon.");
                    } else {
                        // We do all the checking in the method.
                        createBladeAccountCmd((Player) sender, arguments[1], arguments[2], arguments[3]);
                    }

                    return true;
                case "resonate":
                    if (arguments.length < 2)
                    {
                        sender.sendMessage("§cResonate usage: /blade resonate <name>");
                        return true;
                    }

                    if (Bukkit.getPlayerExact(arguments[1]) != null)
                    {
                        resonatePlayerBladeCmd((Player) sender, arguments[1]);
                    } else {
                        createBladeCmd((Player) sender, arguments[1]);
                    }

                    return true;

                case "swap":
                    account = BladeData.getAccount((Player) sender);
                    if (account.getPlayerBladeAccount() != null)
                    {
                        if (!account.isDriver())
                        {
                            sender.sendMessage("§eYou are already a blade!");
                            return true;
                        }

                        account.becomeBlade();
                        sender.sendMessage("§aYou've become a blade!");
                    } else {
                        sender.sendMessage("§cYou must have a blade account to become a blade. Use /blade createplayer first!");
                    }

                    return true;

                case "revert":
                    account = BladeData.getAccount((Player) sender);

                    // It's impossible to be a blade and not have a blade account, so we don't check that
                    if (account.isDriver())
                    {
                        sender.sendMessage("§eYou are already a driver!");
                        return true;
                    }

                    account.becomeDriver();
                    sender.sendMessage("§aYou've become a driver!");
                    return true;
                case "restore":
                    account = BladeData.getAccount((Player) sender);
                    account.setVirtualHP(account.getMaxVirtualHP());

                    sender.sendMessage("§aCommand executed without incident.");
                    return true;
                case "set":
                    if (arguments.length == 2)
                    {
                        sender.sendMessage("§cA slot and special name must be provided!");
                        return true;
                    }

                    setBladeSpecialCmd((Player) sender, arguments[1], arguments[2]);

                    return true;
                case "listspecials":
                    sender.sendMessage("§6---[§a Specials§6 ]---");
                    SpecialRegister.getRegistry().forEach((name, special) -> {
                        sender.sendMessage("§e" + special.getDisplayName() + " (" + name + ")");
                    });

                    return true;
                case "affinity":
                    if (BladeData.getAccount((Player) sender).getCurrentBlade() == null)
                    {
                        sender.sendMessage("§cYou have no blade equipped!");
                        return true;
                    }

                    ((Player) sender).openInventory(BladeData.getAccount((Player) sender).getCurrentBlade().getAffinityChart().toInventory());
                    
                    return true;
                case "setvhp":
                    if (arguments[1].equals("all"))
                    {
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            BladeData.getAccount(player).setMaxVirtualHP(Integer.valueOf(arguments[2]));

                            if (arguments[3].equals("true"))
                            {
                                BladeData.getAccount(player).setVirtualHP(BladeData.getAccount(player).getMaxVirtualHP());
                            }
                        });
                    } else {
                        account = BladeData.getAccount(Bukkit.getPlayerExact(arguments[1]));
                        account.setMaxVirtualHP(Integer.valueOf(arguments[2]));

                        if (arguments[3].equals("true"))
                        {
                            account.setVirtualHP(account.getMaxVirtualHP());
                        }
                    }

                    sender.sendMessage("§aCommand executed without incident.");
                    return true;
                case "raisetrust":
                    int delta = Integer.valueOf(arguments[1]);

                    BladeData.getAccount((Player) sender).getCurrentBlade().raiseTrust(delta);
                    return true;
                case "party":
                    account = BladeData.getAccount((Player) sender);

                    switch (arguments[1])
                    {
                        case "create":
                            if (account.getParty() != null)
                            {
                                sender.sendMessage("§cYou're already in a party! Leave with /blade party leave.");
                                return true;
                            }

                            new Party((Player) sender).join((Player) sender);
                            sender.sendMessage("§aCreated a new party!");

                            break;
                        case "join":
                            if (account.getParty() != null)
                            {
                                sender.sendMessage("§cYou're already in a party! Leave with /blade party leave.");
                                return true;
                            }
                            target = arguments[2];

                            if (Bukkit.getPlayerExact(target) == null)
                            {
                                sender.sendMessage("§cNo party/player exists by that name!");
                                return true;
                            }

                            if (BladeData.getAccount(Bukkit.getPlayerExact(target)).getParty() == null) 
                            {
                                sender.sendMessage("§cThe player is not in a party!");
                                return true;
                            }

                            BladeData.getAccount(Bukkit.getPlayerExact(target)).getParty().join((Player) sender);
                            sender.sendMessage("§aJoined party!");
                            break;
                        case "leave":
                            if (account.getParty() != null)
                            {
                                sender.sendMessage("§cYou aren't in a party!");
                                return true;
                            }

                            account.getParty().leave((Player) sender);

                            break;
                    }
                    return true;
                default: 
                    sender.sendMessage("§c" + subcmd + " is not a recognized subcommand!");
                    return true;
            }
        }
    }
    
    //
    // /blade create <name>
    //
    private void createBladeCmd(Player sender, String name)
    {
        if (BladeRegistry.getRegistry().get(name) == null)
        {
            sender.sendMessage("No blade exists by that name!");
            return;
        }

        Blade blade = (Blade) BladeRegistry.getRegistry().get(name).clone();
        BladeData.getAccount(sender).resonateBlade(blade);

        sender.sendMessage("§aSuccessfully resonated with " + blade.name + "! Engage them by using /blade.");
    }

    //
    // /blade createplayer <element> <role> <weapon>
    //
    private void createBladeAccountCmd(Player sender, String elementStr, String roleStr, String weaponStr)
    {
        Element element;
        Role role;
        WeaponType type;

        // Blade stats
        try {
            element = Element.valueOf(elementStr.toUpperCase());
        } catch (IllegalArgumentException error) {
            sender.sendMessage("§c" + elementStr + " is not a recognized element type!");
            return;
        }

        try {
            role = Role.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException error) {
            sender.sendMessage("§c" + roleStr + " is not a recognized blade role type!");
            return;
        }

        try {
            type = WeaponType.valueOf(weaponStr.toUpperCase());
        } catch (IllegalArgumentException error) {
            sender.sendMessage("§c" + weaponStr + " is not a recognized blade weapon type!");
            return;
        }

        // After this point we can assume that the given arguments are valid.
        PlayerAccount account = BladeData.getAccount(sender);
        PlayerBladeAccount blade_account = new PlayerBladeAccount(sender.getDisplayName(), element, role, type);

        account.setPlayerBladeAccount(blade_account);

        sender.sendMessage("§aYou've created a blade account! Ask a prospective driver to use /blade resonate " + sender.getDisplayName() + " to become their blade.");
    }


    //
    // /blade resonate <player>
    //
    private void resonatePlayerBladeCmd(Player sender, String blade_target)
    {
        Player player_blade = Bukkit.getPlayerExact(blade_target);

        if (!player_blade.isOnline())
        {
            sender.sendMessage("§cPlayer " + blade_target + " is not online!");
            return;
        }

        // Add the blade to the driver's stock and set the blade's driver to their driver
        PlayerAccount driver = BladeData.getAccount(sender);
        PlayerAccount blade_act = BladeData.getAccount(player_blade);

        // Make sure that both parties are ready to go
        if (!driver.isDriver())
        {
            sender.sendMessage("§cYou are currently a blade! Use /blade revert to become a driver again.");
            return;
        }
        if (blade_act.isDriver())
        {
            sender.sendMessage("§c" + blade_target + " is currently a driver! Drivers cannot be engaged as blades.");
            return;
        }

        Blade blade = blade_act.getPlayerBladeAccount().getBladeData();
        blade.setDriver(sender);
        blade.getAffinityChart().setOwner(blade);

        driver.resonateBlade(blade);
        blade_act.getPlayerBladeAccount().setDriver(sender);

        // Finally, inform both parties of their new statuses
        sender.sendMessage("§aSuccessfully resonated with " + player_blade.getDisplayName() + "!");
        player_blade.sendMessage("§aYou've become the blade of " + sender.getDisplayName() + "!");
    }

    // /blade set <slot> <special>
    private void setBladeSpecialCmd(Player sender, String slotStr, String name)
    {
        if (BladeData.getAccount(sender).getPlayerBladeAccount() == null)
        {
            sender.sendMessage("§cYou don't have a blade account! Create one with /blade createplayer.");
            return;
        }

        int slot;
        try {
            slot = Integer.valueOf(slotStr);

            if (slot < 1 || slot > 4)
                throw new NumberFormatException();
        } catch (NumberFormatException error) {
            sender.sendMessage("§cFirst argument must be a number between 1 and 4!");
            return;
        }
        
        PlayerBladeSpecial special = SpecialRegister.getSpecial(name);

        if (special == null)
        {
            sender.sendMessage("§c\"" + name + "\" is not a recognized blade special!");
            return;
        }

        PlayerBlade data = (PlayerBlade) BladeData.getAccount(sender).getPlayerBladeAccount().getBladeData();
        data.setBladeSpecial(slot, special);
        sender.sendMessage("§aEquipped \"" + name + "\" into slot " + slot + "!");

        // Replace the blade instance in the registry
        BladeRegistry.getRegistry().put(sender.getDisplayName(), data);
    }
}
