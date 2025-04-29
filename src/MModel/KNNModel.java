package MModel;

import DataHandling.ProcessedDataSet;

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
        this.pDataSet = dataSetIn;
        this.k = k;
    }

    @Override
    public void train() {

    }

    public double calculateEuclideanDistance(int index1, int index2) {
        Map<String, Integer> vector1 = pDataSet.getTrainingSet().getField(index1).getVocabularyVector();
        Map<String, Integer> vector2 = pDataSet.getTrainingSet().getField(index2).getVocabularyVector();

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
    }

    public boolean classifyKNN(Map<String, Integer> featureVector) {
        // Implement the KNN classification logic here
        return false;
    }
}
