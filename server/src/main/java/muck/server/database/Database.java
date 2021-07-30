package muck.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Database {
    protected String dbName;
    protected String connectionString;

    private Connection conn;
    private PreparedStatement statement;

    
    // This function was developed using this tutorial: 
    // https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
    /**
     * Establishes a connection to the database.
     * <br>
     * You should run this in the constructor method of a child class after 
     * providing the database name and connection string.
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
            conn = DriverManager.getConnection(connectionString);
            System.out.println("Connection to database established");
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
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
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
     * @throws SQLException
     */
    public ResultSet getResultSet() throws SQLException {
        return statement.executeQuery();
    }

    /**
     * Used for INSERT, UPDATE and DELETE statements.
     * It is functionally functionally running the executeUpdate function on
     * the statement attribute: https://docs.oracle.com/javase/7/docs/api/java/sql/Statement.html#executeUpdate(java.lang.String)
     * 
     * But it contains some extra functionallity for convenience.
     * 
     * @throws SQLException
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

    public void dropTable(String tableName) throws SQLException {
        if (tableExists(tableName)) {
            // binding not used because jbdc doesn't support binding in drop statements. Users shouldn't have access to this function either way
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
    // PREPARED STATEMENT BINDINGS
    public void bindInt(int pIndex, int parameter) {
        try {
            statement.setInt(pIndex, parameter);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public void bindDouble(int pIndex, double parameter) {
        try {
            statement.setDouble(pIndex, parameter);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public void bindString(int pIndex, String parameter) {
        try {
            statement.setString(pIndex, parameter);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public void bindNull(int pIndex) {
        try {
            statement.setString(pIndex, null);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void bindObj(int pIndex, Object parameter) {
        try {
            statement.setObject(pIndex, parameter);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public void bindBytes(int pIndex, byte[] parameter) {
        try {
            statement.setBytes(pIndex, parameter);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    //END PREPARED STATMENT BINDINGS
}
