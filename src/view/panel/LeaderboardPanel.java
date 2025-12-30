package view.panel;

import model.GameConfig;
import model.leaderboard.LeaderboardEntry;
import view.GameFont;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LeaderboardPanel extends JPanel {
    private JButton backButton;
    private JTable leaderboardTable;
    private BufferedImage backgroundImage;

    public LeaderboardPanel() {
        backgroundImage = loadBackground(GameConfig.BASE_BACKGROUND_PATH + "main_menu.png");
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(GameFont.getFont(48f));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        String[] columnNames = { "Rank", "Name", "Score", "Time" };
        Object[][] data = new Object[10][4];

        leaderboardTable = new JTable(data, columnNames);
        leaderboardTable.setFont(GameFont.getFont(16f));
        leaderboardTable.setRowHeight(30);
        leaderboardTable.setEnabled(false);
        leaderboardTable.getTableHeader().setFont(GameFont.getFont(18f));

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        Color tableBg = new Color(255, 255, 255, 230) ;
                
        leaderboardTable.setBackground(tableBg);
        leaderboardTable.getTableHeader().setBackground(tableBg);
        leaderboardTable.setOpaque(false);
        
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back to Menu");
        backButton.setFont(GameFont.getFont(20f));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        updateLeaderboard(java.util.Collections.emptyList());
    }

    public void updateLeaderboard(java.util.List<LeaderboardEntry> entries) {

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

    private BufferedImage loadBackground(String path) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(path);
            if (is != null) {
                return ImageIO.read(is);
            }
        } catch (IOException e) {
            System.err.println("Failed to load image: " + path);
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
