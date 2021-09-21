package muck.client;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static muck.client.Sound.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class SoundTest {

    @Test
    void dogbarkMatches() {
        Sound dogbark = new Sound("/sounds/longbark2.mp3");
        assertEquals("/sounds/longbark2.mp3", dogbark.soundPath);
    }

    @Test
    void doorbellMatches() {
        Sound doorbell = new Sound("/sounds/doorbell.mp3");
        assertEquals("/sounds/doorbell.mp3", doorbell.soundPath);
    }

}
