package muck.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import muck.client.utilities.RandomNameGenerator;
import muck.core.models.models.User;
import muck.core.models.models.UserModel;


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
    private Text heading;

    @FXML
    private Button change;

    @FXML
    private Text muckPoints;

    @FXML
    private Text health;

    @FXML
    private TextArea achievementWindow;

    @FXML
    private Button achievementButton;

    @FXML
    private Button scoreboardButton;


    @FXML
    private ImageView gameReturn;

    private final BackgroundImage BACKGROUND = new BackgroundImage(new Image("/images/BackgroundAvSelection.png"), null, null, null, null);

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

            achievementButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateAchievements());
            scoreboardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateScoreboard());
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

    /**
     * Set up method.  Assigns the appropriate values to the applicable variables
     * @param uname: The player username
     * @param display: The player display name
     * @param avID: The player's current avatar ID
     */
    public static void playerDashboard(String uname, String display, String avID) {
        userName = uname;
        displayName = display;
        avatarID = avID;
        //TODO: Call the server to get achievements muckPoints and health. Remove below dummy values
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

        muckPointTotal = 100; //TODO: Remove when can call to the server
        healthTotal = 80; //TODO: Remove when can call to the server
    }

    /**
     * Displays the player's achievements in the applicable section of the GUI
     */
    private void updateAchievements() {
        heading.setText("Achievements");
        achievementWindow.clear();
        achievementButton.setStyle("-fx-text-fill: #696969;" + "-fx-background-color: #87cdff;");
        scoreboardButton.setStyle("-fx-text-fill: #87cdff;" + "-fx-background-color:  #696969;");

        for (String[] achieve : achievements ) {
            String achievement = achieve[0] + ": " + achieve[1] +"\n\n";
            achievementWindow.appendText(achievement);
        }
    }

    /**
     * Displays all game players and their associated muckPoint values in descending order
     */
    private void updateScoreboard() {
        int count = 1;

        heading.setText("Scoreboard");
        achievementWindow.clear();
        scoreboardButton.setStyle("-fx-text-fill: #696969;" + "-fx-background-color: #87cdff;");
        achievementButton.setStyle("-fx-text-fill: #87cdff;" + "-fx-background-color:  #696969;");

        // Should we get the database running in time this is how we would
        // call the list of users in ascending order
        /*List<User> userDetails = UserModel.getUsersOrderedByPoints(false);
         for (User eachUser : userDetails) {
             String user = eachUser.getUserName();
             int points = eachUser.getPoints();
             achievementWindow.appendText(user + ": " + points + "\n\n");
             count++;
         }*/

        //TODO: Remove below dummy values and for loop when we can call the server

        RandomNameGenerator random = new RandomNameGenerator();
        ArrayList<String[]> userDetails = new ArrayList<>();
        userDetails.add(new String[]{random.generateName(), "220"});
        userDetails.add(new String[]{random.generateName(), "180"});
        userDetails.add(new String[]{random.generateName(), "150"});
        userDetails.add(new String[]{random.generateName(), "120"});
        userDetails.add(new String[]{random.generateName(), "120"});
        userDetails.add(new String[]{random.generateName(), "100"});
        userDetails.add(new String[]{displayName, "80"});
        userDetails.add(new String[]{random.generateName(), "60"});
        userDetails.add(new String[]{random.generateName(), "40"});

        for (String[] eachUser : userDetails) {
            String user = eachUser[0];
            String points = eachUser[1];
            achievementWindow.appendText(count + ": " + user + " - " + points + "\n\n");
            count++;
        }
    }

    /**
     * returnToGame method
     * Passes the current player username and avatarID back to the MuckController to update if changed
     * @param event: The click event resulting from a player clicking on the return icon
     * @param uname: The players username
     * @param avID: The players current avatar ID
     */
    private void returnToGame(MouseEvent event, String uname, String avID) {
        // TODO: Send username and avatar back to the server for storage
        MuckController.constructor(uname, avID);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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



}