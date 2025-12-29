package model;

public class GameConfig {
    public static final int FPS = 60;
    public static final int ANIMATION_SPEED = 15;

    public static final int TILE_SIZE = 32;
    public static final int LEVEL_HEIGHT = 15 * TILE_SIZE;
    public static final int LEVEL_WIDTH = 30 * TILE_SIZE;

    public static final double WINDOW_SCALE = 1.5;

    public static final int MOVE_SPEED = 5;
    public static final int GRAVITY = 1;
    public static final int JUMP_STRENGTH = -15;

    public static final int AGENT_SPEED = 2;
    public static final int AGENT_PAUSE_FRAMES = 180;
    public static final int AGENT_VISION_RANGE = 1;

    public static final int MESSAGE_DURATION = 60;
    public static final int LAST_LEVEL_NUM = 7;

    public static final String BASE_SPRITE_PATH = "src/resources/sprites/";
    public static final String BASE_BACKGROUND_PATH = "src/resources/backgrounds/";
}
