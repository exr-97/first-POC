import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

/**
 * Creating a class for Spawn thread
 * Declaring the filepath variable with private access modifier
 * And creating a constructor for the filepath to access it from the other class
 */
public class WordSearchOperation implements Callable<Integer> {
    private final String filepath;
    private final String keyWordToSearch;
    private int wordOccurrence = 0;

    public WordSearchOperation(String filepath, String keyWordToSearch) {
        this.filepath = filepath;
        this.keyWordToSearch = keyWordToSearch;
    }

    /*
    Used a callable interface to return the child thread values to main thread.
    In call method calling the child thread.
     */
    @Override
    public Integer call() {
        run();
        return wordOccurrence;
    }

    /*
    Accessing the contents present in the file and storing it in the variable fileContent and
    Check is If file is Empty or Not
    Replacing the Special characters with the space
    Calling a method tokenAndKeyWordSearch.
    */
    public void run() {
        String fileContent;
        try {
            fileContent = Files.readString(Path.of(filepath));
            if (fileContent.isEmpty()) {
                System.out.println("File is Empty");
                return;
            }
            tokenAndKeyWordSearch(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Reading the keyword to check in the file and Storing it in the variable keyWordToSearch
    Checking if the Keyword present in the file or not.
     */
    public void tokenAndKeyWordSearch(String fileContent) {
        fileContent = fileContent.replaceAll(Constants.REGIX_PATTERN, Constants.SINGLE_SPACE);
        StringTokenizer fileContentTokenizer = new StringTokenizer(fileContent);
        while (fileContentTokenizer.hasMoreTokens()) {
            if (keyWordToSearch.equalsIgnoreCase(fileContentTokenizer.nextToken())) {
                wordOccurrence++;
            }
        }
    }

    /*
    Displaying the Final Results and Storing the results in DataBase.
     */
    public void displayResults(int wordOccurrence) throws Exception {
        String theResult;
        String errorMessage;
        if (wordOccurrence > 0) {
            theResult = Constants.RESULT_SUCCESS;
            errorMessage = Constants.SINGLE_SPACE;
            System.out.println("The Searched Word found and it is present " + wordOccurrence + " times in the file");
        } else {
            theResult = Constants.RESULT_ERROR;
            errorMessage = Constants.WORD_ERROR_MESSAGE;
            System.out.println(Constants.WORD_ERROR_MESSAGE);
        }
        WordSearchToDataBase wordSearchDataBase = new WordSearchToDataBase();
        wordSearchDataBase.dataBaseStorage(filepath, keyWordToSearch, theResult, wordOccurrence, errorMessage);
    }
}
