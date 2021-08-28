package muck.client;

import muck.core.Location;
import muck.core.Pair;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

import java.util.function.Supplier;

import com.esotericsoftware.minlog.Log.Logger;

import java.time.Duration;
import java.util.ArrayList;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.animation.*;

/**
 * The Game Map class...
 */
public class GameMap extends Canvas implements EventHandler<KeyEvent> {

	public static int playerSpriteXSize = 5;
	public static int playerSpriteYSize = 5;

	List<Sprite> players = new ArrayList<Sprite>();
	TileMapReader tm = new TileMapReader("/map.tmx");
	Sprite hero = new Sprite(300, 300); // Create the player sprite
	private int startX; // The first tile to be drawn
	private int startY; // The first tile to be drawn
	private int offX; // Tile offset in pixels as hero moves pixel by pixel
	private int offY; // Tile offset in pixels as hero moves pixel by pixel
	private double centerX; // Center of the screen
	private double centerY; // Center of the screen
	private double cameraX; // Top left corner of our viewport
	private double cameraY; // Top left corner of our viewport
	private int layer = 0;
	private int tileId = 0;
	private int GID = 0;
	int n = 0; // water animation
	Rectangle rectangle = new Rectangle();
	double screenHeightInTiles;
	double screenWidthInTiles;
	GraphicsContext gc;
	Image image;
	double cameraMaxX;
	double cameraMaxY;
	private BiConsumer<String, Location> updatePlayer;
	private Supplier<List<Sprite>> otherPlayers;

	/**
	 * GameMap constructor accepts the canvas to be drawn onto. Creates the hero
	 * sprite Sets-up the camera viewport Credit: Toni Epple blog for viewport
	 * design
	 * https://www.javacodegeeks.com/2013/01/writing-a-tile-engine-in-javafx.html
	 * Draws the tiles around the hero (x,y) based on viewport size Provides the
	 * animation loop using AnimationTimer
	 *
	 * @param canvas The gameWindow canvas to be drawn onto.
	 */
	public GameMap(Canvas canvas) {
		setupCanvas(canvas);
		updatePlayer = (s, l) -> {
		};
		MuckClient.logInfo("Canvas only constructor called");
		otherPlayers = () -> new ArrayList<Sprite>();
	}

	/**
	 * GameMap constructor accepts the canvas to be drawn onto. Creates the hero
	 * sprite Sets-up the camera viewport Credit: Toni Epple blog for viewport
	 * design
	 * https://www.javacodegeeks.com/2013/01/writing-a-tile-engine-in-javafx.html
	 * Draws the tiles around the hero (x,y) based on viewport size Provides the
	 * animation loop using AnimationTimer
	 *
	 * @param canvas       The gameWindow canvas to be drawn onto.
	 * @param updatePlayer function to handle updating a player location.
	 * @param getPlayers   function to handle getting all client locations other
	 *                     than the calling client.
	 */
	public GameMap(Canvas canvas, BiConsumer<String, Location> updatePlayer, Supplier<List<Sprite>> getPlayers) {
		this.updatePlayer = updatePlayer;
		this.otherPlayers = getPlayers;
		setupCanvas(canvas);
	}

	private void setupCanvas(Canvas canvas) {
		// Get the graphic context of the canvas
		gc = canvas.getGraphicsContext2D();
		// Load the image
		String imagePath = "/texture.png"; // hard coded needs to be passable
		image = new Image(imagePath);

		centerX = canvas.getWidth() / 2; // Viewport midpoint (half the width of the canvas size)
		centerY = canvas.getHeight() / 2; // Viewport midpoint (half the height of the canvas size)

		cameraMaxX = tm.getWidth() * tm.getTileWidth() - (centerX * 2); // width of tile map in tiles multiply by width
																		// of tiles in pixels. Minus width of screen in
		cameraMaxY = tm.getHeight() * tm.getTileHeight() - (centerY * 2);

		screenHeightInTiles = (centerY * 2) / tm.getTileHeight();
		screenWidthInTiles = (centerX * 2) / tm.getTileWidth();

		canvas.setFocusTraversable(true);
		canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus()); // after map clicked listen for keyboard

