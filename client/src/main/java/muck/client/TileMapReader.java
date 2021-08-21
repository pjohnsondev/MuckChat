package muck.client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The TileMapReader Class reads a TMX map (XML format) from resource path,
 * and stores the information from the map to be rendered.
 */
public class TileMapReader {

    private String path;

    String imagePath; //The path to the tileset for the map
    int width = 0; //Width of the map in tiles
    int height = 0; //Height of the map in tiles
    int tileWidth; //Width of the tiles in pixels
    int tileHeight; //Height of the tiles in pixels
    int tileCount; //Number of tiles in the map
    int tileColumns; //Number of tile columns
    int layers = 0; //Number of layers in the map (max 10)
    String[] data = new String[10]; //The data of each layer max 10 layers
    ArrayList<ArrayList<String[]>> layerContent = new ArrayList<>(); //Array list stores each of the layers flattened

    /**
     * TileMapReader constructor accepts one path parameter
     * @param path the path to the TileMap to be read (TMX File)
     */
    public TileMapReader(String path) {
       this.path = path;
       try {
           readTileMap(path); //Create the tile map
       } catch(Exception e) {
           System.out.println("Error reading tilemap path."); //Throw error is path doesn't work
        }
    }

    /**
     * Getter for map width
     * @return map width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Getter for map height
     * @return map height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Getter for tile width
     * @return tile width
     */
    public int getTileWidth() {
        return this.tileWidth;
    }

    /**
     * Getter for tile height
     * @return tile height
     */
    public int getTileHeight() {
        return this.tileHeight;
    }

    /**
     * Getter for tile columns
     * @return number of tile columns
     */
    public int getTileColumns() {
        return this.tileColumns;
    }

    /**
     * Getter for tile count
     * @return tile count
     */
    public int getTileCount() {
        return this.tileCount;
    }

    /**
     * The addTileMap method is called from the constructor and
     * does the bulk of the TMX reading
     * @param path
     */
    private void readTileMap(String path) {

        try {
            //Create a document reader to read our XML
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            //Pass the path to our TMX map
            Document doc = builder.parse(getClass().getResourceAsStream(path));

            doc.getDocumentElement().normalize();

            readTileSetDetails(doc); //Read the embedded tileSet details.

            readLayerDetails(doc); //Read each of the layers' data details

        } catch (Exception e) {
            System.out.println("ERROR - TILE MANAGER: can not read tilemap");
        }
    }

    /**
     * The readTileSetDetails method accepts the document (XML from our TMX),
     * and extracts the Tile Set details for the map.
     * @param doc The XML (TMX) document to read from
     */
    public void readTileSetDetails(Document doc) {
        NodeList list = doc.getElementsByTagName("tileset"); //Find the tileset tag in the XML
        Node node = list.item(0);
        Element eElement = (Element) node;

        imagePath = eElement.getAttribute("name"); //name of the TileSet image used
        tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth")); //tileWidth
        tileHeight = Integer.parseInt(eElement.getAttribute( "tileheight")); //tileHeight
        tileCount = Integer.parseInt(eElement.getAttribute("tilecount")); //tileCount
        tileColumns = Integer.parseInt(eElement.getAttribute("columns")); //tileColumns
    }

    /**
     * The readLayerDetails method read the data from each of the
     * map layers.
     * @param doc The xml (TMX) document to read from
     */
    public void readLayerDetails(Document doc) {
        NodeList list = doc.getElementsByTagName("layer");
        layers = list.getLength(); //This is the number of layers in the map
        Node node;
        Element eElement;
        //Loop through each of the layers in the TMX map
        for (int i = 0; i < layers; i++) {
            node = list.item(i);
            eElement = (Element) node;
            //Width and height should be equal for each layer only need to set this once
            if (i <= 0) {
                width = Integer.parseInt(eElement.getAttribute("width")); //Layer width
                height = Integer.parseInt(eElement.getAttribute("height")); //Layer height
            }

            //data array is an array of all the GID's (MAP integers that correspond to tiles)
            data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

            //Remove all horizontal lines
            data[i] = data[i].replaceAll("\\h", "");
            data[i] = data[i].trim(); //Remove whitespace before data

            //Read each line of the data and split it into separate array
            try {
                //String reader
                BufferedReader br = new BufferedReader((new StringReader(data[i])));
                String line = ""; //reset line
                ArrayList<String[]> content = new ArrayList<>(); //Reset contentt each time for new layer
                //Read all the lines and split by comma (CSV) add to content
                while ((line = br.readLine()) != null) {
                    content.add(line.split(","));
                }
                layerContent.add(content); //Each layer data is added to layerContent arrayList
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundError: No String data to read from map");
            } catch (IOException e) {
                System.out.println("NoLineToReadError: No String data to read from map");
            }
        }
    }

    /**
     * The getLayerId will determine what the integer (GID) is of the tile at the
     * requested x and y position. This determines which tile is to be rendered.
     * @param layer which layer are you trying to render from
     * @param x the x co-ordinate of that tile
     * @param y the y co-ordinate of that tile
     * @return an integer value (GID) of the tile to be drawn
     */
    public int getLayerId(int layer, int x, int y) {
        try {
            //Lookup the tile ID
            return Integer.parseInt(layerContent.get(layer).get(y)[x]) -1; //Minus 1 as the tilemap uses 0 as blank.
        } catch (NumberFormatException e) {
            return 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }
}
