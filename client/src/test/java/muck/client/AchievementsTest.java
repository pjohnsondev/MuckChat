package muck.client;

import org.junit.jupiter.api.Test;
import static muck.client.Achievements.*;
import static org.junit.jupiter.api.Assertions.*;

public class AchievementsTest {

    @Test
    void duplicateAchievement() {
        if (achievement1_instance == null) {
            achievement1_instance = new Achievements(achievement1, achievement1Title, achievement1Description);
        }
        assertNotNull(achievement1_instance);
    }

    @Test
    void achievement1TitleTest() {
        Achievements achievement1_instance = new Achievements(achievement1, achievement1Title, achievement1Description);
        assertEquals("Hotel California", achievement1_instance.achievementTitle);
    }

    @Test
    void achievement1DescriptionTest() {
        Achievements achievement1_instance = new Achievements(achievement1, achievement1Title, achievement1Description);
        assertEquals("Player has visited the Inn", achievement1_instance.achievementDescription);
    }

    @Test
    void achievementLocked() {
        Achievements achievement1_instance = new Achievements(achievement1, achievement1Title, achievement1Description);
        assertFalse(achievement1_instance.achievementStatus);
    }

    @Test
    void achievementUnlocked() {
        Achievements achievement1_instance = new Achievements(achievement1, achievement1Title, achievement1Description);
        achievement1_instance.achievementUnlock();
        assertTrue(achievement1_instance.achievementStatus);
    }

}
