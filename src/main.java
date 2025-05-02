import MModel.*;
import DataHandling.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
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
        ProcessedDataSet processedDataSet = new ProcessedDataSet(filepathIn, trainProportion);
        System.out.println("Enter the value of k for the KNN model:");
        int k = input.nextInt();
        if (k <= 0) {
            System.out.println("Invalid value for k. Please enter a positive integer.");
            return;
        }
        KNNModel knnModel = new KNNModel(processedDataSet, k);
        System.out.println("KNN model created with k = " + k);

        DataSet testSet = processedDataSet.getTestSet();

        System.out.println("Would you like to write the predictions and model performance to a file? (y/n)");
        String writeToFile = input.next();
        if (writeToFile.equalsIgnoreCase("y")) {
            System.out.println("Writing predictions to " + filepathOut);
            writeFile(filepathOut, processedDataSet, knnModel, k);
        }

        while (true) {
            System.out.println("Would you like to classify a new entry, classify an existing entry, or quit? (0,1,2)");
            String choice = input.next();
            if (choice.equals("0")) {
                System.out.println("Enter new email text:");
                input.nextLine(); // flush the buffer! headaches abound here
                String newEmailText = input.nextLine();
                queryUserNewEmail(newEmailText, knnModel);
                continue;
            } else if (choice.equals("1")) {
                queryUserTrainSet(testSet, knnModel);
                continue;
            } else if (choice.equals("2")) {
                System.out.println("Exiting...");
                input.close();
                break;
            } else {
                System.out.println("Invalid choice. Please enter 0, 1, or 2.");
                continue;
            }

        }

        input.close();
        System.out.println("Program terminated.");
    }

    public static void queryUserTrainSet(DataSet testSet, KNNModel knnModel) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the entry you want to classify:");
        int index = input.nextInt();
        boolean result = knnModel.classifyKNN(testSet.getField(index).getVocabularyVector());
        System.out.println("Classification result for entry " + index + ": " + result);
        System.out.println("Actual label: " + testSet.getField(index).getLabel());
    }

    public static void queryUserNewEmail(String strIn, KNNModel knnModel) {
        String[] newEmailParts = strIn.split(" ");
        Map<String, Integer> newEmailVector = new HashMap<>();
        for (String part : newEmailParts) {
            if (newEmailVector.containsKey(part)) {
                newEmailVector.put(part, newEmailVector.get(part) + 1);
            } else {
                newEmailVector.put(part, 1);
            }
        }
        boolean result = knnModel.classifyKNN(newEmailVector);
        System.out.println("Classification result: " + result);
    }

    public static void writeFile(String filepath, ProcessedDataSet pDS, KNNModel knnModel, int k) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {

            for (int i = 0; i < pDS.getTestSet().size(); i++) {
                boolean result = knnModel.classifyKNN(pDS.getTestSet().getField(i).getVocabularyVector());
                writer.write("Entry " + i + "\n\tPrediction: " + result + "\n");
                writer.write("\tActual: " + pDS.getTestSet().getField(i).getLabel() + "\n");
                writer.flush();
            }
            writer.write("\nModel characteristics:\n");
            writer.write("\tTraining set size: " + pDS.getTrainingSet().size() + "\n");
            writer.write("\tTest set size: " + pDS.getTestSet().size() + "\n");
            writer.write("\tk value: " + k + "\n");

            writer.write("\nModel evaluation results:\n");
            writer.write("\tAccuracy: " + knnModel.getAccuracy() + "\n");
            writer.write("\tPrecision: " + knnModel.getPrecision() + "\n");
            writer.write("\tRecall: " + knnModel.getRecall() + "\n");
            writer.write("\tConfusion Matrix:\n" + knnModel.getConfusionMatrix() + "\n");
            System.out.println("Predictions written to " + filepath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
