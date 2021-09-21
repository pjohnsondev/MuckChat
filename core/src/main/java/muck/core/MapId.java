package muck.core;

public class MapId {
	public final Integer id;

	public MapId(Integer i) {
		id = i;
	}

	public MapId() {
		id = -1;
	}


        @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof MapId && this.id == ((MapId) other).id);
    }

    @Override
    public String toString() {
        return "MapId(" + id + ")";
    }

}
