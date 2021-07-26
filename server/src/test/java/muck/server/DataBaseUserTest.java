package muck.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import muck.server.database.Database;

public class DataBaseUserTest {

    @Test
    public void dbCanConnect(){
        Database db = new Database();
        assertTrue(db.databaseIsConnected());
    }
    @Test
    public void dbUserAccessTes(){
        /*This test unit will ensure the connection to the database is working*/
    }

    @Test
    public void requestUserInformationTest(){
        /*This test unit will test incoming user information testing.*/
    }
}
