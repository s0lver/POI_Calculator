package tamps.cinvestav.s0lver.spCalculator.algorithms.live.sigma;

import tamps.cinvestav.s0lver.spCalculator.classes.StayPoint;

public class ZhenSigmaAlgorithm extends SigmaLiveAlgorithm {
    public ZhenSigmaAlgorithm(long minTimeThreshold, double distanceThreshold) {
        super(minTimeThreshold, distanceThreshold);
    }

    protected StayPoint processLive() {
        double distance = pi.distanceTo(pj);

        if (distance > distanceThreshold) {
            long timespan = pi.timeDifference(pj);

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

