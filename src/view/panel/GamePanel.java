package view.panel;

import controller.GameController;
import controller.MenuController;
import model.*;
import view.renderer.GameRenderer;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final GameController gameController;
    private final GameRenderer renderer;
    private MenuController menuController;

    public GamePanel() {
        setPreferredSize(new Dimension(
                (int) (GameConfig.LEVEL_WIDTH * GameConfig.WINDOW_SCALE),
                (int) (GameConfig.LEVEL_HEIGHT * GameConfig.WINDOW_SCALE)));
        setBackground(new Color(135, 206, 235));
        setFocusable(true);

        renderer = new GameRenderer();
        gameController = new GameController();

        addKeyListener(gameController.getInputHandler());

        startGameLoop();
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public GameController getGameController() {
        return gameController;
    }

    private void startGameLoop() {
        int delay = 1000 / GameConfig.FPS;
        Timer gameLoop = new Timer(delay, _ -> {
            update();
            repaint();
        });
        gameLoop.start();
    }

    private void update() {
        gameController.update();

        if (gameController.isGameOver()) {
            gameController.clearGameOverFlag();
            gameController.resetGame();
            if (menuController != null) {
                menuController.returnToMenu();
            }
        }

        if (gameController.isGameFinished()) {
            gameController.clearGameFinishedFlag();
            gameController.resetGame();
            if (menuController != null) {
                menuController.returnToMenu();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        double scaleX = (double) getWidth() / GameConfig.LEVEL_WIDTH;
        double scaleY = (double) getHeight() / GameConfig.LEVEL_HEIGHT;

        g2d.scale(scaleX, scaleY);

        renderer.render(g2d, gameController.getYogi(), gameController.getLevel(), gameController.getGameModel());
        renderer.renderMessage(g2d, gameController.getStateManager().getDisplayMessage(),
                gameController.getStateManager().getMessageAlpha(),
                GameConfig.LEVEL_WIDTH, GameConfig.LEVEL_HEIGHT);
    }
}
