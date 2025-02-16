package me.matistan05.minecraftsharedinventory.listeners;

import me.matistan05.minecraftsharedinventory.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.inventory.ItemStack;

import static me.matistan05.minecraftsharedinventory.commands.SharedInventoryCommand.*;

/**
 * Not needed listeners:
 * - InventoryCreativeEvent
 * - InventoryMoveItemEvent ?
 * - PlayerFishEvent - pickup and item damage events are fine
 * - InventoryInteractEvent, InventoryEvent - they don't do anything
 * - BlockDispenseEvent - just BlockDispenseArmorEvent handles all player-inventory-related events
 * - InventoryCreativeEvent - always fires with other events, so it's not needed
 * - EntityBreedEvent - only when a baby is born
 * - PlayerEggThrowEvent - only after egg hits the ground, projectileLaunchEvent is enough
 * <p>
 * Maybe needed but would be overkill:
 * - EntitySpawnEvent - when spawning with spawn eggs
 * - PlayerInteractEntityEvent - can be achieved by more specific events, would be overkill
 * - BlockBreakEvent - I don't care about this event when someone broke item with shovel,
 * just ItemDamageEvent is enough
 * - InventoryMoveItemEvent - I think this event never changes player's inventory
 * - PlayerItemBreakEvent - ItemDamage event does the thing
 */

public class EventListeners implements Listener {
    Main main;

