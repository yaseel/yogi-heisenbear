package view.renderer;

import view.menu.MenuButton;
import view.GameFont;

import java.awt.*;
import java.util.List;

public class MenuRenderer {
    private static final Color NORMAL_COLOR = new Color(154, 205, 50);
    private static final Color ACTIVE_COLOR = new Color(255, 255, 100);
    private static final Color OUTLINE_COLOR = Color.BLACK;
    private static final int OUTLINE_THICKNESS = 2;

    public void render(Graphics2D g2d, List<MenuButton> buttons, int hoveredIndex, int selectedIndex, int panelHeight) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        float fontSize = panelHeight / 15f;
        Font font = GameFont.getFont(fontSize);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();

        for (MenuButton button : buttons) {
            boolean isActive = (hoveredIndex == button.getIndex() || selectedIndex == button.getIndex());
            Color textColor = isActive ? ACTIVE_COLOR : NORMAL_COLOR;

            int textX = button.getBounds().x;
            int textY = button.getBounds().y + (button.getBounds().height + fm.getAscent()) / 2 - fm.getDescent();

            drawTextWithOutline(g2d, button.getLabel(), textX, textY, textColor);
        }
    }

    private void drawTextWithOutline(Graphics2D g2d, String text, int x, int y, Color textColor) {
        g2d.setColor(OUTLINE_COLOR);
        for (int dx = -OUTLINE_THICKNESS; dx <= OUTLINE_THICKNESS; dx++) {
            for (int dy = -OUTLINE_THICKNESS; dy <= OUTLINE_THICKNESS; dy++) {
                if (dx != 0 || dy != 0) {
                    g2d.drawString(text, x + dx, y + dy);
                }
            }
        }

        g2d.setColor(textColor);
        g2d.drawString(text, x, y);
    }
}
