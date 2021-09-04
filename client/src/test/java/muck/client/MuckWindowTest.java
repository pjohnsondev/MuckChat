
package muck.client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MuckWindowTest extends ApplicationTest {



    private static final Logger logger = LogManager.getLogger(MuckWindowTest.class);

    @Override
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // TODO: Do this with a mock character???
        logger.info("Initializing window");
        MuckController.constructor("test", "peach");
        FXMLLoader loader = new FXMLLoader(MuckWindowTest.class.getResource("/fxml/MuckWindow.fxml"));
        stage.initStyle(StageStyle.DECORATED);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

/*    @Test
    public void anotherTest() {
        clickOn("#openFullChat");
    }*/


    @AfterAll
    public static void testWindowClose() throws TimeoutException {
        logger.info("Closing window");
        FxToolkit.cleanupStages();
    }

}