package aw.character;

//-------------------------------------------
// by team issue#20 on behalf of team issue#9
// bnolan9
//-------------------------------------------
import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
//-------------------------------------------

//TODO **IMPORTANT**
//Unknown yet how this will interact with JavaFX and backend storage. This is a mere prototype, subject to
//rapid change until the specifics of the project are worked out. Don't rely on any of these methods yet

public class Player extends Character implements ActionListener {
    /**
     * Player constructor. This class is an extension of the Character class for human players.
     * This should instantiate a player with a username that exists in the backend persistent storage.
     * (Will Issue #11 handle player username & password creation in the backend?)
     * If the player username doesn't exist, a CharacterDoesNotExist exception will be thrown.
     * Example usage: Player player1 = new Player("my_username");
     */

    //-------------------------------------------
    // by team issue#20 on behalf of team issue#9
    // bnolan9
    //-------------------------------------------
    private int x; // x pos of character
    private int y; // y pos of character
    private int w; // width of character (image)
    private int h; // height of character (image)
    private int dx; // movement direction x
    private int dy; // movement direction y
    private Image image;
    private RectangleImage character = null;

    //-------------------------------------------

    // commented out temporarily in order to bypass the throw - bnolan9
    // character load and drop into world is still a WIP (01-08-2021)
    //public Player(String username) throws CharacterDoesNotExistException {
        //TODO - Retrieve the username identifier from the backend database, then populate all fields with 
        // player values from the database

        // uncommented by bnolan9 to bypass this step to test load of image->character
        //boolean databaseRetrievalSuccessful = false;
        //boolean databaseRetrievalSuccessful = true;
        //if (!databaseRetrievalSuccessful) {
        //    throw new CharacterDoesNotExistException(username);
       // }
        
    //    setIdentifier(username);
    //}
    public Player(String username){
        setIdentifier(username);
    }

    //TODO How will the player move? A player controller will need to be created
//    public playerController() {
//    }

    //-------------------------------------------
    // by team issue#20 on behalf of team issue#9
    // bnolan9
    //-------------------------------------------

    public void loadImageDims(){
        setWidth(100);
        setHeight(100);
        setXpos(100);
        setYpos(200);
    }

    // set player character image width
    public void setWidth(int i){
        w = i;
    }
    // set player character image height
    public void setHeight(int i){
        h = i;
    }
    // get player width
    public int getWidth(){
        return w;
    }
    // get player height
    public int getHeight(){
        return h;
    }

    // set player x pos
    public void setXpos(int i){
        x = i;
    }
    // set player y pos
    public void setYpos(int i){
        y = i;
    }
    // get player x pos
    public int getXpos() {
        return x;
    }
    // get player y pos
    public int getYpos(){
        return y;
    }

    // capture keyboard key press for LEFT,RIGHT,UP,DOWN arrow keys
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT){
            dx = -2;
        }
        if (key == KeyEvent.VK_RIGHT){
            dx = 2;
        }
        if (key == KeyEvent.VK_UP){
            dy = -2;
        }
        if (key == KeyEvent.VK_DOWN){
            dy = 2;
        }
    }
    // capture keyboard key release for LEFT,RIGHT,UP,DOWN arrow keys
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT){
            dx = 0;
        }
        if(key == KeyEvent.VK_RIGHT){
            dx = 0;
        }
        if(key == KeyEvent.VK_UP){
            dy = 0;
        }
        if(key == KeyEvent.VK_DOWN){
            dy = 0;
        }
    }

    public void movePlayer(){
        x += dx;
        y += dy;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
    }

    private void step() {
        movePlayer();
    }

    //-------------------------------------------

    //TODO: How will a player be able to trade collectables with other players? (Issue 10)
    // Can you hold more than one item?
    /**
     * @param collectable Collectable item to trade
     * @param otherPlayer Other player to trade with
     * @return If player to player collectable transaction is successful return true. Otherwise, return false
     */
    public boolean tradeCollectable(String collectable, Player otherPlayer) {
        //TODO: Does the player currently hold the collectable?

        //TODO: Does the other player currently exist in the database?

        return false;
    }

    //TODO: Does a player have an inventory?
    // Should the item be stored within the database?
    // Can be moved to the Character class
    /**
    * @param item item to be added to inventory
    */
    public void addItemToInventory(String item) {
        //TODO
    }

    // TODO: How are items inside your inventory consumed?
    /**
     * @param item item to be removed/consumed
     */
    public void removeItemFromInventory(String item) {
        //TODO
    }

}


class RectangleImage
{
    private Image img;
    private Rectangle rect;

    public RectangleImage(Image img,int x, int y, ImageObserver o){
        this.img = img;
        this.rect = new Rectangle(x, y, 100,100);
    }

    public Rectangle getRect(){
        return this.rect;
    }

    public Image getImg(){
        return this.img;
    }

    public void move(int x, int y){
        this.rect.setBounds(x, y, rect.width,rect.height);
    }

    public void draw(Graphics2D g2, ImageObserver o){
        g2.drawImage(this.img,this.rect.x,this.rect.y,this.rect.width,this.rect.height,o);
    }

}