package naiveBayes;

import java.util.*;
import java.util.Map;
import java.util.stream.*;

public class NaiveBayesM {
    List<Probability> row; //probabilities for each feature
    Map<String, Integer> totalLabelCounts; // total count of each label in the training set
    int totalRow; // Total number of training entries

    public NaiveBayesM() {
        this.row = new ArrayList<>();
        this.totalLabelCounts = new HashMap<>();
        this.totalRow = 0; 
    }

    public void train(List<Iris> trainingData) {
        for (int i = 0; i < 15; i++) {
            row.add(new Probability());
        }

        // Calculate min and max for each value
        double[][] minMax = new double[15][2]; // min and max for each value
        for (int i = 0; i < 15; i++) {
            int featureIndex = i; 

            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            for (Iris iris : trainingData) {
                double featureValue = iris.getFeatures()[featureIndex];
                if (featureValue < min) {
                    min = featureValue;
                }
                if (featureValue > max) {
                    max = featureValue;
                }
            }
            minMax[i][0] = min;
            minMax[i][1] = max;
        }

        // Calculate intervals
        for (int i = 0; i < 15; i++) {
            double range = (minMax[i][1] - minMax[i][0]) / 40; // Dividing into 40 intervals
            for (int j = 0; j < 40; j++) { // 20 intervals
                double start = minMax[i][0] + j * range;
                double end = minMax[i][0] + (j + 1) * range;
                row.get(i).addInterval(new AttInfo(start, end));
            }
        }

        for (Iris iris : trainingData) {
            totalLabelCounts.put(iris.getLabel(), totalLabelCounts.getOrDefault(iris.getLabel(), 0) + 1);
            for (int i = 0; i < 15; i++) {
                AttInfo interval = row.get(i).getIntervalForValue(iris.getFeatures()[i]);
                if (interval != null) {
                    interval.addLabel(iris.getLabel());
                }
            }
        }
        totalRow = trainingData.size();
    }

    public String predict(Iris iris) {
        // Calculate the probability for each label
        Map<String, Double> labelProbabilities = new HashMap<>();
        for (String label : totalLabelCounts.keySet()) {
            double probability = (double) totalLabelCounts.get(label) / totalRow; // P(label)
            for (int i = 0; i < 15; i++) { // For each column
                AttInfo interval = row.get(i).getIntervalForValue(iris.getFeatures()[i]); 
                if (interval != null && interval.labelCounts.containsKey(label)) {
                    // P(feature|label) 
                    probability *= (double) interval.labelCounts.get(label) / totalLabelCounts.get(label);
                } else {
                    probability *= 1.0 / (totalLabelCounts.get(label) + 1);
                }
            }
            labelProbabilities.put(label, probability);
        }

        //label with the highest P(feature|label) 
        return Collections.max(labelProbabilities.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}