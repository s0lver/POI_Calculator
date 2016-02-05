package tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.buffered;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.LiveAlgorithm;

import java.util.ArrayList;

public abstract class BufferedLiveAlgorithm extends LiveAlgorithm {
    protected ArrayList<GpsFix> list;

    public BufferedLiveAlgorithm(long minTimeThreshold, double distanceThreshold, boolean verbose) {
        super(minTimeThreshold, distanceThreshold, verbose);
        this.list = new ArrayList<>();
    }

    @Override
    public StayPoint processFix(GpsFix fix) {
        if (verbose) System.out.println("Processing FIX " + fix);
        list.add(fix);
        int size = list.size();
        if (size == 1) {
            return null;
        }
        else{
            return processLive();
        }
    }

    @Override
    public StayPoint processLastPart() {
        int n = list.size();
        if (n==0) {
            return null;
        }
        if (verbose){
            System.out.println("Building a stay point in last part with " + n + " fixes.");
        }
        StayPoint sp = StayPoint.createStayPoint(list, 0, n - 1);
        list.clear();
        return sp;
    }

    protected void cleanList(GpsFix pj) {
        list = new ArrayList<>();
        list.add(pj);
        if (verbose) System.out.println("Cleaning the list and adding " + pj);
    }
}
