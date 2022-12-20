import java.io.File;
import java.sql.SQLException;

/**
 * Getting the file path from the command prompt.
 * Calling fileExtensionCheck method for checking the file path extension.
 */
public class FileSearch {
    public static void main(String[] args) throws Exception {
        if (args.length == Constants.UserArgumentNumber) {
            String filePath = args[0];
            String keyWordToSearch = args[1];
            fileExtensionCheck(filePath, keyWordToSearch);
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
        if (filePath.endsWith(Constants.TxtExtension) || filePath.endsWith(Constants.JsonExtension)) {
            processFile(filePath, keyWordToSearch);
        } else {
            System.out.println("The given File is not in '" + Constants.TxtExtension + "' or '" + Constants.JsonExtension + "' format");
            try {
                WordSearchToDataBase wordSearchDataBase = new WordSearchToDataBase();
                wordSearchDataBase.dataBaseStorage(filePath, keyWordToSearch, Constants.ResultError, Constants.InitialWordCount, Constants.FileExtensionErrorMessage);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            WordSearchOperation performOperation = new WordSearchOperation(filePath, keyWordToSearch);
            performOperation.start();
        } else {
            WordSearchToDataBase wordSearchDataBase = new WordSearchToDataBase();
            wordSearchDataBase.dataBaseStorage(filePath, keyWordToSearch, Constants.ResultError, Constants.InitialWordCount, Constants.FilePathErrorMessage);
            System.out.println("File Does Not Exists In the System");
        }
    }
}
