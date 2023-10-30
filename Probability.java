package naiveBayes;

import java.util.*;

public class Probability {
    List<AttInfo> intervals;

    public Probability() {
        this.intervals = new ArrayList<>();
    }

    public void addInterval(AttInfo interval) {
        this.intervals.add(interval);
    }

    public AttInfo getIntervalForValue(double value) {
        for (AttInfo interval : intervals) {
            if (interval.isInInterval(value)) {
                return interval;
            }
        }
        return null;
    }
}