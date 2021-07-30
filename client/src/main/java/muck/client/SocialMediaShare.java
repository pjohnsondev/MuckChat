package muck.client;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SocialMediaShare {

    /**
     * Creates a web enviroment within the Muck client to access social media sharing and authentication APIs
     * Note - currently called by public void start() in App.java (line 70 at time of writing this comment) for testing
     *
     *
     * @param mainStage The object holding the main Muck client
     */
    public static void startWebView(Stage mainStage){
        // ******************************************************************
        // This is HTML to make a page where a user can see their achievement and choose to post it
        // The image source is a random achievement related image from the internet as a placeholder
        // This is copied straight from Facebook documentation, that's why its a bit silly
        String webPage = "<html>\n" +
                "<head>\n" +
                "<title>Your Website Title</title>\n" +
                "<meta property=\"og:url\"           content=\"https://www.your-domain.com/your-page.html\" />\n" +
                "<meta property=\"og:type\"          content=\"website\" />\n" +
                "<meta property=\"og:title\"         content=\"Your Website Title\" />\n" +
                "<meta property=\"og:description\"   content=\"Your description\" />\n" +
                "<meta property=\"og:image\"         content=\"https://www.your-domain.com/path/image.jpg\" />\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"fb-root\"></div>\n" +
                "<script>(function(d, s, id) {\n" +
                "var js, fjs = d.getElementsByTagName(s)[0];\n" +
                "if (d.getElementById(id)) return;\n" +
                "js = d.createElement(s); js.id = id;\n" +
                "js.src = \"https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v3.0\";\n" +
                "fjs.parentNode.insertBefore(js, fjs);\n" +
                "}(document, 'script', 'facebook-jssdk'));</script>\n" +
                " <img src=\"https://i.kym-cdn.com/photos/images/newsfeed/001/385/485/d9d.jpg_orig\" alt=\"alternatetext\"> \n" +
                "<div class=\"fb-share-button\" \n" +
                "data-href=\"https://i.kym-cdn.com/photos/images/newsfeed/001/385/485/d9d.jpg_orig\" \n" +
                "data-layout=\"button_count\">\n" +
                "</div>\n" +
                "</body>\n" +
                "</html\n";
        // End HTML

        // Building the JavaFX WebView
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();

        // Load the abobe HTML as a web page
        engine.loadContent(webPage, "text/html");

        // Placeholder settings for the webpage UI
        // Ideally this will become a sort of pop-up within Muck
        // I glanced over some techniques to do that but decided to come back to that
        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox, 960, 600);

        // This sets the main app to be the web page
        // The result is the entire app becomes like a browser
        // Fine for testing at this stage
        mainStage.setScene(scene);
        mainStage.show();

        // This successfully opens a popup window, however the share to Facebook page doesn't load
        engine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
            @Override
            public WebEngine call(PopupFeatures param) {
                Stage popupStage = new Stage();
                WebView popupWV = new WebView();
                popupStage.setScene(new Scene(popupWV));
                popupStage.show();
                WebEngine popupEngine = popupWV.getEngine();
                popupEngine.setJavaScriptEnabled(true);
                return popupEngine;
            }
        });





    }

    // TODO: implement functionality that will allow users to post to Social Media
    //  platforms their progress and/or achievements.
}
