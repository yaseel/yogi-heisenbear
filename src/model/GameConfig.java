package model;

public class GameConfig {
    public static final int ENTITY_SCALE = 2;

    public static final int FPS = 60;
    public static final int ANIMATION_SPEED = 15;

    public static final int TILE_SIZE = 32;
    public static final int LEVEL_HEIGHT = 25 * TILE_SIZE;
    public static final int LEVEL_WIDTH = 50 * TILE_SIZE;

    public static final int MOVE_SPEED = 5 * ENTITY_SCALE;
    public static final int GRAVITY = 1 * ENTITY_SCALE;
    public static final int JUMP_STRENGTH = -15 * ENTITY_SCALE;

    public static final int AGENT_SPEED = 2 * ENTITY_SCALE;
    public static final int AGENT_PAUSE_FRAMES = 180;
    public static final int AGENT_VISION_RANGE = 1;

    public static final int MESSAGE_DURATION = 60;
    public static final int LAST_LEVEL_NUM = 10;

    public static final String BASE_SPRITE_PATH = "src/resources/sprites/";
}
