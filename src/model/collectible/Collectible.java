package model.collectible;

import model.GameConfig;

import java.awt.*;

public abstract class Collectible {
    public static final String SPRITE_PATH = GameConfig.BASE_SPRITE_PATH + "collectible.png";

    public static final int GUN = 0;
    public static final int METH_BASKET = 1;
    public static final int MONEY = 2;

    public static final int COLLECTIBLE_COUNT = 3;
    public static final int MAX_FRAMES = 2;

    public static final int DEFAULT_STATE = 0;

    public static final int SPRITE_WIDTH = 250;
    public static final int SPRITE_HEIGHT = 250;

    public static final int TILE_SIZE_COUNT = 1;
    public static final int SIZE = GameConfig.TILE_SIZE * TILE_SIZE_COUNT;

    protected int x, y;
    protected int value;
    protected boolean collected;

    public Collectible(int x, int y) {
        this.x = x;
        this.y = y;
        this.collected = false;
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }
}
