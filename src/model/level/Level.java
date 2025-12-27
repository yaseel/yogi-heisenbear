package model.level;

import model.collectible.Collectible;
import model.entity.agent.Agent;
import model.level.tile.Tile;

import java.util.List;

public class Level {
    private List<Tile> tiles;
    private List<Collectible> collectibles;
    private List<Agent> agents;
    private int yogiStartX;
    private int yogiStartY;

    public Level(List<Tile> tiles, List<Collectible> collectibles, List<Agent> agents, int yogiStartX, int yogiStartY) {
        this.tiles = tiles;
        this.collectibles = collectibles;
        this.agents = agents;
        this.yogiStartX = yogiStartX;
        this.yogiStartY = yogiStartY;
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

    public int getRemainingCollectibles() {
        int count = 0;
        for (Collectible collectible : collectibles) {
            if (!collectible.isCollected()) {
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
}
