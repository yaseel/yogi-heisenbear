package model.entity;

import model.GameConfig;

import java.awt.*;

public abstract class Entity {
    protected int x, y;
    protected int velocityX, velocityY;
    protected int action;
    protected Rectangle hitbox;

    protected int animationTick = 0;
    protected int animationIndex = 0;
    protected boolean facingRight = true;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public abstract void update();

    protected abstract void updateAction();

    protected abstract int getActionFrames(int action);

    protected void updateAnimationTick() {
        animationTick++;

        if (animationTick >= GameConfig.ANIMATION_SPEED) {
            animationTick = 0;
            animationIndex++;

            if (animationIndex >= getActionFrames(action)) {
                animationIndex = 0;
            }
        }
    }

    protected void updateHitbox() {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public int getAction() {
        return action;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
}
