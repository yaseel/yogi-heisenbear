package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelLoader {
    public static Level loadLevel(String filename) {
        List<Tile> tiles = new ArrayList<>();
        int yogiStartX = 0;
        int yogiStartY = 0;
        int maxWidth = 0;
        int height = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {
                if (line.length() > maxWidth) {
                    maxWidth = line.length();
                }
                height = row + 1;

                for (int col = 0; col < line.length(); col++) {
                    char c = line.charAt(col);
                    int x = col * GameConfig.TILE_SIZE;
                    int y = row * GameConfig.TILE_SIZE;

                    Tile.Type type = charToTileType(c);

                    if (type == Tile.Type.SPAWN_POINT) {
                        yogiStartX = x;
                        yogiStartY = y;
                        type = Tile.Type.AIR;
                    }

                    tiles.add(new Tile(type, x, y));
                }
                row++;
            }
        } catch (IOException e) {
            System.err.println("Error loading level: " + e.getMessage());
        }

        int widthInPixels = maxWidth * GameConfig.TILE_SIZE;
        int heightInPixels = height * GameConfig.TILE_SIZE;
        return new Level(tiles, yogiStartX, yogiStartY, widthInPixels, heightInPixels);
    }

    private static Tile.Type charToTileType(char c) {
        return switch (c) {
            case '#' -> Tile.Type.WALL;
            case '=' -> Tile.Type.PLATFORM;
            case '_' -> Tile.Type.GROUND;
            case 'Y' -> Tile.Type.SPAWN_POINT;
            default -> Tile.Type.AIR;
        };
    }
}
