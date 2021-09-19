package muck.client;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


// This class contains a mediaPlayer that allows sound to be played
public class Sound {

    String soundPath;
    MediaPlayer mediaPlayer;

    /**
     * Constructor to create a sound object
     * @param audio The sound path for the sound file to be played by the mediaPlayer
     */
    public Sound(String audio) {
        this.soundPath = audio;
    }

    public String getSoundPath(Sound audio) {
        return audio.soundPath;
    }

    /**
     * music Plays the sound file using mediaPlayer
     * @param soundFile The path to the sound file to be played
     */
    public void music(Sound soundFile) {
        try {
                Media h = new Media(getClass().getResource(soundFile.soundPath).toExternalForm());
                mediaPlayer = new MediaPlayer(h);
                mediaPlayer.play();
                System.out.println("Sound should be playing now");
            }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
