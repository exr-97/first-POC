import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;
import java.sql.SQLException;

/**
 * Creating a class for Spawn thread
 * Declaring the filepath variable with private access modifier
 * And creating a constructor for the filepath to access it from the other class
 */
public class PerformWordSearchOperation extends Thread {
    private final String filepath;
    private final String keyWordToSearch;

    public PerformWordSearchOperation(String filepath, String keyWordToSearch) {
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
            fileContent = fileContent.replace("[^a-zA-Z0-9@-]", " ");
            tokenAndKeyWordSearch(fileContent, wordOccurrence);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Reading the keyword to check in the file and Storing it in the variable keyWordToSearch
    Checking if the Keyword present in the file or not And displaying it.
    Creating an object for WordSearchHelper and calling a method dataBaseStorage from that class.
    Storing the results in DataBase.
     */

    public void tokenAndKeyWordSearch(String fileContent, int wordOccurrence) throws SQLException {
        StringTokenizer fileContentTokenizer = new StringTokenizer(fileContent);
        while (fileContentTokenizer.hasMoreTokens()) {
            if (keyWordToSearch.equalsIgnoreCase(fileContentTokenizer.nextToken())) {
                wordOccurrence++;
            }
        }
        WordSearchHelper dataBaseHelper = new WordSearchHelper();
        if (wordOccurrence > 0) {
            dataBaseHelper.dataBaseStorage(filepath, keyWordToSearch, "Success", wordOccurrence, "");
            System.out.println("The Searched Word found and it is repeated " + wordOccurrence + " times in the file");
        } else {
            dataBaseHelper.dataBaseStorage(filepath, keyWordToSearch, "Error", wordOccurrence, "Word Not Found");
            System.out.println("The Searched Word Not found");
        }
    }
}
