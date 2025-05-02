package MModel;

import DataHandling.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    public void evaluateModel() {
        // Implement the evaluation logic here
        System.out.println("Accuracy: " + "\n" + getAccuracy() + "%");
        System.out.println("Precision: " + "\n" + getPrecision());
        System.out.println("Recall: " + "\n" + getRecall());
        System.out.println("Confusion Matrix: " + "\n" + getConfusionMatrix());

    }

    public double calculateEuclideanDistance(Map<String,Integer> vector1, Map<String,Integer> vector2) {
        if (vector1.isEmpty() || vector2.isEmpty()) {
            return Double.MAX_VALUE;
        }
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

    public int[] getKNearestNeighbors(Map<String,Integer> vector, int k) {
        Map<Integer, Double> distances = new HashMap<>();
        for (int i = 0; i < trainingData.size(); i++) { // range thru training data, append to distances
            double distance = calculateEuclideanDistance(vector, trainingData.getField(i).getVocabularyVector());
            distances.put(i, distance);
        }

        ArrayList<Map.Entry<Integer, Double>> sortedDistances = new ArrayList<>(distances.entrySet()); // sort distances to get nearest neighbors
        sortedDistances.sort((a, b) -> Double.compare(a.getValue(), b.getValue()));

        int[] nearestNeighbors = new int[k]; // init array of size k
        for (int i = 0; i < k; i++) {
            nearestNeighbors[i] = sortedDistances.get(i).getKey(); // append nearest neighbor indices to array
        }

        return nearestNeighbors;
    }

    public boolean classifyKNN(Map<String, Integer> featureVector) {
        // Implement the KNN classification logic here
        boolean classification = false;
        int[] nearestNeighbors = getKNearestNeighbors(featureVector, k); // get nearest neighbors
        int trueCount = 0;
        int falseCount = 0;
        for (int i : nearestNeighbors) { // iterate through nearest neighbors
            if (trainingData.getField(i).getLabel()) {
                trueCount++;
            } else {
                falseCount++;
            }
        }
        if (trueCount > falseCount) { // neighbors vote
            classification = true; // if more true than false
        } else if (falseCount == trueCount) { // if equal, nearest neighbor wins
            classification = trainingData.getField(nearestNeighbors[0]).getLabel();
        } // else it falls off and evals to false

        return classification;
    }

    public DataSet getTrainingData() {
        return this.trainingData;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String getConfusionMatrix() {
        // Implement the confusion matrix logic here
        int[][] confusionMatrix = new int[2][2];
        for (int i = 0; i < validationData.size(); i++) {
            Map<String, Integer> featureVector = validationData.getField(i).getVocabularyVector();
            boolean actualLabel = validationData.getField(i).getLabel();
            boolean predictedLabel = classifyKNN(featureVector);

            if (actualLabel && predictedLabel) {
                confusionMatrix[0][0]++;
            } else if (!actualLabel && !predictedLabel) {
                confusionMatrix[1][1]++;
            } else if (actualLabel && !predictedLabel) {
                confusionMatrix[0][1]++;
            } else {
                confusionMatrix[1][0]++;
            }
        }
        return ("\t"+confusionMatrix[0][0] + " " + confusionMatrix[0][1] +
                "\n\t" + confusionMatrix[1][0] + " " + confusionMatrix[1][1]);
    }

    public String getAccuracy() {
        // Implement the accuracy calculation logic here
        int correctPredictions = 0;
        for (int i = 0; i < validationData.size(); i++) {
            Map<String, Integer> featureVector = validationData.getField(i).getVocabularyVector();
            boolean actualLabel = validationData.getField(i).getLabel();
            boolean predictedLabel = classifyKNN(featureVector);

            if (actualLabel == predictedLabel) {
                correctPredictions++;
            }
        }

        // return accuracy as a percentage
        double accuracy = (double) correctPredictions / validationData.size() * 100;
        return String.format("%.2f%%", accuracy);
    }

    public String getPrecision() {
        // Implement the precision calculation logic here
        int truePositive = 0;
        int falsePositive = 0;
        for (int i = 0; i < validationData.size(); i++) {
            Map<String, Integer> featureVector = validationData.getField(i).getVocabularyVector();
            boolean actualLabel = validationData.getField(i).getLabel();
            boolean predictedLabel = classifyKNN(featureVector);

            if (actualLabel && predictedLabel) {
                truePositive++;
            } else if (!actualLabel && predictedLabel) {
                falsePositive++;
            }
        }
        double precision = (double) truePositive / (truePositive + falsePositive) * 100;
        return String.format("%.2f%%", precision);
    }

    public String getRecall() {
        int truePositive = 0;
        int falseNegative = 0;

        for (int i = 0; i < validationData.size(); i++) {
            Map<String, Integer> featureVector = validationData.getField(i).getVocabularyVector();
            boolean actualLabel = validationData.getField(i).getLabel();
            boolean predictedLabel = classifyKNN(featureVector);

            if (actualLabel && predictedLabel) {
                truePositive++;
            } else if (actualLabel && !predictedLabel) {
                falseNegative++;
            }
        }

        double recall = (double) truePositive / (truePositive + falseNegative) * 100;
        return String.format("%.2f%%", recall);
    }
}
