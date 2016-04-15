package tamps.cinvestav.s0lver.trajectoriescomparator;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.Trajectory;

import java.util.ArrayList;
import java.util.Calendar;

/***
 * Measures the time synchronized distance between two different GPS trajectories
 * @see GpsFix
 * @see Trajectory
 */
public class TrajectoryComparator {
    private Trajectory groundTruthTrajectory;
    private Trajectory subSampledTrajectory;

    /***
     * Constructor receiving both trajectories
     * @param groundTruthTrajectory The ground truth trajectory to be employed
     * @param subSampledTrajectory The sub-sampled trajectory to be employed
     */
    public TrajectoryComparator(Trajectory groundTruthTrajectory, Trajectory subSampledTrajectory) {
        this.groundTruthTrajectory = groundTruthTrajectory;
        this.subSampledTrajectory = subSampledTrajectory;
    }

    /***
     * Measures the distance between two GPS trajectories
     * @see Trajectory
     * @return The distance between the two trajectories, converted to meters.
     */
    public double compareEuclidean() {
        ArrayList<GpsFix> internalMappedFixes = new ArrayList<>();
        double distanceSum = 0;
        int subSampledTrajectorySize = subSampledTrajectory.getSize();
        int indexEquivalentFixLeft = 0, indexEquivalentFixRight = 0;
        for (int i = 0; i < subSampledTrajectorySize; i++) {
            // If we are @ last sampled fix, then everything is already done
            if (i + 1 == subSampledTrajectorySize - 1) {
                break;
            }

            // Get anchor points in subsampled trajectory
            GpsFix leftFix = subSampledTrajectory.getFix(i);
            GpsFix rightFix = subSampledTrajectory.getFix(i + 1);

            // Get sub-trajectory of ground truth enclosed by equivalent left and right fixes
            indexEquivalentFixLeft = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i), indexEquivalentFixLeft);
            indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i + 1), indexEquivalentFixLeft);
            Trajectory subTrajectory = groundTruthTrajectory.getSubTrajectory(indexEquivalentFixLeft, indexEquivalentFixRight);

            // 1. Get distance of initial fix in current subtrajectory
            double distanceFirstPoint = subTrajectory.getFix(0).distanceTo(leftFix);
            distanceSum += distanceFirstPoint;

            // 2. Map all of the internal subtrajectory fixes
            internalMappedFixes = mapInternalFixesEuclideanly(subTrajectory, leftFix, rightFix);

            // 3. Get distance for all mapped internal fixes
            double innerDistances = 0;
            int indexInGroundTruth = 1;
            for (GpsFix currentSyntheticFix : internalMappedFixes) {
                GpsFix currentGroundTruthFix = subTrajectory.getFix(indexInGroundTruth);
                innerDistances += currentSyntheticFix.distanceTo(currentGroundTruthFix);
                indexInGroundTruth++;
            }
            distanceSum += innerDistances;
        }

        // 4. Get distance for the last fix that has not been processed
        indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getLast(), indexEquivalentFixRight);
        GpsFix lastFix = groundTruthTrajectory.getTimeClosestFix(subSampledTrajectory.getLast(), indexEquivalentFixRight);
        double distanceLastPoint = subSampledTrajectory.getLast().distanceTo(lastFix);
        distanceSum += distanceLastPoint;

        return distanceSum;
    }

