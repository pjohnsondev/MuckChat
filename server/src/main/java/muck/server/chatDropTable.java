package muck.server;

import muck.server.database.Database;

import javax.xml.crypto.Data;
import java.sql.SQLException;
/**
 * Class containing methods to drop tables from the DB. Inherits from Muck database.
 * Original Author: Ryan Birch (rbirch4@myune.edu.au) as a part of Low Expectations.
 */
public class chatDropTable extends Database {

    protected void dropGroupChat() {
        //Call to the DB to drop the group chat table.
    }

    public void dropChatLog() {

    }
}
