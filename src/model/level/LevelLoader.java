package model.level;

import model.level.tile.Tile;
import view.renderer.SpriteAtlas;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelLoader {
    private static SpriteAtlas spriteAtlas;

    public static void setSpriteAtlas(SpriteAtlas atlas) {
        spriteAtlas = atlas;
    }

    public static Level loadLevel(int levelNumber) {
        BufferedImage[][] levelSprites = spriteAtlas.getLevelSprites();
        int levelIndex = levelNumber - 1;

        BufferedImage terrainImage = levelSprites[levelIndex][0];
        BufferedImage overlayImage = levelSprites[levelIndex][1];

        Tile[][] tileGrid = TerrainParser.parseTerrainImage(terrainImage);
        OverlayParser.OverlayData overlayData = OverlayParser.parseOverlayImage(overlayImage);

        List<Tile> tiles = flattenTileGrid(tileGrid);

        return new Level(
                tiles,
                overlayData.collectibles,
                overlayData.agents,
                overlayData.yogiStartX,
                overlayData.yogiStartY);
    }

    private static List<Tile> flattenTileGrid(Tile[][] grid) {
        List<Tile> tiles = new ArrayList<>();
        for (Tile[] row : grid) {
            Collections.addAll(tiles, row);
        }
        return tiles;
    }
}
