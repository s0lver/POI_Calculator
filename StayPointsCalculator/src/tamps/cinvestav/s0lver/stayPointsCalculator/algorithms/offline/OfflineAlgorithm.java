package tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

import java.util.ArrayList;

public abstract class OfflineAlgorithm {
    protected long minTimeTreshold;
    protected double distanceTreshold;
    protected boolean verbose;
    protected ArrayList<GpsFix> gpsFixes;

    public OfflineAlgorithm(ArrayList<GpsFix> gpsFixes, long minTimeTreshold, double distanceTreshold, boolean verbose) {
        this.gpsFixes = gpsFixes;
        this.minTimeTreshold = minTimeTreshold;
        this.distanceTreshold = distanceTreshold;
        this.verbose = verbose;
    }

    public abstract ArrayList<StayPoint> extractStayPoints();
}
