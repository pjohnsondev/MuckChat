package muck.client;

import org.junit.jupiter.api.Test;

import static muck.client.Achievements.*;
import static org.junit.jupiter.api.Assertions.*;



public class AchievementsTest {

//    @Test
//    void achievement1Test() {
//        Achievements achieve1 = new Achievements(achievement1, achievement1Title, achievement1Description);
//        achieve1.achievementUnlock(achieve1);
//        assertTrue(achieve1.achievementStatus);
//    }

    @Test
    void achievement1TitleTest() {
        Achievements achieve1 = new Achievements(achievement1, achievement1Title, achievement1Description);
        assertEquals("Hotel California", achieve1.achievementTitle);
    }

    @Test
    void achievement1DescriptionTest() {
        Achievements achieve1 = new Achievements(achievement1, achievement1Title, achievement1Description);
        assertEquals("Player has visited the Inn", achieve1.achievementDescription);
    }





}
