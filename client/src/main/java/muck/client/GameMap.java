package muck.client;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.scene.layout.BorderPane;
import muck.client.character_client.VillagerNPC;
import muck.core.AvatarLocation;
import muck.core.Location;
import java.util.ArrayList;
import java.util.List;
import muck.core.TriConsumer;
import java.util.function.Supplier;
import muck.client.character_client.CatNPC;

/**
 * The Game Map class is the primary rendering tool for the muck graphics.
 * It controls the animation loop to render the tileMap.
 */
public class GameMap extends Canvas implements EventHandler<KeyEvent> {
	TileMapReader tm = new TileMapReader("/maps/homeTown.tmx");
	public Sprite hero = new Sprite(300, 300); // Create the player sprite
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
	private int n = 0; // water animation
	private int uP = 0; // update players
	private double screenHeightInTiles;
	private double screenWidthInTiles;
	private GraphicsContext gc;
	private Image image;
	private double cameraMaxX;
	private double cameraMaxY;
	private TriConsumer<String, Integer, Location> updatePlayer;
	private Supplier<List<Sprite>> otherPlayers;
	private BorderPane gamePane;
	List<Sprite> players = new ArrayList<Sprite>();
	public int worldID = 1; //World 1 = HomeTown World 2 = Cave

	// Added NPC's - @kgusti
	CatNPC cat = new CatNPC("1", 400, 250, "brown", tm);
	VillagerNPC villager1 = new VillagerNPC("V1", 280, 750, "male1", tm);
	VillagerNPC villager2 = new VillagerNPC("V2", 650, 250, "female1", tm);
	VillagerNPC villager3 = new VillagerNPC("V3", 750, 500, "female4", tm,
			"down", 0, 100, 0);

	// CA - Infinite Loops
	// Use this method when returning from a game landing page
	public GameMap(Canvas canvas, BorderPane borderPane) {
		setupCanvas(canvas, "/tilesets/texture.png", tm);
		gamePane = borderPane;
		updatePlayer = (s, m, l) -> {
		};
		otherPlayers = () -> new ArrayList<Sprite>();
	}

	//This constructor is used when only canvas required
	public GameMap(Canvas canvas) {
		setupCanvas(canvas, "/tilesets/texture.png", tm);
		updatePlayer = (s, m, l) -> {
		};
		otherPlayers = () -> new ArrayList<Sprite>();
	}

	public GameMap(Canvas canvas, BorderPane boarderPane, String world, TileMapReader tmNew) {
		this.gamePane = boarderPane;
		setupCanvas(canvas, world, tmNew);
		updatePlayer = (s, m, l) -> {
		};
		otherPlayers = () -> new ArrayList<Sprite>();
	}

	/**
	 * GameMap constructor accepts the canvas to be drawn onto.
	 *
	 * @param canvas       The gameWindow canvas to be drawn onto.
	 * @param borderPane   The muck borderPane to be passed into the WorldController
	 *                     for game landing page updates
	 * @param updatePlayer function to handle updating a player location.
	 * @param getPlayers   function to handle getting all client locations other
	 *                     than the calling client.
	 */
	public GameMap(Canvas canvas, BorderPane borderPane, TriConsumer<String, Integer, Location> updatePlayer,
				   Supplier<List<Sprite>> getPlayers) {
		this.updatePlayer = updatePlayer;
		this.otherPlayers = getPlayers;
		this.gamePane = borderPane;

		setupCanvas(canvas, "/tilesets/texture.png", tm);
	}

	/**
	 * GameMap constructor accepts the canvas to be drawn onto.
	 * Creates the hero sprite
	 * Sets-up the camera viewport Credit: Toni Epple blog for viewport design https://www.javacodegeeks.com/2013/01/writing-a-tile-engine-in-javafx.html
	 * Draws the tiles around the hero (x,y) based on viewport size
	 * Provides the animation loop using AnimationTimer
	 *
	 * @param canvas The gameWindow canvas to be drawn onto.
	 */
	public void setupCanvas(Canvas canvas, String world, TileMapReader tmNew) {
		tm = tmNew;
		// Get the graphic context of the canvas
		gc = canvas.getGraphicsContext2D();
		// Load the image
		String imagePath = world; // the tileset
		image = new Image(imagePath);

		centerX = canvas.getWidth() / 2; //Viewport midpoint (half the width of the canvas size)
		centerY = canvas.getHeight() / 2; //Viewport midpoint (half the height of the canvas size)

		cameraMaxX = tm.getWidth() * tm.getTileWidth() - (centerX * 2); //width of tile map in tiles multiply by width of tiles in pixels. Minus width of screen in pixels
		cameraMaxY = tm.getHeight() * tm.getTileHeight() - (centerY * 2);

		screenHeightInTiles = (centerY * 2) / tm.getTileHeight();
		screenWidthInTiles = (centerX * 2) / tm.getTileWidth();

		canvas.setFocusTraversable(true);
		canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus()); //after map clicked listen for keyboard events

		canvas.setOnKeyPressed(this);
		canvas.setOnKeyReleased(this);

