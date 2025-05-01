import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import DataHandling.*;

/**
 * Project name: email_classification_project
 * Package: PACKAGE_NAME
 * Author: Jae Elizabeth Giesen
 * Date: 4/30/2025
 * Purpose:
 * Modified on:
 */

/**
 * TestDataHandling is used to test the DataHandling classes, particularly the constructors.
 */
public class TestDataHandling {
    public static void main(String[] args) {

        //Scanner input = new Scanner(System.in);
        //System.out.println("Enter the filename for the test:");
        //String filename = input.nextLine();
        //input.close();
        String filename = "./spam_or_not_spam.csv";
        System.out.println("Running simple test...");
        simpleTest(filename);
        System.out.println("Running complex test...");
        complexTest(filename);
    }

    /**
     *  Simple test for the DataSet class. Constructs a DataSet from a file,
     *  retrieves a random entry, and prints the vocabulary vector + label.
     */
    public static void simpleTest(String filename) {
        DataSet dataSet = new DataSet(filename);

        Random rand = new Random();
        int randomIndex = rand.nextInt(dataSet.size());

        booleanTextEntry randomEntry = dataSet.getField(randomIndex);
        System.out.println("Random Entry no. " + randomIndex + ":");
        for (Map.Entry<String,Integer> entry : randomEntry.getVocabularyVector().entrySet()) {
            System.out.println(entry);
        }

        System.out.println("Random Entry label: " + randomEntry.getLabel());
        System.out.println("Simple test complete.");
    }

    /**
     * Complex test for the ProcessedDataSet class. Constructs a ProcessedDataSet from a file,
     * retrieves 5 random entries from each of the training, test, and validation sets, prints the
     * vocabulary vector + label for each entry.
     */
    public static void complexTest(String filename) {
        ProcessedDataSet processedDataSet = new ProcessedDataSet(filename, 0.7);
        Random rand = new Random();

        System.out.println("Training Set:");
        for (int i = 0; i < 5; i++) {
            int randomIndex = rand.nextInt(processedDataSet.getTrainingSet().size());
            booleanTextEntry randomEntry = processedDataSet.getTrainingSet().getField(randomIndex);
            System.out.println("\tRandom Entry no. " + randomIndex + ":");
            System.out.println("\t"+randomEntry.toString());
            System.out.println("\tRandom Entry label: " + randomEntry.getLabel());
            System.out.println("\tTest Entry size: " + randomEntry.getSizeOfVocabularyVector());
        }

        System.out.println("Training set test complete.");
        System.out.println("--------------------------------------------");
        System.out.println("Test Set:");
        for (int i = 0; i < 5; i++) {
            int randomIndex = rand.nextInt(processedDataSet.getTestSet().size());
            booleanTextEntry randomEntry = processedDataSet.getTestSet().getField(randomIndex);
            System.out.println("\tRandom Entry no. " + randomIndex + ":");
            System.out.println("\t"+randomEntry.toString());
            System.out.println("\tRandom Entry label: " + randomEntry.getLabel());
            System.out.println("\tTest Entry size: " + randomEntry.getSizeOfVocabularyVector());
        }

        System.out.println("Testing set test complete.");
        System.out.println("--------------------------------------------");
        System.out.println("Validation Set:");
        for (int i = 0; i < 5; i++) {
            int randomIndex = rand.nextInt(processedDataSet.getValidationSet().size());
            booleanTextEntry randomEntry = processedDataSet.getValidationSet().getField(randomIndex);
            System.out.println("\tRandom Entry no. " + randomIndex + ":");
            System.out.println("\t"+randomEntry.toString());
            System.out.println("\tRandom Entry label: " + randomEntry.getLabel());
            System.out.println("\tTest Entry size: " + randomEntry.getSizeOfVocabularyVector());
        }
        System.out.println("Validation set test complete.");
        System.out.println("-------------------------------------------");
        System.out.println("Complex test complete.");
    }
}
