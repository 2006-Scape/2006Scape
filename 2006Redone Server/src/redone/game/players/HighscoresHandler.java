package redone.game.players;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

import static redone.game.players.PlayerSave.loadPlayerInfo;

public class HighscoresHandler {
    public ArrayList<Client> players = new ArrayList<>();

    public HighscoresHandler() {
        File dir = new File("./data/characters");
        File[] directoryListing = dir.listFiles();
        for (File child : directoryListing) {
            Client player = new Client(null, -1);
            player.playerName = child.getName().split("\\.")[0];
            loadPlayerInfo(player, child.getName().split("\\.")[0], "", false);
            players.add(player);
        }
    }

    public String getRank(int i, String sortBy) {
        if (players.size() <= i)
            return "Nobody";

        switch (sortBy)
        {
            case "level":
                players.sort(new totalLevelComparator());
                return players.get(i).playerName + ": " + players.get(i).getPlayerAssistant().getTotalLevel();
            case "gold":
                players.sort(new totalGoldComparator());
                return players.get(i).playerName + ": " + players.get(i).getPlayerAssistant().totalGold() + "gp";
            default:
                players.sort(new globalDmgComparator());
                return players.get(i).playerName + ": " + players.get(i).globalDamageDealt;
        }
    }

    private class totalLevelComparator implements Comparator<Client> {
        @Override
        public int compare(Client client, Client t1) {
            return - client.getPlayerAssistant().getTotalLevel() + t1.getPlayerAssistant().getTotalLevel();
        }
    }

    private class totalGoldComparator implements Comparator<Client> {
        @Override
        public int compare(Client client, Client t1) {
            return - client.getPlayerAssistant().totalGold() + t1.getPlayerAssistant().totalGold();
        }
    }

    private class globalDmgComparator implements Comparator<Client> {
        @Override
        public int compare(Client client, Client t1) {
            return - client.globalDamageDealt + t1.globalDamageDealt;
        }
    }

}
