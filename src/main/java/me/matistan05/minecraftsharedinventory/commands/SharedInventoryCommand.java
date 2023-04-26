package me.matistan05.minecraftsharedinventory.commands;

import me.matistan05.minecraftsharedinventory.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SharedInventoryCommand implements CommandExecutor {
    public static BukkitTask game;
    public static boolean inGame = false;
    public static List<String> players = new LinkedList<>();
    Inventory inventory;
    public static List<Boolean> ops = new LinkedList<>();
    private static Main main;
    public SharedInventoryCommand(Main main) {
        SharedInventoryCommand.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if(args.length == 0) {
            p.sendMessage(ChatColor.RED + "You must type an argument. For help, type: /sharedinventory help");
            return true;
        }
        if(args[0].equals("help")) {
            if(args.length != 1)
            {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + "------- " + ChatColor.WHITE + " Minecraft Shared Inventory " + ChatColor.GREEN + "----------");
            p.sendMessage(ChatColor.BLUE + "Here is a list of shared inventory commands:");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory add <player> <player> ... " + ChatColor.AQUA + "- adds players to the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory remove <player> <player> ... " + ChatColor.AQUA + "- removes  players from the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory start " + ChatColor.AQUA + "- starts the shared inventory game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory reset " + ChatColor.AQUA + "- resets the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory list " + ChatColor.AQUA + "- shows a list of players in shared inventory game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory help " + ChatColor.AQUA + "- shows a list of shared inventory commands");
            p.sendMessage(ChatColor.GREEN + "----------------------------------");
            return true;
        }
        if(args[0].equals("list")) {
            if(args.length != 1)
            {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if(players.size() == 0) {
                p.sendMessage(ChatColor.RED + "There is no player in your game!");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + "------- " + ChatColor.WHITE + " Minecraft shared inventory " + ChatColor.GREEN + "----------");
            for (String player : players) {
                p.sendMessage(ChatColor.AQUA + player);
            }
            p.sendMessage(ChatColor.GREEN + "----------------------------------");
            return true;
        }
        if (args[0].equals("add")) {
            if(args.length < 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            int count = 0;
            for(int i = 1; i < args.length; i++) {
                Player target = Bukkit.getPlayerExact(args[i]);
                if(target == null || players.contains(target.getName())) {continue;}
                players.add(target.getName());
                count++;
            }
            if(count > 0) {
                p.sendMessage(ChatColor.AQUA + "Successfully added " + count + " player" + (count == 1 ? "" : "s") + " to the game!");
            } else {
                p.sendMessage(ChatColor.RED + "Could not add " + (args.length == 2 ? "this player!" : "these players!"));
            }
            return true;
        }
        if (args[0].equals("remove")) {
            if(args.length < 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /manhunt help");
                return true;
            }
            int count = 0;
            for(int i = 1; i < args.length; i++) {
                Player target = Bukkit.getPlayerExact(args[i]);
                if(target == null || !players.contains(target.getName())) {continue;}
                players.remove(target.getName());
                count++;
            }
            if(count > 0) {
                p.sendMessage(ChatColor.AQUA + "Successfully removed " + count + " player" + (count == 1 ? "" : "s") + " from the game!");
            } else {
                p.sendMessage(ChatColor.RED + "Could not remove " + (args.length == 2 ? "this player!" : "these players!"));
            }
            if(players.size() == 0) {
                reset();
            }
            return true;
        }
        if(args[0].equals("reset")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            p.sendMessage(ChatColor.AQUA + "shared inventory game has been reset!");
            reset();
            return true;
        }
        if(args[0].equals("start")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (players.size() == 0) {
                p.sendMessage(ChatColor.RED + "There are no players in your game!");
                return true;
            }
            if (players.size() == 1) {
                p.sendMessage(ChatColor.RED + "There must be at least 2 players!");
                return true;
            }
            if (inGame) {
                p.sendMessage(ChatColor.YELLOW + "The game has already started!");
                return true;
            }
            if(main.getConfig().getBoolean("timeSetDayOnStart")) {
                p.getWorld().setTime(0);
            }
            if(main.getConfig().getBoolean("weatherClearOnStart")) {
                p.getWorld().setStorm(false);
            }
            for (String t : players) {
                Player player = Bukkit.getPlayerExact(t);
                if(player == null) {continue;}
                player.getInventory().clear();
                if(main.getConfig().getBoolean("takeAwayOps")) {
                    ops.add(player.isOp());
                    player.setOp(false);
                }
                player.setGameMode(GameMode.SURVIVAL);
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setSaturation(20);
            }
            inGame = true;
            playersMessage(ChatColor.AQUA + "START!");
            inventory = Bukkit.createInventory(p, InventoryType.PLAYER);
            game = new BukkitRunnable() {
                @Override
                public void run() {
                    for(int i = 0; i < players.size(); i++) {
                        Player player = Bukkit.getPlayerExact(players.get(i));
                        if(player == null) {continue;}
                        if(!inventoryEqual(player.getInventory(), inventory)) {
                            for(int j = 0; j < players.size(); j++) {
                                Player target = Bukkit.getPlayerExact(players.get(j));
                                if(target == null || i == j) {continue;}
                                target.getInventory().setContents(player.getInventory().getContents());
                                inventory.setContents(player.getInventory().getContents());
                            }
                        }
                    }
                }
            }.runTaskTimer(main, 0, 1);
            return true;
        }
        p.sendMessage(ChatColor.RED + "Wrong argument. For help, type: /sharedinventory help");
        return true;
    }
    public static void reset() {
        if(inGame) {
            inGame = false;
            game.cancel();
            if(main.getConfig().getBoolean("takeAwayOps")) {
                for (int i = 0; i < players.size(); i++) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(players.get(i));
                    target.setOp(ops.get(i));
                }
            }
        }
        ops.clear();
        players.clear();
    }
    public static void playersMessage(String s) {
        for (String value : players) {
            Player player = Bukkit.getPlayerExact(value);
            if(player == null) {continue;}
            player.sendMessage(s);
        }
    }
    public static boolean inventoryEqual(Inventory player, Inventory player1) {
        for(int i = 0; i < 41; i++) {
            if (i == 36) {
                i = 40;
            }
            if(!Objects.equals(player.getItem(i), player1.getItem(i))) {
                return false;
            }
        }
        return true;
    }
}