		//Game loop draw the canvas
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				hero.move(tm, hero); //controls hero movement
				//Check the location to move to new worlds
				if (WorldController.locationCheck(new Location((int) hero.getPosX(), (int) hero.getPosY()), gamePane, worldID, canvas) != 0) {
					this.stop(); //stop this instance new instance for new world started.
				}
				updatePlayers();
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //blank the screen
				cameraX = hero.getPosX() - centerX; //Camera top left relative to hero X
				cameraY = hero.getPosY() - centerY; //Camera top left relative to hero Y

				//Ensure the camera does not go outside the game boundaries
				cameraPositionCheck();
				startX = (int) (cameraX / tm.getTileWidth());
				startY = (int) (cameraY / tm.getTileHeight());
				offX = (int) (cameraX % tm.getTileWidth());
				offY = (int) (cameraY % tm.getTileWidth());

				drawLayer(0); //draws a single layer pass the layer number (floor)

				drawLayer(1); //passableOver

				//Water animation controller 15 frames each cycle
				if (n < 15) {
					drawLayer(3); //Water animation layer
				}
				if (n > 30) {
					n = 0;
				} //reset water animation timer
				n++;

				drawLayer(2); //Solid

				Sprite.drawHero(gc, tm, hero, centerX, centerY); //Draws the hero

				handleNPC(); // handle all NPCs - @kgusti

				// Added by Trent - 20/08
				// Gets a list of other player locations and draws them on screen
				for (Sprite p : players) {
					try {
						if (p != null) {
							p.drawPlayer(gc, p, p.getPosX() - cameraX, p.getPosY() - cameraY);
						}
					} catch (NullPointerException ex) {
						MuckClient.logError(ex);
					}
				}
				drawLayer(4); //passable under
			}
		};
		timer.start();
	}

	/**
	 * The drawLayer method draws the muck map one layer at a time on the canvas.
	 *
	 * @param layer The layer id of the layer to be drawn
	 */
	public void drawLayer(int layer) {
		for (int y = 0; y <= screenHeightInTiles + 1; y++) {
			for (int x = 0; x <= screenWidthInTiles + 1; x++) {

				int GID = getTileIndex(x + startX, y + startY, layer);
				if (GID != -1) { //Don't render blank tiles in layers (0 with -1 offset)
					gc.save();
					//Translate the viewport around the hero. (Easier to relative draw)
					gc.translate((x * tm.getTileWidth()) - offX, (y * tm.getTileHeight()) - offY);
					drawTile(gc, GID, image);
					//Restore the old state
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
	 */
	public void drawTile(GraphicsContext gc, int tileIndex, Image tileImage) {
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
		int tileId = tm.getLayerId(layer, x, y);
		return tileId;
	}

	/**
	 * The method cameraPositionCheck ensures the users viewport does
	 * not go outside of the tmx map
	 * cameraMaxX maximum width of TMX map minus the viewport width
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

	public void updatePlayers() {
		if (otherPlayers != null) {
			players = otherPlayers.get();
		}
		if (updatePlayer != null) {
			updatePlayer.accept(hero.getAvatar(), worldID, new Location((int) hero.getPosX(), (int) hero.getPosY()));
		}
	}



	//Handle key presses for movement.
	@Override
	public void handle(KeyEvent e) {

		// Get the Type of the Event
		String type = e.getEventType().getName();

		// Get the KeyCode of the Event
		KeyCode keyCode = e.getCode();

		// Handle Hero movement
		if (type == "KEY_PRESSED" && keyCode == KeyCode.D) {
			hero.setDx(0.5);
		}
		if (type == "KEY_RELEASED" & keyCode == KeyCode.D) {
			hero.setDx(0);
		}
		if (type == "KEY_PRESSED" && keyCode == KeyCode.S) {
			hero.setDy(0.5);
		}
		if (type == "KEY_RELEASED" & keyCode == KeyCode.S) {
			hero.setDy(0);
		}
		if (type == "KEY_PRESSED" && keyCode == KeyCode.A) {
			hero.setDx(-0.5);
		}
		if (type == "KEY_RELEASED" & keyCode == KeyCode.A) {
			hero.setDx(0);
		}
		if (type == "KEY_PRESSED" && keyCode == KeyCode.W) {
			hero.setDy(-0.5);
		}
		if (type == "KEY_RELEASED" & keyCode == KeyCode.W) {
			hero.setDy(0);
		}
	}

	/**
	 * Handle all NPC movement and draw
	 * @kgusti - Anyone Welcome
	 */
	public void handleNPC() {
		// NPC movement
		cat.handle();
		villager1.handle();
		villager2.handle();
		villager3.handle();

		// draw NPCs
		cat.drawCatNPC(gc, cameraX, cameraY, tm);
		villager1.drawVillagerNPC(gc, cameraX, cameraY, tm);
		villager2.drawVillagerNPC(gc, cameraX, cameraY, tm);
		villager3.drawVillagerNPC(gc, cameraX, cameraY, tm);
	}
}
