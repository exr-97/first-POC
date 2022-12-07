import java.io.File;

/**
 * Getting the file path from the command prompt
 * calling the filePath method for searching the file in system
 */
public class WordSearch {
    public static void main(String[] args) throws Exception {
        String filepath = args[0];
        WordSearch wordsearch = new WordSearch();
        wordsearch.filePath(filepath);
    }

    /*hgfjhgshkjhdkjhcdhckjhdkjdhjdh
    Assigning the file path to File and checking file is Present in the system or not
    */
    public void filePath(String filepath) throws Exception {
        System.out.println("Searching File...");
        Thread.sleep(500);
        File file = new File(filepath);
        if (file.exists()) {
            System.out.println("The File exists");
            PerformWordSearchOperation performOperation = new PerformWordSearchOperation(filepath);
            performOperation.start();
        } else {
            System.out.println("File Does Not Exists In the System");
        }
    }
}
