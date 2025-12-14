package view.panel;

import controller.InputHandler;
import model.*;
import model.bag.BrownBag;
import model.entity.agent.Agent;
import model.level.Level;
import model.level.LevelLoader;
import model.entity.yogi.YogiBear;
import view.GameFrame;
import model.collision.CollisionSystem;
import view.game.GameMessages;
import view.game.GameStateManager;
import view.renderer.GameRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GamePanel extends JPanel {
    private YogiBear yogi;
    private Level level;
    private GameModel gameModel;
    private InputHandler inputHandler;

    private CollisionSystem collisionSystem;
    private GameStateManager stateManager;
    private GameRenderer renderer;

    private int currentLevelNumber = 1;
    private GameFrame gameFrame;

    public GamePanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        loadLevel(currentLevelNumber);

        setPreferredSize(new Dimension(GameConfig.LEVEL_WIDTH, GameConfig.LEVEL_HEIGHT));
        setBackground(new Color(135, 206, 235));
        setFocusable(true);

        yogi = new YogiBear(level.getYogiStartX(), level.getYogiStartY());
        gameModel = new GameModel();

        collisionSystem = new CollisionSystem(yogi, level);
        stateManager = new GameStateManager(level, yogi, gameModel);
        renderer = new GameRenderer();

        inputHandler = new InputHandler(yogi, collisionSystem);
        addKeyListener(inputHandler);

        startGameLoop();
    }

    private void loadLevel(int levelNumber) {
        level = LevelLoader.loadLevel("src/resources/levels/level" + levelNumber + ".txt");
    }

    private void loadNextLevel() {
        currentLevelNumber++;
        loadLevel(currentLevelNumber);
        resetLevelState();
    }

    private void resetLevelState() {
        yogi.setX(level.getYogiStartX());
        yogi.setY(level.getYogiStartY());
        yogi.setVelocityY(0);
        yogi.setOnGround(false);

        collisionSystem = new CollisionSystem(yogi, level);
        stateManager = new GameStateManager(level, yogi, gameModel);

        // update input handler with new collision system for new level
        inputHandler.setCollisionSystem(collisionSystem);

        // clear any stuck keys
        inputHandler.clearAllKeys();
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
        for (Agent agent : level.getAgents()) {
            agent.update();
        }

        checkBagCollection();

        // stop yogi when message is displayed, except for collect all bags message
        if (!stateManager.isShowingMessage() || Objects.equals(stateManager.getDisplayMessage(), GameMessages.COLLECT_ALL_BAGS)) {

            inputHandler.update();
            yogi.update();

            CollisionSystem.CollisionResult result = collisionSystem.checkAll();
            if (result == CollisionSystem.CollisionResult.FELL) {
                stateManager.onFell();
            } else if (result == CollisionSystem.CollisionResult.LEVEL_COMPLETE) {
                stateManager.onLevelComplete();
            } else if (result == CollisionSystem.CollisionResult.CAUGHT) {
                stateManager.onCaught();
            } else if (result == CollisionSystem.CollisionResult.BLOCKED) {
                stateManager.onBlocked();
            }
        }

        stateManager.updateMessage();

        if (stateManager.isLevelComplete() && !stateManager.isShowingMessage()) {
            if (currentLevelNumber >= GameConfig.LAST_LEVEL_NUM) {
                stateManager.onGameFinished();
                stateManager.resetLevelCompleteFlag();
            } else {
                loadNextLevel();
                stateManager.resetLevelCompleteFlag();
            }
        }

        if (stateManager.isGameOver() && !stateManager.isShowingMessage()) {
            gameFrame.showPanel(PanelType.MENU);
            resetGame();
        }

        if (stateManager.isGameFinished() && !stateManager.isShowingMessage()) {
            gameFrame.showPanel(PanelType.MENU);
            resetGame();
        }
    }

    private void checkBagCollection() {
        for (BrownBag bag : level.getBags()) {
            if (!bag.isCollected() && yogi.getBounds().intersects(bag.getBounds())) {
                bag.collect();
                stateManager.onBagCollected(bag);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Calculate scale factors
        double scaleX = (double) getWidth() / GameConfig.LEVEL_WIDTH;
        double scaleY = (double) getHeight() / GameConfig.LEVEL_HEIGHT;

        g2d.scale(scaleX, scaleY);

        renderer.render(g2d, level, yogi, gameModel);
        renderer.renderMessage(g2d, stateManager.getDisplayMessage(), stateManager.getMessageAlpha(),
                GameConfig.LEVEL_WIDTH, GameConfig.LEVEL_HEIGHT);
    }

    public void resetGame() {
        currentLevelNumber = 1;
        loadLevel(currentLevelNumber);

        gameModel.reset();

        resetLevelState();
    }
}
