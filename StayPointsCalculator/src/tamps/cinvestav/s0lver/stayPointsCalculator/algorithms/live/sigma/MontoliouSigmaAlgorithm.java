package tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.sigma;

import tamps.cinvestav.s0lver.locationentities.StayPoint;

public class MontoliouSigmaAlgorithm extends SigmaLiveAlgorithm {
    private long maxTimeThreshold;

    public MontoliouSigmaAlgorithm(long maxTimeThreshold, long minTimeThreshold, double distanceThreshold) {
        super(minTimeThreshold, distanceThreshold);
        this.maxTimeThreshold = maxTimeThreshold;
    }

    protected StayPoint processLive() {
        long timespan = pjMinus.timeDifference(pj);
        if (timespan > maxTimeThreshold) {
            resetAccumulated();
            return null;
        }

        double distance = pi.distanceTo(pj);

        if (distance > distanceThreshold) {
            timespan = pi.timeDifference(pj);

            if (timespan > minTimeThreshold) {
                StayPoint sp = StayPoint.createStayPoint(sigmaLatitude, sigmaLongitude, arrivalTime, departureTime, amountFixes);
                resetAccumulated();
                return sp;
            }
            resetAccumulated();
            return null;
        }

        return null;
    }

    @Override
    public StayPoint processLastPart() {
        if (amountFixes >= 1){
            return StayPoint.createStayPoint(sigmaLatitude, sigmaLongitude, arrivalTime, departureTime, amountFixes);
        }
        return null;
    }
}
