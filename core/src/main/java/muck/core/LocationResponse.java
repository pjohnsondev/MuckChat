package muck.core;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import muck.core.LocationResponseData;
import muck.core.AvatarLocation;
import muck.core.MapId;

public class LocationResponse {

	public final List<LocationResponseData> data;

	public LocationResponse(List<Triple<AvatarLocation, MapId, Location>> d) {
		data = d.stream().map(p -> new LocationResponseData(p)).collect(Collectors.toList());
	}

	public LocationResponse() {
		data = new ArrayList<LocationResponseData>();
	}
}
