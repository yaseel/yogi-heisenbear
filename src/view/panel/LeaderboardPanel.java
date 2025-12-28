package view.panel;

import model.leaderboard.LeaderboardEntry;
import model.leaderboard.LeaderboardManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LeaderboardPanel extends JPanel {
    private JButton backButton;
    private JTable leaderboardTable;

    public LeaderboardPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(135, 206, 235));

        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        String[] columnNames = { "Rank", "Name", "Score", "Time" };
        Object[][] data = new Object[10][4];

        leaderboardTable = new JTable(data, columnNames);
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 16));
        leaderboardTable.setRowHeight(30);
        leaderboardTable.setEnabled(false);
        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(135, 206, 235));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadLeaderboard();
    }

    public void loadLeaderboard() {
        List<LeaderboardEntry> entries = LeaderboardManager.getTopEntries(10);

        Object[][] data = new Object[10][4];
        for (int i = 0; i < 10; i++) {
            if (i < entries.size()) {
                LeaderboardEntry entry = entries.get(i);
                data[i][0] = (i + 1);
                data[i][1] = entry.getPlayerName();
                data[i][2] = entry.getScore();
                data[i][3] = entry.getFormattedTime();
            } else {
                data[i][0] = (i + 1);
                data[i][1] = "-";
                data[i][2] = "-";
                data[i][3] = "-";
            }
        }

        leaderboardTable.setModel(
                new javax.swing.table.DefaultTableModel(data, new String[] { "Rank", "Name", "Score", "Time" }) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                });
    }

    public JButton getBackButton() {
        return backButton;
    }
}
