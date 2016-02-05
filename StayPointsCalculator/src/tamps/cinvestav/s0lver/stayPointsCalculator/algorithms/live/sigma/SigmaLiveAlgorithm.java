package tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.sigma;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.LiveAlgorithm;

import java.util.Date;

public abstract class SigmaLiveAlgorithm extends LiveAlgorithm {
    protected GpsFix pi, pj, pjMinus;
    protected int amountFixes;
    protected double sigmaLatitude;
    protected double sigmaLongitude;
    protected Date arrivalTime, departureTime;

    public SigmaLiveAlgorithm(long minTimeThreshold, double distanceThreshold, boolean verbose) {
        super(minTimeThreshold, distanceThreshold, verbose);

        this.amountFixes = 0;
        this.sigmaLatitude = 0;
        this.sigmaLongitude = 0;
    }

    public StayPoint processFix(GpsFix fix) {
        includeFix(fix);
        if (amountFixes == 1) {
            pi = fix;
            return null;
        }
        else if (amountFixes == 2) {
            pjMinus = pi;
            pj = fix;
        }
        else {
            pjMinus = pj;
            pj = fix;
        }
        return processLive();
    }

    protected void includeFix(GpsFix fix) {
        amountFixes++;
        departureTime = new Date(fix.getTimestamp().getTime());
        sigmaLatitude += fix.getLatitude();
        sigmaLongitude += fix.getLongitude();
    }

    /***
     * pj is the new pi
     */
    protected void resetAccumulated() {
        amountFixes = 1;
        sigmaLatitude = pj.getLatitude();
        sigmaLongitude = pj.getLongitude();
        arrivalTime = new Date(pj.getTimestamp().getTime());
        departureTime = new Date(pj.getTimestamp().getTime());

        pi = pj;
        pj = null;
        pjMinus = null;
    }
}
