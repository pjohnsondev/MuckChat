package muck.server;

import muck.core.ClientId;

import java.lang.Math;
import java.util.List;

import muck.core.Triple;
import muck.core.Id;
import muck.core.Location;
import muck.core.MapId;


import java.util.ArrayList;

/**
 * determines the location of the user who sent the command, determines
 * the locations of other users within the app and determines which user(s) are closest,
 * then passes this data to the interaction controller
 */
public class ProximityFilter {
    
    public double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }
    // The character location tracker the server uses can be found in MuckServer.java (im pretty sure)
    public List<Id<ClientId>> getIDsInRangeOf(Id<ClientId> id, double dist, ICharacterLocationTracker<ClientId> CLT) {
        Location inputIdLocation = CLT.getLocationById(id);
        List<Triple<Id<ClientId>, MapId, Location>> information = CLT.getAllClientLocationsExcept(id);
        List<Id<ClientId>> returnList = new ArrayList<Id<ClientId>>(); 
        for(int i = 0; i < information.size(); i++) {
            if(dist(information.get(i).right().getX(), information.get(i).right().getY(), inputIdLocation.getX(), inputIdLocation.getY()) <= dist) {
                returnList.add(information.get(i).left());
            }
        }
        return returnList;
    }

}