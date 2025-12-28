package model.leaderboard;

public class LeaderboardEntry implements Comparable<LeaderboardEntry> {
    private final String playerName;
    private final int score;
    private final long timeMillis;

    public LeaderboardEntry(String playerName, int score, long timeMillis) {
        this.playerName = playerName;
        this.score = score;
        this.timeMillis = timeMillis;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public String getFormattedTime() {
        long totalSeconds = timeMillis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public int compareTo(LeaderboardEntry other) {
        if (this.score != other.score) {
            return Integer.compare(other.score, this.score);
        }
        return Long.compare(this.timeMillis, other.timeMillis);
    }

    public String toFileString() {
        return playerName + "|" + score + "|" + timeMillis;
    }

    public static LeaderboardEntry fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 3) {
            return new LeaderboardEntry(
                    parts[0],
                    Integer.parseInt(parts[1]),
                    Long.parseLong(parts[2]));
        }
        return null;
    }
}
