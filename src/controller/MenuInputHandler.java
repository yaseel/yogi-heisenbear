package controller;

import view.menu.MenuButton;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.function.Consumer;

public class MenuInputHandler {
    private List<MenuButton> buttons;
    private int selectedIndex = -1;
    private int hoveredIndex = -1;
    private Consumer<Void> repaintCallback;
    private Consumer<Integer> actionCallback;

    public MenuInputHandler(List<MenuButton> buttons, Consumer<Void> repaintCallback,
            Consumer<Integer> actionCallback) {
        this.buttons = buttons;
        this.repaintCallback = repaintCallback;
        this.actionCallback = actionCallback;
    }

    public MouseMotionListener createMouseMotionListener() {
        return new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int oldHovered = hoveredIndex;
                hoveredIndex = -1;
                for (MenuButton button : buttons) {
                    if (button.contains(e.getPoint())) {
                        hoveredIndex = button.getIndex();
                        break;
                    }
                }
                if (oldHovered != hoveredIndex) {
                    repaintCallback.accept(null);
                }
            }
        };
    }

    public MouseListener createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (hoveredIndex != -1) {
                    actionCallback.accept(hoveredIndex);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((Component) e.getSource()).requestFocusInWindow();
            }
        };
    }

    public KeyListener createKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        };
    }

    private void handleKeyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_UP) {
            if (selectedIndex == -1) {
                selectedIndex = 0;
            } else {
                selectedIndex = (selectedIndex - 1 + buttons.size()) % buttons.size();
            }
            repaintCallback.accept(null);
        } else if (keyCode == KeyEvent.VK_DOWN) {
            if (selectedIndex == -1) {
                selectedIndex = 0;
            } else {
                selectedIndex = (selectedIndex + 1) % buttons.size();
            }
            repaintCallback.accept(null);
        } else if (keyCode == KeyEvent.VK_ENTER && selectedIndex != -1) {
            actionCallback.accept(selectedIndex);
        }
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public int getHoveredIndex() {
        return hoveredIndex;
    }
}
