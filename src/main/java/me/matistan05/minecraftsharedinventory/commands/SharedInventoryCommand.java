package me.matistan05.minecraftsharedinventory.commands;

import me.matistan05.minecraftsharedinventory.Main;
import me.matistan05.minecraftsharedinventory.classes.SharedInventoryGame;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.*;

public class SharedInventoryCommand implements CommandExecutor {
    private static Main main;
    public static Map<String, SharedInventoryGame> games = new HashMap<>();

    public SharedInventoryCommand(Main main) {
        SharedInventoryCommand.main = main;
    }

    @Override
    public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "You must type an argument. For help, type: /sharedinventory help");
        } else if (args[0].equals("help")) {
            if (!p.hasPermission("sharedinventory.help") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + "------- " + ChatColor.WHITE + " Minecraft Shared Inventory " + ChatColor.GREEN + "----------");
            p.sendMessage(ChatColor.BLUE + "Here is a list of shared inventory commands:");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory create <game> " + ChatColor.AQUA + "- creates a new shared inventory game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory delete <game> " + ChatColor.AQUA + "- deletes a shared inventory game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory add <game> <player> <player> ... " + ChatColor.AQUA + "- adds players to the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory add <game> @a " + ChatColor.AQUA + "- adds all players to the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory remove <game> <player> <player> ... " + ChatColor.AQUA + "- removes players from the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory remove <game> @a " + ChatColor.AQUA + "- removes all players from the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory start <game> " + ChatColor.AQUA + "- starts the shared inventory game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory reset <game> " + ChatColor.AQUA + "- resets the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory list <game>(optional) " + ChatColor.AQUA + "- shows a list of shared inventory games, or players in a specified game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory rules <rule> value(optional) " + ChatColor.AQUA + "- changes some additional rules of the game (in config.yml)");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory help " + ChatColor.AQUA + "- shows a list of shared inventory commands");
            p.sendMessage(ChatColor.GREEN + "----------------------------------");
        } else if (args[0].equals("rules")) {
            if (!p.hasPermission("sharedinventory.rules") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 3 && args.length != 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (!main.getConfig().contains(args[1])) {
                p.sendMessage(ChatColor.RED + "There is no such rule. See the config.yml file for more information.");
                return true;
            }
            if (args.length == 2) {
                p.sendMessage(ChatColor.AQUA + "The value of the rule " + args[1] + " is: " + main.getConfig().get(args[1]));
                return true;
            }
            if (!args[2].equals("true") && !args[2].equals("false")) {
                p.sendMessage(ChatColor.RED + "The value must be true or false!");
                return true;
            }
            main.getConfig().set(args[1], Boolean.parseBoolean(args[2]));
            main.saveConfig();
            p.sendMessage(ChatColor.AQUA + "The value of the rule " + args[1] + " has been changed to: " + args[2]);
        } else if (args[0].equals("list")) {
            if (!p.hasPermission("sharedinventory.list") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length == 1) {
                if (games.isEmpty()) {
                    p.sendMessage(ChatColor.RED + "There are no shared inventory games!");
                    return true;
                }
                p.sendMessage(ChatColor.AQUA + "List of shared inventory games:");
                for (String game : games.keySet()) {
                    p.sendMessage(ChatColor.YELLOW + game);
                }
                return true;
            }
            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (!games.containsKey(args[1])) {
                p.sendMessage(ChatColor.RED + "There is no such game!");
                return true;
            }
            SharedInventoryGame game = games.get(args[1]);
            game.listPlayers(p);
        } else if (args[0].equals("create")) {
            if (!p.hasPermission("sharedinventory.create") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (games.containsKey(args[1])) {
                p.sendMessage(ChatColor.RED + "There is already a game with this name!");
                return true;
            }
            games.put(args[1], new SharedInventoryGame(main));
            p.sendMessage(ChatColor.AQUA + "Successfully created a new shared inventory game!");
        } else if (args[0].equals("delete")) {
            if (!p.hasPermission("sharedinventory.delete") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (!games.containsKey(args[1])) {
                p.sendMessage(ChatColor.RED + "There is no such game!");
                return true;
            }
            SharedInventoryGame game = games.get(args[1]);
            game.reset();
            games.remove(args[1]);
            p.sendMessage(ChatColor.AQUA + "Successfully deleted shared inventory game!");
        } else if (args[0].equals("add")) {
            if (!p.hasPermission("sharedinventory.add") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length < 3 || (args[2].equals("@a") && args.length != 3)) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (!games.containsKey(args[1])) {
                p.sendMessage(ChatColor.RED + "There is no such game!");
                return true;
            }
            SharedInventoryGame game = games.get(args[1]);
            game.addPlayers(p, args);
        } else if (args[0].equals("remove")) {
            if (!p.hasPermission("sharedinventory.remove") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length < 3 || (args[1].equals("@a") && args.length != 3)) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (!games.containsKey(args[1])) {
                p.sendMessage(ChatColor.RED + "There is no such game!");
                return true;
            }
            SharedInventoryGame game = games.get(args[1]);
            game.removePlayers(p, args);
        } else if (args[0].equals("reset")) {
            if (!p.hasPermission("sharedinventory.reset") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (!games.containsKey(args[1])) {
                p.sendMessage(ChatColor.RED + "There is no such game!");
                return true;
            }
            SharedInventoryGame game = games.get(args[1]);
            p.sendMessage(ChatColor.AQUA + "shared inventory game has been reset!");
            game.reset();
        } else if (args[0].equals("start")) {
            if (!p.hasPermission("sharedinventory.start") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (!games.containsKey(args[1])) {
                p.sendMessage(ChatColor.RED + "There is no such game!");
                return true;
            }
            SharedInventoryGame game = games.get(args[1]);
            game.startGame(p);
        } else {
            p.sendMessage(ChatColor.RED + "Wrong argument. For help, type: /sharedinventory help");
        }
        return true;
    }

    public static boolean isAlreadyInGame(List<String> playersToAdd) {
        for (SharedInventoryGame game : games.values()) {
            for (String player : playersToAdd) {
                if (game.isPlayer(player)) {
                    return true;
                }
            }
        }
        return false;
    }
}
