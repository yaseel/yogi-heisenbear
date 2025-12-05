package model;

import java.awt.*;

public class YogiBear {
    private int x, y;
    private int width, height;
    private int velocityX, velocityY;
    private boolean onGround;
    private boolean crouching;

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

    public void standUp() {
        if (crouching) {
            crouching = false;
            int prevHeight = height;
            height *= 2;
            y -= (height - prevHeight);
        }
    }

    public void moveLeft() {
        velocityX = -GameConfig.MOVE_SPEED;
    }

    public void moveRight() {
        velocityX = GameConfig.MOVE_SPEED;
    }

    public void stopMoving() {
        velocityX = 0;
    }

    public void jump() {
        if (onGround) {
            velocityY = GameConfig.JUMP_STRENGTH;
            onGround = false;
        }
    }

    public void update(int levelWidth) {
        x += velocityX;

        if (x < 0)
            x = 0;
        if (x > levelWidth - width)
            x = levelWidth - width;

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
}
