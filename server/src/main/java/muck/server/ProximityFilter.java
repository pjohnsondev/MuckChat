package muck.server;

import muck.server.CharacterLocationTracker;

import muck.core.ClientId;

/**
 * determines the location of the user who sent the command, determines
 * the locations of other users within the app and determines which user(s) are closest,
 * then passes this data to the interaction controller
 */
public class ProximityFilter {
    CharacterLocationTracker<ClientId> CLT = new CharacterLocationTracker<ClientId>();
    //TODO
}