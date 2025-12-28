package view.renderer;

import model.GameConfig;
import model.collectible.Collectible;
import model.entity.agent.Agent;
import model.entity.yogi.YogiBear;
import model.level.tile.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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

    private BufferedImage background;
    private BufferedImage heartSprite;
    private BufferedImage levelsSprite;
    private BufferedImage[][] levelSprites;

    public SpriteAtlas() {
        loadSprites();
        loadAllSubImages();
    }

    private void loadSprites() {
        yogiSprite = loadSprite(YogiBear.SPRITE_PATH);
        agentSprite = loadSprite(Agent.SPRITE_PATH);
        collectibleSprite = loadSprite(Collectible.SPRITE_PATH);
        tileSprite = loadSprite(Tile.SPRITE_PATH);
        background = loadSprite(GameConfig.BASE_SPRITE_PATH + "background.png");
        heartSprite = loadSprite(GameConfig.BASE_SPRITE_PATH + "heart.png");
        levelsSprite = loadSprite(GameConfig.BASE_SPRITE_PATH + "levels.png");
    }

    private BufferedImage loadSprite(String path) {
        try {
            File spriteFile = new File(path);
            if (spriteFile.exists()) {
                return ImageIO.read(spriteFile);
            }
        } catch (IOException e) {
            System.err.println("Failed to load sprite: " + path);
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

    public BufferedImage getBackground() {
        return background;
    }

    public BufferedImage getHeartSprite() {
        return heartSprite;
    }

    public BufferedImage[][] getLevelSprites() {
        return levelSprites;
    }
}
