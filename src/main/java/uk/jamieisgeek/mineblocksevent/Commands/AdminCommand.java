package uk.jamieisgeek.mineblocksevent.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.jamieisgeek.mineblocksevent.MineBlocksEvent;
import uk.jamieisgeek.sootlib.CommandHelpers.CommandHandler;
import uk.jamieisgeek.sootlib.CommandHelpers.CommandInfo;
import uk.jamieisgeek.sootlib.Misc.TextManager;

@CommandInfo(name = "event", permission = "mineblocks.admin", requiresPlayer = false)
public class AdminCommand extends CommandHandler {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 1) {
            sender.sendMessage(new TextManager().translateHex("&#ee6680 Please specify a subcommand."));
            return;
        }

        switch (args[0]) {
            case "start" -> MineBlocksEvent.getGameManager().startGame();

            case "end" -> MineBlocksEvent.getGameManager().endGame();

            case "resetplayer" -> {
                if(args.length != 2) {
                    return;
                }

                Player player = Bukkit.getPlayer(args[1]);

                if(player == null) {
                    sender.sendMessage(new TextManager().translateHex("&#ee6680 Player not found."));
                    return;
                }

                MineBlocksEvent.getGameManager().resetPlayer(player);
            }
        }
    }
}
