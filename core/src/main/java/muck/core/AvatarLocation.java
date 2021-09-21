package muck.core;

public class AvatarLocation {
    public final String location;

    public AvatarLocation(String a) { location = a; }
    public AvatarLocation() { location = "";}

        @Override
	public int hashCode() {
	    return location.hashCode();
	}

    @Override
    public boolean equals(Object other) {
        return (other instanceof AvatarLocation && this.location == ((AvatarLocation) other).location);
    }

    @Override
    public String toString() {
        return "AvatarLocation(" + location + ")";
    }

}
