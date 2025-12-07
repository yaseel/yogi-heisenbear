package view;

import controller.InputHandler;
import model.*;
import model.agent.Agent;
import view.collision.AgentCollisionHandler;
import view.collision.BoundaryHandler;
import view.collision.CollisionHandler;
import view.game.GameStateManager;
import view.renderer.GameRenderer;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private YogiBear yogi;
    private Level level;
    private GameModel gameModel;
    private InputHandler inputHandler;

    private CollisionHandler collisionHandler;
    private AgentCollisionHandler agentCollisionHandler;
    private BoundaryHandler boundaryHandler;
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

        collisionHandler = new CollisionHandler(yogi, level);
        agentCollisionHandler = new AgentCollisionHandler(yogi, level);
        boundaryHandler = new BoundaryHandler(yogi);
        stateManager = new GameStateManager(level, yogi, gameModel);
        renderer = new GameRenderer();

        inputHandler = new InputHandler(yogi);
        addKeyListener(inputHandler);

        startGameLoop();
    }

    private void loadLevel(int levelNumber) {
        level = LevelLoader.loadLevel("src/resources/levels/level" + levelNumber + ".txt");
    }

    private void loadNextLevel() {
        currentLevelNumber++;
        loadLevel(currentLevelNumber);

        yogi.setX(level.getYogiStartX());
        yogi.setY(level.getYogiStartY());
        yogi.setVelocityY(0);
        yogi.setOnGround(false);

        collisionHandler = new CollisionHandler(yogi, level);
        agentCollisionHandler = new AgentCollisionHandler(yogi, level);
        stateManager = new GameStateManager(level, yogi, gameModel);
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
        inputHandler.update();
        yogi.update();

        for (Agent agent : level.getAgents()) {
            agent.update();
        }

        collisionHandler.checkAllCollisions();
        checkBagCollection();

        if (!stateManager.isShowingMessage()) {
            BoundaryHandler.BoundaryResult boundaryResult = boundaryHandler.checkBoundaries(level);
            if (boundaryResult == BoundaryHandler.BoundaryResult.FAIL) {
                stateManager.onFell();
            } else if (boundaryResult == BoundaryHandler.BoundaryResult.NEXT_LEVEL) {
                stateManager.onLevelComplete();
            }

            if (agentCollisionHandler.checkAgentCollisions()) {
                stateManager.onCaught();
            }
        }

        stateManager.updateMessage();

        if (stateManager.isLevelComplete() && !stateManager.isShowingMessage()) {
            loadNextLevel();
            stateManager.resetLevelCompleteFlag();
        }

        if (stateManager.isGameOver() && !stateManager.isShowingMessage()) {
            gameFrame.showPanel(PanelType.MENU);
            resetGame();
        }
    }

    private void checkBagCollection() {
        for (BrownBag bag : level.getBags()) {
            if (!bag.isCollected() && yogi.getBounds().intersects(bag.getBounds())) {
                bag.collect();
                stateManager.onBagCollected();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.render(g, level, yogi, gameModel);
        renderer.renderMessage(g, stateManager.getDisplayMessage(), stateManager.getMessageAlpha(), getWidth(),
                getHeight());
    }

    public void resetGame() {
        currentLevelNumber = 1;
        loadLevel(currentLevelNumber);

        gameModel.reset();

        yogi.setX(level.getYogiStartX());
        yogi.setY(level.getYogiStartY());
        yogi.setVelocityY(0);
        yogi.setOnGround(false);

        collisionHandler = new CollisionHandler(yogi, level);
        agentCollisionHandler = new AgentCollisionHandler(yogi, level);
        stateManager = new GameStateManager(level, yogi, gameModel);
    }
}
