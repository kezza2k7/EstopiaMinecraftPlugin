package net.estopia.estopia.Listeners;

import net.estopia.estopia.Estopia;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;

public class PlayerLeaveListener implements Listener {
    private  final Estopia plugin;

    public PlayerLeaveListener(Estopia plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        String leaveMessage = this.plugin.getConfig().getString("messages.quit");
        if(leaveMessage != null) {
            leaveMessage = leaveMessage.replace("%player%", player.getDisplayName());
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', leaveMessage));
        }
    }


}

