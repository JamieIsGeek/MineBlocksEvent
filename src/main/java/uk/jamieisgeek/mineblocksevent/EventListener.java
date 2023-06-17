package uk.jamieisgeek.mineblocksevent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(!MineBlocksEvent.getGameManager().isActive()) {
            return;
        }

        MineBlocksEvent.getGameManager().addPlayerScore(event.getPlayer());
    }
}
