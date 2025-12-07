package view.game;

import model.GameConfig;
import model.GameModel;
import model.Level;
import model.YogiBear;

public class GameStateManager {
    private Level level;
    private YogiBear yogi;
    private GameModel gameModel;

    private String displayMessage;
    private int messageAlpha;
    private int messageTimer;
    private boolean levelComplete;
    private boolean gameOver;
    private boolean shouldResetLevel;

    public GameStateManager(Level level, YogiBear yogi, GameModel gameModel) {
        this.level = level;
        this.yogi = yogi;
        this.gameModel = gameModel;
        this.displayMessage = null;
        this.messageAlpha = 0;
        this.messageTimer = 0;
        this.levelComplete = false;
        this.gameOver = false;
        this.shouldResetLevel = false;
    }

    public void onCaught() {
        gameModel.loseLife();

        if (gameModel.getLives() <= 0) {
            displayMessage = "GAME OVER";
            gameOver = true;
            shouldResetLevel = false;
        } else {
            displayMessage = "CAUGHT!";
            shouldResetLevel = true;
        }

        messageAlpha = 255;
        messageTimer = GameConfig.CAUGHT_MESSAGE_DURATION;
    }

    public void onFell() {
        gameModel.loseLife();

        if (gameModel.getLives() <= 0) {
            displayMessage = "GAME OVER";
            gameOver = true;
            shouldResetLevel = false;
        } else {
            displayMessage = "FELL!";
            shouldResetLevel = true;
        }

        messageAlpha = 255;
        messageTimer = GameConfig.CAUGHT_MESSAGE_DURATION;
    }

    public void onLevelComplete() {
        displayMessage = "LEVEL COMPLETE!";
        messageAlpha = 255;
        messageTimer = GameConfig.CAUGHT_MESSAGE_DURATION;
        levelComplete = true;
    }

    public void onBlocked() {
        displayMessage = "COLLECT ALL BAGS!";
        messageAlpha = 255;
        messageTimer = GameConfig.CAUGHT_MESSAGE_DURATION / 2;
        shouldResetLevel = false;
    }

    public void updateMessage() {
        if (displayMessage != null && messageTimer > 0) {
            messageTimer--;
            messageAlpha = (int) (255 * ((double) messageTimer / GameConfig.CAUGHT_MESSAGE_DURATION));

            if (messageTimer == 0) {
                displayMessage = null;
                messageAlpha = 0;

                if (shouldResetLevel) {
                    resetLevel();
                    shouldResetLevel = false;
                }
            }
        }
    }

    private void resetLevel() {
        yogi.setX(level.getYogiStartX());
        yogi.setY(level.getYogiStartY());
        yogi.setVelocityY(0);
        yogi.setOnGround(false);
        level.resetLevel();
    }

    public void onBagCollected() {
        gameModel.addScore(GameConfig.POINTS_PER_BAG);
    }

    public boolean isShowingMessage() {
        return displayMessage != null;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public int getMessageAlpha() {
        return messageAlpha;
    }

    public boolean isLevelComplete() {
        return levelComplete;
    }

    public void resetLevelCompleteFlag() {
        levelComplete = false;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void resetGameOverFlag() {
        gameOver = false;
    }
}
