package muck.core;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import muck.core.LocationResponseData;

public class LocationResponse {

	public final List<LocationResponseData> data;

	public LocationResponse(List<Pair<String, Location>> d) {
	    data = d.stream().map(p -> new LocationResponseData(p)).collect(Collectors.toList());
	}

	public LocationResponse() {
		data = new ArrayList<LocationResponseData>();
	}
}
