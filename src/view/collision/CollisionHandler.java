package view.collision;

import model.*;
import java.awt.Rectangle;

public class CollisionHandler {
    private YogiBear yogi;
    private Level level;

    public CollisionHandler(YogiBear yogi, Level level) {
        this.yogi = yogi;
        this.level = level;
    }

    public void checkAllCollisions() {
        checkTileCollisions();
    }

    private void checkTileCollisions() {
        Rectangle yogiBounds = yogi.getBounds();
        boolean onSolidGround = false;

        for (Tile tile : level.getTiles()) {
            if (!tile.isSolid())
                continue;

            Rectangle tileBounds = new Rectangle(
                    tile.getX(),
                    tile.getY(),
                    tile.getSize(),
                    tile.getSize());

            if (yogiBounds.intersects(tileBounds)) {
                if (checkVerticalCollision(tile)) {
                    onSolidGround = true;
                }

                if (tile.getType() != Tile.Type.PLATFORM) {
                    checkHorizontalCollision(tile);
                    checkCeilingCollision(tile);
                }
            }
        }

        if (!onSolidGround && yogi.getY() < GameConfig.LEVEL_HEIGHT - yogi.getHeight()) {
            yogi.setOnGround(false);
        }
    }

    private boolean checkVerticalCollision(Tile tile) {
        if (!yogi.isFalling())
            return false;

        int yogiBottom = yogi.getY() + yogi.getHeight();
        int tileTop = tile.getY();
        int previousYogiBottom = yogiBottom - yogi.getVelocityY();

        boolean skipPlatform = tile.getType() == Tile.Type.PLATFORM && yogi.isDropThroughRequested()
                && yogi.isCrouching();

        if (!skipPlatform && previousYogiBottom <= tileTop + 5 && yogiBottom > tileTop) {
            yogi.setY(tileTop - yogi.getHeight());
            yogi.setVelocityY(0);
            yogi.setOnGround(true);
            yogi.clearDropThrough();
            return true;
        }

        return false;
    }

    private void checkHorizontalCollision(Tile tile) {
        int yogiLeft = yogi.getX();
        int yogiRight = yogi.getX() + yogi.getWidth();
        int tileLeft = tile.getX();
        int tileRight = tile.getX() + tile.getSize();

        int yogiCenterY = yogi.getY() + yogi.getHeight() / 2;
        int tileCenterY = tile.getY() + tile.getSize() / 2;

        if (Math.abs(yogiCenterY - tileCenterY) < tile.getSize()) {
            if (yogiRight > tileLeft && yogiRight < tileLeft + 10 && yogiLeft < tileLeft) {
                yogi.setX(tileLeft - yogi.getWidth());
            } else if (yogiLeft < tileRight && yogiLeft > tileRight - 10 && yogiRight > tileRight) {
                yogi.setX(tileRight);
            }
        }
    }

    private void checkCeilingCollision(Tile tile) {
        if (!yogi.isJumping())
            return;

        int yogiTop = yogi.getY();
        int tileBottom = tile.getY() + tile.getSize();
        int previousYogiTop = yogiTop - yogi.getVelocityY();

        if (previousYogiTop >= tileBottom - 5 && yogiTop < tileBottom) {
            yogi.setY(tileBottom);
            yogi.setVelocityY(0);
            yogi.setOnGround(false);
        }
    }
}
