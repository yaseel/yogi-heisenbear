package view.panel;

import controller.MenuController;
import model.GameConfig;
import view.menu.MenuButton;
import view.menu.MenuLayoutManager;
import view.renderer.MenuRenderer;
import controller.MenuInputHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuPanel extends JPanel {
    private BufferedImage backgroundImage;
    private List<MenuButton> buttons;

    private MenuRenderer renderer;
    private MenuInputHandler inputHandler;
    private MenuLayoutManager layoutManager;

    private MenuController menuController;

    public MenuPanel() {
        backgroundImage = loadBackground(GameConfig.BASE_BACKGROUND_PATH + "main_menu.png");
        setLayout(null);
        setFocusable(true);

        buttons = new ArrayList<>();
        buttons.add(new MenuButton("START GAME", 0));
        buttons.add(new MenuButton("LEADERBOARD", 1));
        buttons.add(new MenuButton("EXIT", 2));

        renderer = new MenuRenderer();
        layoutManager = new MenuLayoutManager();
        inputHandler = new MenuInputHandler(buttons, _ -> repaint(), this::executeAction);

        addMouseMotionListener(inputHandler.createMouseMotionListener());
        addMouseListener(inputHandler.createMouseListener());
        addKeyListener(inputHandler.createKeyListener());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                layoutManager.updateLayout(buttons, getWidth(), getHeight());
                repaint();
            }
        });
    }

    public void setMenuController(MenuController controller) {
        this.menuController = controller;
    }

    private void executeAction(int index) {
        if (menuController == null)
            return;

        switch (index) {
            case 0:
                menuController.onStartGame();
                break;
            case 1:
                menuController.onShowLeaderboard();
                break;
            case 2:
                menuController.onExit();
                break;
        }
    }

    private BufferedImage loadBackground(String path) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(path);
            if (is != null) {
                return ImageIO.read(is);
            }
        } catch (IOException e) {
            System.err.println("Failed to load image: " + path);
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }

        layoutManager.updateLayout(buttons, getWidth(), getHeight());
        renderer.render(g2d, buttons, inputHandler.getHoveredIndex(), inputHandler.getSelectedIndex(), getHeight());
    }
}
