import MModel.*;
import DataHandling.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Project name: email_classification_project
 * Package: PACKAGE_NAME
 * Author: Jae Elizabeth Giesen
 * Date: 4/27/2025
 * Purpose:
 * Modified on:
 */
public class main {
    public static void main(String[] args) {
        String filepathOut = "./predictions.txt";
        String filepathIn = "./spam_or_not_spam.csv";

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the training proportion (0.0 - 1.0):");
        double trainProportion = input.nextDouble();
        if (trainProportion <= 0 || trainProportion >= 1) {
            System.out.println("Invalid training proportion. Please enter a value between 0.0 and 1.0.");
            return;
        }
        ProcessedDataSet processedDataSet = new ProcessedDataSet(filepathIn, 0.7);
        System.out.println("Enter the value of k for the KNN model:");
        int k = input.nextInt();
        if (k <= 0) {
            System.out.println("Invalid value for k. Please enter a positive integer.");
            return;
        }
        KNNModel knnModel = new KNNModel(processedDataSet, k);
        System.out.println("KNN model created with k = " + k);

        DataSet testSet = processedDataSet.getTestSet();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepathOut))) {

            for (int i = 0; i < testSet.size(); i++) {
                boolean result = knnModel.classifyKNN(testSet.getField(i).getVocabularyVector());
                writer.write("Entry " + i + ": " + result + "\n");
                writer.flush();
            }
            writer.write("\n");
            writer.write("Model evaluation results:\n");
            writer.write("Accuracy: " + knnModel.getAccuracy() + "%\n");
            writer.write("Precision: " + knnModel.getPrecision() + "\n");
            writer.write("Recall: " + knnModel.getRecall() + "\n");
            writer.write("Confusion Matrix: " + knnModel.getConfusionMatrix() + "\n");
            System.out.println("Predictions written to " + filepathOut);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        while (true) {
            System.out.println("Enter the index of the entry you want to classify:");
            int index = input.nextInt();
            boolean result = knnModel.classifyKNN(testSet.getField(index).getVocabularyVector());
            System.out.println("Classification result for entry " + index + ": " + result);
            System.out.println("Actual label: " + testSet.getField(index).getLabel());
            System.out.println("Do you want to classify another entry? (Y/N)");
            String choice = input.next();
            if (choice.equalsIgnoreCase("N")) {
                break;
            }
        }
    }
}
