package model.entity.yogi;

import model.GameConfig;
import model.collision.CollisionHelper;
import model.entity.Entity;

import java.awt.*;

public class YogiBear extends Entity {
    public static final String SPRITE_PATH = GameConfig.BASE_SPRITE_PATH + "yogi.png";

    public static final int IDLE = 0;
    public static final int WALK = 1;
    public static final int JUMP = 2;
    public static final int CROUCH_IDLE = 3;
    public static final int CROUCH_WALK = 4;

    public static final int ANIMATION_COUNT = 5;
    public static final int MAX_FRAMES = 4;

    public static final int SPRITE_WIDTH = 349;
    public static final int SPRITE_HEIGHT = 483;

    public static final int TILE_WIDTH = 1;
    public static final int TILE_HEIGHT = 2;

    private boolean onGround;
    private boolean crouching;
    private boolean dropThroughPlatform;
    private int dropStartY;

    private int width, height;
    private int[][] levelData;

    public YogiBear(int x, int y) {
        super(x, y);
        this.onGround = false;
        this.crouching = false;
        this.width = GameConfig.TILE_SIZE * TILE_WIDTH;
        this.height = GameConfig.TILE_SIZE * TILE_HEIGHT;
        this.hitbox = new Rectangle(x, y, width, height);
    }

    public void crouch() {
        if (onGround && !crouching) {
            crouching = true;
            int prevHeight = height;
            height /= 2;
            y += (prevHeight - height);
        }
    }

    public void standUp(boolean canStand) {
        if (crouching && canStand) {
            crouching = false;
            int prevHeight = height;
            height *= 2;
            y -= (height - prevHeight);
        }
    }

    public void moveLeft() {
        facingRight = false;
        if (crouching) {
            velocityX = -GameConfig.MOVE_SPEED / 2;
        } else {
            velocityX = -GameConfig.MOVE_SPEED;
        }
    }

    public void moveRight() {
        facingRight = true;
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

    @Override
    public void update() {
        velocityY += GameConfig.GRAVITY;

        int newY = y + velocityY;
        if (CollisionHelper.canMoveHere(x, newY, width, height, levelData, this)) {
            y = newY;
            onGround = false;
        } else {
            y = CollisionHelper.getEntityYPosUnderRoofOrAboveFloor(y, height, velocityY);
            if (velocityY > 0) {
                onGround = true;
                clearDropThrough();
            }
            velocityY = 0;
        }

        if (velocityX != 0) {
            int newX = x + velocityX;
            if (CollisionHelper.canMoveHere(newX, y, width, height, levelData, this)) {
                x = newX;
            }
        }

        if (!onGround && velocityY == 0) {
            if (!CollisionHelper.isEntityOnFloor(getHitbox(), levelData, this)) {
                onGround = false;
            }
        }

        updateHitbox();
        updateAction();
        updateJumpAnimation();
        updateAnimationTick();
    }

    private void updateJumpAnimation() {
        if (action != JUMP) {
            return;
        }

        if (velocityY < 0) {
            animationIndex = 0;
        } else {
            animationIndex = 1;
        }
    }

    @Override
    protected void updateAnimationTick() {
        if (action == JUMP) {
            return;
        }
        super.updateAnimationTick();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    protected int getActionFrames(int action) {
        return switch (action) {
            case IDLE -> 4;
            case WALK -> 4;
            case JUMP -> 2;
            case CROUCH_IDLE -> 1;
            case CROUCH_WALK -> 3;
            default -> throw new IllegalStateException("Player action not found: " + action);
        };
    }

    @Override
    protected void updateAction() {
        int oldAction = action;

        if (!onGround) {
            action = JUMP;
        } else if (crouching) {
            if (velocityX == 0) {
                action = CROUCH_IDLE;
            } else {
                action = CROUCH_WALK;
            }
        } else if (velocityX != 0) {
            action = WALK;
        } else {
            action = IDLE;
        }

        if (action != oldAction) {
            animationIndex = 0;
            animationTick = 0;
        }
    }

    public boolean isCrouching() {
        return crouching;
    }

    public void requestDropThrough() {
        if (!dropThroughPlatform) {
            dropStartY = y;
        }
        dropThroughPlatform = true;
    }

    public boolean isDropThroughRequested() {
        return dropThroughPlatform;
    }

    public void clearDropThrough() {
        dropThroughPlatform = false;
    }

    public boolean canClearDropThrough() {
        return y > dropStartY + GameConfig.TILE_SIZE;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isJumping() {
        return velocityY < 0;
    }

    public boolean isFalling() {
        return velocityY > 0;
    }

    public void setLevelData(int[][] levelData) {
        this.levelData = levelData;
    }

    public boolean canStandUp() {
        if (!crouching)
            return true;
        int standingHeight = height * 2;
        int standingY = y - (standingHeight - height);
        return CollisionHelper.canMoveHere(x, standingY, width, standingHeight, levelData, this);
    }
}
