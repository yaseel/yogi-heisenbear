package model.leaderboard;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardManager {
    private static final String LEADERBOARD_FILE = "leaderboard.txt";

    public static void saveEntry(LeaderboardEntry entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE, true))) {
            writer.write(entry.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to save leaderboard entry: " + e.getMessage());
        }
    }

    public static List<LeaderboardEntry> getTopEntries(int limit) {
        List<LeaderboardEntry> entries = new ArrayList<>();

        File file = new File(LEADERBOARD_FILE);
        if (!file.exists()) {
            return entries;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LeaderboardEntry entry = LeaderboardEntry.fromFileString(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load leaderboard: " + e.getMessage());
        }

        Collections.sort(entries);

        return entries.subList(0, Math.min(limit, entries.size()));
    }
}