		canvas.setOnKeyPressed(this);
		canvas.setOnKeyReleased(this);

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if (otherPlayers != null) {
					MuckClient.logInfo("Requesting player locations.");
					players = otherPlayers.get();
				}
			    if (updatePlayer != null)
				MuckClient.logInfo("Updating player location.");
			    updatePlayer.accept(hero.getAvatar(), new Location((int)hero.getPosX(), (int)hero.getPosY()));
			}
		}, 0, 500);

		// Game loop draw the canvas
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				hero.move(tm, hero);

				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				cameraX = hero.getPosX() - centerX; // Camera top left relative to hero X
				cameraY = hero.getPosY() - centerY; // Camera top left relative to hero Y
				// Ensure the camera does not go outside the game boundaries
				cameraPositionCheck();
				startX = (int) (cameraX / tm.getTileWidth());
				startY = (int) (cameraY / tm.getTileHeight());
				offX = (int) (cameraX % tm.getTileWidth());
				offY = (int) (cameraY % tm.getTileWidth());

				drawLayer(0); // draws a single layer pass the layer number (floor)
				n++;
				drawLayer(1);
				if (n < 15) {
					drawLayer(3); // Water animation layer
				}
				if (n > 30) {
					n = 0;
				} // reset water animation timer
				drawLayer(2);

				Sprite.drawHero(gc, tm, hero, centerX, centerY);

				// Added by Trent - 20/08
				// Gets a list of other player locations and draws them on screen
				for (Sprite p : players) {
					try {
						if (p != null)
							Sprite.drawHero(gc, tm, p, p.getPosX(), p.getPosY());
					} catch (NullPointerException ex) {
						MuckClient.logError(ex);
					}
				}

				drawLayer(4);
			}
		};
		timer.start();

	}

	/**
	 *
	 * @param layer
	 */
	public void drawLayer(int layer) {
		for (int y = 0; y <= screenHeightInTiles + 1; y++) {
			for (int x = 0; x <= screenWidthInTiles + 1; x++) {

				GID = getTileIndex(x + startX, y + startY, layer);
				if (GID != -1) { // Don't render blank tiles in layers (0 with -1 offset)
					gc.save();
					// Translate the viewport around the hero. (Easier to relative draw)
					gc.translate((x * tm.getTileWidth()) - offX, (y * tm.getTileHeight()) - offY);
					drawTile(gc, GID, image, x, y);
					// Restore the old state
					gc.restore();
				}
			}
		}
	}

	/**
	 * The draw tile method draws a single tile based on the input parameters
	 *
	 * @param gc        : The graphics context for our canvas
	 * @param tileIndex : The GID of the tile to be drawn
	 * @param tileImage : The tileSet image
	 * @param destX     : The destination X
	 * @param destY     : The destination Y
	 */
	public void drawTile(GraphicsContext gc, int tileIndex, Image tileImage, int destX, int destY) {
		int tileWidth = tm.getTileWidth();
		int tileHeight = tm.getTileHeight();
		int cols = tm.getTileColumns();

		int x = tileIndex % cols;
		int y = tileIndex / cols;

		gc.drawImage(tileImage, x * tileWidth, y * tileHeight, tileWidth, tileHeight, 0, 0, tileWidth, tileHeight);
	}

	/**
	 * The getTileIndex method returns the GID of the tile to be drawn
	 *
	 * @param x : The x position of the tile on the TMX map.
	 * @param y : The y position of the tile on the TMX map.
	 * @return : The GID of the tile to be drawn
	 */
	public int getTileIndex(int x, int y, int layer) {
		tileId = tm.getLayerId(layer, x, y);
		return tileId;
	}

	/**
	 * The method cameraPositionCheck ensures the users viewport does not go outside
	 * of the tmx map cameraMaxX maximum width of TMX map minus the viewport width
	 * cameraMaxY maximum height of the TMX map minus the viewport height
	 */
	public void cameraPositionCheck() {

		if (cameraX >= cameraMaxX) {
			cameraX = cameraMaxX;
		}
		if (cameraY >= cameraMaxY) {
			cameraY = cameraMaxY;
		}
		if (cameraX < 0) {
			cameraX = 0;
		}
		if (cameraY < 0) {
			cameraY = 0;
		}
	}

	@Override
	public void handle(KeyEvent e) {

		// Get the Type of the Event
		String type = e.getEventType().getName();

		// Get the KeyCode of the Event
		KeyCode keyCode = e.getCode();

		// Handle Hero movement
		if (type == "KEY_PRESSED" && keyCode == KeyCode.D) {
			hero.setDx(1);
		}
		if (type == "KEY_RELEASED" & keyCode == KeyCode.D) {
			hero.setDx(0);
		}
		if (type == "KEY_PRESSED" && keyCode == KeyCode.S) {
		    hero.setDy(1);
		}
		if (type == "KEY_RELEASED" & keyCode == KeyCode.S) {
			hero.setDy(0);
		}
		if (type == "KEY_PRESSED" && keyCode == KeyCode.A) {
		    hero.setDx(-1);
		}
		if (type == "KEY_RELEASED" & keyCode == KeyCode.A) {
			hero.setDx(0);
		}
		if (type == "KEY_PRESSED" && keyCode == KeyCode.W) {
		    hero.setDy(-1);
		}
		if (type == "KEY_RELEASED" & keyCode == KeyCode.W) {
			hero.setDy(0);
		}

	}
}
