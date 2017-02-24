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
    private final static boolean OBTAINED = true;

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
     * Measures the distance between two GPS trajectories using only geometric aspects (the ground truth fixes are
     * mapped in equal-length portions in the sub-sampled trajectory)
     * @return The distance between the two trajectories (meters).
     * @see GpsFix
     * @see Trajectory
     */
    public double compareEuclidean() {
        ArrayList<GpsFix> internalMappedFixes = new ArrayList<>();
        double distanceSum = 0;
        int subSampledTrajectorySize = subSampledTrajectory.getSize();
        int indexEquivalentFixLeft = 0, indexEquivalentFixRight = 0;
        for (int i = 0; i < subSampledTrajectorySize; i++) {
            // If we are @ last sampled fix, then everything is already done
            if (i + 1 == subSampledTrajectorySize) {
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

    /***
     * Gets the euclidean synthetic fixes for current trajectories.
     * It includes the genuine sub-sampled anchor fixes from where the synthetic fixes are generated.
     * The mapping is done considering only geometric aspects (the ground truth fixes are mapped in equal-length portions)
     * @return An ArrayList of GpsFix with the mapped synthetic fixes
     * @see GpsFix
     * @see Trajectory
     */
    public ArrayList<GpsFix> getEuclideanSyntheticFixes() {
        ArrayList<GpsFix> allFixes = new ArrayList<>();
        ArrayList<GpsFix> internalMappedFixes = new ArrayList<>();
        int subSampledTrajectorySize = subSampledTrajectory.getSize();
        int indexEquivalentFixLeft = 0, indexEquivalentFixRight = 0;
        for (int i = 0; i < subSampledTrajectorySize; i++) {
            // If we are @ last sampled fix, then everything is already done
            if (i + 1 == subSampledTrajectorySize ) {
                break;
            }

            // Get anchor points in sub-sampled trajectory
            GpsFix leftFix = subSampledTrajectory.getFix(i);
            GpsFix rightFix = subSampledTrajectory.getFix(i + 1);

            // Get sub-trajectory of ground truth enclosed by equivalent left and right fixes
            indexEquivalentFixLeft = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i), indexEquivalentFixLeft);
            indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i + 1), indexEquivalentFixLeft);
            Trajectory subTrajectory = groundTruthTrajectory.getSubTrajectory(indexEquivalentFixLeft, indexEquivalentFixRight);

            // Map all of the internal sub-trajectory fixes
            internalMappedFixes = mapInternalFixesEuclideanly(subTrajectory, leftFix, rightFix);

            allFixes.add(leftFix);
            allFixes.addAll(internalMappedFixes);
        }

        indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getLast(), indexEquivalentFixRight);
        GpsFix lastFix = groundTruthTrajectory.getTimeClosestFix(subSampledTrajectory.getLast(), indexEquivalentFixRight);
        allFixes.add(lastFix);
        return allFixes;
    }

    /***
     * Maps the existing fixes of the sub-trajectory into the sub-sampled segment specified by left and right fix parameters
     * @param subTrajectory The ground-truth sub-trajectory from where the fixes will be mapped
     * @param leftFix The starting fix of the sub-trajectory segment
     * @param rightFix The ending fix of the sub-trajectory segment
     * @return An ArrayList of GpsFix with the mapped fixes
     * @see GpsFix
     * @see Trajectory
     */
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

    /***
     * Measures the distance between the two GPS trajectories using time aspects (the ground truth fixes are mapped in
     * portions whose size depends on the time (velocity) weight they represent in the ground truth trajectory
     * @return The distance between trajectories
     * @see GpsFix
     * @see Trajectory
     */
    public double compareSynchronized() {
        ArrayList<GpsFix> internalMappedFixes;
        double distanceSum = 0;
        int subSampledTrajectorySize = subSampledTrajectory.getSize();
        int indexEquivalentFixLeft = 0, indexEquivalentFixRight = 0;
        for (int i = 0; i < subSampledTrajectorySize; i++) {
            // If we are @ last sampled fix, then everything is already done
            if (i + 1 == subSampledTrajectorySize) {
                break;
            }

            // Get anchor points in sub-sampled trajectory
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
            internalMappedFixes = mapInternalFixesSynchronizedly(subTrajectory, leftFix, rightFix);

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

    /***
     * Gets the synchronized synthetic fixes for current trajectories.
     * It includes the genuine sub-sampled anchor fixes from where the synthetic fixes are generated.
     * The mapping is done considering geometric and time aspects (the ground truth fixes are mapped in portions whose
     * size depends on their time proportion in the ground truth trajectory)
     * @return An ArrayList of GpsFix with the mapped synthetic fixes
     * @see GpsFix
     * @see Trajectory
     */
    public ArrayList<GpsFix> getSynchronizedSyntheticFixes() {
        ArrayList<GpsFix> allFixes = new ArrayList<>();
        ArrayList<GpsFix> internalMappedFixes;
        int subSampledTrajectorySize = subSampledTrajectory.getSize();
        int indexEquivalentFixLeft = 0, indexEquivalentFixRight = 0;
        for (int i = 0; i < subSampledTrajectorySize; i++) {
            // If we are @ last sampled fix, then everything is already done
            if (i + 1 == subSampledTrajectorySize) {
                break;
            }

            // Get anchor points in sub-sampled trajectory
            GpsFix leftFix = subSampledTrajectory.getFix(i);
            GpsFix rightFix = subSampledTrajectory.getFix(i + 1);

            // Get sub-trajectory of ground truth enclosed by equivalent left and right fixes
            indexEquivalentFixLeft = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i), indexEquivalentFixLeft);
            indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i + 1), indexEquivalentFixLeft);
            Trajectory subTrajectory = groundTruthTrajectory.getSubTrajectory(indexEquivalentFixLeft, indexEquivalentFixRight);

            internalMappedFixes = mapInternalFixesSynchronizedly(subTrajectory, leftFix, rightFix);
            allFixes.add(leftFix);
            allFixes.addAll(internalMappedFixes);
        }

        indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getLast(), indexEquivalentFixRight);
        GpsFix lastFix = groundTruthTrajectory.getTimeClosestFix(subSampledTrajectory.getLast(), indexEquivalentFixRight);
        allFixes.add(lastFix);
        return allFixes;
    }

    /***
     * Maps the existing fixes of the sub-trajectory into the sub-sampled segment specified by left and right fix parameters
     * @param subTrajectory The ground-truth sub-trajectory from where the fixes will be mapped
     * @param leftFix The starting fix of the sub-trajectory segment
     * @param rightFix The ending fix of the sub-trajectory segment
     * @return An ArrayList of GpsFix with the mapped fixes
     * @see GpsFix
     * @see Trajectory
     */
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

    /***
     * Builds a synthetic GPS fix according to the formula for splitting line segments:
     * x = [(k1 * p2_x) + (k2 * p1_x)] / (k1 + k2) and
     * y = [(k1 * p2_y) + (k2 * p1_y)] / (k1 + k2)
     * The line segment is divided into k1+k2 same-length parts and the synthetic fix is generated in the k1 slot
     * Moar info <a href="http://www.teacherschoice.com.au/Maths_Library/Analytical%20Geometry/AnalGeom_3.htm">here</a>
     * For instance
     * k1=1, k2=4 | * - - - |
     * k1=2, k2=3 | - * - - |
     * k1=3, k2=2 | - - * - |
     * k1=4, k2=1 | - - - * |
     * @param currentMappedFix For setting the time of the generated fix (Logger frequency is at 1Hz)
     * @param ssInitialFix The left anchor fix in the current segment of sub-sampled trajectory
     * @param ssLastFix The right anchor fix in the current segment of sub-sampled trajectory
     * @param k1               The numerator of the fraction (proportion) corresponding to the fix to be generated.
     * @param k2               The denominator of the fraction (proportion) corresponding to the fix to be generated.
     * @return A synthetic fix
     * @see GpsFix
     */
    private GpsFix buildSyntheticFix(int currentMappedFix, GpsFix ssInitialFix, GpsFix ssLastFix, double k1, double k2) {
        double mappedLatitude = ((k1 * ssLastFix.getLatitude()) + (k2 * ssInitialFix.getLatitude())) / (k1 + k2);
        double mappedLongitude = ((k1 * ssLastFix.getLongitude()) + (k2 * ssInitialFix.getLongitude())) / (k1 + k2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ssInitialFix.getTimestamp());
        calendar.add(Calendar.SECOND, currentMappedFix);

        return new GpsFix(OBTAINED, mappedLatitude, mappedLongitude, 0, 0, 0, calendar.getTime());
    }

}
