package muck.client.character_client;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import muck.client.TileMapReader;
import muck.core.character.NPC;

public class VillagerNPC extends NPC{
    private final Image image;
    private final TileMapReader tm; // map NPC should be on
    private int base; // top or bottom row villager type

    /**
     * Constructor - Position and colour
     * @param identifier Unique identifier
     * @param xPos Starting x position
     * @param yPos Starting y position
     * @param villager Type of villager
     * @param tm Tile Map NPC should be on
     */
    public VillagerNPC(String identifier, int xPos, int yPos, String villager, TileMapReader tm) {
        image = new Image("/images/NPC_Characters/villagerSprite.png");
        setIdentifier(identifier);
        setXPos(xPos);
        setYPos(yPos);
        setNPCStats(50, 10, 10, 5);
        setVillager(villager);
        this.tm = tm;
        setNpcRandomWalk(0.4, (float)Math.random() * (50) + 30, (float)Math.random() * (30) + 40);
    }

    /**
     * Constructor - Position, colour and starting direction
     * @param identifier Unique identifier
     * @param xPos Starting x position
     * @param yPos Starting y position
     * @param villager Type of villager
     * @param tm Tile Map NPC should be on
     * @param direction Starting facing direction
     * @param speed Speed of random walk
     * @param timeWait Time waiting in random walk
     * @param timeWalk Time walking in random walk
     */
    public VillagerNPC(String identifier, int xPos, int yPos, String villager, TileMapReader tm, String direction,
                       int speed, int timeWait, int timeWalk) {
        image = new Image("/images/NPC_Characters/villagerSprite.png");
        setIdentifier(identifier);
        setXPos(xPos);
        setYPos(yPos);
        setNPCStats(50, 10, 10, 5);
        setVillager(villager);
        this.tm = tm;
        changeDirection(direction);
        setNpcRandomWalk(speed, timeWait, timeWalk);
    }

    /**
     * Set the villager type
     * @param villager villager type
     */
    public void setVillager(String villager) {
        switch (villager) {
            case "female1":
                setSx(156);
                base = 0;
                break;
            case "male2":
                setSx(311);
                base = 0;
                break;
            case "female2":
                setSx(467);
                base = 0;
                break;
            case "female3":
                setSx(0);
                base = 221;
                break;
            case "male3":
                setSx(156);
                base = 221;
                break;
            case "female4":
                setSx(311);
                base = 221;
                break;
            case "male4":
                setSx(467);
                base = 221;
                break;
            default:
                setSx(0);
                base = 0;
                break;
        }
        setSh(base);
    }

    /**
     * Draw villager to the map
     * @param gc The graphics context to draw to map
     * @param cameraX,cameraY The top left corner co-ordinate of the viewport - Added by dandre20
     */
    public void drawVillagerNPC(GraphicsContext gc, Double cameraX, Double cameraY, TileMapReader tm) {
        if (this.tm.getHeight() == tm.getHeight() && this.tm.getWidth() == tm.getWidth()) {
            gc.drawImage(
                    this.image,
                    getSourceRectangle()[0],
                    getSourceRectangle()[1],
                    52,
                    55,
                    this.getXPos() - cameraX,
                    this.getYPos() - cameraY,
                    26,
                    30);
        }
    }

    /**
     * Change the facing direction
     * @param direction New direction
     */
    public void changeDirection(String direction) {
        this.setDirection(direction);
        switch (getDirection()) {
            case "left":
                setSh(base + 56);
                break;
            case "right":
                setSh(base + 111);
                break;
            case "up":
                setSh(base + 166);
                break;
            default:
                setSh(base);
                break;
        }
    }

    /**
     * Dialog option for villager NPC
     * @param option dialog option
     * @return String of chosen dialog option
     */
    public String dialog(int option) {
        String message;
        switch (option) {
            case 1:
                message = "Hello!";
                break;
            case 2:
                message = "How can I help you?";
                break;
            case 3:
                message = "There's a cave around here, somewhere.";
                break;
            default:
                message = "...";
                break;
        }
        return message;
    }

    /**
     * Implements npcStateRandomWalk and allows villager to walk in random directions
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
                (int)Math.floor((getXPos()+27)/tm.getTileWidth()),
                (int)Math.abs((getYPos()+27)/tm.getTileHeight()));

        // collision detection for layer 1 objects and boundaries
        if (GID != -1 || getXPos() == 0 || getYPos() + 10 == 0) {
            setXPos(previousX);
            setYPos(previousY);
        }
    }
}
