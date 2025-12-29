package view.panel;

import view.GameFont;
import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JButton startButton;
    private JButton leaderboardButton;
    private JButton exitButton;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(135, 206, 235));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Yogi Heisenbear");
        title.setFont(GameFont.getFont(48f));
        c.gridy = 0;
        add(title, c);

        startButton = new JButton("Start Game");
        startButton.setFont(GameFont.getFont(20f));
        c.gridy = 1;
        add(startButton, c);

        leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setFont(GameFont.getFont(18f));
        c.gridy = 2;
        add(leaderboardButton, c);

        exitButton = new JButton("Exit");
        exitButton.setFont(GameFont.getFont(16f));
        c.gridy = 3;
        add(exitButton, c);
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getLeaderboardButton() {
        return leaderboardButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
