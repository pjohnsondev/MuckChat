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
     */
    public void music() {
        try {
                Media h = new Media(getClass().getResource(this.soundPath).toExternalForm());
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
     * achievementSounds plays a ringing sound. Used in the achievementPopUp method
     * as an achievement notification appears
     */
    public static void achievementSound() {
        Sound achievementSound = new Sound("/sounds/tilegame.mp3");
        achievementSound.music();
    }


    /**
     * dogbarkSound Plays the dog barking sound using the music method.
     */
    public static void dogbarkSound() {
        Sound dogbark = new Sound("/sounds/longbark2.mp3");
        dogbark.music();
    }


    /**
     * doorbellSound Plays the doorbell sound using the music method.
     */
    public static void doorbellSound() {
        Sound doorbell = new Sound("/sounds/doorbell.mp3");
        doorbell.music();
    }

}
