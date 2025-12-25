package view.renderer;

import model.*;
import model.bag.BrownBag;
import model.bag.GunBag;
import model.bag.MethBag;
import model.bag.MoneyBag;

import model.entity.agent.Agent;
import model.level.Level;
import model.level.Tile;
import model.entity.yogi.YogiBear;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameRenderer {

    private BufferedImage yogiSprite;
    private BufferedImage[][] yogiAnimations;

    private BufferedImage agentSprite;
    private BufferedImage[][] agentAnimations;

    public GameRenderer() {
        loadSprites();
        loadAnimations();
    }

    public void render(Graphics g, YogiBear yogi, Level level, GameModel gameModel) {
        renderTiles(g, level);
        renderBags(g, level);
        renderYogi(g, yogi);
        renderAgents(g, level);
        renderUI(g, gameModel);
    }

    private void loadSprites() {
        yogiSprite = loadSprite(YogiBear.spritePath);
        agentSprite = loadSprite(Agent.spritePath);
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

    private void loadAnimations() {
        yogiAnimations = new BufferedImage[YogiBear.ANIMATION_COUNT][YogiBear.MAX_FRAMES];
        loadSubImages(yogiAnimations, yogiSprite, YogiBear.SPRITE_WIDTH, YogiBear.SPRITE_HEIGHT);

        agentAnimations = new BufferedImage[Agent.ANIMATION_COUNT][Agent.MAX_FRAMES];
        loadSubImages(agentAnimations, agentSprite, Agent.SPRITE_WIDTH, Agent.SPRITE_HEIGHT);
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

    private void renderTiles(Graphics g, Level level) {
        for (Tile tile : level.getTiles()) {
            if (tile.getType() == Tile.Type.WALL) {
                g.setColor(new Color(101, 67, 33));
                g.fillRect(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());
            } else if (tile.getType() == Tile.Type.PLATFORM) {
                g.setColor(new Color(139, 69, 19));
                g.fillRect(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());
            } else if (tile.getType() == Tile.Type.GROUND) {
                g.setColor(new Color(90, 60, 30));
                g.fillRect(tile.getX(), tile.getY(), tile.getSize(), tile.getSize());
            }
        }
    }

    private void renderBags(Graphics g, Level level) {
        for (BrownBag bag : level.getBags()) {
            if (!bag.isCollected()) {
                if (bag instanceof MethBag)
                    g.setColor(Color.CYAN);
                else if (bag instanceof GunBag)
                    g.setColor(Color.DARK_GRAY);
                else if (bag instanceof MoneyBag)
                    g.setColor(Color.GREEN);
                g.fillRect(bag.getX(), bag.getY(), bag.getSize(), bag.getSize());
            }
        }
    }

    private void renderYogi(Graphics g, YogiBear yogi) {
        double scale;
        int scaledHeight;
        int yogiY;

        if (!yogi.isCrouching()) {
            scale = (double) yogi.getHeight() / YogiBear.SPRITE_HEIGHT;
            scaledHeight = yogi.getHeight();
            yogiY = yogi.getY();
        } else {
            scale = (double) yogi.getHeight() * 2 / YogiBear.SPRITE_HEIGHT;
            scaledHeight = yogi.getHeight() * 2;
            yogiY = yogi.getY() - GameConfig.TILE_SIZE;
        }

        int scaledWidth = (int) (YogiBear.SPRITE_WIDTH * scale);
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
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + gameModel.getScore(), 10, 25);
        g.drawString("Lives: " + gameModel.getLives(), 10, 50);
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
