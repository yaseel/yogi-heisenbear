package view.panel;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JButton startButton;
    private JButton leaderboardButton;
    private JButton exitButton;
    private JTextField nameField;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(135, 206, 235));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("Yogi Heisenbear");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        c.gridy = 0;
        add(title, c);

        JLabel nameLabel = new JLabel("Player Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        c.gridy = 1;
        add(nameLabel, c);

        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setText("Player");
        c.gridy = 2;
        add(nameField, c);

        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridy = 3;
        add(startButton, c);

        leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setFont(new Font("Arial", Font.PLAIN, 18));
        c.gridy = 4;
        add(leaderboardButton, c);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        c.gridy = 5;
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

    public String getPlayerName() {
        String name = nameField.getText().trim();
        return name.isEmpty() ? "Player" : name;
    }
}
