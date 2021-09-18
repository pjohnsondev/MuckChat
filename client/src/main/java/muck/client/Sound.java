package muck.client;

import java.nio.file.Paths;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class Sound {

    String soundPath;
    MediaPlayer mediaPlayer;

    public Sound(String audio) {
        this.soundPath = audio;
    }

    public String getSoundPath(Sound audio) {
        return audio.soundPath;
    }

    public void music(Sound soundFile) {
        try {
//            String s = "src/main/resources/sounds/tilegame.mp3";

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
