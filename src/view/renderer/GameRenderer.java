package view.renderer;

import model.*;
import model.collectible.Collectible;
import model.collectible.Gun;
import model.collectible.MethBasket;
import model.collectible.Money;

import model.entity.agent.Agent;
import model.level.Level;
import model.level.LevelLoader;
import model.level.tile.Tile;
import model.entity.yogi.YogiBear;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameRenderer {

    private final SpriteAtlas spriteAtlas;
    private final BufferedImage[][] yogiAnimations;
    private final BufferedImage[][] agentAnimations;
    private final BufferedImage[][] collectibleSubImages;
    private final BufferedImage[][] tileSprites;
    private final BufferedImage background;
    private final BufferedImage heartSprite;

    public GameRenderer() {
        spriteAtlas = new SpriteAtlas();
        LevelLoader.setSpriteAtlas(spriteAtlas);
        yogiAnimations = spriteAtlas.getYogiAnimations();
        agentAnimations = spriteAtlas.getAgentAnimations();
        collectibleSubImages = spriteAtlas.getCollectibleSubImages();
        tileSprites = spriteAtlas.getTileSprites();
        background = spriteAtlas.getBackground();
        heartSprite = spriteAtlas.getHeartSprite();
    }

    public void render(Graphics g, YogiBear yogi, Level level, GameModel gameModel) {
        renderBackground(g);
        renderTiles(g, level);
        renderCollectibles(g, level);
        renderYogi(g, yogi);
        renderAgents(g, level);
        renderUI(g, gameModel);
        renderHitbox(g, yogi.getHitbox());
    }

    private void renderHitbox(Graphics g, Rectangle hitbox) {
        g.setColor(Color.RED);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    private void renderBackground(Graphics g) {
        g.drawImage(background, 0, 0, GameConfig.LEVEL_WIDTH, GameConfig.LEVEL_HEIGHT, null);
    }

    private void renderTiles(Graphics g, Level level) {
        for (Tile tile : level.getTiles()) {
            if (tile.getType() != Tile.Type.AIR) {
                BufferedImage tileSprite = tileSprites[tile.getSpriteIndex()][tile.getVariant()];
                g.drawImage(tileSprite, tile.getX(), tile.getY(), tile.getSize(), tile.getSize(), null);
            }
        }
    }

    private void renderCollectibles(Graphics g, Level level) {
        double scale;
        int scaledHeight;
        int scaledWidth;
        BufferedImage sprite;

        for (Collectible collectible : level.getCollectibles()) {
            if (!collectible.isCollected()) {
                if (collectible instanceof MethBasket) {
                    scale = (double) MethBasket.SIZE / Collectible.SPRITE_HEIGHT;
                    scaledHeight = MethBasket.SIZE;
                    scaledWidth = (int) (MethBasket.SPRITE_WIDTH * scale);
                    sprite = collectibleSubImages[Collectible.METH_BASKET][Collectible.DEFAULT_STATE];
                } else if (collectible instanceof Gun) {
                    scale = (double) Gun.SIZE / Collectible.SPRITE_HEIGHT;
                    scaledHeight = Gun.SIZE;
                    scaledWidth = (int) (Gun.SPRITE_WIDTH * scale);
                    sprite = collectibleSubImages[Collectible.GUN][Collectible.DEFAULT_STATE];
                } else {
                    scale = (double) Money.SIZE / Collectible.SPRITE_HEIGHT;
                    scaledHeight = Money.SIZE;
                    scaledWidth = (int) (Money.SPRITE_WIDTH * scale);
                    sprite = collectibleSubImages[Collectible.MONEY][Collectible.DEFAULT_STATE];
                }

                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(
                        sprite,
                        collectible.getX(), collectible.getY(),
                        scaledWidth, scaledHeight,
                        null);
            } else if (collectible.isCollected() && collectible instanceof Gun) {
                scale = (double) Gun.SIZE / Collectible.SPRITE_HEIGHT;
                scaledHeight = Gun.SIZE;
                scaledWidth = (int) (Gun.SPRITE_WIDTH * scale);
                sprite = collectibleSubImages[Collectible.GUN][Gun.NO_GUN_STATE];

                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(
                        sprite,
                        collectible.getX(), collectible.getY(),
                        scaledWidth, scaledHeight,
                        null);
            }
        }
    }

    private void renderYogi(Graphics g, YogiBear yogi) {
        int yogiY;
        int scaledWidth, scaledHeight;
        double scale;

        if (!yogi.isCrouching()) {
            scale = (double) yogi.getHeight() / YogiBear.SPRITE_HEIGHT;
            scaledHeight = yogi.getHeight();
            yogiY = yogi.getY();
        } else {
            scale = (double) yogi.getHeight() * 2 / YogiBear.SPRITE_HEIGHT;
            scaledHeight = yogi.getHeight() * 2;
            int scaledOffset = YogiBear.TILE_HEIGHT - 1;
            yogiY = yogi.getY() - GameConfig.TILE_SIZE * scaledOffset;
        }

        scaledWidth = (int) (YogiBear.SPRITE_WIDTH * scale);
        int action = yogi.getAction();
        BufferedImage sprite = yogiAnimations[action][yogi.getAnimationIndex()];

        Graphics2D g2d = (Graphics2D) g;

        if (!yogi.isFacingRight()) {
            g2d.drawImage(sprite,
                    yogi.getX() + scaledWidth, yogiY,
                    -scaledWidth, scaledHeight,
                    null);
        } else {
            g2d.drawImage(sprite,
                    yogi.getX(), yogiY,
                    scaledWidth, scaledHeight,
                    null);
        }
    }

    private void renderAgents(Graphics g, Level level) {
        double scale = (double) Agent.HEIGHT / Agent.SPRITE_HEIGHT;
        int scaledHeight = Agent.HEIGHT;
        int scaledWidth = (int) (Agent.SPRITE_WIDTH * scale);

        for (Agent agent : level.getAgents()) {
            int action = agent.getAction();
            BufferedImage sprite = agentAnimations[action][agent.getAnimationIndex()];
            Graphics2D g2d = (Graphics2D) g;

            if (agent.isFacingRight()) {
                g2d.drawImage(sprite,
                        agent.getX(), agent.getY(),
                        scaledWidth, scaledHeight,
                        null);
            } else {
                g2d.drawImage(sprite,
                        agent.getX() + scaledWidth, agent.getY(),
                        -scaledWidth, scaledHeight,
                        null);
            }
        }
    }

    private void renderUI(Graphics g, GameModel gameModel) {
        if (heartSprite != null) {
            int heartSize = 30;
            int heartSpacing = 40;
            int startX = 10;
            int startY = 10;

            for (int i = 0; i < gameModel.getLives(); i++) {
                g.drawImage(heartSprite, startX + (i * heartSpacing), startY, heartSize, heartSize, null);
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + gameModel.getScore(), 10, 60);
        g.drawString("Time: " + gameModel.getFormattedTime(), 10, 85);
    }

    public void renderMessage(Graphics g, String message, int alpha, int width, int height) {
        if (message != null && alpha > 0) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 60));

            FontMetrics fm = g2d.getFontMetrics();
            int msgWidth = fm.stringWidth(message);
            int msgX = (width - msgWidth) / 2;
            int msgY = height / 2;

            g2d.drawString(message, msgX, msgY);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}
