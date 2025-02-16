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
    private static Main main;
    public static BukkitTask game;
    public static boolean inGame = false;
    public static List<String> players = new LinkedList<>();
    public static List<Boolean> ops = new LinkedList<>();
    public static Inventory sharedInventory;
    // Name of a player that changed the inventory (set to "" when command was initiated)
    public static String playerNameWithDifferentInventory = null;
    // If a command was initiated by a player or a console, this is set to true
    public static boolean commandInitiated = false;

    public SharedInventoryCommand(Main main) {
        SharedInventoryCommand.main = main;
    }

    @Override
    public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "You must type an argument. For help, type: /sharedinventory help");
            return true;
        }
//        if (args[0].equals("print")) {//todo: remove this
//            if (!(p instanceof Player)) {
//                p.sendMessage(ChatColor.RED + "You must be a player to use this command!");
//                return true;
//            }
//            Player player = (Player) p;
//            printInventory(player, player.getInventory());
//            return true;
//        }
//        if (args[0].equals("printShared")) {
//            if (!(p instanceof Player)) {
//                p.sendMessage(ChatColor.RED + "You must be a player to use this command!");
//                return true;
//            }
//            Player player = (Player) p;
//            printInventory(player, sharedInventory);
//            return true;
//        }
        if (args[0].equals("help")) {
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
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory add <player> <player> ... " + ChatColor.AQUA + "- adds players to the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory add @a " + ChatColor.AQUA + "- adds all players");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory remove <player> <player> ... " + ChatColor.AQUA + "- removes  players from the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory remove @a " + ChatColor.AQUA + "- removes all players");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory start " + ChatColor.AQUA + "- starts the shared inventory game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory reset " + ChatColor.AQUA + "- resets the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory list " + ChatColor.AQUA + "- shows a list of players in shared inventory game");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory rules <rule> value(optional) " + ChatColor.AQUA + "- changes some additional rules of the game (in config.yml)");
            p.sendMessage(ChatColor.YELLOW + "/sharedinventory help " + ChatColor.AQUA + "- shows a list of shared inventory commands");
            p.sendMessage(ChatColor.GREEN + "----------------------------------");
            return true;
        }
        if (args[0].equals("rules")) {
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
            return true;
        }
        if (args[0].equals("list")) {
            if (!p.hasPermission("sharedinventory.list") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (main.getConfig().getBoolean("playWithEveryone")) {
                p.sendMessage(ChatColor.AQUA + "Everyone is in the game!");
                return true;
            }
            if (players.isEmpty()) {
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
            if (!p.hasPermission("sharedinventory.add") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length < 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            int count = 0;
            if (args[1].equals("@a")) {
                if (args.length != 2) {
                    p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                    return true;
                }
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (players.contains(target.getName())) continue;
                    players.add(target.getName());
                    if (inGame) setUpPlayer(target, true);
                    count++;
                }
                if (count > 0) {
                    p.sendMessage(ChatColor.AQUA + "Successfully added " + count + " player" + (count == 1 ? "" : "s") + " to the game!");
                } else {
                    p.sendMessage(ChatColor.RED + "No player was added!");
                }
                return true;
            }
            for (int i = 1; i < args.length; i++) {
                Player target = Bukkit.getPlayerExact(args[i]);
                if (target == null || players.contains(target.getName())) continue;
                players.add(target.getName());
                if (inGame) setUpPlayer(target, true);
                count++;
            }
            if (count > 0) {
                p.sendMessage(ChatColor.AQUA + "Successfully added " + count + " player" + (count == 1 ? "" : "s") + " to the game!");
            } else {
                p.sendMessage(ChatColor.RED + "Could not add " + (args.length == 2 ? "this player!" : "these players!"));
            }
            return true;
        }
        if (args[0].equals("remove")) {
            if (!p.hasPermission("sharedinventory.remove") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length < 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            int count = 0;
            if (args[1].equals("@a")) {
                if (args.length != 2) {
                    p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                    return true;
                }
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (!players.contains(target.getName()) || (inGame && players.size() == 2)) continue;
                    removePlayer(target.getName());
                    count++;
                }
                if (count > 0) {
                    p.sendMessage(ChatColor.AQUA + "Successfully removed " + count + " player" + (count == 1 ? "" : "s") + " from the game!");
                } else {
                    p.sendMessage(ChatColor.RED + "No player was removed!");
                }
                return true;
            }
            for (int i = 1; i < args.length; i++) {
                Player target = Bukkit.getPlayerExact(args[i]);
                if (target == null || !players.contains(target.getName()) || (inGame && players.size() == 2)) continue;
                players.remove(target.getName());
                count++;
            }
            if (count > 0) {
                p.sendMessage(ChatColor.AQUA + "Successfully removed " + count + " player" + (count == 1 ? "" : "s") + " from the game!");
            } else {
                p.sendMessage(ChatColor.RED + "Could not remove " + (args.length == 2 ? "this player!" : "these players!"));
            }
            if (players.isEmpty()) {
                reset();
            }
            return true;
        }
        if (args[0].equals("reset")) {
            if (!p.hasPermission("sharedinventory.reset") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            p.sendMessage(ChatColor.AQUA + "shared inventory game has been reset!");
            reset();
            return true;
        }
        if (args[0].equals("start")) {
            if (!p.hasPermission("sharedinventory.start") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedinventory help");
                return true;
            }
            if (main.getConfig().getBoolean("playWithEveryone")) {
                players.clear();
                for (Player target : Bukkit.getOnlinePlayers()) {
                    players.add(target.getName());
                }
            }
            if (players.isEmpty()) {
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
            if (main.getConfig().getBoolean("timeSetDayOnStart")) {
                p.getServer().getWorlds().get(0).setTime(0);
            }
            if (main.getConfig().getBoolean("weatherClearOnStart")) {
                p.getServer().getWorlds().get(0).setStorm(false);
            }
            for (String t : players) {
                Player player = Bukkit.getPlayerExact(t);
                setUpPlayer(player, false);
            }
            inGame = true;
            playerNameWithDifferentInventory = null;
            commandInitiated = false;
            playersMessage(ChatColor.AQUA + "START!");
            sharedInventory = Bukkit.createInventory(null, InventoryType.PLAYER);
            game = new BukkitRunnable() {
                @Override
                public void run() {
                    int changedInventories = 0;
                    Player playerWithDifferentInventory = null;
                    for (String player : players) {
                        Player target = Bukkit.getPlayerExact(player);
                        if (target == null) continue;
                        if (!inventoryEqual(sharedInventory, target.getInventory())) {
                            changedInventories += 1;
                            if (playerWithDifferentInventory == null) playerWithDifferentInventory = target;
                        }
                    }

                    if (changedInventories >= 1) {
                        if ((changedInventories > 1 || playerNameWithDifferentInventory == null) && !commandInitiated) {
                            playersMessage(ChatColor.DARK_RED + "Sync error! Someone has changed the inventory in a not implemented way!");
                        }

                        Player playerWithNewInventory;

                        if (playerNameWithDifferentInventory != null && !commandInitiated) {
                            playerWithNewInventory = Bukkit.getPlayerExact(playerNameWithDifferentInventory);
                            if (playerWithNewInventory == null) {
                                playerWithNewInventory = playerWithDifferentInventory;
                            }
                        } else {
                            playerWithNewInventory = playerWithDifferentInventory;
                        }

                        for (String player : players) {
                            Player target = Bukkit.getPlayerExact(player);
                            if (target == null || playerWithNewInventory.getName().equals(target.getName())) continue;
                            target.getInventory().setContents(playerWithNewInventory.getInventory().getContents());
                        }
                        sharedInventory.setContents(playerWithNewInventory.getInventory().getContents());
                    }

                    playerNameWithDifferentInventory = null;
                    commandInitiated = false;
                }
            }.runTaskTimer(main, 0, 1);
            return true;
        }
        p.sendMessage(ChatColor.RED + "Wrong argument. For help, type: /sharedinventory help");
        return true;
    }

    private void removePlayer(String name) {
        int index = players.indexOf(name);
        players.remove(index);
        if (main.getConfig().getBoolean("takeAwayOps")) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
            target.setOp(ops.get(index));
            ops.remove(index);
        }
    }

    public static void reset() {
        if (inGame) {
            inGame = false;
            game.cancel();
            if (main.getConfig().getBoolean("takeAwayOps")) {
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
            if (player == null) continue;
            player.sendMessage(s);
        }
    }

    public static boolean inventoryEqual(Inventory inv1, Inventory inv2) {
        if (inv1.getSize() != inv2.getSize()) return false;
        for (int i = 0; i < inv1.getSize(); i++) {
            // why was I not checking armor slots???
//            if (i == 36) {
//                i = 40;
//            }
            if (!Objects.equals(inv1.getItem(i), inv2.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    public static void setUpPlayer(Player player, boolean setSharedInventory) {
        if (player == null) return;
        if (setSharedInventory) player.getInventory().setContents(sharedInventory.getContents());
        else player.getInventory().clear();
        if (main.getConfig().getBoolean("takeAwayOps")) {
            ops.add(player.isOp());
            player.setOp(false);
        }
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(5);
    }

    public static void printInventory(Player player, Inventory inventory) {
        playersMessage("Inventory size: " + inventory.getSize());
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) player.sendMessage(i + ": null");
            else if (inventory.getItem(i).getType().isAir()) player.sendMessage(i + ": air");
            else player.sendMessage(i + ": " + inventory.getItem(i).getType() + " x" + inventory.getItem(i).getAmount());
        }
    }
}
