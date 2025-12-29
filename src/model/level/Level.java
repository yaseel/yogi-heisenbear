package model.level;

import model.GameConfig;
import model.collectible.Collectible;
import model.collectible.MethBasket;
import model.entity.agent.Agent;
import model.level.tile.Tile;

import java.awt.image.BufferedImage;
import java.util.List;

public class Level {
    private List<Tile> tiles;
    private List<Collectible> collectibles;
    private List<Agent> agents;
    private int yogiStartX;
    private int yogiStartY;
    private BufferedImage background;

    public Level(List<Tile> tiles, List<Collectible> collectibles, List<Agent> agents, int yogiStartX, int yogiStartY,
            BufferedImage background) {
        this.tiles = tiles;
        this.collectibles = collectibles;
        this.agents = agents;
        this.yogiStartX = yogiStartX;
        this.yogiStartY = yogiStartY;
        this.background = background;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Collectible> getCollectibles() {
        return collectibles;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public int getYogiStartX() {
        return yogiStartX;
    }

    public int getYogiStartY() {
        return yogiStartY;
    }

    public BufferedImage getBackground() {
        return background;
    }

    public int getRemainingCollectibles() {
        int count = 0;
        for (Collectible collectible : collectibles) {
            if (!collectible.isCollected() && collectible instanceof MethBasket) {
                count++;
            }
        }
        return count;
    }

    public void resetLevel() {
        for (Agent agent : agents) {
            agent.reset();
        }
    }

    public int[][] getLevelData() {
        int cols = GameConfig.LEVEL_WIDTH / GameConfig.TILE_SIZE;
        int rows = GameConfig.LEVEL_HEIGHT / GameConfig.TILE_SIZE;
        int[][] data = new int[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                data[row][col] = -1;
            }
        }

        for (Tile tile : tiles) {
            int col = tile.getX() / GameConfig.TILE_SIZE;
            int row = tile.getY() / GameConfig.TILE_SIZE;
            if (row >= 0 && row < rows && col >= 0 && col < cols) {
                data[row][col] = tile.getType().ordinal();
            }
        }

        return data;
    }
}
