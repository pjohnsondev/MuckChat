package muck.client.character_client;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import muck.client.TileMapReader;
import muck.core.character.NPC;
import muck.core.character.NPCStateRandomWalk;

public class CatNPC extends NPC {
    private final Image image;
    private int base;
    private TileMapReader tm;

    /**
     * Constructor - Position and colour
     * @param identifier Unique identifier
     * @param xPos Starting x position
     * @param yPos Starting y position
     * @param colour Colour of cat
     * @param tm Tile Map NPC should be on
     */
    public CatNPC(String identifier, int xPos, int yPos, String colour, TileMapReader tm) {
        image = new Image("/images/NPC_Characters/catSprite.png");
        setIdentifier(identifier);
        setXPos(xPos);
        setYPos(yPos);
        setNPCStats(10, 1, 1, 1);
        setColour(colour);
        this.tm = tm;
        setNpcRandomWalk(0.3, 60, 30);
    }

    /**
     * Constructor - Position, colour and starting direction
     * @param identifier Unique identifier
     * @param xPos Starting x position
     * @param yPos Starting y position
     * @param colour Colour of cat
     * @param tm Tile Map NPC should be on
     * @param direction Starting facing direction
     * @param speed Speed of random walk
     * @param timeWait Time waiting in random walk
     * @param timeWalk Time walking in random walk
     */
    public CatNPC(String identifier, int xPos, int yPos, String colour, TileMapReader tm, String direction,
                  int speed, int timeWait, int timeWalk) {
        image = new Image("/images/NPC_Characters/catSprite.png");
        setIdentifier(identifier);
        setXPos(xPos);
        setYPos(yPos);
        setNPCStats(10, 1, 1, 1);
        setColour(colour);
        this.tm = tm;
        changeDirection(direction);
        setNpcRandomWalk(speed, timeWait, timeWalk);
    }

    /**
     * Set the colour of the cat. Default is white
     * @param colour Colour of cat (grey, brown, black, beige, tip, tiger, white)
     */
    public void setColour(String colour) {
        switch (colour) {
            case "grey":
                setSx(144);
                base = 0;
                break;
            case "brown":
                setSx(288);
                base = 0;
                break;
            case "black":
                setSx(432);
                base = 0;
                break;
            case "beige":
                setSx(0);
                base = 192;
                break;
            case "tip":
                setSx(144);
                base = 192;
                break;
            case "spot":
                setSx(288);
                base = 192;
                break;
            case "tiger":
                setSx(432);
                base = 192;
                break;
            default:
                setSx(0);
                base = 0;
                break;
        }
        setSh(base);
    }

    /**
     * Draw cat to the map
     * @param gc The graphics context to draw to map
     * @param cameraX,cameraY The top left corner co-ordinate of the viewport - Added by dandre20
     */
    public void drawCatNPC(GraphicsContext gc, Double cameraX, Double cameraY, TileMapReader tm) {
        if (this.tm.getHeight() == tm.getHeight() && this.tm.getWidth() == tm.getWidth()) {
            gc.drawImage(
                    this.image,
                    getSourceRectangle()[0],
                    getSourceRectangle()[1],
                    48,
                    48,
                    this.getXPos() - cameraX,
                    this.getYPos() - cameraY,
                    26,
                    30);
        }
    }

    /**
     * Change the facing direction of cat NPC
     * @param direction New direction
     */
    public void changeDirection(String direction) {
        this.setDirection(direction);
        switch (getDirection()) {
            case "left":
                setSh(base + 49);
                break;
            case "right":
                setSh(base + 97);
                break;
            case "up":
                setSh(base + 145);
                break;
            default:
                setSh(base);
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
     * Implements npcStateRandomWalk and allows cat to walk in random directions
     */
    @Override
    public void handle() {
        double previousX = this.getXPos();
        double previousY = this.getYPos();
        super.handle();
        changeDirection(this.getDirection());

        // get layer 1
        int GID = tm.getLayerId(
                2,
                (int)Math.floor((getXPos()+24)/tm.getTileWidth()),
                (int)Math.abs((getYPos()+24)/tm.getTileHeight()));

        // collision detection for layer 1 objects and boundaries
        if (GID != -1 || getXPos() == 0 || getYPos() + 10 == 0) {
            setXPos(previousX);
            setYPos(previousY);
        }
    }
}
