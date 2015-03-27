package tamps.cinvestav.algorithms;

import tamps.cinvestav.s0lver.poicalculator.GpsFix;
import tamps.cinvestav.s0lver.poicalculator.StayPoint;

import java.util.ArrayList;

public class MontoliuAlgorithm extends StayPointsDetectionAlgorithm {

    public MontoliuAlgorithm(double minTimeTreshold, double distanceTreshold) {
        super(minTimeTreshold, distanceTreshold, false);
    }

    @Override
    public ArrayList<StayPoint> extractPois(ArrayList<GpsFix> gpsFixes) {
        ArrayList<StayPoint> output = new ArrayList<>();

        int i = 0, j = 0;
        int n = gpsFixes.size();
        long t = 0L;
        double d = 0;

        // :O Crap!
        return output;
    }
}
