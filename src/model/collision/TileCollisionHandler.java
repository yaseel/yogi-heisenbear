package model.collision;

import model.GameConfig;
import model.level.Level;
import model.level.tile.Tile;
import model.entity.yogi.YogiBear;

import java.awt.Rectangle;

public class TileCollisionHandler {
    private YogiBear yogi;
    private Level level;

    public TileCollisionHandler(YogiBear yogi, Level level) {
        this.yogi = yogi;
        this.level = level;
    }

    public void checkTileCollisions() {
        boolean landedOnSomething = false;

        for (Tile tile : level.getTiles()) {
            if (tile.isSolid()) {
                landedOnSomething = checkTileCollision(tile) || landedOnSomething;
            }
        }

        // if yogi didn't land on anything and isn't at level bottom, he's airborne
        if (!landedOnSomething && yogi.getY() < GameConfig.LEVEL_HEIGHT - yogi.getHeight()) {
            yogi.setOnGround(false);
        }
    }

    // returns true if yogi landed on this tile
    private boolean checkTileCollision(Tile tile) {
        Rectangle yogiBounds = yogi.getBounds();
        Rectangle tileBounds = new Rectangle(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());

        if (!yogiBounds.intersects(tileBounds)) {
            return false;
        }

        // calculate overlaps
        int yogiLeft = yogi.getX();
        int yogiRight = yogi.getX() + yogi.getWidth();
        int yogiTop = yogi.getY();
        int yogiBottom = yogi.getY() + yogi.getHeight();

        int tileLeft = tile.getX();
        int tileRight = tile.getX() + tile.getSize();
        int tileTop = tile.getY();
        int tileBottom = tile.getY() + tile.getSize();

        int overlapLeft = yogiRight - tileLeft;
        int overlapRight = tileRight - yogiLeft;
        int overlapTop = yogiBottom - tileTop;
        int overlapBottom = tileBottom - yogiTop;

        // find smallest overlap direction
        int minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

        boolean isPlatform = tile.getType() == Tile.Type.PLATFORM;

        // platforms only have top collision when falling
        if (isPlatform) {
            return handlePlatformCollision(tile, overlapTop, tileTop);
        }

        // walls and ground have full collision
        if (minOverlap == overlapTop && yogi.isFalling()) {
            return handleLanding(tileTop);
        }

        if (minOverlap == overlapBottom && yogi.isJumping()) {
            handleCeilingHit(tileBottom);
            return false;
        }

        if (minOverlap == overlapLeft) {
            yogi.setX(tileLeft - yogi.getWidth());
            return false;
        }

        if (minOverlap == overlapRight) {
            yogi.setX(tileRight);
            return false;
        }

        return false;
    }

    private boolean handlePlatformCollision(Tile tile, int overlapTop, int tileTop) {
        // only collide with platforms when falling
        if (!yogi.isFalling()) {
            return false;
        }

        // check if dropping through
        if (yogi.isDropThroughRequested() && yogi.isCrouching()) {
            return false;
        }

        // check if yogi was above the platform last frame
        int prevBottom = yogi.getY() + yogi.getHeight() - yogi.getVelocityY();
        if (prevBottom <= tileTop && overlapTop > 0 && overlapTop < tile.getSize()) {
            return handleLanding(tileTop);
        }

        return false;
    }

    private boolean handleLanding(int tileTop) {
        yogi.setY(tileTop - yogi.getHeight());
        yogi.setVelocityY(0);
        yogi.setOnGround(true);
        yogi.clearDropThrough();
        return true;
    }

    private void handleCeilingHit(int tileBottom) {
        yogi.setY(tileBottom);
        yogi.setVelocityY(0);
    }

    // checks if yogi can stand up from crouch without hitting ceiling
    public boolean canStandUp() {
        int standingHeight = yogi.getHeight() * 2;
        int headRoom = standingHeight - yogi.getHeight();
        int headY = yogi.getY() - headRoom;

        Rectangle headArea = new Rectangle(yogi.getX(), headY, yogi.getWidth(), headRoom);

        for (Tile tile : level.getTiles()) {
            if (tile.getType() == Tile.Type.WALL || tile.getType() == Tile.Type.GROUND) {
                Rectangle tileRect = new Rectangle(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());
                if (tileRect.intersects(headArea)) {
                    return false;
                }
            }
        }

        return true;
    }
}
