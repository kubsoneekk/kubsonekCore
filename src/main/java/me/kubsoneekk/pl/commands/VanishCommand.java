package me.kubsoneekk.pl.commands;

import me.kubsoneekk.pl.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class VanishCommand implements CommandExecutor, Listener {

    private static Map<UUID, Integer> vanishActionbarThreads = new HashMap<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("v") || player.isOp()) {
                if (Main.invisiblePlayers.contains(player.getUniqueId())) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.showPlayer(player);

                    }
                    Main.invisiblePlayers.remove(player.getUniqueId());
                    player.sendMessage("Jesteś widoczny dla innych graczy");

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6» §eJesteś widoczny dla innych graczy §6«"));
                } else if (!Main.invisiblePlayers.contains(player.getUniqueId())) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.hidePlayer(player);
                    }
                    Main.invisiblePlayers.add(player.getUniqueId());

                    handleVanishNotificationBar(player);
                }
            }
        }
        return false;
    }

    public void handleVanishNotificationBar(Player player) {
        BukkitScheduler scheduler = getServer().getScheduler();
        int bukkitTaskId = scheduler.scheduleSyncRepeatingTask(Main.getMain(), () -> {
            if(!Main.invisiblePlayers.contains(player.getUniqueId())){
                scheduler.cancelTask(vanishActionbarThreads.get(player.getUniqueId()));
                vanishActionbarThreads.remove(player.getUniqueId());
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6» §aJesteś juź widoczny dla innych graczy §6«"));
                return;
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6» §eJesteś niewidzialny dla innych graczy! §6«"));
        }, 0L, 20L);
        vanishActionbarThreads.put(player.getUniqueId(), bukkitTaskId);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(Main.invisiblePlayers.contains(event.getPlayer().getUniqueId())) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.hidePlayer(event.getPlayer());
            }
            handleVanishNotificationBar(event.getPlayer());
        }
    }
}
