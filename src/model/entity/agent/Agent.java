package model.entity.agent;

import model.GameConfig;
import model.entity.Entity;
import model.entity.yogi.YogiBear;

import java.awt.*;

public class Agent extends Entity {

    public static final int IDLE = 0;
    public static final int WALK = 1;

    public static final int ANIMATION_COUNT = 2;
    public static final int MAX_FRAMES = 4;

    public static final int SPRITE_WIDTH = 488;
    public static final int SPRITE_HEIGHT = 491;

    public static final int TILE_WIDTH = 2;
    public static final int TILE_HEIGHT = 4;

    public static final int WIDTH = GameConfig.TILE_SIZE * TILE_WIDTH;
    public static final int HEIGHT = GameConfig.TILE_SIZE * TILE_HEIGHT;

    public static final String SPRITE_PATH = GameConfig.BASE_SPRITE_PATH + "agent.png";

    private int startX, startY;
    private int patrolStartCol;
    private int patrolEndCol;
    private int targetX;
    private boolean movingRight;
    private int pauseTimer;

    public Agent(int x, int y, int patrolStartCol, int patrolEndCol) {
        super(x, y);
        this.startX = x;
        this.startY = y;

        this.patrolStartCol = patrolStartCol;
        this.patrolEndCol = patrolEndCol;

        this.movingRight = true;
        this.targetX = patrolEndCol * GameConfig.TILE_SIZE;
        this.facingRight = true;
        this.pauseTimer = 0;

        updateAction();
    }

    @Override
    public void update() {
        updateAction();
        updateAnimationTick();

        if (pauseTimer > 0) {
            pauseTimer--;
            return;
        }

        if (movingRight) {
            x += GameConfig.AGENT_SPEED;
            facingRight = true;

            if (x >= targetX) {
                x = targetX;
                movingRight = false;
                targetX = patrolStartCol * GameConfig.TILE_SIZE;
                pauseTimer = GameConfig.AGENT_PAUSE_FRAMES;
            }
        } else {
            x -= GameConfig.AGENT_SPEED;
            facingRight = false;

            if (x <= targetX) {
                x = targetX;
                movingRight = true;
                targetX = patrolEndCol * GameConfig.TILE_SIZE;
                pauseTimer = GameConfig.AGENT_PAUSE_FRAMES;
            }
        }
    }

    @Override
    protected void updateAction() {
        int oldAction = action;

        if (pauseTimer > 0) {
            action = IDLE;
        } else {
            action = WALK;
        }

        if (action != oldAction) {
            animationIndex = 0;
            animationTick = 0;
        }
    }

    @Override
    protected int getActionFrames(int action) {
        return switch (action) {
            case IDLE -> 1;
            case WALK -> 4;
            default -> throw new IllegalStateException("Agent action not found: " + action);
        };
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public boolean canSeeYogi(YogiBear yogi) {
        int visionRange = GameConfig.AGENT_VISION_RANGE * GameConfig.TILE_SIZE;

        int yogiBottom = yogi.getY() + yogi.getHeight();
        int agentBottom = y + HEIGHT;

        boolean yOverlap = !(yogiBottom < y || yogi.getY() > agentBottom);

        if (!yOverlap) {
            return false;
        }

        if (facingRight) {
            int visionStart = x + WIDTH;
            int visionEnd = visionStart + visionRange;
            return yogi.getX() >= visionStart && yogi.getX() < visionEnd;
        } else {
            int visionEnd = x;
            int visionStart = visionEnd - visionRange;
            return yogi.getX() + yogi.getWidth() > visionStart && yogi.getX() + yogi.getWidth() <= visionEnd;
        }
    }

    public void reset() {
        x = startX;
        y = startY;
        movingRight = true;
        targetX = patrolEndCol * GameConfig.TILE_SIZE;
        facingRight = true;
        pauseTimer = 0;
        action = WALK;
        animationIndex = 0;
        animationTick = 0;
    }
}
