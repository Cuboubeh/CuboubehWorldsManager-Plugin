package fr.cuboubeh.cuboubehworldsmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HubCommand implements CommandExecutor {

    private static final String PREFIX = "§7[§6§lCuboubeh§7] ";

    private final Plugin plugin;

    public HubCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hub")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(PREFIX + "Cette commande ne peut être utilisée que par un joueur.");
                return true;
            }

            Player player = (Player) sender;

            player.sendMessage(PREFIX + "Vous serez téléporté au spawn dans quelques instants...");

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (player.isOnline()) {
                    teleportToSpawn(player);
                }
            }, 60L);

            return true;
        }
        return false;
    }

    private void teleportToSpawn(Player player) {
        World world = Bukkit.getWorld("world");
        player.teleport(world.getSpawnLocation());
        player.sendMessage(PREFIX + "Vous avez été téléporté au spawn du monde 'world'.");
    }
}
