package controller;

import model.*;
import model.collectible.Collectible;
import model.collision.BoundaryHandler;
import model.entity.agent.Agent;
import model.leaderboard.LeaderboardEntry;
import model.leaderboard.LeaderboardManager;
import model.level.Level;
import model.level.LevelLoader;
import model.entity.yogi.YogiBear;
import view.game.GameMessages;
import view.game.GameStateManager;

import java.awt.Rectangle;
import java.util.Objects;

public class GameController {
    private YogiBear yogi;
    private Level level;
    private GameModel gameModel;
    private InputHandler inputHandler;
    private GameStateManager stateManager;

    private int currentLevelNumber = 1;
    private boolean gameOver = false;
    private boolean gameFinished = false;
    private String playerName = "Player";

    public GameController() {
        loadLevel(currentLevelNumber);
        yogi = new YogiBear(level.getYogiStartX(), level.getYogiStartY());
        yogi.setLevelData(level.getLevelData());
        gameModel = new GameModel();
        gameModel.startTimer();
        stateManager = new GameStateManager(level, yogi, gameModel);
        inputHandler = new InputHandler(yogi);
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
        yogi.setLevelData(level.getLevelData());

        gameModel.startTimer();
        stateManager = new GameStateManager(level, yogi, gameModel);
        inputHandler.clearAllKeys();
    }

    public void update() {
        for (Agent agent : level.getAgents()) {
            agent.update();
        }

        checkCollection();

        if (!stateManager.isShowingMessage()
                || Objects.equals(stateManager.getDisplayMessage(), GameMessages.COLLECT_ALL_BASKETS)) {
            inputHandler.update();
            yogi.update();

            Rectangle yogiBox = yogi.getHitbox();

            BoundaryHandler.checkBoundaries(yogiBox, level, stateManager);

            for (Agent agent : level.getAgents()) {
                if (yogiBox.intersects(agent.getHitbox())) {
                    stateManager.onCaught();
                    break;
                }
            }
        }

        stateManager.updateMessage();

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
            gameModel.stopTimer();
            gameOver = true;
        }

        if (stateManager.isGameFinished() && !stateManager.isShowingMessage()) {
            gameModel.stopTimer();
            LeaderboardEntry entry = new LeaderboardEntry(
                    playerName,
                    gameModel.getScore(),
                    gameModel.getElapsedTime());
            LeaderboardManager.saveEntry(entry);
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

    public void setPlayerName(String playerName) {
        this.playerName = playerName != null && !playerName.trim().isEmpty() ? playerName : "Player";
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
