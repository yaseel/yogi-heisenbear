package controller;

import view.GameFrame;
import view.panel.GamePanel;
import view.panel.PanelType;

public class MenuController {
    private final GameFrame gameFrame;
    private final GamePanel gamePanel;

    public MenuController(GameFrame gameFrame, GamePanel gamePanel) {
        this.gameFrame = gameFrame;
        this.gamePanel = gamePanel;
    }

    public void onStartGame() {
        gameFrame.showPanel(PanelType.GAME);
        gamePanel.requestFocusInWindow();
    }

    public void onExitGame() {
        System.exit(0);
    }

    public void returnToMenu() {
        gameFrame.showPanel(PanelType.MENU);
    }
}
