package view.panel;

import controller.GameController;
import controller.LeaderboardController;
import controller.MenuController;
import model.*;
import view.dialog.GameCompletionDialog;
import view.renderer.GameRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class GamePanel extends JPanel implements Runnable {
    private final GameController gameController;
    private final GameRenderer renderer;
    private final LeaderboardController leaderboardController;
    private MenuController menuController;
    private Thread gameThread;

    public GamePanel() {
        setPreferredSize(new Dimension(
                (int) (GameConfig.LEVEL_WIDTH * GameConfig.WINDOW_SCALE),
                (int) (GameConfig.LEVEL_HEIGHT * GameConfig.WINDOW_SCALE)));
        setBackground(new Color(135, 206, 235));
        setFocusable(true);

        renderer = new GameRenderer();
        gameController = new GameController();
        leaderboardController = new LeaderboardController(null);

        addKeyListener(gameController.getInputHandler());
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                windowFocusLost();
            }
        });

        startGameLoop();
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public GameController getGameController() {
        return gameController;
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void windowFocusLost() {
        gameController.getInputHandler().clearAllKeys();
    }

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / GameConfig.FPS;
        double timePerUpdate = 1_000_000_000.0 / GameConfig.UPS;

        long previousTime = System.nanoTime();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                deltaU--;
            }

            if (deltaF >= 1) {
                repaint();
                deltaF--;
            }
        }
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

            // Show completion dialog
            int score = gameController.getGameModel().getScore();
            String formattedTime = gameController.getGameModel().getFormattedTime();

            GameCompletionDialog.CompletionResult result = GameCompletionDialog.showDialog(this, score, formattedTime);

            if (result.shouldSave) {
                long timeMillis = gameController.getGameModel().getElapsedTime();
                leaderboardController.saveEntry(result.playerName, score, timeMillis);
            }

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
