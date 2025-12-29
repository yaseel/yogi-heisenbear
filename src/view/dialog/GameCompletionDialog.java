package view.dialog;

import view.GameFont;
import javax.swing.*;
import java.awt.*;

public class GameCompletionDialog {

    public static class CompletionResult {
        public final boolean shouldSave;
        public final String playerName;

        public CompletionResult(boolean shouldSave, String playerName) {
            this.shouldSave = shouldSave;
            this.playerName = playerName;
        }
    }

    public static CompletionResult showDialog(Component parent, int score, String formattedTime) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;

        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(GameFont.getFont(14f));
        c.gridy = 1;
        panel.add(scoreLabel, c);

        JLabel timeLabel = new JLabel("Time: " + formattedTime);
        timeLabel.setFont(GameFont.getFont(14f));
        c.gridy = 2;
        panel.add(timeLabel, c);

        JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setFont(GameFont.getFont(14f));
        c.gridy = 3;
        c.gridwidth = 1;
        panel.add(nameLabel, c);

        JTextField nameField = new JTextField("Player", 15);
        nameField.setFont(GameFont.getFont(14f));
        c.gridx = 1;
        panel.add(nameField, c);

        String[] options = { "Save Score", "Back to Menu" };
        int result = JOptionPane.showOptionDialog(
                parent,
                panel,
                "Finish!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        boolean shouldSave = (result == JOptionPane.YES_OPTION);
        String playerName = nameField.getText().trim();
        if (playerName.isEmpty()) {
            playerName = "Player";
        }

        return new CompletionResult(shouldSave, playerName);
    }
}
