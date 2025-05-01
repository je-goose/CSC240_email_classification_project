import DataHandling.ProcessedDataSet;
import MModel.KNNModel;

import java.util.Map;

/**
 * Project name: email_classification_project
 * Package: PACKAGE_NAME
 * Author: Jae Elizabeth Giesen
 * Date: 4/30/2025
 * Purpose:
 * Modified on:
 */
public class TestModel {
    public static void main(String[] args) {
        // Create a ProcessedDataSet object
        String filename = "./spam_or_not_spam.csv";
        ProcessedDataSet processedDataSet = new ProcessedDataSet(filename, 0.7);

        // Create a KNNModel object
        int k = 3;
        KNNModel knnModel = new KNNModel(processedDataSet, k);
        System.out.println("KNN model created with k = " + k);

        // Train the model
        knnModel.train();
        System.out.println("Training model...");

        // Test the model with a sample feature vector
        Map<String, Integer> featureVector = processedDataSet.getValidationSet().getField(0).getVocabularyVector();
        boolean result = knnModel.classifyKNN(featureVector);
        System.out.println("Classification result: " + result);
    }
}
