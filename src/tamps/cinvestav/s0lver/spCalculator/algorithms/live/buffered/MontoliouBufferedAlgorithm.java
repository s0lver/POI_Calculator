package tamps.cinvestav.s0lver.spCalculator.algorithms.live.buffered;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

public class MontoliouBufferedAlgorithm extends BufferedLiveAlgorithm{

    private long maxTimeThreshold;

    public MontoliouBufferedAlgorithm(long maxTimeThreshold, long minTimeThreshold, double distanceThreshold) {
        super(minTimeThreshold,distanceThreshold);
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
            cleanList(pj);
            return null;
        }

        double distance = pi.distanceTo(pj);

        if (distance > distanceThreshold) {
            timespan = pi.timeDifference(pj);

            if (timespan > minTimeThreshold) {
                StayPoint sp = StayPoint.createStayPoint(list, 0, n - 1);
                cleanList(pj);
                return sp;
            }
            cleanList(pj);
        }

        return null;
    }
}
