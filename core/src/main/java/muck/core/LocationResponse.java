package muck.core;

import java.util.List;
import java.util.ArrayList;
import muck.core.character.Character;

public class LocationResponse {

	public final List<Pair<String, Location>> data;

	public LocationResponse(List<Pair<String, Location>> d) {
		data = d;
	}

	public LocationResponse() {
	    data = new ArrayList<Pair<String, Location>>();
	}
}
