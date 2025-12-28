package model;

public class GameModel {
    private int score;
    private int lives;

    private long startTime;
    private long elapsedTime;
    private boolean timerRunning;

    public GameModel() {
        this.score = 0;
        this.lives = 3;
        this.startTime = 0;
        this.elapsedTime = 0;
        this.timerRunning = false;
    }

    public void addScore(int points) {
        score += points;
    }

    public void loseLife() {
        lives--;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
        timerRunning = true;
    }

    public void stopTimer() {
        if (timerRunning) {
            elapsedTime = System.currentTimeMillis() - startTime;
            timerRunning = false;
        }
    }

    public long getElapsedTime() {
        if (timerRunning) {
            return System.currentTimeMillis() - startTime;
        }
        return elapsedTime;
    }

    public String getFormattedTime() {
        long totalSeconds = getElapsedTime() / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void reset() {
        score = 0;
        lives = 3;
        startTime = 0;
        elapsedTime = 0;
        timerRunning = false;
    }
}
