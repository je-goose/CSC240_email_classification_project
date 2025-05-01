package DataHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

import static java.util.Collections.shuffle;

/**
 * Project name: email_classification_project
 * Package: DataHandling
 * Author: Jae Elizabeth Giesen
 * Date: 4/27/2025
 * Purpose:
 * Modified on:
 */

/**
 * Represents a processed dataset consisting of training, test, and validation sets.
 * The dataset is read from a file and split into three parts based on the specified proportions.
 */
public class ProcessedDataSet {
    protected DataSet trainingSet;
    protected DataSet testSet;
    protected DataSet validationSet;

    public ProcessedDataSet(DataSet trainingSet, DataSet testSet, DataSet validationSet) {
        this.trainingSet = trainingSet;
        this.testSet = testSet;
        this.validationSet = validationSet;
    }

    public ProcessedDataSet(String filename, double trainProportion) {
        if (trainProportion < 0 || trainProportion > 1) {
            throw new IllegalArgumentException("Train proportion must be between 0 and 1.");
        }
        try {
            Scanner readIn = new Scanner(new File(filename));
            ArrayList<booleanTextEntry> fields = new ArrayList<>();

            // assuming email,label format
            readIn.nextLine(); // Skip header line
            while (readIn.hasNextLine()) {
                String line = readIn.nextLine();
                String[] parts = line.split(",");

                boolean label = parts[1].equals("1");
                booleanTextEntry entry = new booleanTextEntry(label, parts[0]);

                fields.add(entry);
            }
            readIn.close();
            shuffle(fields);

            int trainFinalIndex = (int) (fields.size() * trainProportion);
            int testFinalIndex = (int) (0.9 * fields.size());

            ArrayList<booleanTextEntry> trainingFields = new ArrayList<>(fields.subList(0, trainFinalIndex));
            ArrayList<booleanTextEntry> testFields = new ArrayList<>(fields.subList(trainFinalIndex + 1, testFinalIndex));
            ArrayList<booleanTextEntry> validationFields = new ArrayList<>(fields.subList(testFinalIndex + 1, fields.size()));

            this.trainingSet = new DataSet(trainingFields);
            this.testSet = new DataSet(testFields);
            this.validationSet = new DataSet(validationFields);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public DataSet getTrainingSet() {
        return this.trainingSet;
    }

    public DataSet getTestSet() {
        return this.testSet;
    }

    public DataSet getValidationSet() {
        return this.validationSet;
    }
}
