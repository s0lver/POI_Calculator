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
        for (int i = 0; i < compressedTrajectory.getSize(); i++) {
            GpsFix sampledFix = compressedTrajectory.getFix(i);
            int indexEquivalentFixLeft = i;

            // If we are @ last sampled fix, then everything is already done
            if (i + 1 == compressedTrajectory.getSize()) {
                break;
            }

            int indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(compressedTrajectory.getFix(i + 1), indexEquivalentFixLeft);
            Trajectory subtrajectory = groundTruthTrajectory.getSubtrajectory(indexEquivalentFixLeft, indexEquivalentFixRight);

            for (int j = 1; j < subtrajectory.getSize(); j++) {

            }


        }

        double totalDistance = 0;
        return convertCoordinatesDistanceToMeters(totalDistance);
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
