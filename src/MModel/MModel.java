package MModel;

import DataHandling.ProcessedDataSet;

/**
 * Project name: email_classification_project
 * Package: MModel
 * Author: Jae Elizabeth Giesen
 * Date: 4/27/2025
 * Purpose:
 * Modified on:
 */
public abstract class MModel {
    protected ProcessedDataSet pDataSet;

    public abstract void train();
}
