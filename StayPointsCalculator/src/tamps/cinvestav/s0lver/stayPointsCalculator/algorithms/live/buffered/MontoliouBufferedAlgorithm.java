package tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.buffered;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

public class MontoliouBufferedAlgorithm extends BufferedLiveAlgorithm{

    private long maxTimeThreshold;

    public MontoliouBufferedAlgorithm(long maxTimeThreshold, long minTimeThreshold, double distanceThreshold, boolean verbose) {
        super(minTimeThreshold, distanceThreshold, verbose);
        this.maxTimeThreshold = maxTimeThreshold;
    }

    @Override
    protected StayPoint processLive() {
        int n = list.size();

        GpsFix pi = list.get(0);
        GpsFix pj = list.get(n - 1);
        GpsFix pjMinus = list.get(n - 2);

        long timespan = pjMinus.timeDifference(pj);
        if (timespan > maxTimeThreshold) {
            if (verbose) System.out.println("Max time constraint exceeded.");
            cleanList(pj);
            return null;
        }

        double distance = pi.distanceTo(pj);
        if (verbose) System.out.println("Distance is " + distance);

        if (distance > distanceThreshold) {
            timespan = pi.timeDifference(pj);
            if (verbose) System.out.println("Timestamp is " + timespan);

            if (timespan > minTimeThreshold) {
                StayPoint sp = StayPoint.createStayPoint(list, 0, n - 1);
                cleanList(pj);
                System.out.println("Stay point created! " + sp);
                return sp;
            }
            cleanList(pj);
        }

        return null;
    }
}
