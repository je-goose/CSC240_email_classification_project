package MModel;

import DataHandling.DataSet;

/**
 * Project name: email_classification_project
 * Package: MModel
 * Author: Jae Elizabeth Giesen
 * Date: 4/27/2025
 * Purpose:
 * Modified on:
 */
public abstract class MModel {
    protected DataSet trainingData;
    protected DataSet testingData;
    protected DataSet validationData;

    public abstract void train();

    public abstract void evaluateModel();
}
