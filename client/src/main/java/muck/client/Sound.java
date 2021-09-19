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


    /**
     * music Plays the sound file using mediaPlayer
     * @param soundFile The path to the sound file to be played
     */
    public void music(Sound soundFile) {
        try {
                Media h = new Media(getClass().getResource(soundFile.soundPath).toExternalForm());
                mediaPlayer = new MediaPlayer(h);
                if(mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                    mediaPlayer.play();
                }
            }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * dogBark Plays the dog barking sound using the music method.
     */
    public static void dogBark() {
        Sound barkFile = new Sound("/sounds/longbark2.mp3");
        barkFile.music(barkFile);
        }

}
