package model.level;

import model.GameConfig;
import model.level.tile.Tile;
import model.level.tile.TileType;
import model.level.tile.TileVariantCalculator;

import java.awt.image.BufferedImage;

public class TerrainParser {

    public static Tile[][] parseTerrainImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Tile[][] tileGrid = new Tile[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int rgb = image.getRGB(col, row) & 0xFFFFFF;
                int x = col * GameConfig.TILE_SIZE;
                int y = row * GameConfig.TILE_SIZE;

                Tile.Type tileType = colorToTileType(rgb);
                int spriteIndex = tileTypeToSpriteIndex(tileType);

                tileGrid[row][col] = new Tile(tileType, x, y, spriteIndex);
            }
        }

        TileVariantCalculator.calculateVariants(tileGrid, width, height);

        return tileGrid;
    }

    private static Tile.Type colorToTileType(int rgb) {
        if (rgb == (TileType.Colors.WALL_COLOR.getRGB() & 0xFFFFFF))
            return Tile.Type.WALL;
        else if (rgb == (TileType.Colors.PLATFORM_COLOR.getRGB() & 0xFFFFFF))
            return Tile.Type.PLATFORM;
        else if (rgb == (TileType.Colors.GROUND_COLOR.getRGB() & 0xFFFFFF))
            return Tile.Type.GROUND;
        else
            return Tile.Type.AIR;

    }

    private static int tileTypeToSpriteIndex(Tile.Type type) {
        return switch (type) {
            case WALL -> TileType.WALL;
            case PLATFORM -> TileType.PLATFORM;
            case GROUND -> TileType.GROUND;
            default -> 0;
        };
    }
}
