package model.level;

import model.GameConfig;
import model.collectible.Collectible;
import model.collectible.Gun;
import model.collectible.MethBasket;
import model.collectible.Money;
import model.entity.agent.Agent;
import model.entity.yogi.YogiBear;
import model.level.tile.TileType;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class OverlayParser {

    public static class OverlayData {
        public int yogiStartX = 0;
        public int yogiStartY = 0;
        public List<Collectible> collectibles = new ArrayList<>();
        public List<Agent> agents = new ArrayList<>();
    }

    private static class PatrolLine {
        int row;
        int startCol;
        int endCol;

        PatrolLine(int row, int startCol, int endCol) {
            this.row = row;
            this.startCol = startCol;
            this.endCol = endCol;
        }
    }

    public static OverlayData parseOverlayImage(BufferedImage image) {
        OverlayData data = new OverlayData();

        parseEntities(image, data);
        parsePatrolRoutes(image, data);

        return data;
    }

    private static void parseEntities(BufferedImage image, OverlayData data) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int rgb = image.getRGB(col, row) & 0xFFFFFF;
                int x = col * GameConfig.TILE_SIZE;
                int y = row * GameConfig.TILE_SIZE;

                handleEntityPixel(rgb, x, y, data);
            }
        }
    }

    private static void handleEntityPixel(int rgb, int x, int y, OverlayData data) {
        if (rgb == (TileType.Colors.YOGI_SPAWN.getRGB() & 0xFFFFFF)) {
            data.yogiStartX = x;
            data.yogiStartY = y - GameConfig.TILE_SIZE * (YogiBear.TILE_HEIGHT - 1);
        } else if (rgb == (TileType.Colors.MONEY.getRGB() & 0xFFFFFF)) {
            int moneyY = y - GameConfig.TILE_SIZE * (Collectible.TILE_SIZE_COUNT - 1);
            data.collectibles.add(new Money(x, moneyY));
        } else if (rgb == (TileType.Colors.METH_BASKET.getRGB() & 0xFFFFFF)) {
            int basketY = y - GameConfig.TILE_SIZE * (Collectible.TILE_SIZE_COUNT - 1);
            data.collectibles.add(new MethBasket(x, basketY));
        } else if (rgb == (TileType.Colors.GUN.getRGB() & 0xFFFFFF)) {
            int gunY = y - GameConfig.TILE_SIZE * (Collectible.TILE_SIZE_COUNT - 1);
            data.collectibles.add(new Gun(x, gunY));
        }
    }

    private static void parsePatrolRoutes(BufferedImage image, OverlayData data) {
        int width = image.getWidth();
        int height = image.getHeight();
        List<PatrolLine> patrolLines = new ArrayList<>();

        for (int row = 0; row < height; row++) {
            extractPatrolLines(image, row, width, patrolLines);
        }

        createAgents(patrolLines, data);
    }

    private static void extractPatrolLines(BufferedImage image, int row, int width, List<PatrolLine> patrolLines) {
        int lineStartCol = -1;

        for (int col = 0; col < width; col++) {
            int rgb = image.getRGB(col, row) & 0xFFFFFF;

            if (rgb == (TileType.Colors.AGENT_PATROL.getRGB() & 0xFFFFFF)) {
                if (lineStartCol == -1) {
                    lineStartCol = col;
                }
            } else {
                if (lineStartCol != -1) {
                    patrolLines.add(new PatrolLine(row, lineStartCol, col - 1));
                    lineStartCol = -1;
                }
            }
        }

        if (lineStartCol != -1) {
            patrolLines.add(new PatrolLine(row, lineStartCol, width - 1));
        }
    }

    private static void createAgents(List<PatrolLine> patrolLines, OverlayData data) {
        for (PatrolLine line : patrolLines) {
            int agentX = line.startCol * GameConfig.TILE_SIZE;
            int agentY = line.row * GameConfig.TILE_SIZE - GameConfig.TILE_SIZE * (Agent.TILE_HEIGHT - 1);
            data.agents.add(new Agent(agentX, agentY, line.startCol, line.endCol));
        }
    }
}
