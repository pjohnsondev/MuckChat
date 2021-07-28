package muck.server.models;

import muck.server.database.MuckDatabase;

public class Model {
    private MuckDatabase db;
    public Model() {
        db = new MuckDatabase();
    }
}