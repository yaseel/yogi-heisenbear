package controller;

import view.GameFrame;
import view.panel.GamePanel;
import view.panel.LeaderboardPanel;
import view.panel.PanelType;

public class MenuController {
    private final GameFrame gameFrame;
    private final GamePanel gamePanel;
    private final LeaderboardPanel leaderboardPanel;

    public MenuController(GameFrame gameFrame, GamePanel gamePanel, LeaderboardPanel leaderboardPanel) {
        this.gameFrame = gameFrame;
        this.gamePanel = gamePanel;
        this.leaderboardPanel = leaderboardPanel;
    }

    public void onStartGame() {
        gameFrame.showPanel(PanelType.GAME);
        gamePanel.requestFocusInWindow();
    }

    public void onShowLeaderboard() {
        leaderboardPanel.loadLeaderboard();
        gameFrame.showPanel(PanelType.LEADERBOARD);
    }

    public void onExitGame() {
        System.exit(0);
    }

    public void returnToMenu() {
        gameFrame.showPanel(PanelType.MENU);
    }
}
