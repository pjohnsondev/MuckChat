package muck.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayerDashboardController implements Initializable {

    private static String userName;
    private static String displayName;
    private static String avatarID;
    private static ArrayList<String[]> achievements = new ArrayList<>();

    //private static String[][] achievements;
    private static int muckPointTotal;
    private static int healthTotal;
    private Image fullAvatar = AvatarController.getFullAvatar(avatarID);

    @FXML
    private Button achievement; //TODO: Remove (Achievement Testing)

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView avatarFullBody;

    @FXML
    private Text username;

    @FXML
    private Button change;

    @FXML
    private Text muckPoints;

    @FXML
    private Text health;

    @FXML
    private TextArea achievementWindow;

    @FXML
    private ImageView gameReturn;

    private final BackgroundImage BACKGROUND = new BackgroundImage(new Image("/images/BackgroundAvSelection.jpg"), null, null, null, null);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            avatarFullBody.setPreserveRatio(true);
            gridPane.setBackground(new Background(BACKGROUND));
            avatarFullBody.setImage(fullAvatar);
            selection(avatarID);
            centreImage();
            achievementWindow.setStyle("-fx-text-fill: white;");
            if (achievements.size() > 0) {
                updateAchievements();
            }
            username.setText(displayName);
            muckPoints.setText(String.valueOf(muckPointTotal));
            health.setText(String.valueOf(healthTotal));


            change.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    //This will take over the scene that currently holds the player dashboard screen
                    AvatarController.avatarCreation(userName, displayName, avatarID);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Avatar.fxml")));
                    stage.setScene(new Scene(parent));
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            gameReturn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> returnToGame(event, userName, avatarID));

            // ********* ACHIEVEMENT TESTING *********
            boolean achievement1 = false;
            String achievement1Title = "Hotel California";
            String achievement1Description = "Player has visited the Inn";
            achievement.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Achievements achieve = new Achievements(achievement1, achievement1Title, achievement1Description);
                achieve.achievementUnlock(achieve);
            });
            // ********* ACHIEVEMENT TESTING *********

        } catch (Exception e) {
            System.out.print("Error in initialize");
        }
    }

    public static void playerDashboard(String uname, String display, String avID) {
        userName = uname;
        displayName = display;
        avatarID = avID;
        //TODO: Call the server to get achievements muckPoints and health. Remove below
        achievements.clear();
        //achievements = new String[][]{{"Hotel California", "Player has visited the Inn"}, {"Retail Therapy", "Player has visited the Shops"}, {"Alien Exterminator", "Player has won a game of Space Invaders"},{"Hotel California", "Player has visited the Inn"}, {"Retail Therapy", "Player has visited the Shops"}, {"Alien Exterminator", "Player has won a game of Space Invaders"},{"Hotel California", "Player has visited the Inn"}, {"Retail Therapy", "Player has visited the Shops"}, {"Alien Exterminator", "Player has won a game of Space Invaders"},{"Hotel California", "Player has visited the Inn"}, {"Retail Therapy", "Player has visited the Shops"}, {"Alien Exterminator", "Player has won a game of Space Invaders"}};

        achievements.add(new String[]{"Hotel California", "Player has visited the Inn"});
        achievements.add(new String[]{"Retail Therapy", "Player has visited the Shops"});
        achievements.add(new String[]{"Alien Exterminator", "Player has won a game of Space Invaders"});
        achievements.add(new String[]{"Hotel California", "Player has visited the Inn"});
        achievements.add(new String[]{"Retail Therapy", "Player has visited the Shops"});
        achievements.add(new String[]{"Alien Exterminator", "Player has won a game of Space Invaders"});
        achievements.add(new String[]{"Hotel California", "Player has visited the Inn"});
        achievements.add(new String[]{"Retail Therapy", "Player has visited the Shops"});
        achievements.add(new String[]{"Alien Exterminator", "Player has won a game of Space Invaders"});

        muckPointTotal = 100; //TODO: Remove
        healthTotal = 80; //TODO: Remove
    }

    private void updateAchievements() {
        for (String[] achieve : achievements ) {
            String achievement = achieve[0] + ": " + achieve[1] +"\n\n";
            achievementWindow.appendText(achievement);
        }
    }

    private void returnToGame(MouseEvent event, String uname, String avID) {
        // TODO: Send username and avatar back to the server for storage
        MuckController.constructor(uname, avID);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void centreImage() { //TODO: This is not centring vertically???
        Image img = avatarFullBody.getImage();
        if (img != null) {
            double w;
            double h;

            double ratioX = avatarFullBody.getFitWidth() / img.getWidth();
            double ratioY = avatarFullBody.getFitHeight() / img.getHeight();

            double reducCoeff = Math.min(ratioX, ratioY);

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            avatarFullBody.setX((avatarFullBody.getFitWidth() - w) / 2);
            avatarFullBody.setY((avatarFullBody.getFitHeight() - h) / 2);

        }
    }

    private void selection(String character) {
        try {
            switch (character) {
                case "peach":
                    avatarFullBody.setLayoutY(90.0);
                    avatarFullBody.setFitHeight(390);
                    break;
                case "batman":
                    avatarFullBody.setLayoutY(40.0);
                    break;
                case "pikachu":
                    avatarFullBody.setLayoutY(210.0);
                    avatarFullBody.setFitHeight(250);
                    break;
                case "skeleton":
                    avatarFullBody.setLayoutY(120.0);
                    avatarFullBody.setFitHeight(410);
                    break;
                case "wonderWoman":
                    avatarFullBody.setLayoutY(70.0);
                    avatarFullBody.setFitHeight(400);
                    break;
                case "yoshi":
                    avatarFullBody.setLayoutY(180.0);
                    avatarFullBody.setFitHeight(300);
                    break;
                case "error":
                    break;
                default:
                    break;
            }
            centreImage();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


        /*public static void playerDashboard(String uname) {
        userName = uname;
        // TODO: Need to call the database for current avatar and muck point values
        try {
            FXMLLoader loader = new FXMLLoader(AvatarController.class.getResource("/fxml/Avatar.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setRoot(root);
            scene.getStylesheets().add(PlayerDashboardController.class.getResource("/css/style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle("Muck 2021");
            stage.setMaxWidth(1200);
            stage.setMaxHeight(1100);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

        /*public static void playerDashboard(String uname, String display, MouseEvent event, String avID) {
        userName = uname;
        displayName = display;
        avatarID = avID;
        //TODO: Call the server to get all the relevant information
        try {
            Parent root = FXMLLoader.load(PlayerDashboardController.class.getResource("/fxml/PlayerDashboard.fxml"));
            Scene scene = new Scene(root);
            scene.setRoot(root);
            scene.getStylesheets().add(PlayerDashboardController.class.getResource("/css/style.css").toExternalForm());
            //This line gets the Stage Information
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(AvatarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}