package model;

import java.awt.*;

public class YogiBear {
    private int x, y;
    private int width, height;
    private int velocityX, velocityY;
    private boolean onGround;
    private boolean crouching;
    private boolean dropThroughPlatform;

    public YogiBear(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = GameConfig.TILE_SIZE;
        this.height = GameConfig.TILE_SIZE * 2;
        this.velocityX = 0;
        this.velocityY = 0;
        this.onGround = false;
        this.crouching = false;
    }

    public void crouch() {
        if (onGround && !crouching) {
            crouching = true;
            int prevHeight = height;
            height /= 2;
            y += (prevHeight - height);
        }
    }

    public void standUp(Level level) {
        if (crouching) {
            if (canStandUp(level)) {
                crouching = false;
                int prevHeight = height;
                height *= 2;
                y -= (height - prevHeight);
            }
        }
    }

    private boolean canStandUp(Level level) {
        int standingHeight = height * 2;
        int headSpace = standingHeight - height;
        int checkY = y - headSpace;

        for (Tile tile : level.getTiles()) {
            if (tile.getType() == Tile.Type.WALL || tile.getType() == Tile.Type.GROUND) {
                Rectangle tileRect = new Rectangle(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());
                Rectangle headRect = new Rectangle(x, checkY, width, headSpace);

                if (tileRect.intersects(headRect)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void moveLeft() {
        if (crouching) {
            velocityX = -GameConfig.MOVE_SPEED / 2;
        } else {
            velocityX = -GameConfig.MOVE_SPEED;
        }
    }

    public void moveRight() {
        if (crouching) {
            velocityX = GameConfig.MOVE_SPEED / 2;
        } else {
            velocityX = GameConfig.MOVE_SPEED;
        }
    }

    public void stopMoving() {
        velocityX = 0;
    }

    public void jump() {
        if (onGround) {
            if (crouching) {
                velocityY = (int) (GameConfig.JUMP_STRENGTH * 0.6);
            } else {
                velocityY = GameConfig.JUMP_STRENGTH;
            }
            onGround = false;
        }
    }

    public void update() {
        x += velocityX;

        if (x < 0)
            x = 0;
        if (x > GameConfig.LEVEL_WIDTH - width)
            x = GameConfig.LEVEL_WIDTH - width;

        velocityY += GameConfig.GRAVITY;
        y += velocityY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isCrouching() {
        return crouching;
    }

    public void requestDropThrough() {
        dropThroughPlatform = true;
    }

    public boolean isDropThroughRequested() {
        return dropThroughPlatform;
    }

    public void clearDropThrough() {
        dropThroughPlatform = false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isJumping() {
        return (this.velocityY < 0);
    }

    public boolean isFalling() {
        return (this.velocityY > 0);
    }
}
