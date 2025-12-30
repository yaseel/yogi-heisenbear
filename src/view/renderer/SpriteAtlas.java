package view.renderer;

import model.GameConfig;
import model.collectible.Collectible;
import model.entity.agent.Agent;
import model.entity.yogi.YogiBear;
import model.level.tile.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;

public class SpriteAtlas {

    private BufferedImage yogiSprite;
    private BufferedImage[][] yogiAnimations;

    private BufferedImage agentSprite;
    private BufferedImage[][] agentAnimations;

    private BufferedImage collectibleSprite;
    private BufferedImage[][] collectibleSprites;

    private BufferedImage tileSprite;
    private BufferedImage[][] tileSprites;

    private BufferedImage[] backgrounds;
    private BufferedImage heartSprite;
    private BufferedImage levelsSprite;
    private BufferedImage[][] levelSprites;

    public static final int BACKGROUND_COUNT = 4;
    public static final String[] BACKGROUND_FILES = { "forest.png", "desert.png", "industrial.png", "lab.png" };

    public SpriteAtlas() {
        loadSprites();
        loadAllSubImages();
    }

    private void loadSprites() {
        yogiSprite = loadSprite(YogiBear.SPRITE_PATH);
        agentSprite = loadSprite(Agent.SPRITE_PATH);
        collectibleSprite = loadSprite(Collectible.SPRITE_PATH);
        tileSprite = loadSprite(Tile.SPRITE_PATH);
        heartSprite = loadSprite(GameConfig.BASE_SPRITE_PATH + "heart.png");
        levelsSprite = loadSprite(GameConfig.BASE_SPRITE_PATH + "levels.png");
        loadBackgrounds();
    }

    private BufferedImage loadSprite(String path) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(path);
            if (is != null) {
                return ImageIO.read(is);
            }
            System.err.println("Resource not found in JAR: " + path);
        } catch (IOException e) {
            System.err.println("Failed to load sprite: " + path);
            e.printStackTrace();
        }
        return null;
    }

    private void loadAllSubImages() {
        yogiAnimations = new BufferedImage[YogiBear.ANIMATION_COUNT][YogiBear.MAX_FRAMES];
        loadSubImages(yogiAnimations, yogiSprite, YogiBear.SPRITE_WIDTH, YogiBear.SPRITE_HEIGHT);

        agentAnimations = new BufferedImage[Agent.ANIMATION_COUNT][Agent.MAX_FRAMES];
        loadSubImages(agentAnimations, agentSprite, Agent.SPRITE_WIDTH, Agent.SPRITE_HEIGHT);

        collectibleSprites = new BufferedImage[Collectible.COLLECTIBLE_COUNT][Collectible.MAX_FRAMES];
        loadSubImages(collectibleSprites, collectibleSprite, Collectible.SPRITE_WIDTH, Collectible.SPRITE_HEIGHT);

        tileSprites = new BufferedImage[Tile.TILE_COUNT][Tile.MAX_TILE_VARIANTS];
        loadSubImages(tileSprites, tileSprite, Tile.SPRITE_SIZE, Tile.SPRITE_SIZE);

        int levelTileWidth = GameConfig.LEVEL_WIDTH / GameConfig.TILE_SIZE;
        int levelTileHeight = GameConfig.LEVEL_HEIGHT / GameConfig.TILE_SIZE;
        levelSprites = new BufferedImage[GameConfig.LAST_LEVEL_NUM][2];
        loadSubImages(levelSprites, levelsSprite, levelTileWidth, levelTileHeight);
    }

    private void loadSubImages(BufferedImage[][] sprites, BufferedImage spriteSheet, int spriteWidth,
            int spriteHeight) {
        if (spriteSheet == null)
            return;

        for (int i = 0; i < sprites.length; i++) {
            for (int j = 0; j < sprites[i].length; j++) {
                sprites[i][j] = spriteSheet.getSubimage(
                        j * spriteWidth,
                        i * spriteHeight,
                        spriteWidth,
                        spriteHeight);
            }
        }
    }

    public BufferedImage[][] getYogiAnimations() {
        return yogiAnimations;
    }

    public BufferedImage[][] getAgentAnimations() {
        return agentAnimations;
    }

    public BufferedImage[][] getCollectibleSubImages() {
        return collectibleSprites;
    }

    public BufferedImage[][] getTileSprites() {
        return tileSprites;
    }

    private void loadBackgrounds() {
        backgrounds = new BufferedImage[BACKGROUND_COUNT];
        for (int i = 0; i < BACKGROUND_COUNT; i++) {
            backgrounds[i] = loadSprite(GameConfig.BASE_BACKGROUND_PATH + BACKGROUND_FILES[i]);
        }
    }

    public BufferedImage getBackground(String filename) {
        for (int i = 0; i < BACKGROUND_FILES.length; i++) {
            if (BACKGROUND_FILES[i].equals(filename)) {
                return backgrounds[i];
            }
        }
        return backgrounds[0];
    }

    public BufferedImage getHeartSprite() {
        return heartSprite;
    }

    public BufferedImage[][] getLevelSprites() {
        return levelSprites;
    }
}
