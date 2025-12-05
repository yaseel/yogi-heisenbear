package view;

import controller.InputHandler;
import model.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private YogiBear yogi;
    private Level level;
    private InputHandler inputHandler;
    private Timer gameLoop;

    public GamePanel() {
        level = LevelLoader.loadLevel("src/resources/levels/level1.txt");

        setPreferredSize(new Dimension(level.getWidth(), level.getHeight()));
        setBackground(new Color(135, 206, 235));
        setFocusable(true);

        yogi = new YogiBear(level.getYogiStartX(), level.getYogiStartY());

        inputHandler = new InputHandler(yogi);
        addKeyListener(inputHandler);

        startGameLoop();
    }

    private void startGameLoop() {
        int delay = 1000 / GameConfig.FPS;
        gameLoop = new Timer(delay, _ -> {
            inputHandler.update();
            yogi.update(level.getWidth());
            checkCollisions();
            repaint();
        });
        gameLoop.start();
    }

    private void checkCollisions() {
        Rectangle yogiBounds = yogi.getBounds();
        boolean onSolidGround = false;

        for (Tile tile : level.getTiles()) {
            if (!tile.isSolid()) continue;

            Rectangle tileBounds = new Rectangle(
                    tile.getX(),
                    tile.getY(),
                    tile.getSize(),
                    tile.getSize()
            );

            if (yogiBounds.intersects(tileBounds)) {
                if (yogi.getVelocityY() >= 0) {
                    int yogiBottom = yogi.getY() + yogi.getHeight();
                    int tileTop = tile.getY();
                    int previousYogiBottom = yogiBottom - yogi.getVelocityY();

                    if (previousYogiBottom <= tileTop + 5 && yogiBottom > tileTop) {
                        yogi.setY(tileTop - yogi.getHeight());
                        yogi.setVelocityY(0);
                        yogi.setOnGround(true);
                        onSolidGround = true;
                    }
                }

                if (tile.getType() != Tile.Type.PLATFORM) {
                    int yogiLeft = yogi.getX();
                    int yogiRight = yogi.getX() + yogi.getWidth();
                    int tileLeft = tile.getX();
                    int tileRight = tile.getX() + tile.getSize();

                    int yogiCenterY = yogi.getY() + yogi.getHeight() / 2;
                    int tileCenterY = tile.getY() + tile.getSize() / 2;

                    if (Math.abs(yogiCenterY - tileCenterY) < tile.getSize()) {
                        if (yogiRight > tileLeft && yogiRight < tileLeft + 10 && yogiLeft < tileLeft) {
                            yogi.setX(tileLeft - yogi.getWidth());
                        } else if (yogiLeft < tileRight && yogiLeft > tileRight - 10 && yogiRight > tileRight) {
                            yogi.setX(tileRight);
                        }
                    }
                }
            }
        }

        if (!onSolidGround && yogi.getY() < level.getHeight() - yogi.getHeight()) {
            yogi.setOnGround(false);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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

        g.setColor(new Color(139, 90, 43));
        g.fillRect(yogi.getX(), yogi.getY(), yogi.getWidth(), yogi.getHeight());
    }
}
