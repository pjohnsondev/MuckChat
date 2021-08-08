package muck.client.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import org.checkerframework.framework.qual.FromByteCode;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class CardsGameController implements Initializable {

    @FXML // fx:id="menu"
    private MenuBar menu; // Value injected by FXMLLoader

    @FXML //
    private Button askForCard;

    @FXML
    private Button goFish;

    @FXML
    private Label setsMadeTotal;





}
