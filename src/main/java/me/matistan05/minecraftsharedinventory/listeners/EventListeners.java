package me.matistan05.minecraftsharedinventory.listeners;

import me.matistan05.minecraftsharedinventory.Main;
import me.matistan05.minecraftsharedinventory.classes.SharedInventoryGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.inventory.Inventory;
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
 * - EntitySpawnEvent - when spawning with spawn eggs, not possible in survival, total overkill
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
//        Bukkit.broadcastMessage("PlayerDropItemEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onPickupItem(EntityPickupItemEvent e) {
//        Bukkit.broadcastMessage("EntityPickupItemEvent");
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        SharedInventoryGame game = getGameFromPlayer(player.getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(player.getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(player.getName());
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent e) {
//        Bukkit.broadcastMessage("PlayerBucketEmptyEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent e) {
//        Bukkit.broadcastMessage("PlayerBucketFillEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
//        Bukkit.broadcastMessage("BlockPlaceEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
//        Bukkit.broadcastMessage("InventoryClickEvent");
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        SharedInventoryGame game = getGameFromPlayer(player.getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(player.getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(player.getName());
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
//        Bukkit.broadcastMessage("InventoryDragEvent");
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        SharedInventoryGame game = getGameFromPlayer(player.getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(player.getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(player.getName());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
//        Bukkit.broadcastMessage("PlayerDeathEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getEntity().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        game.setPlayerNameWithDifferentInventory(e.getEntity().getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
//        Bukkit.broadcastMessage("PlayerJoinEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        e.getPlayer().getInventory().setContents(game.getSharedInventoryContents());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
//        Bukkit.broadcastMessage("PlayerRespawnEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        e.getPlayer().getInventory().setContents(game.getSharedInventoryContents());
    }

    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent e) {
//        Bukkit.broadcastMessage("PlayerSwapHandItemsEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
//        Bukkit.broadcastMessage("PlayerItemDamageEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent e) {
//        Bukkit.broadcastMessage("PlayerItemConsumeEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
//        Bukkit.broadcastMessage("ProjectileLaunchEvent");
        if (!(e.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) e.getEntity().getShooter();
        SharedInventoryGame game = getGameFromPlayer(player.getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(player.getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(player.getName());
    }

    @EventHandler
    public void onArrowPickup(PlayerPickupArrowEvent e) {
//        Bukkit.broadcastMessage("PlayerPickupArrowEvent");
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onDispenseArmor(BlockDispenseArmorEvent e) {
//        Bukkit.broadcastMessage("BlockDispenseArmorEvent");//
        if (!(e.getTargetEntity() instanceof Player)) return;
        Player player = (Player) e.getTargetEntity();
        SharedInventoryGame game = getGameFromPlayer(player.getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(player.getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(player.getName());
    }

    @EventHandler
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
//        Bukkit.broadcastMessage("PlayerArmorStandManipulateEvent");//
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onLeadUse(PlayerLeashEntityEvent e) {
//        Bukkit.broadcastMessage("PlayerLeashEntityEvent");//
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onEntityShear(PlayerShearEntityEvent e) {
//        Bukkit.broadcastMessage("PlayerShearEntityEvent");//
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(e.getPlayer().getName())) {
                e.setCancelled(true);
            }
        } else game.setPlayerNameWithDifferentInventory(e.getPlayer().getName());
    }

    @EventHandler
    public void onVehicleCreate(VehicleCreateEvent e) {
//        Bukkit.broadcastMessage("VehicleCreateEvent");
        for (SharedInventoryGame game : games.values()) {
            if (!game.inProgress()) continue;
            if (game.getPlayerNameWithDifferentInventory() != null) {
                e.setCancelled(true);
            } else game.setPlayerNameWithDifferentInventory("");
        }
    }

    @EventHandler
    public void onCauldronLevelChange(CauldronLevelChangeEvent e) {
        Bukkit.broadcastMessage("CauldronLevelChangeEvent");
        for (SharedInventoryGame game : games.values()) {
            if (!game.inProgress()) continue;
            if (game.getPlayerNameWithDifferentInventory() != null) {
                e.setCancelled(true);
            } else game.setPlayerNameWithDifferentInventory("");
        }
    }

    @EventHandler
    public void onLodeStoneClick(PlayerInteractEvent e) { // todo: good
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getClickedBlock() == null) return;
        if (!e.getClickedBlock().getType().name().contains("LODESTONE")) return;
        if (!e.getPlayer().getInventory().getItemInMainHand().getType().name().contains("COMPASS")) return;
        Bukkit.broadcastMessage("LodeStoneSetEvent");
    }

    @EventHandler
    public void onItemFramePlace(HangingPlaceEvent e) {
//        Bukkit.broadcastMessage("HangingPlaceEvent");
        for (SharedInventoryGame game : games.values()) {
            if (!game.inProgress()) continue;
            if (game.getPlayerNameWithDifferentInventory() != null) {
                e.setCancelled(true);
            } else game.setPlayerNameWithDifferentInventory("");
        }
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
//        Bukkit.broadcastMessage("PlayerCommandPreprocessEvent");
        String command = e.getMessage().split(" ")[0].substring(1);
        if (commandEqual(command, "give") ||
                commandEqual(command, "clear") ||
                commandEqual(command, "enchant")) {
            for (SharedInventoryGame game : games.values()) {
                if (!game.inProgress()) continue;
                if (game.getPlayerNameWithDifferentInventory() != null) {
                    e.setCancelled(true);
                } else {
                    game.setCommandInitiated(true);
                    game.setPlayerNameWithDifferentInventory("");
                }
            }
        }
    }

    @EventHandler
    public void onConsoleCommand(ServerCommandEvent e) {
//        Bukkit.broadcastMessage("ServerCommandEvent");
        String command = e.getCommand().split(" ")[0];
        if (commandEqual(command, "give") ||
                commandEqual(command, "clear") ||
                commandEqual(command, "enchant")) {
            for (SharedInventoryGame game : games.values()) {
                if (!game.inProgress()) continue;
                if (game.getPlayerNameWithDifferentInventory() != null) {
                    e.setCancelled(true);
                } else {
                    game.setCommandInitiated(true);
                    game.setPlayerNameWithDifferentInventory("");
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        Player player = (Player) e.getPlayer();
        SharedInventoryGame game = getGameFromPlayer(e.getPlayer().getName());
        if (game == null) return;
        if (!game.inProgress()) return;
        ItemStack cursorItem = e.getPlayer().getItemOnCursor();
        Inventory topInv = player.getOpenInventory().getTopInventory();
        boolean cursorItemAir = cursorItem.getType().isAir();
        boolean topInvItemsAir = true;
        InventoryType topInvType = topInv.getType();
        if (cursorItemAir && notStaticInventory(topInvType)) {
            // first slot is the result of crafting, in inventory type CRAFTING and WORKBENCH
            // in type ENCHANTING, there is no result slot
            // and in the rest nonstatic inventories, result slot is the last slot
            for (int i = 0; i < topInv.getSize(); i++) {
                if (i == 0 && (topInvType == InventoryType.CRAFTING || topInvType == InventoryType.WORKBENCH)) continue;
                if (i + 1 == topInv.getSize() &&
                        topInvType != InventoryType.CRAFTING &&
                        topInvType != InventoryType.WORKBENCH &&
                        topInvType != InventoryType.ENCHANTING) break;
                ItemStack item = topInv.getItem(i);
                if (item != null && !item.getType().isAir()) {
                    topInvItemsAir = false;
                    break;
                }
            }
        }
        if (topInvItemsAir && cursorItemAir) return;
        if (game.getPlayerNameWithDifferentInventory() != null) {
            if (!game.getPlayerNameWithDifferentInventory().equals(player.getName())) {
                if (!cursorItemAir) {
                    player.getWorld().dropItem(player.getLocation(), cursorItem);
                    e.getPlayer().getItemOnCursor().setAmount(0);
                }
                if (!topInvItemsAir) {
                    for (int i = 0; i < topInv.getSize(); i++) {
                        if (i == 0 && (topInvType == InventoryType.CRAFTING || topInvType == InventoryType.WORKBENCH)) continue;
                        if (i + 1 == topInv.getSize() &&
                                topInvType != InventoryType.CRAFTING &&
                                topInvType != InventoryType.WORKBENCH &&
                                topInvType != InventoryType.ENCHANTING) break;
                        ItemStack item = topInv.getItem(i);
                        if (item != null && !item.getType().isAir()) {
                            player.getWorld().dropItem(player.getLocation(), item.clone());
                        }
                    }
                    e.getPlayer().getOpenInventory().getTopInventory().clear();
                }
            }
        } else game.setPlayerNameWithDifferentInventory(player.getName());
    }

    public static boolean commandEqual(String commandToCheck, String command) {
        return commandToCheck.equals(command) || commandToCheck.equals("minecraft:" + command);
    }

    public static SharedInventoryGame getGameFromPlayer(String playerName) {
        for (SharedInventoryGame game : games.values()) {
            if (game.isPlayer(playerName)) {
                return game;
            }
        }
        return null;
    }

    public static boolean notStaticInventory(InventoryType inventoryType) {
        return inventoryType == InventoryType.CRAFTING ||
                inventoryType == InventoryType.WORKBENCH ||
                inventoryType == InventoryType.SMITHING ||
                inventoryType == InventoryType.STONECUTTER ||
                inventoryType == InventoryType.GRINDSTONE ||
                inventoryType == InventoryType.LOOM ||
                inventoryType == InventoryType.ANVIL ||
                inventoryType == InventoryType.ENCHANTING ||
                inventoryType == InventoryType.MERCHANT;
    }
}
