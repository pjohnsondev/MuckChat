package muck.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayerDashboardController implements Initializable {
    private static String userName;
    private static String avatarID = "peach";
    private Image fullAvatar = AvatarController.getFullAvatar(avatarID);
    private int muckPointTotal = 15;
    private int healthTotal = 80;


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
    private ImageView gameReturn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            avatarFullBody.setPreserveRatio(true);
            avatarFullBody.setImage(fullAvatar);
            centreImage();
            username.setText(userName);
            muckPoints.setText(String.valueOf(muckPointTotal));
            health.setText(String.valueOf(healthTotal));

            change.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                AvatarController.avatarCreation(event, userName, avatarID);
            });

            gameReturn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                returnToGame(event, userName, avatarID);
            });
        } catch (Exception e) {
            System.out.print("Error in initialize");
        }
    }

    public static void playerDashboard(String uname) {
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
    }

    public static void playerDashboard(String uname, MouseEvent event, String avID) {
        userName = uname;
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
    }

    private void returnToGame(MouseEvent event, String uname, String avID) {
        // TODO: Send username and avatar back to the server for storage
        MuckController.constructor(event, uname, avID);
    }

    private void centreImage() { //TODO: This is not centring vertically???
        Image img = avatarFullBody.getImage();
        if (img != null) {
            double w;
            double h;

            double ratioX = avatarFullBody.getFitWidth() / img.getWidth();
            double ratioY = avatarFullBody.getFitHeight() / img.getHeight();

            double reducCoeff;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            avatarFullBody.setX((avatarFullBody.getFitWidth() - w) / 2);
            avatarFullBody.setY((avatarFullBody.getFitHeight() - h) / 2);

        }
    }

    public static void main(String[] args) {
        PlayerDashboardController.playerDashboard("Test");
    }
}