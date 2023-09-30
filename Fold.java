package knnPack;
import java.util.*;

public class Fold {
    TData trainingData;
    PData predictionData;

    public Fold(TData trainingData, PData predictionData) {
        this.trainingData = trainingData;
        this.predictionData = predictionData;
    }

    public static List<Fold> NFold(List<Iris> data, int N) {
        int foldSize = data.size() / N;
        List<Fold> folds = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            TData tdata = new TData();
            PData pdata = new PData();

            for (int j = 0; j < data.size(); j++) {
                if (j / foldSize == i) {
                    pdata.att.add(data.get(j));
                    pdata.originalLabel.add(data.get(j).label);
                } else {
                    tdata.att.add(data.get(j));
                    tdata.label.add(data.get(j).label);
                }
            }

            folds.add(new Fold(tdata, pdata));
        }

        return folds;
    }
}

