package knnPack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class KNNModel {
	

    public List<Double> KNN(List<Iris> data, int N, int k) {
    	List<Fold> folds = Fold.NFold(data, N);
        double totalAccuracy = 0;
        List<Double> returnValues = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            Fold fold = folds.get(i);
            int correctCount = 0;

            for (int j = 0; j < fold.predictionData.att.size(); j++) {
                Iris predAttributes = fold.predictionData.att.get(j);
                String predictedLabel = PData.predictLabel(predAttributes, fold.trainingData, k);

                if (predictedLabel.equals(fold.predictionData.originalLabel.get(j))) {
                    correctCount++;
                }
            }
            
            double accuracy = (double) correctCount / fold.predictionData.att.size();
            totalAccuracy += accuracy;
            returnValues.add(accuracy);
        }
        returnValues.add(totalAccuracy/N);
        
        return returnValues;
        
    }
    public static void main(String[] args) {
        KNNModel model = new KNNModel();
        List<Iris> data = new ArrayList<>();
        
        String filePath = "C://Users//djyan//Downloads//iris//iris.data";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                	data.add(new Iris(line));
                
            }
        } catch (IOException e) {
            System.out.println("error reading the file: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("enter number of folds (N): ");
        int N = scanner.nextInt();
        System.out.println("enter number of nearest neighbors (k): ");
        int k = scanner.nextInt();
        
        
        for(Double accuracy: model.KNN(data, N, k)) {
        	System.out.println("\n" +accuracy);
        }
    }
}
