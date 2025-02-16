package me.matistan05.minecraftsharedinventory.classes;

import me.matistan05.minecraftsharedinventory.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.stream.Collectors;

import static me.matistan05.minecraftsharedinventory.commands.SharedInventoryCommand.isAlreadyInGame;

public class SharedInventoryGame {
    private final Main main;
    private final List<SharedInventoryPlayer> players = new ArrayList<>();
    private BukkitTask game;
    private boolean inGame;
    private Inventory sharedInventory;
    // Name of a player that changed the inventory (set to "" when command was initiated)
    private String playerNameWithDifferentInventory = null;
    // If a command was initiated by a player or a console, this is set to true
    private boolean commandInitiated = false;

    public SharedInventoryGame(Main main) {
        this.main = main;
    }

    public void addPlayers(CommandSender p, String[] args) {
        List<String> playersToAdd;
        if (args[2].equals("@a")) {
            playersToAdd = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        } else {
            playersToAdd = Arrays.stream(args).skip(2).collect(Collectors.toList());
            playersToAdd = playersToAdd.stream().filter(name -> Bukkit.getPlayerExact(name) != null).collect(Collectors.toList());
        }
        playersToAdd = playersToAdd.stream().filter(name -> !isPlayer(name)).collect(Collectors.toList());
        if (isAlreadyInGame(playersToAdd)) {
            p.sendMessage(ChatColor.RED + "You can't add players that are in another shared inventory game!");
            return;
        }
        int count = 0;
        for (String name : playersToAdd) {
            if (isPlayer(name)) continue;
            players.add(new SharedInventoryPlayer(name));
            if (inGame) setUpPlayer(name, true);
            count++;
        }
        if (count > 0) {
            p.sendMessage(ChatColor.AQUA + "Successfully added " + count + " player" + (count == 1 ? "" : "s") + " to the game!");
        } else {
            p.sendMessage(ChatColor.RED + "No player was added!");
        }
    }

    public void removePlayers(CommandSender p, String[] args) {
        int count = 0;
        List<String> playersToRemove;
        if (args[2].equals("@a")) {
            if (inGame) {
                p.sendMessage(ChatColor.RED + "You can't remove all players while in game!");
                return;
            }
            playersToRemove = players.stream().map(SharedInventoryPlayer::getName).collect(Collectors.toList());
        } else {
            playersToRemove = Arrays.stream(args).skip(2).collect(Collectors.toList());
            playersToRemove = playersToRemove.stream().filter(this::isPlayer).collect(Collectors.toList());
            if (inGame) {
                if (players.size() - playersToRemove.size() == 0) {
                    p.sendMessage(ChatColor.RED + "There must be at least one player in the game!");
                    return;
                }
            }
        }
        for (String playerName : playersToRemove) {
            removePlayer(playerName);
            count++;
        }
        if (count > 0) {
            p.sendMessage(ChatColor.AQUA + "Successfully removed " + count + " player" + (count == 1 ? "" : "s") + " from the game!");
        } else {
            p.sendMessage(ChatColor.RED + "No player was removed!");
        }
    }

    public void listPlayers(CommandSender p) {
        if (main.getConfig().getBoolean("playWithEveryone")) {
            p.sendMessage(ChatColor.AQUA + "Everyone is in the game!");
            return;
        }
        if (players.isEmpty()) {
            p.sendMessage(ChatColor.RED + "There is no player in your game!");
            return;
        }
        p.sendMessage(ChatColor.YELLOW + "-------" + ChatColor.WHITE + " Minecraft shared inventory " + ChatColor.YELLOW + "----------");
        if (inGame) {
            p.sendMessage(ChatColor.RED + "Game is in progress!");
        } else {
            p.sendMessage(ChatColor.GREEN + "Game is not in progress!");
        }
        for (SharedInventoryPlayer playerObject : players) {
            p.sendMessage(ChatColor.AQUA + playerObject.getName());
        }
        p.sendMessage(ChatColor.YELLOW + "----------------------------------");
    }

    public void reset() {
        if (inGame) {
            inGame = false;
            game.cancel();
        }
        while (!players.isEmpty()) {
            removePlayer(players.get(0).getName());
        }
    }

