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

            MenuController menuController = new MenuController(frame, gamePanel, leaderboardPanel);
            gamePanel.setMenuController(menuController);

            menuPanel.getStartButton().addActionListener(_ -> {
                String playerName = menuPanel.getPlayerName();
                gamePanel.getGameController().setPlayerName(playerName);
                menuController.onStartGame();
            });
            menuPanel.getLeaderboardButton().addActionListener(_ -> menuController.onShowLeaderboard());
            menuPanel.getExitButton().addActionListener(_ -> menuController.onExitGame());

            leaderboardPanel.getBackButton().addActionListener(_ -> menuController.returnToMenu());

            frame.showPanel(PanelType.MENU);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
