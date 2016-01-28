package tamps.cinvestav.s0lver.spCalculatorDOS.algorithms.offline;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

import java.util.ArrayList;

public class ZhenAlgorithm extends OfflineAlgorithm {

    public ZhenAlgorithm(ArrayList<GpsFix> gpsFixes, long minTimeTreshold, double distanceTreshold) {
        super(gpsFixes, minTimeTreshold, distanceTreshold, false);
    }

    @Override
    public ArrayList<StayPoint> extractStayPoints() {
        ArrayList<StayPoint> result = new ArrayList<>();
        GpsFix pi, pj;
        double distance;
        long timespan;

        int i = 0, j = 0, pointNum = gpsFixes.size();
        boolean weAreDone = false;

        if (gpsFixes.size() == 0) {
            System.out.println("Provided list is empty");
            return result;
        }

        while (i < pointNum) {
            if (weAreDone) {
                StayPoint sp = StayPoint.createStayPoint(gpsFixes, i, j);
                result.add(sp);
                break;
            }
            pi = gpsFixes.get(i);
            j = i + 1;
            while (j < pointNum) {
                pj = gpsFixes.get(j);
                distance = pi.distanceTo(pj); //distance(pi, pj);
                if (distance > distanceTreshold) {
                    timespan = pi.timeDifference(pj);
                    // If the point is NOT within the interval, then we
                    // have moved out of the stay point
                    if (timespan > minTimeTreshold) {
                        StayPoint sp = StayPoint.createStayPoint(gpsFixes, i, j);
                        result.add(sp);
                        if (verbose) {
                            System.out.println("New SP generated. Fixes involved: ");
                            for (int k = i; k <= j; k++) {
                                System.out.println(gpsFixes.get(k));
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