    public void startGame(CommandSender p) {
        if (main.getConfig().getBoolean("playWithEveryone")) {
            players.clear();
            for (Player target : Bukkit.getOnlinePlayers()) {
                players.add(new SharedInventoryPlayer(target.getName()));
            }
        }
        if (players.isEmpty()) {
            p.sendMessage(ChatColor.RED + "There are no players in your game!");
            return;
        }
        if (inGame) {
            p.sendMessage(ChatColor.YELLOW + "The game has already started!");
            return;
        }
        if (main.getConfig().getBoolean("timeSetDayOnStart")) {
            p.getServer().getWorlds().get(0).setTime(0);
        }
        if (main.getConfig().getBoolean("weatherClearOnStart")) {
            p.getServer().getWorlds().get(0).setStorm(false);
        }
        if (main.getConfig().getBoolean("clearInventories")) {
            sharedInventory = Bukkit.createInventory(null, InventoryType.PLAYER);
        } else {
            Player player = Bukkit.getPlayerExact(players.get(0).getName());
            if (player == null) sharedInventory = Bukkit.createInventory(null, InventoryType.PLAYER);
            else sharedInventory.setContents(player.getInventory().getContents());
        }
        for (SharedInventoryPlayer playerObject : players) {
            setUpPlayer(playerObject.getName(), !main.getConfig().getBoolean("clearInventories"));
        }
        inGame = true;
        playerNameWithDifferentInventory = null;
        commandInitiated = false;
        playersMessage(ChatColor.AQUA + "START!");
        game = new BukkitRunnable() {
            @Override
            public void run() {
                int changedInventories = 0;
                Player playerWithDifferentInventory = null;
                for (SharedInventoryPlayer playerObject : players) {
                    Player player = Bukkit.getPlayerExact(playerObject.getName());
                    if (player == null) continue;
                    if (!inventoryEqual(sharedInventory, player.getInventory())) {
                        changedInventories += 1;
                        if (playerWithDifferentInventory == null) playerWithDifferentInventory = player;
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

                    for (SharedInventoryPlayer playerObject : players) {
                        Player player = Bukkit.getPlayerExact(playerObject.getName());
                        if (player == null || playerWithNewInventory.getName().equals(player.getName())) continue;
                        player.getInventory().setContents(playerWithNewInventory.getInventory().getContents());
                    }
                    sharedInventory.setContents(playerWithNewInventory.getInventory().getContents());
                }

                playerNameWithDifferentInventory = null;
                commandInitiated = false;
            }
        }.runTaskTimer(main, 0, 1);
    }

    private void removePlayer(String name) {
        SharedInventoryPlayer playerObject = getPlayer(name);
        if (playerObject == null) return;
        if (main.getConfig().getBoolean("takeAwayOps")) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
            target.setOp(playerObject.isOp());
        }
        Player player = Bukkit.getPlayerExact(name);
        if (player != null) {
            player.setGameMode(playerObject.getOldGameMode());
            player.getInventory().setContents(playerObject.getOldInventory());
        }
        players.removeIf(p -> p.getName().equals(name));
    }

    private void setUpPlayer(String name, boolean setSharedInventory) {
        Player player = Bukkit.getPlayerExact(name);
        if (player == null) return;
        SharedInventoryPlayer playerObject = getPlayer(player.getName());
        if (playerObject == null) return;
        playerObject.setOldGameMode(player.getGameMode());
        player.setGameMode(GameMode.SURVIVAL);
        playerObject.setOldInventory(player.getInventory().getContents().clone());
        if (setSharedInventory) player.getInventory().setContents(sharedInventory.getContents());
        else player.getInventory().clear();
        if (main.getConfig().getBoolean("takeAwayOps")) {
            playerObject.setOp(player.isOp());
            player.setOp(false);
        }
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(5);
    }

    private void playersMessage(String message) {
        for (SharedInventoryPlayer playerObject : players) {
            Player player = Bukkit.getPlayerExact(playerObject.getName());
            if (player == null) continue;
            player.sendMessage(message);
        }
    }

    private static boolean inventoryEqual(Inventory inv1, Inventory inv2) {
        if (inv1.getSize() != inv2.getSize()) return false;
        for (int i = 0; i < inv1.getSize(); i++) {
            if (!Objects.equals(inv1.getItem(i), inv2.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    private SharedInventoryPlayer getPlayer(String name) {
        return players.stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
    }

    public boolean isPlayer(String name) {
        return players.stream().anyMatch(p -> p.getName().equals(name));
    }

    public Collection<SharedInventoryPlayer> getPlayers() {
        return players;
    }
}
