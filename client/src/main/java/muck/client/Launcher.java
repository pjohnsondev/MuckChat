package muck.client;

/**
 * A launcher to avoid an issue whereby even if the application is bundled as a "fat jar" including JavaFX, if
 * it is launched directly using App, Java will object that JavaFX is not on the module path
 *
 * https://openjfx.io/openjfx-docs/#modular
 */
public class Launcher {

    public static void main(String[] args) {
        App.main(args);
    }

}
