package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    public static Level loadLevel(String filename) {
        List<Tile> tiles = new ArrayList<>();
        List<BrownBag> bags = new ArrayList<>();
        int yogiStartX = 0;
        int yogiStartY = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {

                for (int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);
                    int x = col * GameConfig.TILE_SIZE;
                    int y = row * GameConfig.TILE_SIZE;

                    Tile.Type type = charToTileType(c);

                    if (type == Tile.Type.SPAWN_POINT) {
                        yogiStartX = x;
                        yogiStartY = y;
                        type = Tile.Type.AIR;
                    } else if (type == Tile.Type.BAG) {
                        bags.add(new BrownBag(x, y));
                        type = Tile.Type.AIR;
                    }

                    tiles.add(new Tile(type, x, y));
                }
                row++;
            }
        } catch (IOException e) {
            System.err.println("Error loading level: " + e.getMessage());
        }

        return new Level(tiles, bags, yogiStartX, yogiStartY);
    }

    private static Tile.Type charToTileType(char c) {
        return switch (c) {
            case '#' -> Tile.Type.WALL;
            case '=' -> Tile.Type.PLATFORM;
            case '_' -> Tile.Type.GROUND;
            case 'Y' -> Tile.Type.SPAWN_POINT;
            case '*' -> Tile.Type.BAG;
            default -> Tile.Type.AIR;
        };
    }
}
