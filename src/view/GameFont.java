package view;

import java.awt.Font;
import java.io.InputStream;

public class GameFont {
    private static Font customFont;
    private static final String FONT_PATH = "resources/BoldPixels.otf";

    static {
        try {
            InputStream is = GameFont.class.getClassLoader().getResourceAsStream(FONT_PATH);
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            System.err.println("Failed to load font: " + e);
        }
    }

    public static Font getFont(float size) {
        return customFont.deriveFont(size);
    }
}
