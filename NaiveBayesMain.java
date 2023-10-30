package naiveBayes;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NaiveBayesMain {
    public static void main(String[] args) {
        NaiveBayesM model = new NaiveBayesM();
        List<Iris> data = new ArrayList<>();
        String filePath = "C://Users//djyan//Downloads//iris//iris.data";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                data.add(new Iris(line));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
    
    public static List<Double> naiveBayesNFold(List<Iris> data, int N, NaiveBayesM model) { 
        List<Double> accuracies = new ArrayList<>();
        List<Fold> folds = Fold.NFold(data, N);
        
        for (Fold fold : folds) {

        	List<Iris> trainingIris = new ArrayList<>(fold.trainingData.att);
            List<Iris> testIris = new ArrayList<>(fold.predictionData.att);

            model.train(trainingIris); 
            int correctPredictions = 0;
            for (Iris iris : testIris) { 
                String predictedLabel = model.predict(iris);

                System.out.println("Predicted label: " + predictedLabel);
                System.out.println("Original label: " + iris.getLabel());

                if (predictedLabel.equals(iris.getLabel())) {
                    correctPredictions++;
                }
            }
            double accuracy = (double) correctPredictions / testIris.size();
            accuracies.add(accuracy);
        }
        return accuracies;
    }
}