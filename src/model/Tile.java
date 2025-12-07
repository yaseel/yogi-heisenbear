package model;

public class Tile {
    public enum Type {
        AIR, // .
        WALL, // #
        PLATFORM, // =
        GROUND, // _
        SPAWN_POINT, // Y
        BAG, // *
        AGENT_SPAWN // A
    }

    private Type type;
    private int x, y;
    private int size;

    public Tile(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.size = GameConfig.TILE_SIZE;
    }

    public boolean isSolid() {
        return type == Type.WALL || type == Type.PLATFORM || type == Type.GROUND;
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
}
