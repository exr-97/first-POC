import java.io.File;

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
            fileExtensionCheck(filePath, keyWordToSearch);
        } else {
            System.out.println("Please pass the Arguments");
        }
    }

    /*
    Checking File Extension of the filePath whether '.txt' or '.json' file.
    And Calling processFile method for searching the file in system.
     */
    public static void fileExtensionCheck(String filePath, String keyWordToSearch) {
        if (filePath.endsWith(".txt") || filePath.endsWith(".json")) {
            processFile(filePath, keyWordToSearch);
        } else {
            System.out.println("The given File is not in '.txt' or '.json' format");
        }
    }

    /*
    Assigning the file path to File and checking file is Present in the system or not
    */
    public static void processFile(String filepath, String keyWordToSearch) {
        System.out.println("Searching File...");
        File file = new File(filepath);
        if (file.exists()) {
            System.out.println("The File exists");
            PerformWordSearchOperation performOperation = new PerformWordSearchOperation(filepath, keyWordToSearch);
            performOperation.start();
        } else {
            System.out.println("File Does Not Exists In the System");
        }
    }
}
