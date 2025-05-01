package DataHandling;

import java.util.HashMap;
import java.util.Map;

/**
 * Project name: email_classification_project
 * Package: DataHandling
 * Author: Jae Elizabeth Giesen
 * Date: 4/27/2025
 * Purpose:
 * Modified on:
 */

/**
 * Represents a single entry in the dataset, which consists of a boolean label and a vocabulary vector.
 */
public class booleanTextEntry {
    private final boolean label;
    private final Map<String, Integer> vocabularyVector;


    /**
     * Constructor for booleanTextEntry.
     *
     * @param label     The boolean label (true or false).
     * @param textBody  The text body to be converted into a vocabulary vector.
     */
    public booleanTextEntry(Boolean label, String textBody) {
        this.label = label;
        this.vocabularyVector = new HashMap<>();

        String[] textBodyParts = textBody.split(" ");

        for (String part: textBodyParts) {
            if (vocabularyVector.containsKey(part)) {
                vocabularyVector.put(part, vocabularyVector.get(part) + 1);
            } else {
                vocabularyVector.put(part, 1);
            }
        }
    }

    /**
     * Returns the truth value of the label.
     * @return The boolean label (true or false).
     */
    public boolean getLabel() {
        return this.label;
    }

    /**
     * Returns the vocabulary vector.
     * @return A Map representing the vocabulary vector, where keys are words and values are their counts.
     */
    public Map<String, Integer> getVocabularyVector() {
        return this.vocabularyVector;
    }

    public int getLabelAsInt() {
        return this.label ? 1 : 0;
    }

    public int getSizeOfVocabularyVector() {
        return this.vocabularyVector.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Label: ").append(label).append("\n");
        sb.append("Vocabulary Vector: ").append(vocabularyVector).append("\n");
        return sb.toString();
    }
}
