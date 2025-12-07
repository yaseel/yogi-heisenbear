package view.panel;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JButton startButton;
    private JButton exitButton;

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

        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        c.gridy = 1;
        add(startButton, c);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 16));
        c.gridy = 2;
        add(exitButton, c);
    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
}
