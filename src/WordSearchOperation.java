import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;
import java.sql.SQLException;

/**
 * Creating a class for Spawn thread
 * Declaring the filepath variable with private access modifier
 * And creating a constructor for the filepath to access it from the other class
 */
public class WordSearchOperation extends Thread {
    private final String filepath;
    private final String keyWordToSearch;

    public WordSearchOperation(String filepath, String keyWordToSearch) {
        this.filepath = filepath;
        this.keyWordToSearch = keyWordToSearch;
    }

    /*
    Accessing the contents present in the file and storing it in the variable fileContent and
    Check is If file is Empty or Not
    Replacing the Special characters with the space
    Calling a method tokenAndKeyWordSearch.
     */

    public void run() {
        int wordOccurrence = 0;
        String fileContent;
        try {
            fileContent = Files.readString(Path.of(filepath));
            if (fileContent.isEmpty()) {
                System.out.println("File is Empty");
                return;
            }
            fileContent = fileContent.replaceAll(Constants.RegixPattern, Constants.SingleSpace);
            tokenAndKeyWordSearch(fileContent, wordOccurrence);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Reading the keyword to check in the file and Storing it in the variable keyWordToSearch
    Checking if the Keyword present in the file or not And displaying it.
    Creating an object for WordSearchHelper and calling a method dataBaseStorage from that class.
    Storing the results in DataBase.
     */

    public void tokenAndKeyWordSearch(String fileContent, int wordOccurrence) throws SQLException {
        String theResult;
        String errorMessage;
        StringTokenizer fileContentTokenizer = new StringTokenizer(fileContent);
        while (fileContentTokenizer.hasMoreTokens()) {
            if (keyWordToSearch.equalsIgnoreCase(fileContentTokenizer.nextToken())) {
                wordOccurrence++;
            }
        }
        if (wordOccurrence > 0) {
            theResult = Constants.ResultSuccess;
            errorMessage = Constants.SingleSpace;
            System.out.println("The Searched Word found and it is repeated " + wordOccurrence + " times in the file");
        } else {
            theResult = Constants.ResultError;
            errorMessage = Constants.WordErrorMessage;
            System.out.println("The Searched Word Not found");
        }
        WordSearchToDataBase wordSearchDataBase = new WordSearchToDataBase();
        wordSearchDataBase.dataBaseStorage(filepath, keyWordToSearch, theResult, wordOccurrence, errorMessage);
    }
}
