package tamps.cinvestav.s0lver.spCalculatorDOS.algorithms.live.buffered;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

public class ZhengBufferedAlgorithm extends BufferedLiveAlgorithm {

    public ZhengBufferedAlgorithm(long minTimeThreshold, double distanceThreshold) {
        super(minTimeThreshold,distanceThreshold);
    }

    @Override
    protected StayPoint processLive() {
        int n = list.size();

        GpsFix pi = list.get(0);
        GpsFix pj = list.get(n - 1);

        double distance = pi.distanceTo(pj);

        if (distance > distanceThreshold) {
            long timespan = pi.timeDifference(pj);

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
