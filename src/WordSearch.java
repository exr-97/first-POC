import java.io.File;

/**
 * Getting the file path from the command prompt
 * calling the filePath method for searching the file in system
 */
public class WordSearch {
    public static void main(String[] args) {
        if (args.length == 2) {
            String filepath = args[0];
            String keyWordToSearch = args[1];
            WordSearch wordsearch = new WordSearch();
            wordsearch.processFile(filepath, keyWordToSearch);
        } else {
            System.out.println("Please pass the Arguments");
        }
    }

    /*
    Assigning the file path to File and checking file is Present in the system or not
    */
    public void processFile(String filepath, String keyWordToSearch) {
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
