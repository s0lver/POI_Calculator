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
        GpsFix firstFixInCompressedTrj = compressedTrajectory.getFirst();
        double distanceFirstFix = firstFixInCompressedTrj.distanceTo(groundTruthTrajectory.getFirst());

        int compressedTrjSize = compressedTrajectory.getSize();
        double internalDistances = 0;
        for (int i = 1; i < compressedTrjSize - 1; i++) {
            // TODO falta interpolar los puntos de la compressedTrajectory para compararlos con los equivalentes en la trayectoria groundtruth
            GpsFix currentFix = compressedTrajectory.getFix(i);
            GpsFix timeNearestFix = groundTruthTrajectory.getTimeClosestFix(currentFix, 1);
            double distanceBetweenFixes = currentFix.distanceTo(timeNearestFix);
            internalDistances += distanceBetweenFixes;
        }

        GpsFix lastFixInCompressedTrj = compressedTrajectory.getLast();
        double distanceLastFix = lastFixInCompressedTrj.distanceTo(groundTruthTrajectory.getLast());

        double totalDistance = distanceFirstFix + internalDistances + distanceLastFix;
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
