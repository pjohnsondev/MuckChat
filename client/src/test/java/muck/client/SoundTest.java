package muck.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SoundTest {

    @Test
    void achievementSoundObjectExistsTest() {
        Sound achievementSound = new Sound("/sounds/tilegame.mp3");
        assertNotNull(achievementSound);
    }

    @Test
    void achievementMatchesTest() {
        Sound achievementSound = new Sound("/sounds/tilegame.mp3");
        assertEquals("/sounds/tilegame.mp3", achievementSound.soundPath);
    }

    @Test
    void dogbarkSoundObjectExistsTest() {
        Sound achievementSound = new Sound("/sounds/longbark2.mp3");
        assertNotNull(achievementSound);
    }

    @Test
    void dogbarkMatchesTest() {
        Sound dogbark = new Sound("/sounds/longbark2.mp3");
        assertEquals("/sounds/longbark2.mp3", dogbark.soundPath);
    }

    @Test
    void doorbellSoundObjectExistsTest() {
        Sound achievementSound = new Sound("/sounds/doorbell.mp3");
        assertNotNull(achievementSound);
    }

    @Test
    void doorbellMatchesTest() {
        Sound doorbell = new Sound("/sounds/doorbell.mp3");
        assertEquals("/sounds/doorbell.mp3", doorbell.soundPath);
    }

}
