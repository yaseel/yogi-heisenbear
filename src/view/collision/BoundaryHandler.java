package view.collision;

import model.GameConfig;
import model.Level;
import model.YogiBear;

public class BoundaryHandler {
    private YogiBear yogi;

    public BoundaryHandler(YogiBear yogi) {
        this.yogi = yogi;
    }

    public BoundaryResult checkBoundaries(Level level) {
        int yogiLeft = yogi.getX();
        int yogiRight = yogi.getX() + yogi.getWidth();
        int yogiBottom = yogi.getY() + yogi.getHeight();

        if (yogiRight < 0) {
            return BoundaryResult.FAIL;
        }

        if (yogiBottom > GameConfig.LEVEL_HEIGHT) {
            return BoundaryResult.FAIL;
        }

        if (yogiLeft > GameConfig.LEVEL_WIDTH) {
            if (level.getRemainingBags() == 0) {
                return BoundaryResult.NEXT_LEVEL;
            }
        }

        return BoundaryResult.NONE;
    }

    public enum BoundaryResult {
        NONE,
        FAIL,
        NEXT_LEVEL
    }
}
