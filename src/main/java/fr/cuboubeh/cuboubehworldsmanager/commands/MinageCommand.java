package fr.cuboubeh.cuboubehworldsmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinageCommand implements CommandExecutor {

    private static final String PREFIX = "§7[§6§lCuboubeh§7] ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("minage")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(PREFIX + "Cette commande ne peut être utilisée que par un joueur.");
                return true;
            }

            Player player = (Player) sender;
            List<World> minageWorlds = getMinageWorlds();

            if (minageWorlds.isEmpty()) {
                player.sendMessage(PREFIX + "Aucun monde minage n'a été trouvé.");
                return true;
            }

            int randomIndex = new Random().nextInt(minageWorlds.size());
            World randomMinageWorld = minageWorlds.get(randomIndex);

            player.teleport(randomMinageWorld.getSpawnLocation());
            player.sendMessage(PREFIX + "Vous avez été téléporté dans un monde minage ("+randomMinageWorld.getName()+").");

            return true;
        }
        return false;
    }

    private List<World> getMinageWorlds() {
        List<World> minageWorlds = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            if (world.getName().startsWith("minage-")) {
                minageWorlds.add(world);
            }
        }

        return minageWorlds;
    }
}