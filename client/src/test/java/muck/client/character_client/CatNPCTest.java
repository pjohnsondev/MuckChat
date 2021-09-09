package muck.client.character_client;

import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import muck.client.TileMapReader;

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
                (new int[]{0, 49}),
                cat.getSourceRectangle(),
                "Direction should be left");

        cat.changeDirection("right");
        assertArrayEquals(
                (new int[]{0, 97}),
                cat.getSourceRectangle(),
                "Direction should be right");

        cat.changeDirection("up");
        assertArrayEquals(
                (new int[]{0, 145}),
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

    // Test cat NPC draw method is invoked
    @Test
    public void testDrawCatNPC() {
        final Canvas canvas = new Canvas(250,250);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        logger.info("Testing draw cat npc can invoke");
        doNothing().when(cat).drawCatNPC(gc, 0.0, 0.0);
        cat.drawCatNPC(gc, 0.0, 0.0);
        verify(cat, times(1)).drawCatNPC(gc, 0.0, 0.0);
    }

    // Test cat NPC walk is invoked
    @Test
    public void testHandle() {
        TileMapReader tm = new TileMapReader("/maps/homeTown.tmx");
        cat.setNpcRandomWalk(0.3, 60, 30);

        logger.info("Testing handle random walk can invoke");
        doNothing().when(cat).handle(tm);
        cat.handle(tm);
        verify(cat, times(1)).handle(tm);
    }
}
