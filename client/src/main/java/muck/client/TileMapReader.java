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

public class TileMapReader {

    private String path;

    String imagePath;
    int width = 0;
    int height = 0;
    int tileWidth;
    int tileHeight;
    int tileCount;
    int tileColumns;
    int layers = 0;
    String[] data = new String[10];
    int n = 0; //TobeRemoved

    public TileMapReader(String path) {
       this.path = path;
       try {
           addTileMap(path);
       } catch(Exception e) {
           System.out.println("Error reading tilemap path.");
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public int getTileColumns() {
        return this.tileColumns;
    }

    public int getTileCount() {
        return this.tileCount;
    }

    private void addTileMap(String path) {

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document doc = builder.parse(getClass().getResourceAsStream(path));

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element eElement = (Element) node;

            imagePath = eElement.getAttribute("name");
            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute( "tileheight"));
            tileCount = Integer.parseInt(eElement.getAttribute("tilecount"));
            tileColumns = Integer.parseInt(eElement.getAttribute("columns"));
            //sprite = new Sprite("tile/" + imagePath + ".png", tileWidth, tileHeight);

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for(int i =0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if(i <= 0 ) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }

                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();
                //System.out.println("-------------------\n" + data[i]);
            }
        } catch (Exception e) {
            System.out.println("ERROR - TILE MANAGER: can not read tilemap");
        }
    }

    public int getLayerId(int layer, int x, int y) {
        String mapId = data[layer];
        mapId = mapId.replaceAll("\\h","");
        mapId = mapId.trim();
        ArrayList<String[]> content = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader((new StringReader(mapId)));
            String line = "";
            while((line = br.readLine()) != null) {
                    content.add(line.split(","));
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundError: No String data to read from map");
        } catch (IOException e) {
            System.out.println("NoLineToReadError: No String data to read from map");
        }

        try {
            return Integer.parseInt(content.get(y)[x]) -1;
        } catch (NumberFormatException e) {
            return 0;
            }
    }



}