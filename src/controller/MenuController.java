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

        MusicManager.getInstance().playMenuMusic();
    }

    public void onStartGame() {
        MusicManager.getInstance().playGameMusic();
        gamePanel.getGameController().startGame();
        gameFrame.showPanel(PanelType.GAME);
        gamePanel.requestFocusInWindow();
    }

    public void onShowLeaderboard() {
        leaderboardController.refreshLeaderboard();
        gameFrame.showPanel(PanelType.LEADERBOARD);
    }

    public void onExit() {
        System.exit(0);
    }

    public void returnToMenu() {
        MusicManager.getInstance().playMenuMusic();
        gameFrame.showPanel(PanelType.MENU);
        menuPanel.requestFocusInWindow();
    }
}
