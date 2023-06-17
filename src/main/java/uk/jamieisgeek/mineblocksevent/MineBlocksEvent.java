package uk.jamieisgeek.mineblocksevent;

import org.bukkit.plugin.java.JavaPlugin;
import uk.jamieisgeek.mineblocksevent.Commands.AdminCommand;
import uk.jamieisgeek.sootlib.Registers.CommandRegisterer;

import java.lang.reflect.InvocationTargetException;

public final class MineBlocksEvent extends JavaPlugin {
    private static GameManager gameManager;

    @Override
    public void onEnable() {
        gameManager = new GameManager(this);

        this.getCommand("event").setExecutor(new AdminCommand());
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);

        getLogger().info("MineBlocksEvent has been enabled!");
    }
    @Override
    public void onDisable() {
        getLogger().info("MineBlocksEvent has been disabled!");
    }

    public static GameManager getGameManager() {
        return gameManager;
    }
}