    public EventListeners(Main main) {
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    // Item on cursor is the one after the drop
    // e.getPlayer().getInventory() - always after drop, even after calling e.setCancelled(true);
    // If item is dropped from open inventory, it is then cancelled by InventoryClickEvent
    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent e) {
//        Bukkit.broadcastMessage("EntityPickupItemEvent");
        if (!inGame || !(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if (!players.contains(player.getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(player.getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = player.getName();
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent e) {
//        Bukkit.broadcastMessage("PlayerBucketEmptyEvent");
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent e) {
//        Bukkit.broadcastMessage("PlayerBucketFillEvent");
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
//        Bukkit.broadcastMessage("BlockPlaceEvent");
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
//        Bukkit.broadcastMessage("InventoryClickEvent");
        if (!inGame || !(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        if (!players.contains(player.getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(player.getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = player.getName();
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
//        Bukkit.broadcastMessage("InventoryDragEvent");
        if (!inGame || !(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        if (!players.contains(player.getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(player.getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = player.getName();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
//        Bukkit.broadcastMessage("PlayerDeathEvent");
        if (!inGame || !players.contains(e.getEntity().getName())) return;
        playerNameWithDifferentInventory = e.getEntity().getName();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
//        Bukkit.broadcastMessage("PlayerJoinEvent");
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        e.getPlayer().getInventory().setContents(sharedInventory.getContents());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
//        Bukkit.broadcastMessage("PlayerRespawnEvent");
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        e.getPlayer().getInventory().setContents(sharedInventory.getContents());
    }

    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent e) {
//        Bukkit.broadcastMessage("PlayerSwapHandItemsEvent");
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
//        Bukkit.broadcastMessage("PlayerItemDamageEvent");
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent e) {
//        Bukkit.broadcastMessage("PlayerItemConsumeEvent");
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
//        Bukkit.broadcastMessage("ProjectileLaunchEvent");
        if (!inGame || !(e.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) e.getEntity().getShooter();
        if (!players.contains(player.getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(player.getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = player.getName();
    }

    @EventHandler
    public void onArrowPickup(PlayerPickupArrowEvent e) {
//        Bukkit.broadcastMessage("PlayerPickupArrowEvent");
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onDispenseArmor(BlockDispenseArmorEvent e) {
//        Bukkit.broadcastMessage("BlockDispenseArmorEvent");//
        if (!inGame || !(e.getTargetEntity() instanceof Player)) return;
        Player player = (Player) e.getTargetEntity();
        if (!players.contains(player.getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(player.getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = player.getName();
    }

    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
//        Bukkit.broadcastMessage("PlayerArmorStandManipulateEvent");//
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onLeadUse(PlayerLeashEntityEvent e) {
//        Bukkit.broadcastMessage("PlayerLeashEntityEvent");//
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onEntityShear(PlayerShearEntityEvent e) {
//        Bukkit.broadcastMessage("PlayerShearEntityEvent");//
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else playerNameWithDifferentInventory = e.getPlayer().getName();
    }

    @EventHandler
    public void onVehicleCreate(VehicleCreateEvent e) {
//        Bukkit.broadcastMessage("VehicleCreateEvent");
        if (!inGame) return;
        if (playerNameWithDifferentInventory != null) {
            e.setCancelled(true);
        } else playerNameWithDifferentInventory = "";
    }

//    @EventHandler
//    public void onCrafting(CraftItemEvent e) {//todo: check if this is needed
//        Bukkit.broadcastMessage("CraftItemEvent");//
//        if (!inGame || !(e.getWhoClicked() instanceof Player)) return;
//        Player player = (Player) e.getWhoClicked();
//        if (!players.contains(player.getName())) return;
//        if (playerNameWithDifferentInventory != null) {
//            if (!playerNameWithDifferentInventory.equals(player.getName())) {
//                e.setCancelled(true);
//            }
//        } else playerNameWithDifferentInventory = player.getName();
//    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
//        Bukkit.broadcastMessage("PlayerCommandPreprocessEvent");
        if (!inGame) return;
        String command = e.getMessage().split(" ")[0].substring(1);
        if (commandEqual(command, "give") ||
                commandEqual(command, "clear") ||
                commandEqual(command, "enchant")) {
            if (playerNameWithDifferentInventory != null) {
                e.setCancelled(true);
            } else {
                commandInitiated = true;
                playerNameWithDifferentInventory = "";
            }
        }
    }

    @EventHandler
    public void onConsoleCommand(ServerCommandEvent e) {
//        Bukkit.broadcastMessage("ServerCommandEvent");
        if (!inGame) return;
        String command = e.getCommand().split(" ")[0];
        if (commandEqual(command, "give") ||
                commandEqual(command, "clear") ||
                commandEqual(command, "enchant")) {
            if (playerNameWithDifferentInventory != null) {
                e.setCancelled(true);
            } else {
                commandInitiated = true;
                playerNameWithDifferentInventory = "";
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
//        Bukkit.broadcastMessage("InventoryCloseEvent, cursor: " + e.getPlayer().getItemOnCursor().getType());
        if (!inGame || !(e.getPlayer() instanceof Player)) return;
        Player player = (Player) e.getPlayer();
        if (!players.contains(player.getName())) return;
        ItemStack cursorItem = e.getPlayer().getItemOnCursor();
        ItemStack[] craftingContents = player.getOpenInventory().getTopInventory().getContents();
        boolean cursorOrCraftingItemsNotAir = !cursorItem.getType().isAir();
        if (!cursorOrCraftingItemsNotAir && player.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING) {
            // first slow is the result of crafting, no need to check for that
            for (int i = 1; i < craftingContents.length; i++) {
                if (!craftingContents[i].getType().isAir()) {
                    cursorOrCraftingItemsNotAir = true;
                    break;
                }
            }
        }
        if (!cursorOrCraftingItemsNotAir) return;
        if (playerNameWithDifferentInventory != null) {
            if (!playerNameWithDifferentInventory.equals(player.getName())) {
                player.getWorld().dropItem(player.getLocation(), cursorItem);
                e.getPlayer().getItemOnCursor().setAmount(0);
                if (player.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING) {
                    for (int i = 1; i < craftingContents.length; i++) {
                        if (!craftingContents[i].getType().isAir()) {
                            player.getWorld().dropItem(player.getLocation(), craftingContents[i]);
                        }
                    }
                    e.getPlayer().getOpenInventory().getTopInventory().clear();
                }
            }
        } else playerNameWithDifferentInventory = player.getName();
    }

    public static boolean commandEqual(String commandToCheck, String command) {
        return commandToCheck.equals(command) || commandToCheck.equals("minecraft:" + command);
    }
}
