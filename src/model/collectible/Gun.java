package model.collectible;

public class Gun extends Collectible {
    public static final int NO_GUN_STATE = 1;

    public Gun(int x, int y) {
        super(x, y);
        this.value = 200;
    }
}
