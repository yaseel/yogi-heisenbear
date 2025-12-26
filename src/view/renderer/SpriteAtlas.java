package view.renderer;

import model.collectible.Collectible;
import model.entity.agent.Agent;
import model.entity.yogi.YogiBear;

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
    private BufferedImage[][] collectibleSubImages;

    public SpriteAtlas() {
        loadSprites();
        loadAllSubImages();
    }

    private void loadSprites() {
        yogiSprite = loadSprite(YogiBear.spritePath);
        agentSprite = loadSprite(Agent.spritePath);
        collectibleSprite = loadSprite(Collectible.spritePath);
    }

    private BufferedImage loadSprite(String path) {
        try {
            File spriteFile = new File(path);
            if (spriteFile.exists()) {
                return ImageIO.read(spriteFile);
            } else {
                System.err.println("Sprite not found: " + spriteFile.getAbsolutePath());
                return null;
            }
        } catch (IOException e) {
            System.err.println("Failed to load sprite: " + e.getMessage());
            return null;
        }
    }

    private void loadAllSubImages() {
        yogiAnimations = new BufferedImage[YogiBear.ANIMATION_COUNT][YogiBear.MAX_FRAMES];
        loadSubImages(yogiAnimations, yogiSprite, YogiBear.SPRITE_WIDTH, YogiBear.SPRITE_HEIGHT);

        agentAnimations = new BufferedImage[Agent.ANIMATION_COUNT][Agent.MAX_FRAMES];
        loadSubImages(agentAnimations, agentSprite, Agent.SPRITE_WIDTH, Agent.SPRITE_HEIGHT);

        collectibleSubImages = new BufferedImage[Collectible.COLLECTIBLE_COUNT][Collectible.MAX_FRAMES];
        loadSubImages(collectibleSubImages, collectibleSprite, Collectible.SPRITE_WIDTH, Collectible.SPRITE_HEIGHT);
    }

    private void loadSubImages(BufferedImage[][] animations, BufferedImage sprite, int spriteWidth, int spriteHeight) {
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = sprite.getSubimage(
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
        return collectibleSubImages;
    }
}
