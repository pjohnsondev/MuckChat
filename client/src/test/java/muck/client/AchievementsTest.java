package muck.client;

import org.junit.jupiter.api.Test;
import static muck.client.Achievements.*;
import static org.junit.jupiter.api.Assertions.*;

public class AchievementsTest {

    @Test
    void duplicateAchievementTest() {
        if (achievement1_instance == null) {
            achievement1_instance = new Achievements(achievement1, ACHIEVEMENT1TITLE, ACHIEVEMENT1DESCRIPTION);
        }
        assertNotNull(achievement1_instance);
    }

    @Test
    void achievementLockedTest() {
        Achievements achievement1_instance = new Achievements(achievement1, ACHIEVEMENT1TITLE, ACHIEVEMENT1DESCRIPTION);
        assertFalse(achievement1_instance.achievementStatus);
    }

    @Test
    void achievementUnlockedTest() {
        Achievements achievement1_instance = new Achievements(achievement1, ACHIEVEMENT1TITLE, ACHIEVEMENT1DESCRIPTION);
        achievement1_instance.achievementUnlock();
        assertTrue(achievement1_instance.achievementStatus);
    }

    @Test
    void achievement1TitleTest() {
        Achievements achievement1_instance = new Achievements(achievement1, ACHIEVEMENT1TITLE, ACHIEVEMENT1DESCRIPTION);
        assertEquals("Hotel California", achievement1_instance.achievementTitle);
    }

    @Test
    void achievement1DescriptionTest() {
        Achievements achievement1_instance = new Achievements(achievement1, ACHIEVEMENT1TITLE, ACHIEVEMENT1DESCRIPTION);
        assertEquals("Player has visited the Inn", achievement1_instance.achievementDescription);
    }

    @Test
    void achievement2TitleTest() {
        Achievements achievement2_instance = new Achievements(achievement2, ACHIEVEMENT2TITLE, ACHIEVEMENT2DESCRIPTION);
        assertEquals("Retail Therapy", achievement2_instance.achievementTitle);
    }

    @Test
    void achievement2DescriptionTest() {
        Achievements achievement2_instance = new Achievements(achievement2, ACHIEVEMENT2TITLE, ACHIEVEMENT2DESCRIPTION);
        assertEquals("Player has visited the Shop", achievement2_instance.achievementDescription);
    }

    @Test
    void achievement3TitleTest() {
        Achievements achievement3_instance = new Achievements(achievement3, ACHIEVEMENT3TITLE, ACHIEVEMENT3DESCRIPTION);
        assertEquals("Hide your kids, Hide your wife", achievement3_instance.achievementTitle);
    }

    @Test
    void achievement3DescriptionTest() {
        Achievements achievement3_instance = new Achievements(achievement3, ACHIEVEMENT3TITLE, ACHIEVEMENT3DESCRIPTION);
        assertEquals("Player has invaded a home", achievement3_instance.achievementDescription);
    }

    @Test
    void achievement4TitleTest() {
        Achievements achievement4_instance = new Achievements(achievement4, ACHIEVEMENT4TITLE, ACHIEVEMENT4DESCRIPTION);
        assertEquals("Indiana Stones", achievement4_instance.achievementTitle);
    }

    @Test
    void achievement4DescriptionTest() {
        Achievements achievement4_instance = new Achievements(achievement4, ACHIEVEMENT4TITLE, ACHIEVEMENT4DESCRIPTION);
        assertEquals("Player has visited the cave", achievement4_instance.achievementDescription);
    }

    @Test
    void achievement5TitleTest() {
        Achievements achievement5_instance = new Achievements(achievement5, ACHIEVEMENT5TITLE, ACHIEVEMENT5DESCRIPTION);
        assertEquals("Would You Like Some Pie?", achievement5_instance.achievementTitle);
    }

    @Test
    void achievement5DescriptionTest() {
        Achievements achievement5_instance = new Achievements(achievement5, ACHIEVEMENT5TITLE, ACHIEVEMENT5DESCRIPTION);
        assertEquals("Player has entered the cottage", achievement5_instance.achievementDescription);
    }

    @Test
    void achievement6TitleTest() {
        Achievements achievement6_instance = new Achievements(achievement6, ACHIEVEMENT6TITLE, ACHIEVEMENT6DESCRIPTION);
        assertEquals("I'm A Real Boy", achievement6_instance.achievementTitle);
    }

    @Test
    void achievement6DescriptionTest() {
        Achievements achievement6_instance = new Achievements(achievement6, ACHIEVEMENT6TITLE, ACHIEVEMENT6DESCRIPTION);
        assertEquals("Player has selected an avatar", achievement6_instance.achievementDescription);
    }

