package me.matistan05.minecraftsharedinventory;

import me.matistan05.minecraftsharedinventory.classes.SharedInventoryGame;
import me.matistan05.minecraftsharedinventory.commands.SharedInventoryCommand;
import me.matistan05.minecraftsharedinventory.commands.SharedInventoryCompleter;
import me.matistan05.minecraftsharedinventory.listeners.EventListeners;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginCommand("sharedinventory").setExecutor(new SharedInventoryCommand(this));
        getCommand("sharedinventory").setTabCompleter(new SharedInventoryCompleter(this));
        new EventListeners(this);
        new Metrics(this, 21883);
        System.out.println("*********************************************************\n" +
                "Thank you for using this plugin! <3\n" +
                "Author: Matistan\n" +
                "If you enjoy this plugin, please rate it on spigotmc.org:\n" +
                "https://www.spigotmc.org/resources/shared-inventory.109491/\n" +
                "*********************************************************");
    }

    @Override
    public void onDisable() {
        for (SharedInventoryGame game : SharedInventoryCommand.games.values()) {
            game.reset();
        }
    }
}
