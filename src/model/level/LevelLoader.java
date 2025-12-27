package model.level;

import model.level.tile.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelLoader {

    public static Level loadLevel(int levelNumber) {
        String terrainFile = "src/resources/levels/level" + levelNumber + "_terrain.png";
        String overlayFile = "src/resources/levels/level" + levelNumber + "_overlay.png";

        try {
            BufferedImage terrainImage = ImageIO.read(new File(terrainFile));
            BufferedImage overlayImage = ImageIO.read(new File(overlayFile));

            Tile[][] tileGrid = TerrainParser.parseTerrainImage(terrainImage);
            OverlayParser.OverlayData overlayData = OverlayParser.parseOverlayImage(overlayImage);

            List<Tile> tiles = flattenTileGrid(tileGrid);

            return new Level(
                    tiles,
                    overlayData.collectibles,
                    overlayData.agents,
                    overlayData.yogiStartX,
                    overlayData.yogiStartY
            );

        } catch (IOException e) {System.err.println("Error loading level " + levelNumber + ": " + e.getMessage());
            return createEmptyLevel();
        }
    }

    private static List<Tile> flattenTileGrid(Tile[][] grid) {
        List<Tile> tiles = new ArrayList<>();
        for (Tile[] row : grid) {
            Collections.addAll(tiles, row);
        }
        return tiles;
    }

    private static Level createEmptyLevel() {
        return new Level(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0, 0);
    }
}
