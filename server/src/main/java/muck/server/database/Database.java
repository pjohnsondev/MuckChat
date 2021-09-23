package muck.server.database;

import java.sql.*;

/**
 * Database class connects to the database and provides sql query execution on the database.
 * The queries are general in nature, allowing other classes a common api to interface with the database.
 */
abstract public class Database {
    protected String dbName;
    protected String connectionString;

    private Connection conn;
    private PreparedStatement statement;

    protected static Database INSTANCE;

    
    // This function was developed using this tutorial: 
    // https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
    /**
     * Establishes a connection to the database.
     * <br>
     * You should run this in the constructor method of a child class after 
     * providing the database name and connection string.f
     * <br>
     * <br>
     * <code>
     *  <pre>
     *      public class ExampleDatabase extends Database {
     *          public ExampleDatabase () {
     *              dbName = "exampledb";
     *              connectionString = String.format("jdbc:derby:%s;create=true", dbName);
     *              connect();
     *          }
     *      }
     *  </pre>
     * </code>
     */
    protected void connect() {
        try {
            if (!this.databaseIsConnected()) {
                conn = DriverManager.getConnection(connectionString);
                System.out.println("Connection to database established");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Run this method to close a connection to the database.
     */
    public void closeConnection() {
        try {
            if (conn != null) {
                /*
                conn.close();
            }
            if (INSTANCE!=null){
            */
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
                INSTANCE =null;
            }



        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            if (ex.getSQLState().equals("XJ015")) {
                System.out.println("Derby shutdown normally");
            } else {
                // could not shut down the database
                // handle appropriately
                System.out.println("Derby shutdown was not achieved");
            }
        }
    }

    /**
     * Checks that the database is connected
     *
     * @return  Boolean telling you whether the database is connected or not
     */
    public Boolean databaseIsConnected() {
        return conn != null;
    }

    /**
     * Your sql query must be written in Apache Derby compliant SQL. See docs 
     * here: https://db.apache.org/derby/manuals/
     * <br>
     * e.g. <code>"SELECT * FROM table"</code>
     * <br>
     * Note that there is no ";" to finish the line unlike in other versions 
     * of SQL.
     * <br>
     * If you want to pass into your statement use "?"s to indicate the variable.
     * e.g. <code>"INSERT INSERT INTO test_table (id, some_text, more_text, floating_point) VALUES (?, ?, ?, ?)"</code>
     * <br>
     * Then bind the variable to the SQL query using its position in the query
     * with the bind method corresponding to the variable's type. 
     * <br>
     * For example the "id" value is the first "?" and it's an int. So if your 
     * variable was 69 you would use:
     * <code> bindInt(1, 69) </code>
     * 
     * @param sql   An Apache Derby compliant SQL query.
     */
    public void query(String sql) {

        try {
            statement = conn.prepareStatement(sql);
        } catch (SQLException exception) {
            System.out.println("Failed to connect to the database");
            System.out.println(exception.getMessage());
        }
    }

    /**
     * This will mostly be used to get the results of a SELECT query.
     * This method is functionally the same as executeQuery from jbdc, read the 
     * docs here: https://docs.oracle.com/javase/7/docs/api/java/sql/Statement.html#executeQuery(java.lang.String)
     * To see how to use a result set please consult the java docs here: 
     * https://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
     * 
     * 
     * @return A ResultSet containing all the rows from your query
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public ResultSet getResultSet() throws SQLException {
        return statement.executeQuery();
    }

    /**
     * Used for INSERT, UPDATE and DELETE statements.
     * It is functionally running the executeUpdate function on
     * the statement attribute: https://docs.oracle.com/javase/7/docs/api/java/sql/Statement.html#executeUpdate(java.lang.String)
     * 
     * But it contains some extra functionallity for convenience.
     * 
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void executeUpdate() throws SQLException {
        statement.executeUpdate();
        conn.commit();

        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
    /**
     * Used for any type of SQL statement, including statements that return multiple results.
     * For more information, refer to: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html#execute()
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void executeInsert() throws SQLException {
        statement.execute();
        conn.commit();

        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * Used to confirm if a table exists in the database.
     * For more information, refer to: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html#execute()
     *
     * @param tableName The table name to check for existence within the database
     *
     * @return Returns true if the table exists, false if it does not
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public Boolean tableExists(String tableName) throws SQLException {
        // found this solution here: https://www.baeldung.com/jdbc-check-table-exists
        tableName = tableName.toUpperCase(); // Derby only understands table names when they are capitalised
        query(
            "SELECT COUNT(*) FROM SYS.SYSTABLES WHERE TABLENAME=?"
        );
        bindString(1, tableName);
        ResultSet resultSet = getResultSet();
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }

    /**
     * Used to remove a table from the database.
     * For more information, refer to: https://db.apache.org/derby/docs/10.2/ref/rrefsqlj34148.html
     *
     * @param tableName The table name to remove from the database
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void dropTable(String tableName) throws SQLException {
        if (tableExists(tableName)) {
            // binding not used because jbdc doesn't support binding in drop statements. Users shouldn't have access to this function either way
            // Generate Prepared Statement to execute, which will drop the table.
            query(
                String.format("DROP TABLE %s", tableName)
            );
            statement.execute();
            conn.commit();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                }
            }    
        }
    }

    /**
     * Checks to see if a table exists and creates it if it does not.
     *
     * @param tableName The table name to check for existence within the database
     * @param sql       The sql query used to create the table
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void createTableIfNotExists(String tableName, String sql) throws SQLException{
        if (!tableExists(tableName)) {
            //generate the Create Table Prepared Statement and execute statement.
            query(sql);
            executeUpdate();    
        }

    }

    /**
     * Sets the designated parameter to the given Java int value.
     * The driver converts this to an SQL INTEGER value when it sends it to the database.
     * Description taken from: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html#setInt(int,%20int)
     *
     * @param pIndex the parameter index starts at 1
     * @param parameter the parameter's new value to update
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    // PREPARED STATEMENT BINDINGS
    public void bindInt(int pIndex, int parameter) throws SQLException {
        statement.setInt(pIndex, parameter);
    }

    /**
     * Sets the designated parameter to the given Java double value.
     * The driver converts this to an SQL DOUBLE value when it sends it to the database.
     * Description taken from: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html#setDouble(int,%20double)
     *
     * @param pIndex the parameter index starts at 1
     * @param parameter the parameter's new value to update
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void bindDouble(int pIndex, double parameter) throws SQLException{
        statement.setDouble(pIndex, parameter);
    }

    /**
     * Sets the designated parameter to the given Java String value.
     * The driver converts this to an SQL VARCHAR or LONGVARCHAR value
     * (depending on the argument's size relative to the driver's limits on VARCHAR values)
     * when it sends it to the database.
     * Description taken from: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html#setString(int,%20java.lang.String)
     *
     * @param pIndex the parameter index starts at 1
     * @param parameter the parameter's new value to update
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void bindString(int pIndex, String parameter) throws SQLException{
        statement.setString(pIndex, parameter);
    }

    /**
     * Sets the designated parameter to the given Java String value.
     * The driver converts this to an SQL VARCHAR or LONGVARCHAR value
     * (depending on the argument's size relative to the driver's limits on VARCHAR values)
     * when it sends it to the database.
     * Description taken from: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html#setString(int,%20java.lang.String)
     * This differs from bindString because the new parameter value is automatically set to NULL
     *
     * @param pIndex the parameter index starts at 1
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void bindNull(int pIndex) throws SQLException {
        statement.setString(pIndex, null);
    }

    /**
     * Sets the value of the designated parameter using the given object.
     * The second parameter must be of type Object; therefore, the java.lang equivalent objects should be used for built-in types.
     * Description taken from: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html#setObject(int,%20java.lang.Object)
     * For more detailed information, refer to the link above.
     *
     * @param pIndex the parameter index starts at 1
     * @param parameter the parameter's new value to update
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void bindObj(int pIndex, Object parameter) throws SQLException{
        statement.setObject(pIndex, parameter);
    }

    /**
     * Sets the designated parameter to the given Java array of bytes.
     * The driver converts this to an SQL VARBINARY or LONGVARBINARY
     * (depending on the argument's size relative to the driver's limits on VARBINARY values)
     * when it sends it to the database.
     * Description taken from: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html#setBytes(int,%20byte[])
     * @param pIndex the parameter index starts at 1
     * @param parameter the parameter's new value to update
     *
     * @throws SQLException Provides information on database connection or other related errors. See: https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public void bindBytes(int pIndex, byte[] parameter) throws SQLException {
        statement.setBytes(pIndex, parameter);
    }

    public void bindDate(int pIndex, Date parameter) throws SQLException {
        statement.setDate(pIndex,parameter);
    }

    /*
    protected void finalize() throws Throwable {
        try {

            DriverManager.getConnection("jdbc:derby:;shutdown=true");

        } catch (SQLException ex) {
            if (ex.getSQLState().equals("XJ015")) {
                System.out.println("Derby shutdown normally");
            } else {
                // could not shut down the database
                // handle appropriately
                System.out.println("Derby shutdown was not achieved");
            }
        }
    }

     */
    //END PREPARED STATEMENT BINDINGS
}
