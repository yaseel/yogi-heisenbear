package model;

import model.agent.Agent;

import java.util.List;

public class Level {
    private List<Tile> tiles;
    private List<BrownBag> bags;
    private List<Agent> agents;
    private int yogiStartX;
    private int yogiStartY;

    public Level(List<Tile> tiles, List<BrownBag> bags, List<Agent> agents, int yogiStartX, int yogiStartY) {
        this.tiles = tiles;
        this.bags = bags;
        this.agents = agents;
        this.yogiStartX = yogiStartX;
        this.yogiStartY = yogiStartY;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<BrownBag> getBags() {
        return bags;
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

    public int getRemainingBags() {
        int count = 0;
        for (BrownBag bag : bags) {
            if (!bag.isCollected()) {
                count++;
            }
        }
        return count;
    }

    public void resetLevel() {
        for (BrownBag bag : bags) {
            bag.reset();
        }

        for (Agent agent : agents) {
            agent.reset();
        }
    }
}
