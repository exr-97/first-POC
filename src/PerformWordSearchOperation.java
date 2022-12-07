import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Creating a class for Spawn thread
 * Declaring the filepath variable with private access modifier
 * And creating a constructor for the filepath to access it from the other class
 */
public class PerformWordSearchOperation extends Thread {
    private final String filepath;

    public PerformWordSearchOperation(String filepath) {
        this.filepath = filepath;
    }

    /*
    Accessing the contents present in the file and storing it in the variable fileContent and
    Check is If file is Empty or Not
    Replacing the Special characters with the space
    Reading the keyword to check in the file and Storing it in the variable keyWordToSearch
    Checking if the Keyword present in the file or not.
     */
    public void run() {
        int wordOccurrence = 0;
        Scanner scanner = new Scanner(System.in);
        String fileContent;
        try {
            fileContent = Files.readString(Path.of(filepath));
            if (fileContent.isEmpty()) {
                System.out.println("File is Empty");
                return;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileContent = fileContent.replaceAll("[^a-zA-Z0-9@-]", " ");
        System.out.println("Enter the word to search");
        String keyWordToSearch = scanner.next();
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