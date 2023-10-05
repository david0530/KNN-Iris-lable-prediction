package naiveBayes;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class NaiveBayesMain {
    public static void main(String[] args) {
        NaiveBayesModel model = new NaiveBayesModel();
        List<Iris> data = new ArrayList<>();
        String filePath = "C://Users//djyan//Downloads//iris//iris.data";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                data.add(new Iris(line));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        Collections.shuffle(data);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of folds (N): ");
        int N = scanner.nextInt();
        scanner.close(); 

        List<Double> accuracies = naiveBayesNFold(data, N, model);
        double totalAccuracy = 0;
        for (Double accuracy : accuracies) {
            totalAccuracy += accuracy;
        }
        double averageAccuracy = totalAccuracy / accuracies.size();        
        for (Double accuracy : accuracies) {
            System.out.println("Accuracy: " + accuracy);
        }
        System.out.println("Average Accuracy: " + averageAccuracy);
    }
    
    public static List<Double> naiveBayesNFold(List<Iris> data, int N, NaiveBayesModel model) {
        List<Double> accuracies = new ArrayList<>();
        List<Fold> folds = Fold.NFold(data, N);
        
        for (Fold fold : folds) {
        	model.train(fold.trainingData.att);
            int correctPredictions = 0;
            for (int i = 0; i < fold.predictionData.att.size(); i++) {
                Iris testIris = fold.predictionData.att.get(i);
                String predictedLabel = model.predict(testIris);
                

                if (predictedLabel.equals(fold.predictionData.originalLabel.get(i))) {
                    correctPredictions++;
                }
            }
            double accuracy = (double) correctPredictions / fold.predictionData.att.size();
            accuracies.add(accuracy);
        }
        return accuracies;
    }
}