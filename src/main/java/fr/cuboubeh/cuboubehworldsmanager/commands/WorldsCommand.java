package fr.cuboubeh.cuboubehworldsmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldsCommand implements CommandExecutor {

    private static final String PREFIX = "§7[§6§lCuboubeh§7] ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("worlds")) {
            if (args.length == 0) {
                sender.sendMessage("Utilisation : /worlds <tp/regen/create/delete/list>");
                return true;
            }

            String subCommand = args[0];

            switch (subCommand.toLowerCase()) {
                case "tp":
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("Cette sous-commande ne peut être utilisée que par un joueur.");
                        return true;
                    }

                    if (args.length < 2) {
                        sender.sendMessage("Utilisation : /worlds tp <nom du monde>");
                        return true;
                    }

                    String tpWorldName = args[1];
                    World tpTargetWorld = Bukkit.getWorld(tpWorldName);

                    if (tpTargetWorld == null) {
                        sender.sendMessage("Le monde " + tpWorldName + " n'existe pas.");
                        return true;
                    }

                    Player tpPlayer = (Player) sender;
                    tpPlayer.teleport(tpTargetWorld.getSpawnLocation());
                    sender.sendMessage("Vous avez été téléporté dans le monde " + tpWorldName);
                    break;

                case "regen":
                    if (args.length < 2) {
                        sender.sendMessage("Utilisation : /worlds regen <nom du monde>");
                        return true;
                    }

                    String regenWorldName = args[1];
                    World regenTargetWorld = Bukkit.getWorld(regenWorldName);

                    if (regenTargetWorld == null) {
                        sender.sendMessage("Le monde " + regenWorldName + " n'existe pas.");
                        return true;
                    }

                    for (Player player : regenTargetWorld.getPlayers()) {
                        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    }

                    Bukkit.unloadWorld(regenTargetWorld, true);
                    boolean deleted = regenTargetWorld.getWorldFolder().delete();

                    if (deleted) {
                        sender.sendMessage("Le monde " + regenWorldName + " a été supprimé avec succès.");
                    } else {
                        sender.sendMessage("Une erreur s'est produite lors de la suppression du monde " + regenWorldName + ".");
                        return true;
                    }

                    sender.sendMessage("Régénération du monde en cours...");

                    WorldCreator regenWorldCreator = new WorldCreator(regenWorldName);
                    World regeneratedWorld = regenWorldCreator.createWorld();

                    sender.sendMessage("Le monde " + regenWorldName + " a été régénéré avec succès.");
                    break;

                case "create":
                    if (args.length < 3) {
                        sender.sendMessage("Utilisation : /worlds create <nom du monde> <type du monde (normal / flat)>");
                        return true;
                    }

                    String createWorldName = args[1];
                    String createWorldType = args[2];

                    if (!createWorldType.equalsIgnoreCase("normal") && !createWorldType.equalsIgnoreCase("flat")) {
                        sender.sendMessage("Type de monde invalide. Utilisation : /worlds create <nom du monde> <type du monde (normal / flat)>");
                        return true;
                    }

                    WorldCreator createWorldCreator = new WorldCreator(createWorldName);

                    sender.sendMessage("Création du monde en cours...");

                    if (createWorldType.equalsIgnoreCase("flat")) {
                        createWorldCreator.generatorSettings("3;minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;");
                    }

                    World newCreateWorld = createWorldCreator.createWorld();
                    sender.sendMessage("Le monde " + createWorldName + " a été créé avec succès de type " + createWorldType);
                    break;

                case "delete":
                    if (args.length < 2) {
                        sender.sendMessage("Utilisation : /worlds delete <nom du monde>");
                        return true;
                    }

                    String deleteWorldName = args[1];

                    if (deleteWorldName.equalsIgnoreCase("world")) {
                        sender.sendMessage("Le monde 'world' ne peut pas être supprimé.");
                        return true;
                    }

                    World deleteTargetWorld = Bukkit.getWorld(deleteWorldName);

                    if (deleteTargetWorld == null) {
                        sender.sendMessage("Le monde " + deleteWorldName + " n'existe pas.");
                        return true;
                    }

                    for (Player player : deleteTargetWorld.getPlayers()) {
                        player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                        player.sendMessage(PREFIX + "Vous avez été exclu du monde " + deleteWorldName + " car celui-ci à été supprimé");
                    }

                    Bukkit.unloadWorld(deleteTargetWorld, true);
                    boolean deleted1 = deleteTargetWorld.getWorldFolder().delete();

                    if (deleted1) {
                        sender.sendMessage("Le monde " + deleteWorldName + " a été supprimé avec succès.");
                    } else {
                        sender.sendMessage("Une erreur s'est produite lors de la suppression du monde " + deleteWorldName + ".");
                    }
                    break;

                case "list":
                    sender.sendMessage("Liste des mondes:");

                    for (World world : Bukkit.getWorlds()) {
                        sender.sendMessage("- " + world.getName());
                    }
                    break;

                default:
                    sender.sendMessage("Sous-commande inconnue. Utilisation : /worlds <tp/regen/create/delete/list>");
                    break;
            }

            return true;
        }
        return false;
    }
}
