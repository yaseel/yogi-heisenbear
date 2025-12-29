import controller.MenuController;
import view.GameFrame;
import view.panel.GamePanel;
import view.panel.LeaderboardPanel;
import view.panel.MenuPanel;
import view.panel.PanelType;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            MenuPanel menuPanel = new MenuPanel();
            GamePanel gamePanel = new GamePanel();
            LeaderboardPanel leaderboardPanel = new LeaderboardPanel();

            frame.addPanel(PanelType.MENU, menuPanel);
            frame.addPanel(PanelType.GAME, gamePanel);
            frame.addPanel(PanelType.LEADERBOARD, leaderboardPanel);

            MenuController menuController = new MenuController(frame, gamePanel, menuPanel, leaderboardPanel);
            gamePanel.setMenuController(menuController);

            frame.showPanel(PanelType.MENU);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
