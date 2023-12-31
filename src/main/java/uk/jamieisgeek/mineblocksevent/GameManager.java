package uk.jamieisgeek.mineblocksevent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.jamieisgeek.sootlib.Misc.TextManager;

import java.util.*;
import java.util.stream.Collectors;

public class GameManager {
    private final MineBlocksEvent plugin;
    private final ScoreboardInterface scoreboardInterface;
    private final Map<Player, Integer> playerScore = new HashMap<>();
    public boolean active = false;
    public GameManager(MineBlocksEvent plugin) {
        this.plugin = plugin;
        this.scoreboardInterface = new ScoreboardInterface(6, new TextManager().translateHex("&#4b6ba0MineBlocks"));
    }

    public void startGame() {
        for(Player player : this.plugin.getServer().getOnlinePlayers()) {
            this.playerScore.put(player, 0);
        }

        this.setupScoreboard();

        active = true;
    }

    private void setupScoreboard() {
        TextManager tm = new TextManager();
        Bukkit.getOnlinePlayers().forEach(player -> {
            this.scoreboardInterface.setUniversalLine(1, tm.translateHex("&#DDAA33█ 1st: &f" + this.getTopPlayer(1)));
            this.scoreboardInterface.setUniversalLine(2, tm.translateHex("&#C0C0C0█ 2nd: &f" + this.getTopPlayer(2)));
            this.scoreboardInterface.setUniversalLine(3, tm.translateHex("&#CD7F32█ 3rd: &f" + this.getTopPlayer(3)));
            this.scoreboardInterface.setLine(player, 5, tm.translateHex("&#4b6ba0█ Blocks Mined: &f" + this.playerScore.get(player)));

            this.scoreboardInterface.displayForPlayer(player);
        });
    }

    private String getTopPlayer(int position) {
        if(position > playerScore.size()) {
            return "N/A";
        }

        List<Player> sortedPlayers = playerScore.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .toList();

        if(sortedPlayers.get(position - 1) == null) {
            return "N/A";
        }

        return sortedPlayers.get(position - 1).getName();
    }

    private int getPlayerPosition(Player player) {
        List<Map.Entry<Player, Integer>> list = new ArrayList<>(playerScore.entrySet());

        // Sort the list in descending order based on integer values
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Check if the position is within the valid range
        if (player == null) {
            throw new IllegalArgumentException("Invalid player");
        }

        // Retrieve the Player object at the specified position
        for(int i = 0; i < list.size(); i++) {
            Map.Entry<Player, Integer> entry = list.get(i);
            if(entry.getKey().equals(player)) {
                return i + 1;
            }
        }

        return -1;
    }

    public void endGame() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(new TextManager().translateHex("&#ee6680The game has ended!"));

            player.sendMessage(new TextManager().translateHex("&#ee66801st: &f" + this.getTopPlayer(1)));
            player.sendMessage(new TextManager().translateHex("&#ee66802nd: &f" + this.getTopPlayer(2)));
            player.sendMessage(new TextManager().translateHex("&#ee66803rd: &f" + this.getTopPlayer(3)));

            String position;
            position = String.valueOf(this.getPlayerPosition(player));
            if(position.endsWith("1")) {
                position += "st";
            } else if(position.endsWith("2")) {
                position += "nd";
            } else if(position.endsWith("3")) {
                position += "rd";
            } else {
                position += "th";
            }

            player.sendMessage(new TextManager().translateHex("&#ee6680You placed &f" + position));
            this.scoreboardInterface.removePlayerFromScoreboard(player);
        });

        this.active = false;
    }

    public void addPlayerScore(Player player) {
        this.playerScore.put(player, this.playerScore.get(player) + 1);

        this.setupScoreboard();
    }

    public void resetPlayer(Player player) {
        this.playerScore.remove(player);
        this.playerScore.put(player, 0);

        this.setupScoreboard();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setTotalExperience(0);
        player.getActivePotionEffects().clear();
    }

    public boolean isActive() {
        return this.active;
    }
}
