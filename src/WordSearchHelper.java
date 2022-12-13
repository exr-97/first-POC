import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Class created with the help MySQL
 * Creating the connection by using jdbc.
 * Creating a table with some columns which is the data.
 * Calling method processTable if table is not present in the Database.
 */
class WordSearchHelper {
    String driverClass = "com.mysql.cj.jdbc.Driver";
    String sqlUrl = "jdbc:mysql://localhost:3306/WordSearchPoc1";
    String userName = "root";
    String dbPassword = "Armyairforce@1";
    String dateAndTimeFormat = "dd/MM/yyyy HH:mm:ss";
    String creatTable = "create table audit(PathToTheFile varchar(100), SearchedWord varchar(45), DateAndTimeOfSearch varchar(45), Result varchar(45), WordCount int, ErrorMessage varchar(100))";

    public void dataBaseStorage(String filePath, String wordSearched, String theResult, int wordCount, String errorMessage) throws SQLException {
        Connection connectionToDataBase = null;
        Statement statementQueries;
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(this.dateAndTimeFormat);
        LocalDateTime now = LocalDateTime.now();
        String presentDateAndTime = dateTimeFormat.format(now);
        try {
            connectionToDataBase = DriverManager.getConnection(this.sqlUrl, this.userName, this.dbPassword);
            statementQueries = connectionToDataBase.createStatement();
            DatabaseMetaData checkIfTableIsThere = connectionToDataBase.getMetaData();
            ResultSet tables = checkIfTableIsThere.getTables(null, null, "audit", null);
            if (tables.next()) {
                statementQueries.execute("INSERT INTO audit VALUES ('" + filePath + "','" + wordSearched + "','" + presentDateAndTime + "','" + theResult + "'," + wordCount + ",'" + errorMessage + "')");
            } else {
                this.processTable(filePath, wordSearched, presentDateAndTime, theResult, wordCount, errorMessage);
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
    private void processTable(String filePath, String wordSearched, String presentDateAndTime, String theResult, int wordCount, String errorMessage) throws SQLException {
        Connection connectionToDataBase = null;
        try {
            Class.forName(this.driverClass);
            connectionToDataBase = DriverManager.getConnection(this.sqlUrl, this.userName, this.dbPassword);
            Statement st = connectionToDataBase.createStatement();
            st.execute(this.creatTable);
            st.execute("INSERT INTO audit VALUES ('" + filePath + "','" + wordSearched + "','" + presentDateAndTime + "','" + theResult + "'," + wordCount + ",'" + errorMessage + "')");

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } finally {
            Objects.requireNonNull(connectionToDataBase).close();
        }
    }
}