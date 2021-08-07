package muck.client;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SocialMediaShare {

    /**
     * Uploads an image to the internet and opens the default browser to the FaceBook share dialog to
     * share that image to a users FaceBook page
     *
     * @param filePath Location of the image to be shared
     */
    public static void shareImageToFacebook(String filePath) throws IOException, URISyntaxException {

        String uploadResponse = uploadToImgur(filePath);
        String imgurID = getImgurIdFromResponse(uploadResponse);
        String facebookShareUrl = getFBShareUrlFromID(imgurID);
        openWebpage(facebookShareUrl);

    }

    /**
     * Takes a locally stored file and encoded it to a Base64 string
     * Intended to be used to upload files to websites via POST request
     *
     * @param filePath Local path of the file to be encoded
     * @return Base64 encoded string of file data
     */
    public static String getImageBase64String(String filePath) throws IOException {
        File image = new File(filePath);
        FileInputStream imgStream = new FileInputStream(image);
        byte [] buffer = new byte[(int) image.length()];

        imgStream.read(buffer);

        return Base64.getEncoder().encodeToString(buffer);
    }

    /**
     * Uploads a file to Imgur.com anonymously. Future access to the file requires the "image id" which is part of the
     * return of the function
     *
     * Note - Intended to be used as a staging point for sharing on social media due to Facebook not supporting
     * direct upload of binary images for sharing on user profiles
     *
     * @param filePath Local path of file to upload
     * @return Returns the response to the POST request made to the Imgur API
     */
    public static String uploadToImgur(String filePath) throws IOException {
        // This id is from making an "app", it allows access to the API
        String clientID = "9531cc0ffc4c873";

        // Get the Bas64 string of an image
        String data = getImageBase64String(filePath);
        // This has to be further encoded to deal with URL special characters
        data = URLEncoder.encode(data, StandardCharsets.UTF_8);

        // The following is mostly boilerplate to get Java to make an HTTP POST request

        //API endpoint for uploading images
        URL uploadEnpoint = new URL("https://api.imgur.com/3/image");

        // Building a POST request header
        HttpURLConnection con = (HttpURLConnection)uploadEnpoint.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Client-ID " + clientID);
        con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

        // Send request
        con.connect();
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write("image="+data); // This might be where the function breaks, I've tried a few different formatting things
        wr.flush();

        // Get the response
        StringBuilder stb = new StringBuilder();
        BufferedReader rd = new BufferedReader(
                // Receive the POST response and format to a string block
                new InputStreamReader(con.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            stb.append(line).append("\n");
        }
        wr.close();
        rd.close();

        // Returns the entire response, possibly will clean this up to only return the ID
        return stb.toString();

    }

    /**
     * Fucntion to convert a string URL into a URI object
     *
     * Note - This is a seperate function so it can be unit tested without actually opening a web browser
     *
     * @param url String containing the URL of a web page to be opened
     * @return URI object properly formatted to be used to open a web page
     */
    public static URI formatUri(String url) throws MalformedURLException {
        URL tempUrl = null;
        tempUrl = new URL(url);
        String nullFragment = null;
        URI uri = null;
        try {
            uri = new URI(tempUrl.getProtocol(), tempUrl.getHost(), tempUrl.getPath(), tempUrl.getQuery(), nullFragment);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;

    }

    /**
     * Intended to be use to open sharing dialog for social media (Facebook in particular at time of comment)
     *
     * @param url URL object of webpage to open in desktop browser
     */
    public static void openWebpage(String url) {
        URI uri = null;

        try {
            uri = formatUri(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to take the ID of an image uploaded to Imgur.com and produce a URL that will open the sharing
     * dialog on Facebook to share that image
     *
     * @param imgurID The Imgur.com ID of the photo to be shared on FaceBook
     * @return the URL required to open sharing dialog for the provided Imgur.com ID
     */
    public static String getFBShareUrlFromID(String imgurID) {
        String firstPartofUrl = "https://www.facebook.com/sharer/sharer.php?kid_directed_site=0&sdk=joey&u=https://i.imgur.com/";
        String lastPartofUrl = ".jpeg&display=popup&ref=plugin&src=share_button";
        return firstPartofUrl + imgurID + lastPartofUrl;
    }

    /**
     * Function to get the image ID of an image uploaded to Imgur.com by POST request using Imgur API
     *
     * @param response The response from a POST request to upload an image to Imgur.com
     * @return The image ID of the image that was uploaded
     */
    public static String getImgurIdFromResponse(String response){
        String imgurID = "";
        JsonObject responseJson = new JsonParser().parse(response).getAsJsonObject();
        JsonObject dataJson = responseJson.get("data").getAsJsonObject();
        imgurID = dataJson.get("id").getAsString();

        return imgurID;
    }

    /**
     * Creates a web enviroment within the Muck client to access social media sharing and authentication APIs
     *
     * Note - At time of writing this comment I've abandoned this method in favour of opening a desktop browser
     * due to FaceBook not supporting the WebView browser
     *
     * @param mainStage The object holding the main Muck client
     */
    public static void startWebView(Stage mainStage) throws IOException, URISyntaxException {


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
        engine.setJavaScriptEnabled(true);

        // Load the above HTML as a web page
        //engine.loadContent(webPage, "text/html");
        engine.load("https://www.facebook.com/sharer/sharer.php");

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
}




