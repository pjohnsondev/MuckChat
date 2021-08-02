package muck.protocol.connection;
import java.util.ArrayList;

public class PlayerList() {
  private ArrayList<String> currentPlayers;

  PlayerList(ArrayList<String> players) {
      currentPlayers = players;
  }

  public void setCurrentPlayers(ArrayList<String> players) {
      currentPlayers = players;
  }

  public ArrayList<String> getCurrentPlayers() {
      return currentPlayers;
  }
}
