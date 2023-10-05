# Naive Bayes and KNN-Iris-lable-prediction

NaiveBayesModel Class: Implements a Naive Bayes classifier for the Iris dataset with discretization of continuous attributes, offering methods to train the model and predict labels of new instances.

NaiveBayesMain Class: Acts as the driver class, managing data input/output and orchestrating the training, testing, and evaluation processes using the Naive Bayes model.

KNNModel.java: Orchestrates the overall K-Nearest Neighbors algorithm, leveraging other classes to process the Iris dataset and compute prediction accuracy.

Iris.java: Represents a single data point in the Iris dataset, encapsulating its attributes and label.

TData.java: Holds the training data attributes and labels.

PData.java: Manages prediction data, its original labels, predicted labels, and contains the logic to predict a label for a given data point.

Fold.java: Manages a single fold in cross-validation, containing both training and prediction data, and also implements the N-fold cross-validation method.

