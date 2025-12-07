package model;

import model.agent.Agent;
import model.agent.AgentSpawn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    private static class LevelData {
        List<Tile> tiles = new ArrayList<>();
        List<BrownBag> bags = new ArrayList<>();
        List<Agent> agents = new ArrayList<>();
        List<AgentSpawn> agentSpawns = new ArrayList<>();
        int yogiStartX = 0;
        int yogiStartY = 0;
    }

    public static Level loadLevel(String filename) {
        LevelData data = new LevelData();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            parseLevelFile(br, data);
        } catch (IOException e) {
            System.err.println("Error loading level: " + e.getMessage());
        }

        return new Level(data.tiles, data.bags, data.agents, data.yogiStartX, data.yogiStartY);
    }

    private static void parseLevelFile(BufferedReader br, LevelData data) throws IOException {
        String line;
        int row = 0;
        boolean inRoutesSection = false;

        while ((line = br.readLine()) != null) {
            if (line.trim().equals("ROUTES")) {
                inRoutesSection = true;
                continue;
            }

            if (inRoutesSection) {
                parseAgentRoute(line, data);
            } else {
                processTileRow(line, row, data);
                row++;
            }
        }
    }

    private static void processTileRow(String line, int row, LevelData data) {
        for (int col = 0; col < line.length(); col++) {
            char c = line.charAt(col);
            int x = col * GameConfig.TILE_SIZE;
            int y = row * GameConfig.TILE_SIZE;

            Tile.Type type = charToTileType(c);
            type = handleSpecialTile(type, x, y, data);

            data.tiles.add(new Tile(type, x, y));
        }
    }

    private static Tile.Type handleSpecialTile(Tile.Type type, int x, int y, LevelData data) {
        switch (type) {
            case SPAWN_POINT:
                data.yogiStartX = x;
                data.yogiStartY = y;
                return Tile.Type.AIR;
            case BAG:
                data.bags.add(new BrownBag(x, y));
                return Tile.Type.AIR;
            case AGENT_SPAWN:
                data.agentSpawns.add(new AgentSpawn(x, y));
                return Tile.Type.AIR;
            default:
                return type;
        }
    }

    private static void parseAgentRoute(String line, LevelData data) {
        if (line.trim().isEmpty()) {
            return;
        }

        String[] parts = line.split(":");
        if (parts.length != 2) {
            return;
        }

        try {
            int agentIndex = Integer.parseInt(parts[0].trim());
            String[] cols = parts[1].split(",");
            int startCol = Integer.parseInt(cols[0].trim());
            int endCol = Integer.parseInt(cols[1].trim());

            if (agentIndex < data.agentSpawns.size()) {
                AgentSpawn spawn = data.agentSpawns.get(agentIndex);
                data.agents.add(new Agent(spawn.x, spawn.y, startCol, endCol));
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid route format: " + line);
        }
    }

    private static Tile.Type charToTileType(char c) {
        return switch (c) {
            case '#' -> Tile.Type.WALL;
            case '=' -> Tile.Type.PLATFORM;
            case '_' -> Tile.Type.GROUND;
            case 'Y' -> Tile.Type.SPAWN_POINT;
            case '*' -> Tile.Type.BAG;
            case 'A' -> Tile.Type.AGENT_SPAWN;
            default -> Tile.Type.AIR;
        };
    }

}
