package controller;

import model.level.Level;
import model.yogi.YogiBear;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class InputHandler extends KeyAdapter {
    private YogiBear yogi;
    private Level level;
    private Set<Integer> pressedKeys;

    public InputHandler(YogiBear yogi, Level level) {
        this.yogi = yogi;
        this.level = level;
        this.pressedKeys = new HashSet<>();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    public void update() {
        boolean moving = false;

        if (pressedKeys.contains(KeyEvent.VK_A)) {
            yogi.moveLeft();
            moving = true;
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            yogi.moveRight();
            moving = true;
        }

        if (pressedKeys.contains(KeyEvent.VK_W)) {
            yogi.jump();
        }

        if (pressedKeys.contains(KeyEvent.VK_S)) {
            yogi.crouch();
            yogi.requestDropThrough();
        } else {
            yogi.standUp(level);
        }

        if (!moving) {
            yogi.stopMoving();
        }
    }
}
