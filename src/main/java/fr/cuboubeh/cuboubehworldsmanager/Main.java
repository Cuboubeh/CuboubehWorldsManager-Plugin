package fr.cuboubeh.cuboubehworldsmanager;

import fr.cuboubeh.cuboubehworldsmanager.commands.*;
import fr.cuboubeh.cuboubehworldsmanager.tabcompleter.WorldsTabCompleter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
public class Main extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        getCommand("worlds").setExecutor(new WorldsCommand());
        getCommand("hub").setExecutor(new HubCommand(this));
        getCommand("minage").setExecutor(new MinageCommand());

        getCommand("worlds").setTabCompleter(new WorldsTabCompleter());
    }
}