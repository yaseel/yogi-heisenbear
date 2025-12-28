package controller;

import model.leaderboard.LeaderboardEntry;
import model.leaderboard.LeaderboardManager;
import view.panel.LeaderboardPanel;

import java.util.List;

public class LeaderboardController {
    private final LeaderboardPanel leaderboardPanel;

    public LeaderboardController(LeaderboardPanel leaderboardPanel) {
        this.leaderboardPanel = leaderboardPanel;
    }

    public void saveEntry(String playerName, int score, long timeMillis) {
        LeaderboardEntry entry = new LeaderboardEntry(playerName, score, timeMillis);
        LeaderboardManager.saveEntry(entry);
    }

    public void refreshLeaderboard() {
        List<LeaderboardEntry> entries = LeaderboardManager.getTopEntries(10);
        leaderboardPanel.updateLeaderboard(entries);
    }

    public List<LeaderboardEntry> getTopEntries(int limit) {
        return LeaderboardManager.getTopEntries(limit);
    }
}
