package model.collision;

import model.GameConfig;
import model.level.Level;
import view.game.GameStateManager;

import java.awt.Rectangle;

public class BoundaryHandler {

    public static void checkBoundaries(Rectangle yogiBox, Level level, GameStateManager stateManager) {
        checkBottomBoundary(yogiBox, stateManager);
        checkRightBoundary(yogiBox, level, stateManager);
    }

    private static void checkBottomBoundary(Rectangle yogiBox, GameStateManager stateManager) {
        if (yogiBox.y + yogiBox.height > GameConfig.LEVEL_HEIGHT) {
            stateManager.onFell();
        }
    }

    private static void checkRightBoundary(Rectangle yogiBox, Level level, GameStateManager stateManager) {
        if (yogiBox.x + yogiBox.width >= GameConfig.LEVEL_WIDTH) {
            if (level.getRemainingCollectibles() == 0) {
                stateManager.onLevelComplete();
            } else {
                stateManager.onBlocked();
            }
        }
    }
}
