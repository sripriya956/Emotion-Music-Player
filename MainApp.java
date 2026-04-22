package com.musicplayer;


public class MainApp {

    public static void main(String[] args) {

        String lastEmotion = "";

        while (true) {
            String emotion = EmotionService.getEmotion();
            System.out.println("Detected Emotion: " + emotion);

            if (!emotion.equals(lastEmotion)) {

                switch (emotion) {
                    case "happy":
                        MusicPlayer.play("Music/happy.wav");
                        break;

                    case "sad":
                        MusicPlayer.play("Music/sad.wav");
                        break;

                    case "angry":
                        MusicPlayer.play("Music/angry.wav");
                        break;

                    default:
                        MusicPlayer.play("Music/neutral.wav");
                }

                lastEmotion = emotion;
            }

            try {
                Thread.sleep(4000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}