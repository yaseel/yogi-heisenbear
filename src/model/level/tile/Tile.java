package model.level.tile;

import model.GameConfig;

public class Tile {
    public static final String SPRITE_PATH = GameConfig.BASE_SPRITE_PATH + "tiles.png";
    public static final int SPRITE_SIZE = 32;
    public static final int TILE_COUNT = 6;
    public static final int MAX_TILE_VARIANTS = 11;

    public static final int VARIANT_SINGLE = 0;
    public static final int VARIANT_HORIZONTAL_LEFT = 1;
    public static final int VARIANT_HORIZONTAL_CENTER = 2;
    public static final int VARIANT_HORIZONTAL_RIGHT = 3;
    public static final int VARIANT_VERTICAL_TOP = 4;
    public static final int VARIANT_VERTICAL_CENTER = 5;
    public static final int VARIANT_VERTICAL_BOTTOM = 6;
    public static final int VARIANT_TOP_LEFT = 7;
    public static final int VARIANT_TOP_RIGHT = 8;
    public static final int VARIANT_BOTTOM_LEFT = 9;
    public static final int VARIANT_BOTTOM_RIGHT = 10;

    public enum Type {
        AIR,
        PLATFORM,
        GROUND,
        SPAWN_POINT,
        COLLECTIBLE,
        AGENT_SPAWN
    }

    private final Type type;
    private final int x;
    private final int y;
    private final int size;
    private final int spriteIndex;
    private int variant;

    public Tile(Type type, int x, int y, int spriteIndex) {
        this(type, x, y, spriteIndex, VARIANT_SINGLE);
    }

    public Tile(Type type, int x, int y, int spriteIndex, int variant) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.size = GameConfig.TILE_SIZE;
        this.spriteIndex = spriteIndex;
        this.variant = variant;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }

    public int getVariant() {
        return variant;
    }

    public Type getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getSpriteIndex() {
        return spriteIndex;
    }
}
