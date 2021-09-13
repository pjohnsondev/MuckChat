package muck.client.components;

import muck.core.structures.UserStructure;

/**
 * This is a very basic singleton that contains information about the active user. It should only be set
 * when the user is logged in. It contains basic information about the user.
 *
 * author: Ethan Carlsson ecarlsso@myune.edu.au
 */
public class ActiveUser {

    private static UserStructure userStructure;

    private static ActiveUser instance = null;

    private ActiveUser() {
    }

    public static ActiveUser getInstance() {
        if (ActiveUser.instance == null) {
            ActiveUser.instance = new ActiveUser();
        }
        return ActiveUser.instance;
    }

    public boolean userIsSignedIn() {
        return userStructure != null;
    }

    public UserStructure getUser() {
        return ActiveUser.userStructure;
    }

    public void setUserStructure(UserStructure userStructure) {
        ActiveUser.userStructure = userStructure;
    }

    public void clearUser() {
        ActiveUser.userStructure = null;
    }
}
