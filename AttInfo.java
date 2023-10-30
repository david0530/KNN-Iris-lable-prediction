package naiveBayes;

import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class AttInfo {
    double intervalStart;
    double intervalEnd;
    Map<String, Integer> labelCounts; // Counts of each label in this interval

    public AttInfo(double intervalStart, double intervalEnd) {
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
        this.labelCounts = new HashMap<>();
    }

    public void addLabel(String label) {
        labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
    }

    public boolean isInInterval(double value) {
        return value >= intervalStart && value < intervalEnd;
    }

}