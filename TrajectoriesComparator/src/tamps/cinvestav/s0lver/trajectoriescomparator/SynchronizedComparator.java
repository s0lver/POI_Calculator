package tamps.cinvestav.s0lver.trajectoriescomparator;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.Trajectory;

/***
 * Measures the time synchronized distance between two different GPS trajectories
 * @see GpsFix
 * @see Trajectory
 */
public class SynchronizedComparator {
    private Trajectory groundTruthTrajectory;
    private Trajectory compressedTrajectory;

    /***
     * Constructor receiving both trajectories
     * @param groundTruthTrajectory The ground truth trajectory to be employed
     * @param compressedTrajectory The sub-sampled trajectory to be employed
     */
    public SynchronizedComparator(Trajectory groundTruthTrajectory, Trajectory compressedTrajectory) {
        this.groundTruthTrajectory = groundTruthTrajectory;
        this.compressedTrajectory = compressedTrajectory;
    }

    /***
     * Measures the distance between two GPS trajectories
     * @see Trajectory
     * @return The distance between the two trajectories, converted to meters.
     */
    public double compareDistance() {
        // TODO complete this method
        double distanceSum = 0;
        for (int i = 0; i < compressedTrajectory.getSize(); i++) {
            GpsFix sampledFix = compressedTrajectory.getFix(i);
            int indexEquivalentFixLeft = i;

            // If we are @ last sampled fix, then everything is already done
            if (i + 1 == compressedTrajectory.getSize()) {
                break;
            }

            int indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(compressedTrajectory.getFix(i + 1), indexEquivalentFixLeft);
            Trajectory subTrajectory = groundTruthTrajectory.getSubTrajectory(indexEquivalentFixLeft, indexEquivalentFixRight);

            for (int indexCurrentMappedFix = 1; indexCurrentMappedFix < subTrajectory.getSize(); indexCurrentMappedFix++) {
                GpsFix groundTruthFix = subTrajectory.getFix(indexCurrentMappedFix);
                GpsFix syntheticFix = mapFix(subTrajectory, indexCurrentMappedFix);
                distanceSum += groundTruthFix.distanceTo(syntheticFix);
            }


        }

        double totalDistance = 0;
        return convertCoordinatesDistanceToMeters(totalDistance);
    }

    private GpsFix mapFix(Trajectory subTrajectory, int indexCurrentMappedFix) {
        // With the subtrajectory refrence you can get its size, instead of using   indexEquivalentFixRight - indexEquivalentFixLeft
        // Also, with the subtrajectory you can get the references to such marker-anchor fixes
        // With the indexCurrentMappedFix you can get the position of the fix to map, and as a consequence the interval in seconds since fixLeft!
        int subTrajectorySize = subTrajectory.getSize();

        GpsFix initialFix = subTrajectory.getFirst();
        GpsFix lastFix = subTrajectory.getLast();


        return null;
    }

    /***
     * Converts a distance expressed in coordinates values to meters
     * @param totalDistance The distance to be converted
     * @return The distance expressed in meters
     */
    private double convertCoordinatesDistanceToMeters(double totalDistance) {
        return 0;
    }
}
