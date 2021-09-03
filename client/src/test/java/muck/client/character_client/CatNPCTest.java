package muck.client.character_client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.mockito.Mockito.*;

public class CatNPCTest {

    private static final Logger logger = LogManager.getLogger(CatNPCTest.class);
    private CatNPC cat;

    @BeforeEach
    public void init() {
        cat = mock(CatNPC.class, CALLS_REAL_METHODS);
    }

    // Test catNPC can be any colour
    @Test
    public void testColour() {
        logger.info("Testing all colours for the cat npc");

        cat.setColour(" ");
        assertArrayEquals(
                (new int[]{0, 0}),
                cat.getSourceRectangle(),
                "Direction should be white");

        cat.setColour("grey");
        assertArrayEquals(
                (new int[]{144, 0}),
                cat.getSourceRectangle(),
                "Direction should be grey");

        cat.setColour("brown");
        assertArrayEquals(
                (new int[]{288, 0}),
                cat.getSourceRectangle(),
                "Direction should be white");

        cat.setColour("black");
        assertArrayEquals(
                (new int[]{432, 0}),
                cat.getSourceRectangle(),
                "Direction should be white");

        cat.setColour("beige");
        assertArrayEquals(
                (new int[]{0, 192}),
                cat.getSourceRectangle(),
                "Direction should be white");

        cat.setColour("tip");
        assertArrayEquals(
                (new int[]{144, 192}),
                cat.getSourceRectangle(),
                "Direction should be white");

        cat.setColour("spot");
        assertArrayEquals(
                (new int[]{288, 192}),
                cat.getSourceRectangle(),
                "Direction should be white");

        cat.setColour("tiger");
        assertArrayEquals(
                (new int[]{432, 192}),
                cat.getSourceRectangle(),
                "Direction should be tiger");
    }

    // Test catNPC can change directions
    @Test
    public void testDirection() {
        logger.info("Testing directions for the cat npc");

        cat.changeDirection("down");
        assertArrayEquals(
                (new int[]{0, 0}),
                cat.getSourceRectangle(),
                "Direction should be down");

        cat.changeDirection("left");
        assertArrayEquals(
                (new int[]{0, 48}),
                cat.getSourceRectangle(),
                "Direction should be left");

        cat.changeDirection("right");
        assertArrayEquals(
                (new int[]{0, 96}),
                cat.getSourceRectangle(),
                "Direction should be right");

        cat.changeDirection("up");
        assertArrayEquals(
                (new int[]{0, 144}),
                cat.getSourceRectangle(),
                "Direction should be up");
    }

    // Test cat NPC dialog
    @Test
    public void testDialog() {
        logger.info("Testing dialog options for the cat npc");
        assertAll(
                () -> assertEquals("Meow!", cat.dialog(1)),
                () -> assertEquals("Hiss!!!", cat.dialog(2)),
                () -> assertEquals("Purr", cat.dialog(3)),
                () -> assertEquals("...", cat.dialog(9))
        );
    }
}
