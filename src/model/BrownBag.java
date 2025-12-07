package model;

import java.awt.*;

public class BrownBag {
    private int x, y;
    private int size;
    private boolean collected;

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

    public void reset() {
        collected = false;
    }
}