    @Test
    void achievement7TitleTest() {
        Achievements achievement7_instance = new Achievements(achievement7, ACHIEVEMENT7TITLE, ACHIEVEMENT7DESCRIPTION);
        assertEquals("Indecisive", achievement7_instance.achievementTitle);
    }

    @Test
    void achievement7DescriptionTest() {
        Achievements achievement7_instance = new Achievements(achievement7, ACHIEVEMENT7TITLE, ACHIEVEMENT7DESCRIPTION);
        assertEquals("Player has selected another avatar", achievement7_instance.achievementDescription);
    }

    @Test
    void achievement8TitleTest() {
        Achievements achievement8_instance = new Achievements(achievement8, ACHIEVEMENT8TITLE, ACHIEVEMENT8DESCRIPTION);
        assertEquals("The Skeleton King", achievement8_instance.achievementTitle);
    }

    @Test
    void achievement8DescriptionTest() {
        Achievements achievement8_instance = new Achievements(achievement8, ACHIEVEMENT8TITLE, ACHIEVEMENT8DESCRIPTION);
        assertEquals("Player has unlocked Skeleton", achievement8_instance.achievementDescription);
    }

    @Test
    void achievement9TitleTest() {
        Achievements achievement9_instance = new Achievements(achievement9, ACHIEVEMENT9TITLE, ACHIEVEMENT9DESCRIPTION);
        assertEquals("Wonderful!", achievement9_instance.achievementTitle);
    }

    @Test
    void achievement9DescriptionTest() {
        Achievements achievement9_instance = new Achievements(achievement9, ACHIEVEMENT9TITLE, ACHIEVEMENT9DESCRIPTION);
        assertEquals("Player has unlocked Wonder Woman", achievement9_instance.achievementDescription);
    }

    @Test
    void achievement10TitleTest() {
        Achievements achievement10_instance = new Achievements(achievement10, ACHIEVEMENT10TITLE, ACHIEVEMENT10DESCRIPTION);
        assertEquals("Better Than Luigi", achievement10_instance.achievementTitle);
    }

    @Test
    void achievement10DescriptionTest() {
        Achievements achievement10_instance = new Achievements(achievement10, ACHIEVEMENT10TITLE, ACHIEVEMENT10DESCRIPTION);
        assertEquals("Player has unlocked Yoshi", achievement10_instance.achievementDescription);
    }

    @Test
    void achievement11TitleTest() {
        Achievements achievement11_instance = new Achievements(achievement11, ACHIEVEMENT11TITLE, ACHIEVEMENT11DESCRIPTION);
        assertEquals("Winner Winner Chicken Dinner", achievement11_instance.achievementTitle);
    }

    @Test
    void achievement11DescriptionTest() {
        Achievements achievement11_instance = new Achievements(achievement11, ACHIEVEMENT11TITLE, ACHIEVEMENT11DESCRIPTION);
        assertEquals("Player has won a game of Frogger", achievement11_instance.achievementDescription);
    }

    @Test
    void achievement12TitleTest() {
        Achievements achievement12_instance = new Achievements(achievement12, ACHIEVEMENT12TITLE, ACHIEVEMENT12DESCRIPTION);
        assertEquals("Alien Exterminator", achievement12_instance.achievementTitle);
    }

    @Test
    void achievement12DescriptionTest() {
        Achievements achievement12_instance = new Achievements(achievement12, ACHIEVEMENT12TITLE, ACHIEVEMENT12DESCRIPTION);
        assertEquals("Player has won a game of Space Invaders", achievement12_instance.achievementDescription);
    }

    @Test
    void achievement13TitleTest() {
        Achievements achievement13_instance = new Achievements(achievement13, ACHIEVEMENT13TITLE, ACHIEVEMENT13DESCRIPTION);
        assertEquals("All skill, No luck", achievement13_instance.achievementTitle);
    }

    @Test
    void achievement13DescriptionTest() {
        Achievements achievement13_instance = new Achievements(achievement13, ACHIEVEMENT13TITLE, ACHIEVEMENT13DESCRIPTION);
        assertEquals("Player has won a game of Tick-Tac-Toe", achievement13_instance.achievementDescription);
    }

    @Test
    void achievement14TitleTest() {
        Achievements achievement14_instance = new Achievements(achievement14, ACHIEVEMENT14TITLE, ACHIEVEMENT14DESCRIPTION);
        assertEquals("Here, Fishy Fishy", achievement14_instance.achievementTitle);
    }

    @Test
    void achievement14DescriptionTest() {
        Achievements achievement14_instance = new Achievements(achievement14, ACHIEVEMENT14TITLE, ACHIEVEMENT14DESCRIPTION);
        assertEquals("Player has won a game of Go-Fish", achievement14_instance.achievementDescription);
    }
}
