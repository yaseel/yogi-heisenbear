package model.level.tile;

import java.awt.Color;

public class TileType {
    public static final int GRASS = 0;
    public static final int WOOD = 1;
    public static final int SANDSTONE = 2;
    public static final int PIPE = 3;

    public static class Colors {
        public static final Color PLATFORM_COLOR = new Color(0x808080);
        public static final Color GROUND_COLOR = new Color(0x5A3C1E);

        public static final Color YOGI_SPAWN = new Color(0x00FF00);
        public static final Color MONEY = new Color(0xFFFF00);
        public static final Color METH_BASKET = new Color(0xFF00FF);
        public static final Color GUN = new Color(0x00FFFF);
        public static final Color AGENT_PATROL = new Color(0xFF0000);
    }
}
