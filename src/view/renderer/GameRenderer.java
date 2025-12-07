package view.renderer;

import model.*;
import model.agent.Agent;

import java.awt.*;

public class GameRenderer {

    public void render(Graphics g, Level level, YogiBear yogi, GameModel gameModel) {
        renderTiles(g, level);
        renderBags(g, level);
        renderYogi(g, yogi);
        renderAgents(g, level);
        renderUI(g, gameModel);
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
        g.setColor(new Color(139, 69, 19));
        for (BrownBag bag : level.getBags()) {
            if (!bag.isCollected()) {
                g.fillRect(bag.getX(), bag.getY(), bag.getSize(), bag.getSize());
            }
        }
    }

    private void renderYogi(Graphics g, YogiBear yogi) {
        g.setColor(new Color(139, 90, 43));
        g.fillRect(yogi.getX(), yogi.getY(), yogi.getWidth(), yogi.getHeight());
    }

    private void renderAgents(Graphics g, Level level) {
        g.setColor(new Color(139, 0, 0));
        for (Agent agent : level.getAgents()) {
            g.fillRect(agent.getX(), agent.getY(), agent.getWidth(), agent.getHeight());
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
