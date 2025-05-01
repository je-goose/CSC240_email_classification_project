package DataHandling;

import java.io.File;
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
 * Represents a dataset of boolean text entries.
 * Each entry consists of a boolean label and a text body.
 */
public class DataSet {
    // Member variables
    protected ArrayList<booleanTextEntry> fields;


    // Member functions

    /**
     * Default constructor for DataSet.
     * Initializes an empty list of boolean text entries.
     */
    public DataSet() {
        this.fields = new ArrayList<>();
    }

    /**
     * Constructor for DataSet that takes an ArrayList of booleanTextEntry objects.
     *
     * @param fields The ArrayList of booleanTextEntry objects to initialize the dataset with.
     */
    public DataSet(ArrayList<booleanTextEntry> fields) {
        this.fields = fields;
    }

    /**
     * Constructor for DataSet that reads from a file.
     * The file should contain lines in the format: "email,label".
     *
     * @param filename The name of the file to read from.
     */
    public DataSet(String filename) {
        this.fields = new ArrayList<>();
        try {
            Scanner readIn = new Scanner(new File(filename));

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
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void addField(booleanTextEntry field) {
        fields.add(field);
    }

    public void removeField(int index) {
        if (index < 0 || index >= fields.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        fields.remove(index);
    }

    public booleanTextEntry getField(int index) {
        if (index < 0 || index >= fields.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return fields.get(index);
    }

    public int size() {
        return fields.size();
    }

    public DataSet getSubset(int start, int end) {
        if (start < 0 || end > fields.size() || start >= end) {
            throw new IndexOutOfBoundsException("Invalid subset range: " + start + " to " + end);
        }
        DataSet subset = new DataSet();
        for (int i = start; i < end; i++) {
            subset.addField(fields.get(i));
        }
        return subset;
    }

    public String toString() {
        String output = "";

        return output;
    }
}
