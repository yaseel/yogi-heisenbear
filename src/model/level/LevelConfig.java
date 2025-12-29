package model.level;

import model.level.tile.TileType;

public class LevelConfig {

    private static class LevelRange {
        int startLevel;
        int endLevel;
        String backgroundFile;
        int groundTilesetIndex;
        int platformTilesetIndex;

        LevelRange(int start, int end, String bg, int ground, int platform) {
            this.startLevel = start;
            this.endLevel = end;
            this.backgroundFile = bg;
            this.groundTilesetIndex = ground;
            this.platformTilesetIndex = platform;
        }

        boolean contains(int level) {
            return level >= startLevel && level <= endLevel;
        }
    }

    private static final LevelRange[] LEVEL_CONFIGS = {
            new LevelRange(1, 2, "forest.png", TileType.GRASS, TileType.WOOD),
            new LevelRange(3, 5, "desert.png", TileType.SANDSTONE, TileType.PIPE),
            new LevelRange(6, 7, "industrial.png", TileType.SANDSTONE, TileType.PIPE),
            new LevelRange(8, 10, "lab.png", TileType.RED_TILE, TileType.CATWALK),
    };

    public static String getBackgroundForLevel(int levelNumber) {
        for (LevelRange range : LEVEL_CONFIGS) {
            if (range.contains(levelNumber)) {
                return range.backgroundFile;
            }
        }
        return "forest.png";
    }

    public static int getGroundTilesetIndex(int levelNumber) {
        for (LevelRange range : LEVEL_CONFIGS) {
            if (range.contains(levelNumber)) {
                return range.groundTilesetIndex;
            }
        }
        return 0;
    }

    public static int getPlatformTilesetIndex(int levelNumber) {
        for (LevelRange range : LEVEL_CONFIGS) {
            if (range.contains(levelNumber)) {
                return range.platformTilesetIndex;
            }
        }
        return 1;
    }
}
