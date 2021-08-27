package muck.server.database;

/**
 * Specifies a column in a database table and generates the sql string to create it
 *
 * ctype (charType enum) is only used for char and is optional
 * size is only used for char and varchar, with default of 10 (bytes)
 */
public class DatabaseColumnDef {
    private enum types {BIGINT,BINARY,BLOB,BOOLEAN,CHAR,CLOB,DATE,DECIMAL,
        DOUBLE,FLOAT,INTEGER,LONGVARBINARY,LONGVARCHAR,
        NUMERIC,REAL,SMALLINT,SQLXML,TIME,TIMESTAMP,VARBINARY,VARCHAR}
    public enum cType {NOTYPE, BYTE, CHAR}
    public String columnName;
    public types columnType;
    public cType charType = cType.NOTYPE;
    public String fkTable;
    public boolean notNull = true;
    public boolean primaryKey = false;
    public boolean unique = false;
    public boolean foreignKey = false;
    public String check; //not implemented
    public int size = 10;

    /**
     * Generates the SQL string for a column in the Create Table SQL statement
     * @return Column definition for CreateTable SQL statement
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(columnName);
        sb.append(" ");
        sb.append(colType());
        if (notNull){
            sb.append(" NOT NULL");
        }
        if (unique){
            sb.append(" UNIQUE");
        }

        return sb.toString();
    }

    /**
     * Generates sql string for the column type definition
     * @return column type definition. eg VARCHAR (10)
     */
    private String colType(){
        StringBuilder bstr = new StringBuilder();
       /* BIGINT,BINARY,BLOB,BOOLEAN,CHAR,CLOB,DATE,DECIMAL,
                DOUBLE,FLOAT,INTEGER,LONGVARBINARY,LONGVARCHAR,
                NUMERIC,REAL,SMALLINT,SQLXML,TIME,TIMESTAMP,VARBINARY,VARCHAR};*/
        switch (columnType) {
            //Unique case for char and varchar
            case CHAR:
            case VARCHAR:
                bstr.append(String.format("CHAR(%d", size));
                if ((charType != cType.NOTYPE) && (charType == cType.CHAR)) {
                    bstr.append(charType);
                }
                bstr.append(")");
                // Default string for all other cases
            default: bstr.append(columnType);
        }
        return bstr.toString();
    }
}
