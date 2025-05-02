# CSC240 Email Classification Project

WCUPA SP2025, CSC240

Dr. Liu Cui

Author: Jae Elizabeth Giesen

## Instructions
1. Run main.java in main
2. The program will automatically search one directory up for the dataset.
3. Set k to a value not more than 5 for performance reasons
4. File will be written to predictions.txt in the same file location as spam_or_not_spam.csv.
5. After writing file, will open query box. Enter integer values for indexing testing data.
6. Repeat as desired, after query closes program exits.

## Packages & Classes
- `main` - Contains the main class and testers for `DataHandling` and `MModel`.
- `DataHandling` - Contains the classes `booleanTextEntry`,`DataSet`,`ProcessedDataSet` that handles the data.
- `MModel` - (Mathematical Model) Contains the abstract base class `MModel` and implementation of `KNNModel`.
