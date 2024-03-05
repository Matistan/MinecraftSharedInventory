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
- select players to your game using `/sharedinventory add` command
- type `/sharedinventory start` to start!

## Commands

- `/sharedinventory add <player> <player> ... ` - adds players
- `/sharedinventory remove <player> <player> ... ` - removes players
- `/sharedinventory start` - starts the game
- `/sharedinventory reset` - resets the game
- `/sharedinventory list` - shows a list of players in the game of shared inventory
- `/sharedinventory help` - shows a list of shared inventory commands

## Configuration Options

Edit the `plugins/MinecraftSharedInventory/config.yml` file to change the following options:

| Key                 | Description                                                                 | Type    | recommended                                                   |
|---------------------|-----------------------------------------------------------------------------|---------|---------------------------------------------------------------|
| timeSetDayOnStart   | Set to true to set the time to day automatically when the game starts.      | boolean | true                                                          |
| weatherClearOnStart | Set to true to set the weather to clear automatically when the game starts. | boolean | true                                                          |
| takeAwayOps         | Set to true to take away OPs for the duration of the game.                  | boolean | false; true, if you play with friends                         |
| usePermissions      | Set to true to require users to have permission to use certain commands.    | boolean | false; true if you don't trust the people you're playing with |

## Permissions

If `usePermissions` is set to `true` in the `config.yml` file, players without ops will need the following permissions to use the commands:

| Permission                      | Description                                                     |
|---------------------------------|-----------------------------------------------------------------|
| sharedinventory.sharedinventory | Allows the player to use all `/sharedinventory` commands.       |
| sharedinventory.add             | Allows the player to use the `/sharedinventory add` command.    |
| sharedinventory.remove          | Allows the player to use the `/sharedinventory remove` command. |
| sharedinventory.start           | Allows the player to use the `/sharedinventory start` command.  |
| sharedinventory.reset           | Allows the player to use the `/sharedinventory reset` command.  |
| sharedinventory.list            | Allows the player to use the `/sharedinventory list` command.   |
| sharedinventory.help            | Allows the player to use the `/sharedinventory help` command.   |

### Bugs & Issues

> **Having issues?** Feel free to report them on the [Issues tab](https://github.com/Matistan/MinecraftSharedInventory/issues). I'll be glad to hear your opinion about the plugin as well as extra features you would like me to add!

Made by [Matistan](https://github.com/Matistan)