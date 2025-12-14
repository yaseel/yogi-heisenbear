package model.bag;

import model.GameConfig;

import java.awt.*;

public abstract class BrownBag {
    protected int x, y;
    protected int size;
    protected int value;
    protected boolean collected;

    public BrownBag(int x, int y) {
        this.x = x;
        this.y = y;
        this.size = GameConfig.TILE_SIZE;
        this.collected = false;
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getValue() {
        return value;
    }
}
