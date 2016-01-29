package tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.sigma;

import tamps.cinvestav.s0lver.locationentities.StayPoint;

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
            return StayPoint.createStayPoint(sigmaLatitude, sigmaLongitude, arrivalTime, departureTime, amountFixes);
        }
        return null;
    }
}

