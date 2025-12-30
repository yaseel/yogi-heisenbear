package controller;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class MusicManager {
    private static MusicManager instance;

    private Clip currentClip;
    private MusicTrack currentTrack;

    private static final String MUSIC_PATH = "resources/music/";

    public enum MusicTrack {
        MENU("menu.wav"),
        GAME("game.wav"),
        END("end.wav");

        private final String fileName;

        MusicTrack(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    private MusicManager() {

    }

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    public void play(MusicTrack track, boolean loop) {
        if (currentTrack == track && currentClip != null && currentClip.isRunning()) {
            return;
        }

        stop();

        try {
            String path = MUSIC_PATH + track.getFileName();
            InputStream audioSrc = getClass().getClassLoader().getResourceAsStream(path);

            if (audioSrc == null) {
                System.err.println("Music file not found: " + path);
                return;
            }

            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            currentClip = AudioSystem.getClip();
            currentClip.open(audioInputStream);

            if (loop) {
                currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            currentClip.start();
            currentTrack = track;

        } catch (Exception e) {
            System.err.println("Error playing music: " + track.getFileName());
            e.printStackTrace();
        }
    }

    public void stop() {
        if (currentClip != null) {
            if (currentClip.isRunning()) {
                currentClip.stop();
            }
            currentClip.close();
            currentClip = null;
        }
        currentTrack = null;
    }

    public void playMenuMusic() {
        play(MusicTrack.MENU, true);
    }

    public void playGameMusic() {
        play(MusicTrack.GAME, true);
    }

    public void playEndMusic() {
        play(MusicTrack.END, false);
    }
}
