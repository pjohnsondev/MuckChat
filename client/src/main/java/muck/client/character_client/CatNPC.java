package muck.client.character_client;

import javafx.scene.canvas.GraphicsContext;
import muck.client.TileMapReader;
import javafx.scene.image.Image;
import muck.core.character.NPC;
import muck.core.character.NPCRandomWalk;

public class CatNPC extends NPC {
    private final Image image;

    // Variables to select specific cat colour and direction
    private int base;
    private int sh;
    private int sx;

    /**
     * Constructor - Position and colour
     * @param identifier Unique identifier
     * @param xPos Starting x position
     * @param yPos Starting y position
     * @param colour Colour of cat
     */
    public CatNPC(String identifier, int xPos, int yPos, String colour) {
        image = new Image("/images/catSprite.png");
        setIdentifier(identifier);
        setXPos(xPos);
        setYPos(yPos);
        setNPCStats(10, 1, 1, 1);
        setColour(colour);

        NPCRandomWalk npcRandomWalk = new NPCRandomWalk(this);
        this.AIBehaviours.add(npcRandomWalk);
    }

    /**
     * Constructor - Position, colour and starting direction
     * @param identifier Unique identifier
     * @param xPos Starting x position
     * @param yPos Starting y position
     * @param colour Colour of cat
     * @param direction Starting facing direction
     */
    public CatNPC(String identifier, int xPos, int yPos, String colour, String direction) {
        image = new Image("/images/catSprite.png");
        setIdentifier(identifier);
        setXPos(xPos);
        setYPos(yPos);
        setNPCStats(10, 1, 1, 1);
        setColour(colour);
        setDirection(direction);

        NPCRandomWalk npcRandomWalk = new NPCRandomWalk(this);
        this.AIBehaviours.add(npcRandomWalk);
    }

    /**
     * Set the colour of the cat
     * @param colour Colour of cat (grey, brown, black, beige, tip, tiger, white)
     */
    public void setColour(String colour) {
        switch (colour) {
            case "grey":
                sx = 144;
                base = 0;
                break;
            case "brown":
                sx = 288;
                base = 0;
                break;
            case "black":
                sx = 432;
                base = 0;
                break;
            case "beige":
                sx = 0;
                base = 192;
                break;
            case "tip":
                sx = 144;
                base = 192;
                break;
            case "spot":
                sx = 288;
                base = 192;
                break;
            case "tiger":
                sx = 432;
                base = 192;
                break;
            default:
                sx = 0;
                base = 0;
                break;
        }
    }

    /**
     * TODO: Draw image so it remains still while player is moving
     * Draw cat
     * @param gc
     * @param tm
     */
    public void drawCatNPC(GraphicsContext gc, TileMapReader tm) {
        gc.drawImage(this.image, this.sx, this.sh, 48,48, getXPos(),getYPos(),26,30);
    }

    /**
     * Set the facing direction of cat
     * @param direction New direction
     */
    public void changeDirection(String direction) {
        switch (direction) {
            case "left" -> sh = base + 48;
            case "right" -> sh = base + 96;
            case "up" -> sh = base + 144;
            default -> sh = base;
        }
    }
}
