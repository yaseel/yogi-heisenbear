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

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        menuPanel.getStartButton().addActionListener(_ -> {
            gamePanel.getGameController().startGame();
            onStartGame();
        });

        menuPanel.getLeaderboardButton().addActionListener(_ -> onShowLeaderboard());
        menuPanel.getExitButton().addActionListener(_ -> onExitGame());

        leaderboardController.getLeaderboardPanel().getBackButton().addActionListener(_ -> returnToMenu());
    }

    private void onStartGame() {
        gameFrame.showPanel(PanelType.GAME);
        gamePanel.requestFocusInWindow();
    }

    private void onShowLeaderboard() {
        leaderboardController.refreshLeaderboard();
        gameFrame.showPanel(PanelType.LEADERBOARD);
    }

    private void onExitGame() {
        System.exit(0);
    }

    public void returnToMenu() {
        gameFrame.showPanel(PanelType.MENU);
    }
}
