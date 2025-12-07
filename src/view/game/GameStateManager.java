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

    public GameStateManager(Level level, YogiBear yogi, GameModel gameModel) {
        this.level = level;
        this.yogi = yogi;
        this.gameModel = gameModel;
        this.displayMessage = null;
        this.messageAlpha = 0;
        this.messageTimer = 0;
    }

    public void onCaught() {
        displayMessage = "CAUGHT!";
        messageAlpha = 255;
        messageTimer = GameConfig.CAUGHT_MESSAGE_DURATION;
        gameModel.loseLife();
    }

    public void updateMessage() {
        if (displayMessage != null && messageTimer > 0) {
            messageTimer--;
            messageAlpha = (int) (255 * ((double) messageTimer / GameConfig.CAUGHT_MESSAGE_DURATION));

            if (messageTimer == 0) {
                displayMessage = null;
                messageAlpha = 0;
                resetLevel();
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
}
