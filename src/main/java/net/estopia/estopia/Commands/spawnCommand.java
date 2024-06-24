package net.estopia.estopia.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.estopia.estopia.Estopia;

public class spawnCommand implements CommandExecutor {
    private final Estopia plugin;
    public spawnCommand(Estopia plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            String youMustBeAPlayerMessage = this.plugin.getConfig().getString("messages.you-Must-Be-A-Player");
            if(youMustBeAPlayerMessage != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', youMustBeAPlayerMessage));
            }
            return true;
        }

        if (!sender.hasPermission("estopia.spawn")) {
            sendNoPermsMessageFromConfig((Player) sender);
            return true;
        }

        Player player = (Player) sender;

        Location location = plugin.getConfig().getLocation("spawn");

        if (location == null) {
            String messageSpawnTeleport = this.plugin.getConfig().getString("messages.spawn.no-location");
            if(messageSpawnTeleport != null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageSpawnTeleport));
            }

            return true;
        }

        player.teleport(location);

        String messageSpawnTeleport = this.plugin.getConfig().getString("messages.spawn.teleport");
        if(messageSpawnTeleport != null) {
            messageSpawnTeleport = messageSpawnTeleport.replace("%player%", player.getDisplayName());
            messageSpawnTeleport = messageSpawnTeleport.replace("%x%", String.valueOf(location.getX()));
            messageSpawnTeleport = messageSpawnTeleport.replace("%y%", String.valueOf(location.getY()));
            messageSpawnTeleport = messageSpawnTeleport.replace("%z%", String.valueOf(location.getZ()));
            messageSpawnTeleport = messageSpawnTeleport.replace("%world%", location.getWorld().getName());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageSpawnTeleport));
        }

        return true;
    }

    private void sendNoPermsMessageFromConfig(Player player) {
        String noPermission = this.plugin.getConfig().getString("messages.no-Permission");
        if(noPermission != null) {
            noPermission = noPermission.replace("%player%", player.getDisplayName());
            noPermission = noPermission.replace("%permission%", "estopia.spawn");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
        }
    }
}
