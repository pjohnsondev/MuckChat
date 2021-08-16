package muck.server.database;

import java.util.ArrayList;

public class DatabaseTools {
    /*
    "users", "CREATE TABLE users (id INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + " username VARCHAR(80) UNIQUE, password BLOB NOT NULL, salt BLOB NOT NULL)"
     */
    //This array list stores the column definitions.
    //The ID column does not need to be stored as it will be auto generated.
    ArrayList<DatabaseColumnDef> columns = new ArrayList<>();
    private StringBuilder ctStrBuilder = new StringBuilder("CREATE TABLE ");
    private StringBuilder pk;
    public String createTableSQL(String tName){
        ctStrBuilder.append(String.format("%s (%s_ID INTEGER UNIQUE GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)",
                        tName.toUpperCase(),tName.toUpperCase()));
        for (DatabaseColumnDef text: columns){
            ctStrBuilder.append(String.format(", %s",text.toString().toUpperCase()));
        }

        for (DatabaseColumnDef text: columns) {
            if (text.foreignKey) {
                ctStrBuilder.append(String.format("CONSTRAINT FK_%s FOREIGN KEY (%s) REFERENCES %s (%s)",
                        text.fkTable.toUpperCase(), text.columnName.toUpperCase(),
                        text.fkTable.toUpperCase(),text.columnName.toUpperCase()));
            }
        }

        ctStrBuilder.append(")");


        //This needs to be update with success of the creation
        return ctStrBuilder.toString();
    }

    public void addColumn(){
        columns.add(new DatabaseColumnDef());
    }
}
