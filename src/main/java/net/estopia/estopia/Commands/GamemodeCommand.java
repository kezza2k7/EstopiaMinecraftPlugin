package net.estopia.estopia.Commands;

import net.estopia.estopia.Estopia;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    private  final Estopia plugin;

    public GamemodeCommand(Estopia plugin) {
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

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /gamemode <gamemode> [player]");
        } else if (args.length == 1 ) {
            if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                if (!player.hasPermission("estopia.gamemode.self.survival")) {
                    sendNoPermsMessageFromConfig(sender, player, "messages.no-Permission", "estopia.gamemode.self.survival");
                    return true;
                }
                player.setGameMode(GameMode.SURVIVAL);
                sendGamemodeChangeSelf(player, "Survival");
            } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                if (!player.hasPermission("estopia.gamemode.self.creative")) {
                    sendNoPermsMessageFromConfig(sender, player, "messages.no-Permission", "estopia.gamemode.self.creative");
                    return true;
                }
                player.setGameMode(GameMode.CREATIVE);
                sendGamemodeChangeSelf(player, "Creative");
            } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
                if (!player.hasPermission("estopia.gamemode.self.adventure")) {
                    sendNoPermsMessageFromConfig(sender, player, "messages.no-Permission", "estopia.gamemode.self.adventure");
                    return true;
                }
                player.setGameMode(GameMode.ADVENTURE);
                sendGamemodeChangeSelf(player, "Adventure");
            } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
                if (!player.hasPermission("estopia.gamemode.self.spectator")) {
                    sendNoPermsMessageFromConfig(sender, player, "messages.no-Permission", "estopia.gamemode.self.spectator");
                    return true;
                }
                player.setGameMode(GameMode.SPECTATOR);
                sendGamemodeChangeSelf(player, "Spectator");
            } else {
                sendInvalidGamemodeMessage(player);
            }
        } else if (args.length == 2) {
            Player target = Bukkit.getServer().getPlayerExact(args[1]);
            if (target != null) {
                if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                    if(!player.hasPermission("estopia.gamemode.others.survival")) {
                        sendNoPermsMessageFromConfig(sender, player, "messages.no-Permission", "estopia.gamemode.others.survival");
                        return true;
                    }
                    target.setGameMode(GameMode.SURVIVAL);
                    sendGamemodeChangeOther(sender, target, "Survival");
                    sendGamemodeChangeSelfOther(target, player, "Survival");
                } else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                    if(!player.hasPermission("estopia.gamemode.others.creative")) {
                        sendNoPermsMessageFromConfig(sender, player, "messages.no-Permission", "estopia.gamemode.others.creative");
                        return true;
                    }
                    target.setGameMode(GameMode.CREATIVE);
                    sendGamemodeChangeOther(sender, target, "Creative");
                    sendGamemodeChangeSelfOther(target, player, "Creative");
                } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
                    if(!player.hasPermission("estopia.gamemode.others.adventure")) {
                        sendNoPermsMessageFromConfig(sender, player, "messages.no-Permission", "estopia.gamemode.others.adventure");
                        return true;
                    }
                    target.setGameMode(GameMode.ADVENTURE);
                    sendGamemodeChangeOther(sender, target, "Adventure");
                    sendGamemodeChangeSelfOther(target, player, "Adventure");
                } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
                    if(!player.hasPermission("estopia.gamemode.others.spectator")) {
                        sendNoPermsMessageFromConfig(sender, player, "messages.no-Permission", "estopia.gamemode.others.spectator");
                        return true;
                    }
                    target.setGameMode(GameMode.SPECTATOR);
                    sendGamemodeChangeOther(sender, target, "Spectator");
                    sendGamemodeChangeSelfOther(target, player, "Spectator");
                } else {
                    sendInvalidGamemodeMessage(player);
                }
            } else {
                String gamemodePlayerNotFound = this.plugin.getConfig().getString("messages.gamemode.player-not-found");
                if(gamemodePlayerNotFound != null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', gamemodePlayerNotFound));
                }
            }
        }
        return true;

    }

    private void sendNoPermsMessageFromConfig(CommandSender sender, Player player, String configpath, String permission) {
        String noPermission = this.plugin.getConfig().getString(configpath);
        if(noPermission != null) {
            noPermission = noPermission.replace("%player%", player.getDisplayName());
            noPermission = noPermission.replace("%permission%", permission);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
        }
    }

    private void sendGamemodeChangeSelf(Player player, String gamemode) {
        String gamemodeChangeSelf = this.plugin.getConfig().getString("messages.gamemode.self");
        if(gamemodeChangeSelf != null) {
            gamemodeChangeSelf = gamemodeChangeSelf.replace("%player%", player.getDisplayName());
            gamemodeChangeSelf = gamemodeChangeSelf.replace("%gamemode%", gamemode);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', gamemodeChangeSelf));
        }
    }

    private void sendGamemodeChangeOther(CommandSender sender, Player player, String gamemode) {
        String gamemodeChangeOther = this.plugin.getConfig().getString("messages.gamemode.other");
        if(gamemodeChangeOther != null) {
            gamemodeChangeOther = gamemodeChangeOther.replace("%player%", player.getDisplayName());
            gamemodeChangeOther = gamemodeChangeOther.replace("%gamemode%", gamemode);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', gamemodeChangeOther));
        }
    }

    private void sendGamemodeChangeSelfOther(Player target, Player sender, String gamemode){
        String gamemodeChangeOther = this.plugin.getConfig().getString("messages.gamemode.other");
        if(gamemodeChangeOther != null) {
            gamemodeChangeOther = gamemodeChangeOther.replace("%player%", sender.getDisplayName());
            gamemodeChangeOther = gamemodeChangeOther.replace("%gamemode%", gamemode);
            target.sendMessage(ChatColor.translateAlternateColorCodes('&', gamemodeChangeOther));
        }
    }

    public void sendInvalidGamemodeMessage(Player player) {
        String invalidGamemode = this.plugin.getConfig().getString("messages.gamemode.invalid");
        if(invalidGamemode != null) {
            invalidGamemode = invalidGamemode.replace("%player%", player.getDisplayName());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', invalidGamemode));
        }
    }
}
