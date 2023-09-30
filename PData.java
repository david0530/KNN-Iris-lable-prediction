package knnPack;

import java.util.*;


public class PData {
    List<Iris> att = new ArrayList<>();
    List<String> originalLabel = new ArrayList<>();
    List<String> predictedLabel = new ArrayList<>();

    private static double getDistance(Iris predAtt, Iris trainAtt) {
        double distance = 0;
        for (int i = 0; i < predAtt.attributes.length; i++) {
            distance += Math.pow(predAtt.attributes[i] - trainAtt.attributes[i], 2);
        }
        return Math.sqrt(distance);
    }

    public static String predictLabel(Iris predAttributes, TData trainingData, int k) {
        List<Iris> neighbors = new ArrayList<>();
        List<Double> distances = new ArrayList<>();
        
        for (Iris trainData : trainingData.att) {
            double distance = getDistance(predAttributes, trainData);
            distances.add(distance);
        }

        for (int i = 0; i < k; i++) {
            int minIndex = distances.indexOf(Collections.min(distances));
            neighbors.add(trainingData.att.get(minIndex));
            Double maxNumber= (double) 1000000000;
            distances.set(minIndex, maxNumber);
        }

        List<String> predLables = new ArrayList<>();
        for (Iris neighbor : neighbors) {
        	predLables.add(neighbor.label);
        }
        
        Map<String, Integer> labelFrequencies = new HashMap<>();
        for (String label : predLables) {
            labelFrequencies.put(label, labelFrequencies.getOrDefault(label, 0) + 1);
        }
        
        String mostFrequentLabel = null;
        int maxCount = 0;
        
        for (Map.Entry<String, Integer> entry : labelFrequencies.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFrequentLabel = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mostFrequentLabel;

    }
}
