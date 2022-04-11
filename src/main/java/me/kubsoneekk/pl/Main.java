package me.kubsoneekk.pl;


import me.kubsoneekk.pl.commands.VanishCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public final class Main extends JavaPlugin {
    public static Main main;
    public static final ArrayList<UUID> invisiblePlayers = new ArrayList<>();
    @Override
    public void onEnable(){
    getLogger().info("╭╮╱╱╱╱╭╮╱╱╱╱╱╱╱╱╱╱╱╱╱╭╮╱╭━━━┳━━━┳━━━┳━━━╮\n" +
                "┃┃╱╱╱╱┃┃╱╱╱╱╱╱╱╱╱╱╱╱╱┃┃╱┃╭━╮┃╭━╮┃╭━╮┃╭━━╯\n" +
                "┃┃╭┳╮╭┫╰━┳━━┳━━┳━╮╭━━┫┃╭┫┃╱╰┫┃╱┃┃╰━╯┃╰━━╮\n" +
                "┃╰╯┫┃┃┃╭╮┃━━┫╭╮┃╭╮┫┃━┫╰╯┫┃╱╭┫┃╱┃┃╭╮╭┫╭━━╯\n" +
                "┃╭╮┫╰╯┃╰╯┣━━┃╰╯┃┃┃┃┃━┫╭╮┫╰━╯┃╰━╯┃┃┃╰┫╰━━╮\n" +
                "╰╯╰┻━━┻━━┻━━┻━━┻╯╰┻━━┻╯╰┻━━━┻━━━┻╯╰━┻━━━╯");
        VanishCommand vanish = new VanishCommand();
        getCommand("vanish").setExecutor(vanish);
        main = this;
        getServer().getPluginManager().registerEvents(vanish, this);
    }
    @Override
    public void onDisable() {
    }
    public static Main getMain(){
        return main;
    }
}
