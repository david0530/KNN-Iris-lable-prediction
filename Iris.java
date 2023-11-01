package naiveBayes;

public class Iris {
    private double[] features;
    private String label;

    public Iris(String dataLine) {
        String[] element = dataLine.split(",");
        this.features = new double[4];
        for (int i = 0; i < 4; i++) {
            this.features[i] = Double.parseDouble(element[i])+0.1;
        }
        this.label = element[4];
    }

    public double[] getFeatures() {
        return features;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
}
