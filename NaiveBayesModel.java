package naiveBayes;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class NaiveBayesModel {
    private Map<String, Double> p_H; // P(H)
    private Map<String, List<Double>> p_XH; // P(X|H)
    private double[][] intervals;
    
    public NaiveBayesModel() {
        p_H = new HashMap<>();
        p_XH = new HashMap<>();
        intervals = new double[4][2]; // 4 attributes, 2 boundary values each
    }

    public void train(List<Iris> trainingData) {
        Map<String, Integer> labelCounts = new HashMap<>();
        int totalCount = trainingData.size();

        // count labels
        for (Iris iris : trainingData) {
            labelCounts.put(iris.label, labelCounts.getOrDefault(iris.label, 0) + 1);
        }

        // calculate 3 P(H) for each flower
        for (Map.Entry<String, Integer> entry : labelCounts.entrySet()) {
            p_H.put(entry.getKey(), entry.getValue() / (double) totalCount);
        }

        // store P(X|H)
        Map<String, List<int[]>> attributeCounts = new HashMap<>();

        // initialize
        double[] minValues = new double[4];
        double[] maxValues = new double[4];
        Arrays.fill(minValues, Double.POSITIVE_INFINITY);
        Arrays.fill(maxValues, Double.NEGATIVE_INFINITY);

        for (Iris iris : trainingData) {
            for (int i = 0; i < 4; i++) { 
                double value = iris.attributes[i];
                if (value < minValues[i]) minValues[i] = value;
                if (value > maxValues[i]) maxValues[i] = value;
            }
        }

        // count attributes
        for (Iris iris : trainingData) {
            List<int[]> counts = attributeCounts.get(iris.label);
            if (counts == null) {
                counts = new ArrayList<>();
                // initialize counts with size 4 and each element as an int[3]
                for (int i = 0; i < 4; i++) {
                    counts.add(new int[3]);
                }
                attributeCounts.put(iris.label, counts);
            }
            for (int i = 0; i < 4; i++) { 
                int index = getIntervalIndex(iris.attributes[i], i);
                counts.get(i)[index]++;
            }
        }
        
        //calculate interval boundaries
        for (int i = 0; i < 4; i++) { 
            double rangeThird = (maxValues[i] - minValues[i]) / 3;
            intervals[i][0] = minValues[i] + rangeThird;       // lower boundary
            intervals[i][1] = minValues[i] + 2 * rangeThird;   // upper boundary
        }
        // calculate P(X|H)
        for (Map.Entry<String, List<int[]>> entry : attributeCounts.entrySet()) {
            String label = entry.getKey();
            List<int[]> counts = entry.getValue();
            List<Double> probs = new ArrayList<>();

            for (int[] count : counts) {
                for (int value : count) {
                    probs.add(value / (double) labelCounts.get(label));
                }
            }

            p_XH.put(label, probs);
        }
    }

    private int getIntervalIndex(double value, int attributeIndex) {
        if (value <= intervals[attributeIndex][0]) return 0;
        else if (value <= intervals[attributeIndex][1]) return 1;
        else return 2;
    }

    public String predict(Iris iris) {
        String bestLabel = null;
        double bestProb = -1;

        for (String label : p_H.keySet()) {
            double prob = p_H.get(label);
            List<Double> attProbs = p_XH.get(label);

            for (int i = 0; i < 4; i++) { 
                int index = getIntervalIndex(iris.attributes[i], i);
                prob *= attProbs.get(i * 3 + index);
            }

            if (prob > bestProb) {
                bestProb = prob;
                bestLabel = label;
            }
        }
        return bestLabel;
    }
}