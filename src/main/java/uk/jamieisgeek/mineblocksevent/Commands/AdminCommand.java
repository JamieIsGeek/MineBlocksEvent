package uk.jamieisgeek.mineblocksevent.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import uk.jamieisgeek.mineblocksevent.MineBlocksEvent;
import uk.jamieisgeek.sootlib.Misc.TextManager;

import java.util.List;

public class AdminCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(new TextManager().translateHex("&#ee6680Please specify a subcommand."));
            return true;
        }

        switch (args[0]) {
            case "start" -> MineBlocksEvent.getGameManager().startGame();

            case "end" -> MineBlocksEvent.getGameManager().endGame();

            case "resetplayer" -> {
                if(args.length != 2) {
                    return true;
                }

                Player player = Bukkit.getPlayer(args[1]);

                if(player == null) {
                    sender.sendMessage(new TextManager().translateHex("&#ee6680 Player not found."));
                    return true;
                }

                MineBlocksEvent.getGameManager().resetPlayer(player);
            }
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            return List.of("start", "end", "resetplayer");
        } else if (args.length == 2) {
            if(args[0].equals("resetplayer")) {
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
            }
        }

        return null;
    }
}
