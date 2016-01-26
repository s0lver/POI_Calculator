package tamps.cinvestav.s0lver.spCalculator.algorithms.live.sigma;

import tamps.cinvestav.s0lver.spCalculator.classes.StayPoint;

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
            StayPoint sp = StayPoint.createStayPoint(sigmaLatitude, sigmaLongitude, arrivalTime, departureTime, amountFixes);
            return sp;
        }
        return null;
    }
}

