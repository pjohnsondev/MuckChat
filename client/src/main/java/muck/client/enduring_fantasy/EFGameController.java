package muck.client.enduring_fantasy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ComboBox;


public class EFGameController implements Initializable {

    //Buttons, TextField etc. from fxml file.
    @FXML
    private StackPane battle_pane;

    @FXML
    private ImageView background_bg;

    @FXML
    private StackPane char_pane_1;

    @FXML
    private ImageView player_char_1;

    @FXML
    private StackPane char_pane_2;

    @FXML
    private ImageView player_char_2;

    @FXML
    private StackPane char_pane_3;

    @FXML
    private ImageView player_char_3;

    @FXML
    private StackPane char_pane_4;

    @FXML
    private ImageView player_char_4;

    @FXML
    private StackPane enemy_pane;

    @FXML
    private ImageView enemy_mob_1;

    @FXML
    private AnchorPane HUD_pane;

    @FXML
    private Button atk_button;

    @FXML
    private Button rest_button;

    @FXML
    private Text HUD_char_1;

    @FXML
    private Text HUD_char_2;

    @FXML
    private Text HUD_char_3;

    @FXML
    private Text HUD_char_4;

    @FXML
    private Text HUD_char_1_HP;

    @FXML
    private Text HUD_char_2_HP;

    @FXML
    private Text HUD_char_3_HP;

    @FXML
    private Text HUD_char_4_HP;

    @FXML
    private Text HUD_char_1_MP;

    @FXML
    private Text HUD_char_2_MP;

    @FXML
    private Text HUD_char_3_MP;

    @FXML
    private Text HUD_char_4_MP;


    @FXML
    private DialogPane game_window;

    @FXML
    private DialogPane battle_log;


    @FXML
    private ChoiceBox<String> magicList;
    private String[] spells = {"Magic","Fire", "Blizzard", "Thunder", "Cure"};

    @FXML
    private ChoiceBox<String> itemList;
    private String[] items = {"Items","Phoenix Down","Remedy","Potion", "Ether"};

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


        //atk_button.setOnAction(pcAtk());
       // rest_button.setOnAction(this.pcRest());
        magicList.getItems().addAll(spells);
        magicList.getSelectionModel().selectFirst();
        itemList.getItems().addAll(items);
        itemList.getSelectionModel().selectFirst();

    }


    @FXML
    public EventHandler<ActionEvent> pcAtk(){
        Player testPlayer = new Player("testname");
        //Battle testBattle = new Battle(testPlayer);
        Monster testMonster = new Monster();
        testMonster.genMonster(1);


//        this.decMobHp(this.pcStr);
        System.out.println("*Attack*");
        battle_log.setContentText(testPlayer.getName() + " lands a blow for " + 7 + " on the " + testMonster.getName());
        return null;
    }

    @FXML
    public EventHandler<ActionEvent> pcRest(){
        battle_log.setContentText("Rested, you regain a small amount of MP!");
        //int mpRegen = this.pcLvl * 7;
        //this.pcMp += mpRegen;
        //if (this.pcMp > this.player.getMP()){
         //   this.pcMp = this.player.getMP();
       // }
        System.out.println("Rested");

        return null;
    }


    @FXML
    public EventHandler<ActionEvent> useMagic(){
        this.magic = new Magic(this.player, this.pcMp);
        //this.magic.useMagic(magicName);
        if (this.magic.getConfirmDmg()){
            this.mobHp -= this.magic.getMagicDmg();
            this.pcMp = this.magic.getPlayerMp();
            System.out.println(+ this.magic.getMagicDmg() + this.monster.getName());
           // if (this.mobHp> 0){
           //     this.mobAtk();
            }
        return null;
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


