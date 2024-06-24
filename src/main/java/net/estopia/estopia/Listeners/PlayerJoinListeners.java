package net.estopia.estopia.Listeners;

import net.estopia.estopia.Estopia;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListeners implements Listener {
    private  final Estopia plugin;

    public PlayerJoinListeners(Estopia plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String joinMessage = this.plugin.getConfig().getString("messages.join");
        if(joinMessage != null) {
            joinMessage = joinMessage.replace("%player%", player.getDisplayName());
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
        }

        if(!player.hasPlayedBefore()) {
            String welcomeMessage = this.plugin.getConfig().getString("messages.welcome");
            if(welcomeMessage != null) {
                welcomeMessage = welcomeMessage.replace("%player%", player.getDisplayName());
                event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', welcomeMessage));
            }

            Location location = plugin.getConfig().getLocation("spawn");

            if (location == null) {
                return;
            }

            player.teleport(location);
        }
    }

}
