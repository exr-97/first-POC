import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

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
            fileContent = fileContent.replaceAll("[^a-zA-Z0-9@-]", " ");
            tokenAndKeyWordSearch(fileContent, wordOccurrence);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Reading the keyword to check in the file and Storing it in the variable keyWordToSearch
    Checking if the Keyword present in the file or not And displaying it.
     */

    public void tokenAndKeyWordSearch(String fileContent, int wordOccurrence) {
        StringTokenizer fileContentTokenizer = new StringTokenizer(fileContent);
        while (fileContentTokenizer.hasMoreTokens()) {
            if (keyWordToSearch.equalsIgnoreCase(fileContentTokenizer.nextToken())) {
                wordOccurrence++;
            }
        }
        if (wordOccurrence > 0) {
            System.out.println("The Searched Word found and it is repeated " + wordOccurrence + " times in the file");
        } else {
            System.out.println("The Searched Word Not found");
        }
    }
}
