package muck.client.character_client;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import muck.client.TileMapReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VillagerNPCTest {

    private static final Logger logger = LogManager.getLogger(CatNPCTest.class);
    private static VillagerNPC villager1;
    private static VillagerNPC villager2;
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

        // setup NPCs
        tm = new TileMapReader("/maps/homeTown.tmx");
        villager1 = new VillagerNPC("v1", 0, 0, "female1", tm);
        villager2 = new VillagerNPC("v2", 0, 0, "female1", tm,
                "right", 10, 0,5);
    }

    // Test VillagerNPC can be any type
    @Test
    void setVillager() {
        logger.info("Testing all types of villagers npc");

        assertArrayEquals(
                (new int[]{156, 0}),
                villager1.getSourceRectangle(),
                "Should start as female1");

        villager1.setVillager(" ");
        assertArrayEquals(
                (new int[]{0, 0}),
                villager1.getSourceRectangle(),
                "Should be male1");

        villager1.setVillager("female1");
        assertArrayEquals(
                (new int[]{156, 0}),
                villager1.getSourceRectangle(),
                "Should be female1");

        villager1.setVillager("male2");
        assertArrayEquals(
                (new int[]{311, 0}),
                villager1.getSourceRectangle(),
                "Should be male2");

        villager1.setVillager("female2");
        assertArrayEquals(
                (new int[]{467, 0}),
                villager1.getSourceRectangle(),
                "Should be female2");

        villager1.setVillager("male3");
        assertArrayEquals(
                (new int[]{156, 221}),
                villager1.getSourceRectangle(),
                "Should be male3");

        villager1.setVillager("female3");
        assertArrayEquals(
                (new int[]{0, 221}),
                villager1.getSourceRectangle(),
                "Should be female3");

        villager1.setVillager("male4");
        assertArrayEquals(
                (new int[]{467, 221}),
                villager1.getSourceRectangle(),
                "Should be male4");

        villager1.setVillager("female4");
        assertArrayEquals(
                (new int[]{311, 221}),
                villager1.getSourceRectangle(),
                "Should be female4");
    }

    // Test VillagerNPC draw method is invoked
    @Test
    void testDrawVillagerNPC() {
        final Canvas canvas = new Canvas(250,250);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        logger.info("Testing draw villager npc is called");
        villager1.drawVillagerNPC(gc, 0.0, 0.0, tm);

        TileMapReader tm2 = new TileMapReader("/maps/cave.tmx");

        logger.info("Testing villager is not drawn to other maps");
        villager1.drawVillagerNPC(gc, 0.0, 0.0, tm2);
    }

    // Test VillagerNPC can change directions
    @Test
    void testChangeDirection() {
        logger.info("Testing directions for the villager npc");

        assertEquals(
                111,
                villager2.getSourceRectangle()[1],
                "Direction should start facing right");

        villager2.changeDirection("down");
        assertEquals(
                0,
                villager2.getSourceRectangle()[1],
                "Direction should be down");

        villager2.changeDirection("left");
        assertEquals(
                56,
                villager2.getSourceRectangle()[1],
                "Direction should be left");

        villager2.changeDirection("right");
        assertEquals(
                111,
                villager2.getSourceRectangle()[1],
                "Direction should be right");

        villager2.changeDirection("up");
        assertEquals(
                166,
                villager2.getSourceRectangle()[1],
                "Direction should be up");
    }

    // Test VillagerNPC dialog
    @Test
    void dialog() {
        logger.info("Testing dialog options for the villager npc");
        assertAll(
                () -> assertEquals("Hello!", villager1.dialog(1)),
                () -> assertEquals("How can I help you?", villager1.dialog(2)),
                () -> assertEquals("There's a cave around here, somewhere.", villager1.dialog(3)),
                () -> assertEquals("...", villager1.dialog(9))
        );
    }

    // Test VillagerNPC random walk
    @Test
    void handle() {
        villager2.setNpcRandomWalk(1, 0, 2);
        boolean x = false;
        boolean y = false;

        logger.info("Testing handle of random walk");
        for(int i = 0; i < 50; i++) {
            villager2.handle();
            if (villager2.getXPos() != 0) {
                x = true;
            }
            if (villager2.getYPos() != 0) {
                y = true;
            }
        }
        assertTrue(x);
        assertTrue(y);
    }
}