package net.estopia.estopia;

import net.estopia.estopia.Commands.GamemodeCommand;
import net.estopia.estopia.Commands.setSpawnCommand;
import net.estopia.estopia.Commands.spawnCommand;
import net.estopia.estopia.Listeners.PlayerJoinListeners;
import net.estopia.estopia.Listeners.PlayerLeaveListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Estopia extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListeners(this), this);
        getCommand("gamemode").setExecutor(new GamemodeCommand(this));
        getCommand("setspawn").setExecutor(new setSpawnCommand(this));
        getCommand("spawn").setExecutor(new spawnCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
