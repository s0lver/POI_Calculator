package tamps.cinvestav.algorithms;

import tamps.cinvestav.s0lver.poicalculator.GpsFix;
import tamps.cinvestav.s0lver.poicalculator.StayPoint;

import java.util.ArrayList;

public class ZhenAlgorithm extends StayPointsDetectionAlgorithm {

    public ZhenAlgorithm(double minTimeTreshold, double distanceTreshold) {
        super(minTimeTreshold, distanceTreshold, false);
    }

    @Override
    public ArrayList<StayPoint> extractPois(ArrayList<GpsFix> list) {
        ArrayList<StayPoint> result = new ArrayList<>();
        GpsFix pi = null, pj = null;
        double distance = 0.0;
        long timespan = 0L;

        int i = 0, j = 0, pointNum = list.size();
        boolean weAreDone = false;

        if (list.size() == 0) {
            System.out.println("Provided list is empty");
            return result;
        }

        while (i < pointNum) {
            if (weAreDone) {
                StayPoint sp = StayPoint.createStayPoint(list, i, j);
                result.add(sp);
                break;
            }
            pi = list.get(i);
            j = i + 1;
            while (j < pointNum) {
                pj = list.get(j);
                distance = distance(pi, pj);
                if (distance > distanceTreshold) {
                    timespan = timespan(pi, pj);
                    // If the point is NOT within the interval, then we
                    // have moved out of the stay point
                    if (timespan > minTimeTreshold) {
                        StayPoint sp = StayPoint.createStayPoint(list, i, j);
                        result.add(sp);
                        if (verbose) {
                            System.out.println("New SP generated. Fixes involved: ");
                            for (int k = i; k <= j; k++) {
                                System.out.println(list.get(k));
                            }
                        }
                    }
                    i = j;
                    break;
                }
                j++;
                // If this increment finalises the iteration...
                if (j == pointNum) {
                    // The we have to stop it
                    weAreDone = true;
                    j--;
                    break;
                }
            }
        }
        return result;
    }
}
