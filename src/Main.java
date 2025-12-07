import view.GameFrame;
import view.panel.GamePanel;
import view.panel.MenuPanel;
import view.panel.PanelType;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            MenuPanel menuPanel = new MenuPanel();
            GamePanel gamePanel = new GamePanel(frame);

            frame.addPanel(PanelType.MENU, menuPanel);
            frame.addPanel(PanelType.GAME, gamePanel);

            menuPanel.getStartButton().addActionListener(e -> {
                gamePanel.resetGame();
                frame.showPanel(PanelType.GAME);
                gamePanel.requestFocusInWindow();
            });

            menuPanel.getExitButton().addActionListener(e -> {
                System.exit(0);
            });

            frame.showPanel(PanelType.MENU);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
