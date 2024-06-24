package net.estopia.estopia.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.estopia.estopia.Estopia;

public class setSpawnCommand implements CommandExecutor {
        private final Estopia plugin;
        public setSpawnCommand(Estopia plugin){
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

            Player player = (Player) sender;

            if (!sender.hasPermission("estopia.setspawn")) {
                sendNoPermsMessageFromConfig(player);
                return true;
            }

            Location location = player.getLocation();

            plugin.getConfig().set("spawn.x", location.getX());
            plugin.getConfig().set("spawn.y", location.getY());
            plugin.getConfig().set("spawn.z", location.getZ());
            plugin.getConfig().set("spawn.world", location.getWorld().getName());

            plugin.saveConfig();

            String messageSpawnSet = this.plugin.getConfig().getString("messages.spawn.set");
            if(messageSpawnSet != null) {
                messageSpawnSet = messageSpawnSet.replace("%player%", player.getDisplayName());
                messageSpawnSet = messageSpawnSet.replace("%x%", String.valueOf(location.getX()));
                messageSpawnSet = messageSpawnSet.replace("%y%", String.valueOf(location.getY()));
                messageSpawnSet = messageSpawnSet.replace("%z%", String.valueOf(location.getZ()));
                messageSpawnSet = messageSpawnSet.replace("%world%", location.getWorld().getName());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageSpawnSet));
            }

            return true;
        }

        private void sendNoPermsMessageFromConfig(Player player) {
            String noPermission = this.plugin.getConfig().getString("messages.no-Permission");
            if(noPermission != null) {
                noPermission = noPermission.replace("%player%", player.getDisplayName());
                noPermission = noPermission.replace("%permission%", "estopia.setspawn");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
            }
        }
}
