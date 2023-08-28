package fr.cuboubeh.cuboubehworldsmanager.tabcompleter;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class WorldsTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("worlds")) {
            List<String> completions = new ArrayList<>();

            if (args.length == 1) {
                completions.add("tp");
                completions.add("regen");
                completions.add("create");
                completions.add("delete");
                completions.add("list");
            }  else if (args.length == 2 && (args[0].equalsIgnoreCase("regen") || args[0].equalsIgnoreCase("delete"))) {
                // Provide the names of existing worlds excluding "world"
                for (World world : Bukkit.getWorlds()) {
                    String worldName = world.getName();
                    if (!worldName.equalsIgnoreCase("world")) {
                        completions.add(worldName);
                    }
                }
            } else if (args.length == 2 && (args[0].equalsIgnoreCase("tp"))) {
                for (World world : Bukkit.getWorlds()) {
                    String worldName = world.getName();
                    completions.add(worldName);
                }
            }

            return completions;
        }
        return null;
    }
}

