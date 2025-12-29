package model.collision;

import model.GameConfig;
import model.entity.yogi.YogiBear;
import model.level.tile.Tile;

import java.awt.Rectangle;

public class CollisionHelper {

    public static boolean canMoveHere(int x, int y, int width, int height, int[][] levelData, YogiBear yogi) {
        if (isSolid(x, y, levelData, yogi))
            return false;
        if (isSolid(x + width - 1, y, levelData, yogi))
            return false;
        if (isSolid(x, y + height - 1, levelData, yogi))
            return false;
        if (isSolid(x + width - 1, y + height - 1, levelData, yogi))
            return false;

        if (height > GameConfig.TILE_SIZE) {
            if (isSolid(x, y + GameConfig.TILE_SIZE, levelData, yogi))
                return false;
            if (isSolid(x + width - 1, y + GameConfig.TILE_SIZE, levelData, yogi))
                return false;
        }

        if (width > GameConfig.TILE_SIZE) {
            if (isSolid(x + GameConfig.TILE_SIZE, y, levelData, yogi))
                return false;
            if (isSolid(x + GameConfig.TILE_SIZE, y + height - 1, levelData, yogi))
                return false;
        }

        return true;
    }

    private static boolean isSolid(int x, int y, int[][] levelData, YogiBear yogi) {
        int maxWidth = levelData[0].length * GameConfig.TILE_SIZE;
        if (x < 0)
            return true;
        if (y < 0)
            return false;
        if (x >= maxWidth)
            return true;
        if (y >= GameConfig.LEVEL_HEIGHT)
            return false;

        int xTile = x / GameConfig.TILE_SIZE;
        int yTile = y / GameConfig.TILE_SIZE;
        int tileValue = levelData[yTile][xTile];

        if (tileValue < 0)
            return false;

        Tile.Type type = Tile.Type.values()[tileValue];

        if (type == Tile.Type.GROUND) {
            return true;
        }

        if (type == Tile.Type.PLATFORM) {
            if (yogi != null && yogi.isFalling() && !yogi.isDropThroughRequested()) {
                int platformTop = yTile * GameConfig.TILE_SIZE;
                Rectangle yogiBox = yogi.getHitbox();
                int yogiFeet = yogiBox.y + yogiBox.height;
                return yogiFeet <= platformTop + GameConfig.TILE_SIZE;
            }
            return false;
        }

        return false;
    }

    public static int getEntityYPosUnderRoofOrAboveFloor(int currentY, int height, int airSpeed) {
        if (airSpeed > 0) {
            int feetAfterMove = currentY + height - 1 + airSpeed;
            int floorTileTop = (feetAfterMove / GameConfig.TILE_SIZE) * GameConfig.TILE_SIZE;
            return floorTileTop - height;
        } else {
            int headAfterMove = currentY + airSpeed;
            int ceilingTileBottom = ((headAfterMove / GameConfig.TILE_SIZE) + 1) * GameConfig.TILE_SIZE;
            return ceilingTileBottom;
        }
    }

    public static boolean isEntityOnFloor(Rectangle hitbox, int[][] levelData, YogiBear yogi) {
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height, levelData, yogi))
            if (!isSolid(hitbox.x + hitbox.width - 1, hitbox.y + hitbox.height, levelData, yogi))
                return false;
        return true;
    }
}
