# Minecraft Shared Inventory

---

View on [Spigot](https://www.spigotmc.org/resources/shared-inventory.109491/) •
Download [here](https://github.com/Matistan/MinecraftSharedInventory/releases)

---

> **Having issues?** Feel free to report them on the [Issues tab](https://github.com/Matistan/MinecraftSharedInventory/issues). I'll be glad to hear your opinion about the plugin as well as extra features you would like me to add!

## Welcome to readme!

Hi! I just want to thank you for your interest in this plugin. I put a lot of effort into this project and I would really love someone to use it!

### Minecraft version

This plugin runs on a Minecraft version 1.16+.

## What is a Shared Inventory?

With this fun little plugin, everyone shares the same inventory. This makes the gameplay very interesting!

## How to use it

- drag the .jar file from the [Release tab](https://github.com/Matistan/MinecraftSharedInventory/releases) to your plugins folder on your server.
- create a game using `/sharedinventory create <game>`
- add players to the game using `/sharedinventory add <game> <player> <player> ...`, or add everyone using `/sharedinventory add <game> @a`
- type `/sharedinventory start <game>` to start!

## Commands

- `/sharedinventory create <game>` - creates a game of shared inventory
- `/sharedinventory delete <game>` - deletes a game of shared inventory
- `/sharedinventory add <game> <player> <player> ... ` - adds players to the game
- `/sharedinventory add <game> @a` - adds all players to the game
- `/sharedinventory remove <game> <player> <player> ... ` - removes players rom the game
- `/sharedinventory remove <game> @a` - removes all players from the game
- `/sharedinventory start <game>` - starts the game
- `/sharedinventory reset <game>` - resets the game
- `/sharedinventory list <game>` - shows a list of players in the game of shared inventory
- `/sharedinventory rules <rule> value(optional)` - changes some additional rules of the game (in config.yml)
- `/sharedinventory help` - shows a list of shared inventory commands

## Configuration Options

Use the command `/sharedinventory rules` or edit the `plugins/MinecraftSharedInventory/config.yml` file to change the following options:

| Key                 | Description                                                                                                                                                          | Type    | recommended                                          |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------|------------------------------------------------------|
| timeSetDayOnStart   | Set to true to set the time to day automatically when the game starts.                                                                                               | boolean | true                                                 |
| weatherClearOnStart | Set to true to set the weather to clear automatically when the game starts.                                                                                          | boolean | true                                                 |
| survivalOnStart     | Set to true to set survival mode for all players when the game starts.                                                                                               | boolean | true                                                 |
| showSyncErrors      | Set to true to show errors in the chat when a player changed his inventory in a not implemented way. (in rare scenarios may cause item duplication or disappearance) | boolean | true                                                 |
| clearInventories    | Set to true to clear players inventories when the game starts (otherwise, they will play with the inventory of the first online player on the list).                 | boolean | true                                                 |
| takeAwayOps         | Set to true to take away OPs for the duration of the game.                                                                                                           | boolean | true                                                 |
| usePermissions      | Set to true to require users to have permission to use certain commands.                                                                                             | boolean | true; false if you trust players you're playing with |

## Quick info about sync errors

To prevent race conditions and item duplications or disappearances, plugin is looking for events that can change player's inventory. However, some events are impossible to keep track of, or I just haven't implemented them yet.
Sync errors are indicated by a dark red message in the chat (can be disabled in the config). If you see this message, please report it [here](https://github.com/Matistan/MinecraftSharedInventory/issues).

### Some known sync error occurrences

Sync errors can occur when:

- scroll clicking an item which is in inventory (not hotbar)
- inserting a disc into a jukebox
- clicking a lodestone with a compass
- removing a lodestone that a compass is pointing to
- placing armor stand
- placing item in item frame
- placing book in a lectern
- removing book from a lectern
- signing a book
- picking a fish with a water bucket
- filling respawn anchor with glowstone
- throwing an ender eye
- spawning an entity with a spawn egg
- placing a saddle on a pig/horse/strider
- using bone meal
- right-clicking an armor from a hotbar
- using name tags
- placing end crystals
- filling up crossbow with an arrow

Most of them are not so hard to implement, and will probably be fixed in a future. Again, it does not necessarily mean that some items will disappear or duplicate, it will only happen
if two inventory events happen in the same in-game tick.

## Permissions

If `usePermissions` is set to `true` in the `config.yml` file, players without ops will need the following permissions to use the commands:

| Permission                      | Description                                                     |
|---------------------------------|-----------------------------------------------------------------|
| sharedinventory.sharedinventory | Allows the player to use all `/sharedinventory` commands.       |
| sharedinventory.create          | Allows the player to use the `/sharedinventory create` command. |
| sharedinventory.delete          | Allows the player to use the `/sharedinventory delete` command. |
| sharedinventory.add             | Allows the player to use the `/sharedinventory add` command.    |
| sharedinventory.remove          | Allows the player to use the `/sharedinventory remove` command. |
| sharedinventory.start           | Allows the player to use the `/sharedinventory start` command.  |
| sharedinventory.reset           | Allows the player to use the `/sharedinventory reset` command.  |
| sharedinventory.list            | Allows the player to use the `/sharedinventory list` command.   |
| sharedinventory.help            | Allows the player to use the `/sharedinventory help` command.   |
| sharedinventory.rules           | Allows the player to use the `/sharedinventory rules` command.  |

### Bugs & Issues

> **Having issues?** Feel free to report them on the [Issues tab](https://github.com/Matistan/MinecraftSharedInventory/issues). I'll be glad to hear your opinion about the plugin as well as extra features you would like me to add!

Made by [Matistan](https://github.com/Matistan)