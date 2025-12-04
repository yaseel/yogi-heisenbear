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
            yogi.update(level.getWidth(), level.getHeight());
            repaint();
        });
        gameLoop.start();
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
            }
        }

        g.setColor(new Color(139, 90, 43));
        g.fillRect(yogi.getX(), yogi.getY(), yogi.getWidth(), yogi.getHeight());
    }
}
