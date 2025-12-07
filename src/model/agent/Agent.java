package model.agent;

import model.GameConfig;
import model.yogi.YogiBear;

import java.awt.*;

public class Agent {
    public enum Direction {
        LEFT, RIGHT
    }

    private int x, y;
    private int width, height;
    private int startX, startY;
    private Direction direction;

    private int patrolStartCol;
    private int patrolEndCol;

    private int targetX;
    private boolean movingRight;
    private int pauseTimer;

    public Agent(int x, int y, int patrolStartCol, int patrolEndCol) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.width = GameConfig.TILE_SIZE;
        this.height = GameConfig.TILE_SIZE * 2;

        this.patrolStartCol = patrolStartCol;
        this.patrolEndCol = patrolEndCol;

        this.movingRight = true;
        this.targetX = patrolEndCol * GameConfig.TILE_SIZE;
        this.direction = Direction.RIGHT;
        this.pauseTimer = 0;
    }

    public void update() {
        if (pauseTimer > 0) {
            pauseTimer--;
            return;
        }

        if (movingRight) {
            x += GameConfig.AGENT_SPEED;
            direction = Direction.RIGHT;
            if (x >= targetX) {
                x = targetX;
                movingRight = false;
                targetX = patrolStartCol * GameConfig.TILE_SIZE;
                pauseTimer = GameConfig.AGENT_PAUSE_FRAMES;
            }
        } else {
            x -= GameConfig.AGENT_SPEED;
            direction = Direction.LEFT;

            if (x <= targetX) {
                x = targetX;
                movingRight = true;
                targetX = patrolEndCol * GameConfig.TILE_SIZE;
                pauseTimer = GameConfig.AGENT_PAUSE_FRAMES;
            }
        }
    }

    public boolean canSeeYogi(YogiBear yogi) {
        int visionRange = GameConfig.AGENT_VISION_RANGE * GameConfig.TILE_SIZE;

        int yogiBottom = yogi.getY() + yogi.getHeight();
        int agentBottom = y + height;

        boolean yOverlap = !(yogiBottom < y || yogi.getY() > agentBottom);

        if (!yOverlap) {
            return false;
        }

        if (direction == Direction.RIGHT) {
            int visionStart = x + width;
            int visionEnd = visionStart + visionRange;
            return yogi.getX() >= visionStart && yogi.getX() < visionEnd;
        } else {
            int visionEnd = x;
            int visionStart = visionEnd - visionRange;
            return yogi.getX() + yogi.getWidth() > visionStart && yogi.getX() + yogi.getWidth() <= visionEnd;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void reset() {
        x = startX;
        y = startY;
        movingRight = true;
        targetX = patrolEndCol * GameConfig.TILE_SIZE;
        direction = Direction.RIGHT;
        pauseTimer = 0;
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
}
