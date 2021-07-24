package muck.client;

import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.stage.Stage;

public class Avatar extends Application {

    String characterName; // will be passed into the class from 'log in screen'
    int uniqueId; // not sure if this is needed to be stored here. Will be passed into the class from 'log in screen'
    int characterAppearance; // Hopefully this will store the selected avatar number
    private final int width = 100; //size of the character
    private final int height = 200; //size of character

    @Override
    public void start(Stage stage) throws Exception {
        final int numCols = 4;
        final int numRows = 3;

        // Set scene
        GridPane root = new GridPane();
        root.setGridLinesVisible(true); //remove this when finished designing
        Scene scene = new Scene(root, 648, 480);

        // Need to set background




        stage.setScene(scene);
        stage.show();
    }

    /*public Avatar (int type, String name, int id) {
        this.characterName = name;
        this.uniqueId = id;


    }

    private void selectPlayer() {

    }*/



    public static void main(String[] args) {
        launch();
    }
}
