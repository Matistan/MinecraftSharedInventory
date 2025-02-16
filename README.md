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
- type `/sharedinventory start <game>` to start!
- if you don't want to play with every player on the server, change the rule `playWithEveryone` to false, and choose the players using `/sharedinventory add` command

## Commands

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

| Key                 | Description                                                                                                                          | Type    | recommended                                                   |
|---------------------|--------------------------------------------------------------------------------------------------------------------------------------|---------|---------------------------------------------------------------|
| timeSetDayOnStart   | Set to true to set the time to day automatically when the game starts.                                                               | boolean | true                                                          |
| weatherClearOnStart | Set to true to set the weather to clear automatically when the game starts.                                                          | boolean | true                                                          |
| clearInventories    | Set to true to clear players inventories when the game starts (otherwise, they will play with first player on the list's inventory). | boolean | true                                                          |
| playWithEveryone    | Set to true to not have to use '/sharedinventory add' every time, and instead play with every player on the server                   | boolean | true                                                          |
| takeAwayOps         | Set to true to take away OPs for the duration of the game.                                                                           | boolean | true                                                          |
| usePermissions      | Set to true to require users to have permission to use certain commands.                                                             | boolean | false; true if you don't trust the people you're playing with |

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