//    public double compareSynchronized() {
//        double distanceSum = 0;
//        for (int i = 0; i < subSampledTrajectory.getSize(); i++) {
//            GpsFix sampledFix = subSampledTrajectory.getFix(i);
//            int indexEquivalentFixLeft = i;
//
//            // If we are @ last sampled fix, then everything is already done
//            if (i + 1 == subSampledTrajectory.getSize()) {
//                break;
//            }
//
//            int indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i + 1), indexEquivalentFixLeft);
//            Trajectory subTrajectory = groundTruthTrajectory.getSubTrajectory(indexEquivalentFixLeft, indexEquivalentFixRight);
//
//            for (int indexCurrentMappedFix = 1; indexCurrentMappedFix < subTrajectory.getSize(); indexCurrentMappedFix++) {
//                GpsFix groundTruthFix = subTrajectory.getFix(indexCurrentMappedFix);
//                GpsFix syntheticFix = mapFixSynchronized(subTrajectory, indexCurrentMappedFix, indexEquivalentFixLeft, indexEquivalentFixRight);
//                distanceSum += groundTruthFix.distanceTo(syntheticFix);
//            }
//        }
//        return distanceSum;
//    }
//
//    private GpsFix mapFixSynchronized(Trajectory subTrajectory, int currentMappedFix, int indexLeftFix, int indexRightFix) {
//        int subTrajectorySize = subTrajectory.getSize();
//
//        double gtInternalDistance = subTrajectory.getInternalDistance();
//        double accumulatedDistanceToCurrentFix = subTrajectory.getSubTrajectory(0, currentMappedFix).getInternalDistance();
//
//        GpsFix ssLeftFix = subSampledTrajectory.getFix(indexLeftFix);
//        GpsFix ssRightFix = subSampledTrajectory.getFix(indexRightFix);
//
//        double k1 = accumulatedDistanceToCurrentFix;
//        double k2 = gtInternalDistance - k1;
//
//        GpsFix mappedGpsFix = buildSyntheticFix(currentMappedFix, ssLeftFix, ssRightFix, k1, k2);
//
//        return mappedGpsFix;
//    }

    /***
     * Measures the distance between two GPS trajectories
     * @see Trajectory
     * @return The distance between the two trajectories, converted to meters.
     */
    public ArrayList<GpsFix> getSyntheticFixesEuclideanly() {
        ArrayList<GpsFix> internalMappedFixes = new ArrayList<>();
        double distanceSum = 0;
        int subSampledTrajectorySize = subSampledTrajectory.getSize();
        int indexEquivalentFixLeft = 0, indexEquivalentFixRight = 0;
        int i = 0;

        // Get anchor points in subsampled trajectory
        GpsFix leftFix = subSampledTrajectory.getFix(i);
        GpsFix rightFix = subSampledTrajectory.getFix(i + 1);

        // Get sub-trajectory of ground truth enclosed by equivalent left and right fixes
        indexEquivalentFixLeft = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i), indexEquivalentFixLeft);
        indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i + 1), indexEquivalentFixLeft);
        Trajectory subTrajectory = groundTruthTrajectory.getSubTrajectory(indexEquivalentFixLeft, indexEquivalentFixRight);

        // 1. Get distance of initial fix in current subtrajectory
        double distanceFirstPoint = subTrajectory.getFix(0).distanceTo(leftFix);
        distanceSum += distanceFirstPoint;

        // 2. Map all of the internal subtrajectory fixes
        internalMappedFixes = mapInternalFixesEuclideanly(subTrajectory, leftFix, rightFix);

        ArrayList<GpsFix> allFixes = new ArrayList<>();
        allFixes.add(leftFix);
        allFixes.addAll(internalMappedFixes);
        allFixes.add(rightFix);
        return allFixes;
    }

    private ArrayList<GpsFix> mapInternalFixesEuclideanly(Trajectory subTrajectory, GpsFix leftFix, GpsFix rightFix) {
        ArrayList<GpsFix> mappedFixes = new ArrayList<>();

        // Map only internal fixes
        int sizeSubTrajectory = subTrajectory.getSize();
        for (int currentMappedFixIndex = 1; currentMappedFixIndex < (sizeSubTrajectory - 1); currentMappedFixIndex++) {
            double k1 = currentMappedFixIndex;
            double k2 = sizeSubTrajectory - k1 - 1;
            GpsFix mappedFix = buildSyntheticFix(currentMappedFixIndex, leftFix, rightFix, k1, k2);
            mappedFixes.add(mappedFix);
        }

        return mappedFixes;
    }


    public ArrayList<GpsFix> getSyntheticFixesSynchronizedly() {
        ArrayList<GpsFix> internalMappedFixes = new ArrayList<>();
        double distanceSum = 0;
        int subSampledTrajectorySize = subSampledTrajectory.getSize();
        int indexEquivalentFixLeft = 0, indexEquivalentFixRight = 0;
        int i = 0;

        // Get anchor points in subsampled trajectory
        GpsFix leftFix = subSampledTrajectory.getFix(i);
        GpsFix rightFix = subSampledTrajectory.getFix(i + 1);

        indexEquivalentFixLeft = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i), indexEquivalentFixLeft);
        indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i + 1), indexEquivalentFixLeft);
        Trajectory subTrajectory = groundTruthTrajectory.getSubTrajectory(indexEquivalentFixLeft, indexEquivalentFixRight);

        double distanceFirstPoint = subTrajectory.getFix(0).distanceTo(leftFix);
        distanceSum += distanceFirstPoint;

        internalMappedFixes = mapInternalFixesSynchronizedly(subTrajectory, leftFix, rightFix);

        ArrayList<GpsFix> allFixes = new ArrayList<>();
        allFixes.add(leftFix);
        allFixes.addAll(internalMappedFixes);
        allFixes.add(rightFix);
        return allFixes;
    }

    private ArrayList<GpsFix> mapInternalFixesSynchronizedly(Trajectory subTrajectory, GpsFix leftFix, GpsFix rightFix) {
        ArrayList<GpsFix> mappedFixes = new ArrayList<>();
        int sizeSubTrajectory = subTrajectory.getSize();

        double groundTruthInternalDistance = subTrajectory.getInternalDistance();
        for (int currentMappedFixIndex=1; currentMappedFixIndex < (sizeSubTrajectory -1); currentMappedFixIndex++) {
            double accumulatedDistanceToCurrentFix = subTrajectory.getSubTrajectory(0, currentMappedFixIndex).getInternalDistance();

            double k1 = accumulatedDistanceToCurrentFix;
            double k2 = groundTruthInternalDistance - k1;

            GpsFix mappedFix = buildSyntheticFix(currentMappedFixIndex, leftFix, rightFix, k1, k2);
            mappedFixes.add(mappedFix);
        }

        return mappedFixes;
    }

    private GpsFix buildSyntheticFix(int currentMappedFix, GpsFix ssInitialFix, GpsFix ssLastFix, double k1, double k2) {
        double mappedLatitude = ((k1 * ssLastFix.getLatitude()) + (k2 * ssInitialFix.getLatitude())) / (k1 + k2);
        double mappedLongitude = ((k1 * ssLastFix.getLongitude()) + (k2 * ssInitialFix.getLongitude())) / (k1 + k2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ssInitialFix.getTimestamp());
        calendar.add(Calendar.SECOND, currentMappedFix);

        return new GpsFix(mappedLatitude, mappedLongitude, 0, 0, 0, calendar.getTime());
    }
}
