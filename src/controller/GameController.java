package controller;

import model.*;
import model.collectible.Collectible;
import model.entity.agent.Agent;
import model.level.Level;
import model.level.LevelLoader;
import model.entity.yogi.YogiBear;
import model.collision.CollisionSystem;
import view.game.GameMessages;
import view.game.GameStateManager;

import java.util.Objects;

public class GameController {
    private YogiBear yogi;
    private Level level;
    private GameModel gameModel;
    private InputHandler inputHandler;
    private CollisionSystem collisionSystem;
    private GameStateManager stateManager;

    private int currentLevelNumber = 1;
    private boolean gameOver = false;
    private boolean gameFinished = false;

    public GameController() {
        loadLevel(currentLevelNumber);
        yogi = new YogiBear(level.getYogiStartX(), level.getYogiStartY());
        gameModel = new GameModel();
        collisionSystem = new CollisionSystem(yogi, level);
        stateManager = new GameStateManager(level, yogi, gameModel);
        inputHandler = new InputHandler(yogi, collisionSystem);
    }

    private void loadLevel(int levelNumber) {
        level = LevelLoader.loadLevel(levelNumber);
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

        inputHandler.setCollisionSystem(collisionSystem);
        inputHandler.clearAllKeys();
    }

    public void update() {
        for (Agent agent : level.getAgents()) {
            agent.update();
        }

        checkCollection();

        // Stop yogi when message is displayed, except for collect all baskets message
        if (!stateManager.isShowingMessage()
                || Objects.equals(stateManager.getDisplayMessage(), GameMessages.COLLECT_ALL_BASKETS)) {
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

        // Handle level progression
        if (stateManager.isLevelComplete() && !stateManager.isShowingMessage()) {
            if (currentLevelNumber >= GameConfig.LAST_LEVEL_NUM) {
                stateManager.onGameFinished();
                stateManager.resetLevelCompleteFlag();
                gameFinished = true;
            } else {
                loadNextLevel();
                stateManager.resetLevelCompleteFlag();
            }
        }

        if (stateManager.isGameOver() && !stateManager.isShowingMessage()) {
            gameOver = true;
        }

        if (stateManager.isGameFinished() && !stateManager.isShowingMessage()) {
            gameFinished = true;
        }
    }

    private void checkCollection() {
        for (Collectible collectible : level.getCollectibles()) {
            if (!collectible.isCollected() && yogi.getHitbox().intersects(collectible.getBounds())) {
                collectible.collect();
                stateManager.onCollect(collectible);
            }
        }
    }

    public void resetGame() {
        currentLevelNumber = 1;
        loadLevel(currentLevelNumber);
        gameModel.reset();
        resetLevelState();
        gameOver = false;
        gameFinished = false;
    }

    public YogiBear getYogi() {
        return yogi;
    }

    public Level getLevel() {
        return level;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public GameStateManager getStateManager() {
        return stateManager;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void clearGameOverFlag() {
        gameOver = false;
    }

    public void clearGameFinishedFlag() {
        gameFinished = false;
    }
}
