package muck.client.enduring_fantasy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import muck.client.GameMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;


public class EFGameController implements Initializable {

    //Buttons, TextField etc. from fxml file.

    @FXML // fx:id="atk_button" HUD attack button
    private Button atk_button;

    @FXML // fx:id="mag_button" HUD magic button
    private Button mag_button;

    @FXML // fx:id="itm_button" HUD item button
    private Button itm_button;

    @FXML // fx:id="rest_button" HUD rest button
    private Button rest_button;

    @FXML //fx:id="HUD_Black_Mage" Black Mage HUD Text
    private Text HUD_Black_Mage;

    @FXML //fx:id="HUD_Paladin" Paladin HUD Text
    private Text HUD_Paladin;

    @FXML //fx:id="HUD_Warrior" Warrior HUD Text
    private Text HUD_Warrior;

    @FXML //fx:id="HUD_White_Mage" White Mage HUD Text
    private Text HUD_White_Mage;

    @FXML //fx:id="Black_Mage_HP" Black Mage HP Text
    private Text Black_Mage_HP;

    @FXML //fx:id="Paladin_HP" Paladin HP Text
    private Text Paladin_HP;

    @FXML //fx:id="Warrior_HP" Warrior HP Text
    private Text Warrior_HP;

    @FXML //fx:id="White_Mage_HP" White Mage HP Text
    private Text White_Mage_HP;

    @FXML //fx:id="Black_Mage_MP" Black Mage MP Text
    private Text Black_Mage_MP;

    @FXML //fx:id="Paladin_MP" Paladin MP Text
    private Text Paladin_MP;

    @FXML //fx:id="Warrior_MP" Warrior MP Text
    private Text Warrior_MP;

    @FXML //fx:id="White_Mage_MP" White Mage MP Text
    private Text White_Mage_MP;

    private int pcHp;
    private int pcMp;
    private int pcMag;
    private int pcStr;
    private int nextLvl;
    private int pcLvl;

    private Player player;
    private Magic magic;

    /** Player Variables **/
    public int getPcHp(){ return this.pcHp; }
    public void setPcHp(int newHp){ this.pcHp = newHp;}
    public int getPcMp(){ return this.pcMp;}
    public void setPcMp(int newMp){ this.pcMp = newMp; }
    public int getPcStr(){ return this.pcStr; }
    public int getPcMag(){ return this.pcMp; }


    /** Load the monster stats **/
    private int mobHp;

    private Monster monster = new Monster();

    /** Load the monster variables**/
    public int getMobHp(){ return this.mobHp; }
    public void decMobHp(int dmg){ this.mobHp -= dmg;}
    public String getMobName(){ return this.monster.getName(); }
    public int getMobDmg(){ return this.monster.getDamage(); }

    public void mobGen() {
        this.monster.genMonster(this.pcLvl);
        this.mobHp = this.monster.getHealth();
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }

    @FXML
    public void pcAtk(){
        Player testPlayer = new Player("testname");
        //Battle testBattle = new Battle(testPlayer);
        Monster testMonster = new Monster();
        testMonster.genMonster(1);


//        this.decMobHp(this.pcStr);
        System.out.println("*Attack*");
        System.out.println(testPlayer.getName() + " lands a blow for " + 7 + " on the " + testMonster.getName());
    }

    @FXML
    public void Battle(Player newPc){
        this.player = newPc;
        this.pcHp = this.player.getHealth();
        this.pcMp = this.player.getMP();
        this.pcMag = this.player.getMagicStr();
        this.pcStr = this.player.getDamage();
        this.pcLvl = this.player.getPlayerLvl();
        this.nextLvl = this.player.getNextLvl();
        this.magic = new Magic(this.player, this.pcMp);

    }

}


