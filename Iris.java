package naiveBayes;

public class Iris {
    private double[] features;
    private String label;

    public Iris(String dataLine) {
        String[] elements = dataLine.split(",");

        this.features = new double[16];
        for (int i = 0; i < 16; i++) {
            // Remove quotes and trim
            String feature = elements[i].replace("\"", "").trim();
            this.features[i] = Double.parseDouble(feature);
        }
        // Remove quotes from label and trim
        this.label = elements[16].replace("\"", "").trim();
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