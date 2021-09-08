package muck.client.character_client;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import muck.core.character.NPC;

public class CatNPC extends NPC {
    private final Image image;
    private int base; // Top and Bottom Colours (0/192)
    private int sx; // Colour (0/144/288/432)
    private int sh; // Direction (48/96/144)

    /**
     * Constructor - Position and colour
     * @param identifier Unique identifier
     * @param xPos Starting x position
     * @param yPos Starting y position
     * @param colour Colour of cat
     */
    public CatNPC(String identifier, int xPos, int yPos, String colour) {
        image = new Image("/images/NPC_Characters/catSprite.png");
        setIdentifier(identifier);
        setXPos(xPos);
        setYPos(yPos);
        setNPCStats(10, 1, 1, 1);
        setColour(colour);
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
        image = new Image("/images/NPC_Characters/catSprite.png");
        setIdentifier(identifier);
        setXPos(xPos);
        setYPos(yPos);
        setNPCStats(10, 1, 1, 1);
        setColour(colour);
        changeDirection(direction);
    }

    /**
     * Set the colour of the cat. Default is white
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
        sh = base;
    }

    /**
     * Draw cat to the map
     * @param gc The graphics context to draw to map
     * @param cameraX,cameraY The top left corner co-ordinate of the viewport - Added by dandre20
     */
    public void drawCatNPC(GraphicsContext gc, Double cameraX, Double cameraY) {
        gc.drawImage(
                this.image,
                this.sx,
                this.sh,
                48,
                48,
                this.getXPos() - cameraX,
                this.getYPos() - cameraY,
                26,
                30);
    }

    /**
     * Change the facing direction of cat NPC
     * @param direction New direction
     */
    public void changeDirection(String direction) {
        this.setDirection(direction);
        System.out.println(this.getDirection());
        switch (getDirection()) {
            case "left":
                sh = base + 48;
                break;
            case "right":
                sh = base + 96;
                break;
            case "up":
                sh = base + 144;
                break;
            default:
                sh = base;
                break;
        }
    }

    /**
     * Dialog option for cat NPC
     * @param option dialog option
     * @return String of chosen dialog option
     */
    public String dialog(int option) {
        String message;
        switch (option) {
            case 1:
                message = "Meow!";
                break;
            case 2:
                message = "Hiss!!!";
                break;
            case 3:
                message = "Purr";
                break;
            default:
                message = "...";
                break;
        }
        return message;
    }

    /**
     * Return source rectangle X and Y coordinates
     * @return Array of source rectangle X and source rectangle Y
     */
    public int[] getSourceRectangle() {
        return new int[]{this.sx, this.sh};
    }
}
