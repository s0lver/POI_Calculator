package tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

public abstract class LiveAlgorithm {

    protected double distanceThreshold;
    protected long minTimeThreshold;

    public LiveAlgorithm(long minTimeThreshold, double distanceThreshold) {
        this.minTimeThreshold = minTimeThreshold;
        this.distanceThreshold = distanceThreshold;
    }

    public abstract StayPoint processFix(GpsFix fix);

    protected abstract StayPoint processLive();

    public abstract StayPoint processLastPart();
}
