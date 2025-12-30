package view.menu;

import java.awt.*;

public class MenuButton {
    private String label;
    private int index;
    private Rectangle bounds;

    public MenuButton(String label, int index) {
        this.label = label;
        this.index = index;
        this.bounds = new Rectangle();
    }

    public String getLabel() {
        return label;
    }

    public int getIndex() {
        return index;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(int x, int y, int width, int height) {
        bounds.setBounds(x, y, width, height);
    }

    public boolean contains(Point point) {
        return bounds.contains(point);
    }
}
