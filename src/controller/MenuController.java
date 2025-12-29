package controller;

import view.GameFrame;
import view.panel.GamePanel;
import view.panel.LeaderboardPanel;
import view.panel.MenuPanel;
import view.panel.PanelType;

public class MenuController {
    private final GameFrame gameFrame;
    private final GamePanel gamePanel;
    private final MenuPanel menuPanel;
    private final LeaderboardController leaderboardController;

    public MenuController(GameFrame gameFrame, GamePanel gamePanel, MenuPanel menuPanel,
            LeaderboardPanel leaderboardPanel) {
        this.gameFrame = gameFrame;
        this.gamePanel = gamePanel;
        this.menuPanel = menuPanel;
        this.leaderboardController = new LeaderboardController(leaderboardPanel);

        menuPanel.setMenuController(this);
        leaderboardController.getLeaderboardPanel().getBackButton().addActionListener(_ -> returnToMenu());
    }

    public void onStartGame() {
        gamePanel.getGameController().startGame();
        gameFrame.showPanel(PanelType.GAME);
        gamePanel.requestFocusInWindow();
    }

    public void onShowLeaderboard() {
        leaderboardController.refreshLeaderboard();
        gameFrame.showPanel(PanelType.LEADERBOARD);
    }

    public void onShowSettings() {
        System.out.println("Settings");
    }

    public void onExit() {
        System.exit(0);
    }

    public void returnToMenu() {
        gameFrame.showPanel(PanelType.MENU);
        menuPanel.requestFocusInWindow();
    }
}
