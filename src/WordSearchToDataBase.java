import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Creating the connection by using jdbc.
 * Creating a table with some columns which is the data.
 * Calling method processTable if table is not present in the Database.
 */
class WordSearchToDataBase {
    String driverClass = "com.mysql.cj.jdbc.Driver";
    String sqlUrl = "jdbc:mysql://localhost:3306/WordSearchPoc1";
    String userName = "root";
    String dbPassword = "Armyairforce@1";
    String dateAndTimeFormat = "dd/MM/yyyy HH:mm:ss";
    String createTable = "create table audit (PathToTheFile varchar(100), SearchedWord varchar(45), DateAndTimeOfSearch varchar(45), Result varchar(45), WordCount int, ErrorMessage varchar(100))";

    public void dataBaseStorage(String filePath, String wordSearched, String theResult, int wordCount, String errorMessage) throws SQLException {
        Connection connectionToDataBase = null;
        Statement statementQueries;
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(this.dateAndTimeFormat);
        LocalDateTime now = LocalDateTime.now();
        String presentDateAndTime = dateTimeFormat.format(now);
        try {
            connectionToDataBase = connectionToDatabase();
            statementQueries = connectionToDataBase.createStatement();
            DatabaseMetaData checkIfTableIsThere = connectionToDataBase.getMetaData();
            ResultSet tables = checkIfTableIsThere.getTables(null, null, Constants.AUDIT, null);
            if (tables.next()) {
                String query = MessageFormat.format("INSERT INTO audit VALUES({0},{1},{2},{3},{4},{5})", "'" + filePath + "'", "'" + wordSearched + "'", "'" + presentDateAndTime + "'", "'" + theResult + "'", "'" + wordCount + "'", "'" + errorMessage + "'");
                statementQueries.execute(query);
            } else {
                processCreateTable(filePath, wordSearched, presentDateAndTime, theResult, wordCount, errorMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(connectionToDataBase).close();
        }
    }

    /*
    Creating the connection by using jdbc.
    Creating a new table and Storing the data in DataBase.
     */
    private void processCreateTable(String filePath, String wordSearched, String presentDateAndTime, String theResult, int wordCount, String errorMessage) throws SQLException {
        Connection connectionToDataBase = connectionToDatabase();
        try {
            Statement statementQueries = connectionToDataBase.createStatement();
            statementQueries.execute(this.createTable);
            String query = MessageFormat.format("INSERT INTO audit VALUES({0},{1},{2},{3},{4},{5})", "'" + filePath + "'", "'" + wordSearched + "'", "'" + presentDateAndTime + "'", "'" + theResult + "'", "'" + wordCount + "'", "'" + errorMessage + "'");
            statementQueries.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(connectionToDataBase).close();
        }
    }
/*
Loading the Driver for MySQL and Establishing the connection.
 */
    private Connection connectionToDatabase() throws SQLException {
        Connection connectionToDatabase = null;
        try {
            Class.forName(this.driverClass);
            connectionToDatabase = DriverManager.getConnection(this.sqlUrl, this.userName, this.dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectionToDatabase;
    }
}
