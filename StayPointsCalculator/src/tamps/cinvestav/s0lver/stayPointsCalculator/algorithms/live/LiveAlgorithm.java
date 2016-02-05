package tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

public abstract class LiveAlgorithm {

    protected double distanceThreshold;
    protected long minTimeThreshold;
    protected boolean verbose;

    public LiveAlgorithm(long minTimeThreshold, double distanceThreshold, boolean verbose) {
        this.minTimeThreshold = minTimeThreshold;
        this.distanceThreshold = distanceThreshold;
        this.verbose = verbose;
    }

    public abstract StayPoint processFix(GpsFix fix);

    protected abstract StayPoint processLive();

    public abstract StayPoint processLastPart();
}
