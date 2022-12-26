import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Creating the connection by using jdbc.
 * Checking if table ia already present or not, if present we are inserting the values.
 * If table not present Creating a table with some columns which is the data.
 * Calling method createNewTableAndInsertValues to create a new table and insert the values in it.
 */
class WordSearchToDataBase {
    String driverClass = "com.mysql.cj.jdbc.Driver";
    String sqlUrl = "jdbc:mysql://localhost:3306/WordSearchPoc1";
    String userName = "root";
    String dbPassword = "Armyairforce@1";
    String dateAndTimeFormat = "dd/MM/yyyy HH:mm:ss";
    String createTable = "create table audit (PathToTheFile varchar(100), SearchedWord varchar(45), DateAndTimeOfSearch varchar(45), Result varchar(45), WordCount int, ErrorMessage varchar(100))";

    /*
    Calling connectionToDatabase method to load the driver and establish the connection.
    Checking if the Table audit is already exists, if exists we are inserting the values into the table.
    And closing the connection.
    If table is not present then calling createNewTableAndInsertValues method so to creat a new table and insert the values.
     */
    public void insertDataToDatabase(String filePath, String wordSearched, String theResult, int wordCount, String errorMessage) throws SQLException {
        Connection connectionToDataBase = null;
        Statement statementQueries;
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(this.dateAndTimeFormat);
        LocalDateTime now = LocalDateTime.now();
        String presentDateAndTime = dateTimeFormat.format(now);
        try {
            connectionToDataBase = connectToDataBase();
            statementQueries = connectionToDataBase.createStatement();
            DatabaseMetaData checkIfTableIsThere = connectionToDataBase.getMetaData();
            ResultSet tables = checkIfTableIsThere.getTables(null, null, Constants.AUDIT, null);
            if (tables.next()) {
                statementQueries.execute(insertValuesToTable(filePath, wordSearched, presentDateAndTime, theResult, wordCount, errorMessage));
            } else {
                createNewTableAndInsertValues(filePath, wordSearched, presentDateAndTime, theResult, wordCount, errorMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(connectionToDataBase).close();
        }
    }

    /*
    Calling connectionToDatabase method to load the driver and establish the connection.
    Creating a new table and insert the values into new table.
     */
    private void createNewTableAndInsertValues(String filePath, String wordSearched, String presentDateAndTime, String theResult, int wordCount, String errorMessage) throws SQLException {
        Connection connectionToDataBase = connectToDataBase();
        try {
            Statement statementQueries = connectionToDataBase.createStatement();
            statementQueries.execute(this.createTable);
            statementQueries.execute(insertValuesToTable(filePath, wordSearched, presentDateAndTime, theResult, wordCount, errorMessage));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(connectionToDataBase).close();
        }
    }

    /*
    Loading the Driver for MySQL and Establishing the connection.
     */
    private Connection connectToDataBase() throws SQLException {
        Connection connectionToDatabase = null;
        try {
            Class.forName(this.driverClass);
            connectionToDatabase = DriverManager.getConnection(this.sqlUrl, this.userName, this.dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectionToDatabase;
    }

    /*
    Inserting the values into the table.
     */
    private String insertValuesToTable(String filePath, String wordSearched, String presentDateAndTime, String theResult, int wordCount, String errorMessage) {
        return MessageFormat.format("INSERT INTO AUDIT VALUES({0},{1},{2},{3},{4},{5})", "'" + filePath + "'", "'" + wordSearched + "'", "'" + presentDateAndTime + "'", "'" + theResult + "'", "'" + wordCount + "'", "'" + errorMessage + "'");
    }
}
