package muck.client.character_client;

import javafx.application.Platform;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import muck.client.TileMapReader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CatNPCTest {

    private static final Logger logger = LogManager.getLogger(CatNPCTest.class);
    private static CatNPC cat1;
    private static CatNPC cat2;
    private static TileMapReader tm;

    // Setup before all tests
    @BeforeAll
    public static void setup() throws InterruptedException {
        // setup JavaFX Runtime if not already running
        try {
            Platform.startup(() -> {
            });
        } catch (java.lang.IllegalStateException e) {
            logger.info("Platform has already been started");
        }

        // setup cat NPCs
        tm = new TileMapReader("/maps/homeTown.tmx");
        cat1 = new CatNPC("cat1", 0, 0, "white", tm);
        cat2 = new CatNPC("cat1", 0, 0, "white", tm,
                "right", 10, 0,5);
    }

    // Test catNPC can be any colour
    @Test
    public void testColour() {
        logger.info("Testing all colours for the cat npc");

        assertArrayEquals(
                (new int[]{0, 0}),
                cat1.getSourceRectangle(),
                "Colour should start as white");

        cat1.setColour(" ");
        assertArrayEquals(
                (new int[]{0, 0}),
                cat1.getSourceRectangle(),
                "Colour should be white");

        cat1.setColour("grey");
        assertArrayEquals(
                (new int[]{144, 0}),
                cat1.getSourceRectangle(),
                "Colour should be grey");

        cat1.setColour("brown");
        assertArrayEquals(
                (new int[]{288, 0}),
                cat1.getSourceRectangle(),
                "Colour should be brown");

        cat1.setColour("black");
        assertArrayEquals(
                (new int[]{432, 0}),
                cat1.getSourceRectangle(),
                "Colour should be black");

        cat1.setColour("beige");
        assertArrayEquals(
                (new int[]{0, 192}),
                cat1.getSourceRectangle(),
                "Colour should be beige");

        cat1.setColour("tip");
        assertArrayEquals(
                (new int[]{144, 192}),
                cat1.getSourceRectangle(),
                "Colour should be tip");

        cat1.setColour("spot");
        assertArrayEquals(
                (new int[]{288, 192}),
                cat1.getSourceRectangle(),
                "Colour should be spot");

        cat1.setColour("tiger");
        assertArrayEquals(
                (new int[]{432, 192}),
                cat1.getSourceRectangle(),
                "Colour should be tiger");
    }

    // Test catNPC can change directions
    @Test
    public void testChangeDirection() {
        logger.info("Testing directions for the cat npc");

        assertArrayEquals(
                (new int[]{0, 97}),
                cat2.getSourceRectangle(),
                "Direction should start facing right");

        cat2.changeDirection("down");
        assertArrayEquals(
                (new int[]{0, 0}),
                cat2.getSourceRectangle(),
                "Direction should be down");

        cat2.changeDirection("left");
        assertArrayEquals(
                (new int[]{0, 49}),
                cat2.getSourceRectangle(),
                "Direction should be left");

        cat2.changeDirection("right");
        assertArrayEquals(
                (new int[]{0, 97}),
                cat2.getSourceRectangle(),
                "Direction should be right");

        cat2.changeDirection("up");
        assertArrayEquals(
                (new int[]{0, 145}),
                cat2.getSourceRectangle(),
                "Direction should be up");
    }

    // Test cat NPC dialog
    @Test
    public void testDialog() {
        logger.info("Testing dialog options for the cat npc");
        assertAll(
                () -> assertEquals("Meow!", cat1.dialog(1)),
                () -> assertEquals("Hiss!!!", cat1.dialog(2)),
                () -> assertEquals("Purr", cat1.dialog(3)),
                () -> assertEquals("...", cat1.dialog(9))
        );
    }

    // Test cat NPC draw method is invoked
    @Test
    public void testDrawCatNPC() {
        final Canvas canvas = new Canvas(250,250);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        logger.info("Testing draw cat npc is called");
        cat1.drawCatNPC(gc, 0.0, 0.0, tm);
    }

    // Test cat NPC random walk
    @Test
    public void testHandle() {
        cat1.setNpcRandomWalk(1, 0, 2);
        boolean x = false;
        boolean y = false;

        logger.info("Testing handle of random walk");
        for(int i = 0; i < 50; i++) {
            cat1.handle();
            if (cat1.getXPos() != 0) {
                x = true;
            }
            if (cat1.getYPos() != 0) {
                y = true;
            }
        }
        assertTrue(x);
        assertTrue(y);
    }
}
