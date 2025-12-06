package model;

import java.util.List;

public class Level {
    private List<Tile> tiles;
    private List<BrownBag> bags;
    private int yogiStartX;
    private int yogiStartY;

    public Level(List<Tile> tiles, List<BrownBag> bags, int yogiStartX, int yogiStartY) {
        this.tiles = tiles;
        this.bags = bags;
        this.yogiStartX = yogiStartX;
        this.yogiStartY = yogiStartY;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<BrownBag> getBags() {
        return bags;
    }

    public int getYogiStartX() {
        return yogiStartX;
    }

    public int getYogiStartY() {
        return yogiStartY;
    }

    public int getRemainingBags() {
        int count = 0;
        for (BrownBag bag : bags) {
            if (!bag.isCollected()) {
                count++;
            }
        }
        return count;
    }
}
