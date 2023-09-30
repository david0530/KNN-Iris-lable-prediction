package knnPack;

public class Iris {
    double[] attributes;
    String label;
    
    public Iris(String dataLine) {
	        String[] element = dataLine.split(",");
	        this.attributes = new double[4];
	        for (int i = 0; i < 4; i++) {
	            this.attributes[i] = Double.parseDouble(element[i]);
	        }
	        this.label = element[4];
	    
    }
}
