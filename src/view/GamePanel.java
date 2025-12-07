package view;

import controller.InputHandler;
import model.*;
import model.agent.Agent;
import view.collision.AgentCollisionHandler;
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
    private GameStateManager stateManager;
    private GameRenderer renderer;

    public GamePanel() {
        level = LevelLoader.loadLevel("src/resources/levels/level1.txt");

        setPreferredSize(new Dimension(GameConfig.LEVEL_WIDTH, GameConfig.LEVEL_HEIGHT));
        setBackground(new Color(135, 206, 235));
        setFocusable(true);

        yogi = new YogiBear(level.getYogiStartX(), level.getYogiStartY());
        gameModel = new GameModel();

        collisionHandler = new CollisionHandler(yogi, level);
        agentCollisionHandler = new AgentCollisionHandler(yogi, level);
        stateManager = new GameStateManager(level, yogi, gameModel);
        renderer = new GameRenderer();

        inputHandler = new InputHandler(yogi);
        addKeyListener(inputHandler);

        startGameLoop();
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
            if (agentCollisionHandler.checkAgentCollisions()) {
                stateManager.onCaught();
            }
        }

        stateManager.updateMessage();
    }

    private void checkBagCollection() {
        for (BrownBag bag : level.getBags()) {
            if (!bag.isCollected() && yogi.getBounds().intersects(bag.getBounds())) {
                bag.collect();
                stateManager.onBagCollected();

                if (level.getRemainingBags() == 0) {
                    // TODO unlock next level
                }
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
}
