package muck.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import muck.server.database.Database;

public class DataBaseUserTest {
    private static final Logger logger = LogManager.getLogger(DataBaseUserTest.class);

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
