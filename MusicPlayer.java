package com.musicplayer;

import javax.sound.sampled.*;
import java.io.File;

public class MusicPlayer {

    private static Clip clip;

    public static void play(String path) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}