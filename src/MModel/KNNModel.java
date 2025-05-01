package MModel;

import DataHandling.*;

import java.util.Map;

/**
 * Project name: email_classification_project
 * Package: MModel
 * Author: Jae Elizabeth Giesen
 * Date: 4/27/2025
 * Purpose:
 * Modified on:
 */
public class KNNModel extends MModel {
    protected int k;

    public KNNModel(ProcessedDataSet dataSetIn, int k) {
        this.trainingData = dataSetIn.getTrainingSet();
        this.testingData = dataSetIn.getTestSet();
        this.validationData = dataSetIn.getValidationSet();
        this.k = k;
    }

    @Override
    public void train() {

    }

    @Override
    public void evaluateModel() {
        // Implement the evaluation logic here
        System.out.println("Accuracy: " + getAccuracy());
        System.out.println("Precision: " + getPrecision());
        System.out.println("Confusion Matrix: " + getConfusionMatrix());
    }

    public double calculateEuclideanDistance(int index1, int index2) {
        Map<String, Integer> vector1 = trainingData.getField(index1).getVocabularyVector();
        Map<String, Integer> vector2 = trainingData.getField(index2).getVocabularyVector();

        double distance = 0.0;

        for (String key : vector1.keySet()) {
            int value1 = vector1.get(key);
            int value2 = vector2.getOrDefault(key, 0);
            distance += Math.pow(value1 - value2, 2);
        }

        for (String key : vector2.keySet()) {
            if (!vector1.containsKey(key)) {
                int value2 = vector2.get(key);
                distance += Math.pow(-value2, 2);
            }
        }

        return Math.sqrt(distance);
    }

    public int[] getKNearestNeighbors(int index, int k) {
    return new int[0];
    }

    public boolean classifyKNN(Map<String, Integer> featureVector) {
        // Implement the KNN classification logic here
        return false;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String getConfusionMatrix() {
        // Implement the confusion matrix logic here
        return "";
    }

    public String getAccuracy() {
        // Implement the accuracy calculation logic here
        return "";
    }

    public String getPrecision() {
        // Implement the precision calculation logic here
        return "";
    }
}
