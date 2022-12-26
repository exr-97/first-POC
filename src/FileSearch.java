import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Getting the file path from the command prompt.
 * Calling fileExtensionCheck method for checking the file path extension.
 */
public class FileSearch {

    public static void main(String[] args) throws Exception {
        if (args.length == Constants.USER_ARGUMENT_NUMBER) {
            String filePath = args[0];
            String keyWordToSearch = args[1];
            FileSearch fileSearch = new FileSearch();
            fileSearch.fileExtensionCheck(filePath, keyWordToSearch);
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
    public void fileExtensionCheck(String filePath, String keyWordToSearch) throws Exception {
        if (filePath.endsWith(Constants.TXT_EXTENSION) || filePath.endsWith(Constants.JSON_EXTENSION)) {
            processFile(filePath, keyWordToSearch);
        } else {
            System.out.println(Constants.FILE_EXTENSION_ERROR_MESSAGE);
            try {
                WordSearchToDataBase wordSearchDataBase = new WordSearchToDataBase();
                wordSearchDataBase.insertDataToDatabase(filePath, keyWordToSearch, Constants.RESULT_ERROR, Constants.INITIAL_WORD_COUNT, Constants.FILE_EXTENSION_ERROR_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    Assigning the file path to File and checking file is Present in the system or not.
    Accessing the values from the Child thread and calling displayResults method to print the Results.
    If file is not present in System, Creating an object for WordSearchHelper and calling a method dataBaseStorage from that class.
    Storing the results in DataBase.
    */
    public void processFile(String filePath, String keyWordToSearch) throws Exception {
        System.out.println("Searching File...");
        File file = new File(filePath);
        WordSearchOperation performOperation = new WordSearchOperation(filePath, keyWordToSearch);
        if (file.exists()) {
            System.out.println("The File exists");
            ExecutorService threadPool = Executors.newFixedThreadPool(1);
            Future<Integer> welcomeChildThread = threadPool.submit(new WordSearchOperation(filePath, keyWordToSearch));
            int wordOccurrence = welcomeChildThread.get();
            performOperation.displayResults(wordOccurrence);
            threadPool.close();
        } else {
            WordSearchToDataBase wordSearchDataBase = new WordSearchToDataBase();
            wordSearchDataBase.insertDataToDatabase(filePath, keyWordToSearch, Constants.RESULT_ERROR, Constants.INITIAL_WORD_COUNT, Constants.FILE_PATH_ERROR_MESSAGE);
            System.out.println(Constants.FILE_PATH_ERROR_MESSAGE);
        }
    }
}
