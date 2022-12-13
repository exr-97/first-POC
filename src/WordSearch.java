import java.io.File;
import java.sql.SQLException;

/**
 * Getting the file path from the command prompt.
 * Calling fileExtensionCheck method for checking the file path extension.
 */
public class WordSearch {
    public static final int userArgumentNumber = 2;

    public static void main(String[] args) {
        if (args.length == userArgumentNumber) {
            String filePath = args[0];
            String keyWordToSearch = args[1];
            try {
                fileExtensionCheck(filePath, keyWordToSearch);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Please pass the Arguments");
        }
    }

    /*
    Checking File Extension of the filePath whether '.txt' or '.json' file.
    And Calling processFile method for searching the file in system.
    If format of file is not supported, Creating an object for WordSearchHelper and calling a method dataBaseStorage from that class.
    Storing the results in DataBase.
     */
    public static void fileExtensionCheck(String filePath, String keyWordToSearch) throws SQLException {
        if (filePath.endsWith(".txt") || filePath.endsWith(".json")) {
            processFile(filePath, keyWordToSearch);
        } else {
            WordSearchHelper dataBaseHelper = new WordSearchHelper();
            dataBaseHelper.dataBaseStorage(filePath, keyWordToSearch, "Error", 0, "Format of the File is not Supported");
            System.out.println("The given File is not in '.txt' or '.json' format");
        }
    }

    /*
    Assigning the file path to File and checking file is Present in the system or not.
    If file is not present in System, Creating an object for WordSearchHelper and calling a method dataBaseStorage from that class.
    Storing the results in DataBase.
    */
    public static void processFile(String filePath, String keyWordToSearch) throws SQLException {
        System.out.println("Searching File...");
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("The File exists");
            PerformWordSearchOperation performOperation = new PerformWordSearchOperation(filePath, keyWordToSearch);
            performOperation.start();
        } else {
            WordSearchHelper dataBaseHelper = new WordSearchHelper();
            dataBaseHelper.dataBaseStorage(filePath, keyWordToSearch, "Error", 0, "File Dose not Exists in the System");
            System.out.println("File Does Not Exists In the System");
        }
    }
}
