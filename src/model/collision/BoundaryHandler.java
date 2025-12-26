package model.collision;

import model.GameConfig;
import model.level.Level;
import model.entity.yogi.YogiBear;

public class BoundaryHandler {
    private YogiBear yogi;

    public BoundaryHandler(YogiBear yogi) {
        this.yogi = yogi;
    }

    public BoundaryResult checkBoundaries(Level level) {
        int yogiRight = yogi.getX() + yogi.getWidth();
        int yogiBottom = yogi.getY() + yogi.getHeight();

        if (yogiBottom > GameConfig.LEVEL_HEIGHT) {
            return BoundaryResult.FELL;
        }

        if (yogiRight >= GameConfig.LEVEL_WIDTH) {
            if (level.getRemainingCollectibles() == 0) {
                return BoundaryResult.NEXT_LEVEL;
            } else {
                return BoundaryResult.BLOCKED;
            }
        }

        return BoundaryResult.NONE;
    }

    public enum BoundaryResult {
        NONE,
        FELL,
        NEXT_LEVEL,
        BLOCKED
    }
}
