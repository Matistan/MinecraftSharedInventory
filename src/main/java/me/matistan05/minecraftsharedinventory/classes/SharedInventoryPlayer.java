package me.matistan05.minecraftsharedinventory.classes;

import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

public class SharedInventoryPlayer {
    private final String name;
    private boolean op;
    private ItemStack[] oldInventory;
    private GameMode oldGameMode;

    public SharedInventoryPlayer(String name) {
        this.name = name;
        this.op = false;
        this.oldInventory = null;
        this.oldGameMode = GameMode.SURVIVAL;
    }

    public String getName() {
        return name;
    }

    public boolean isOp() {
        return op;
    }

    public void setOp(boolean op) {
        this.op = op;
    }

    public ItemStack[] getOldInventory() {
        return oldInventory;
    }

    public void setOldInventory(ItemStack[] oldInventory) {
        this.oldInventory = oldInventory;
    }

    public GameMode getOldGameMode() {
        return oldGameMode;
    }

    public void setOldGameMode(GameMode oldGameMode) {
        this.oldGameMode = oldGameMode;
    }
}
