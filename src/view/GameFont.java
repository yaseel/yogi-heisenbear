package view;

import java.awt.Font;
import java.io.File;

public class GameFont {
    private static Font customFont;
    private static final String FONT_PATH = "src/resources/BoldPixels.otf";

    static {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH));
        } catch (Exception e) {
            System.err.println("Failed to load font: " + e);
        }
    }

    public static Font getFont(float size) {
        return customFont.deriveFont(size);
    }
}
