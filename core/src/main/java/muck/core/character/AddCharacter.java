package muck.core.character;

import muck.core.Location;

public class AddCharacter {
    private Character character;
    private Location location;

    public AddCharacter() {
    }

    public AddCharacter(Character character, Location location) {
        this.character = character;
        this.location = location;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
