package tamps.cinvestav.s0lver.trajectoriescomparator;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.Trajectory;

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
        double distanceSum = 0;
        int subSampledTrajectorySize = subSampledTrajectory.getSize();
        for (int i = 0; i < subSampledTrajectorySize; i++) {
            GpsFix sampledFix = subSampledTrajectory.getFix(i);
            int indexEquivalentFixLeft = i;

            // If we are @ last sampled fix, then everything is already done
            if (i + 1 == subSampledTrajectorySize) {
                break;
            }

            int indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i + 1), indexEquivalentFixLeft);
            Trajectory subTrajectory = groundTruthTrajectory.getSubTrajectory(indexEquivalentFixLeft, indexEquivalentFixRight);

            for (int indexCurrentMappedFix = 1; indexCurrentMappedFix < subTrajectory.getSize(); indexCurrentMappedFix++) {
                GpsFix groundTruthFix = subTrajectory.getFix(indexCurrentMappedFix);
                GpsFix syntheticFix = mapFixEuclidean(subTrajectory, indexCurrentMappedFix, i, i + 1);
                distanceSum += groundTruthFix.distanceTo(syntheticFix);
            }
        }
        return distanceSum;
    }

    public double compareSynchronized() {
        double distanceSum = 0;
        for (int i = 0; i < subSampledTrajectory.getSize(); i++) {
            GpsFix sampledFix = subSampledTrajectory.getFix(i);
            int indexEquivalentFixLeft = i;

            // If we are @ last sampled fix, then everything is already done
            if (i + 1 == subSampledTrajectory.getSize()) {
                break;
            }

            int indexEquivalentFixRight = groundTruthTrajectory.getTimeClosestFixIndex(subSampledTrajectory.getFix(i + 1), indexEquivalentFixLeft);
            Trajectory subTrajectory = groundTruthTrajectory.getSubTrajectory(indexEquivalentFixLeft, indexEquivalentFixRight);

            for (int indexCurrentMappedFix = 1; indexCurrentMappedFix < subTrajectory.getSize(); indexCurrentMappedFix++) {
                GpsFix groundTruthFix = subTrajectory.getFix(indexCurrentMappedFix);
                GpsFix syntheticFix = mapFixSynchronized(subTrajectory, indexCurrentMappedFix, indexEquivalentFixLeft, indexEquivalentFixRight);
                distanceSum += groundTruthFix.distanceTo(syntheticFix);
            }
        }
        return distanceSum;
    }

    private GpsFix mapFixSynchronized(Trajectory subTrajectory, int currentMappedFix, int indexLeftFix, int indexRightFix) {
        int subTrajectorySize = subTrajectory.getSize();

        double gtInternalDistance = subTrajectory.getInternalDistance();
        double accumulatedDistanceToCurrentFix = subTrajectory.getSubTrajectory(0, currentMappedFix).getInternalDistance();

        GpsFix ssLeftFix = subSampledTrajectory.getFix(indexLeftFix);
        GpsFix ssRightFix = subSampledTrajectory.getFix(indexRightFix);

        double k1 = accumulatedDistanceToCurrentFix;
        double k2 = gtInternalDistance - k1;

        GpsFix mappedGpsFix = buildSyntheticFix(currentMappedFix, ssLeftFix, ssRightFix, k1, k2);

        return mappedGpsFix;
    }

    private GpsFix mapFixEuclidean(Trajectory subTrajectory, int currentMappedFix, int indexLeftFix, int indexRightFix) {
        int subTrajectorySize = subTrajectory.getSize();

        GpsFix ssLeftFix = subSampledTrajectory.getFix(indexLeftFix);
        GpsFix ssRightFix = subSampledTrajectory.getFix(indexRightFix);

        // Although based on time, here k1 and k2 are expressing only geometric aspects
        int k1 = currentMappedFix;
        int k2 = subTrajectorySize - k1;
        // we know that subTrajectorySize = k1 + k2
        GpsFix mappedGpsFix = buildSyntheticFix(currentMappedFix, ssLeftFix, ssRightFix, k1, k2);

        return mappedGpsFix;
    }

    private GpsFix buildSyntheticFix(int currentMappedFix, GpsFix ssInitialFix, GpsFix ssLastFix, double k1, double k2) {
        double mappedLatitude = ((k1 * ssLastFix.getLatitude()) + (k2 * ssInitialFix.getLatitude())) / (k1 + k2);
        double mappedLongitude = ((k1 * ssLastFix.getLongitude()) + (k2 * ssInitialFix.getLongitude())) / (k1 + k2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ssInitialFix.getTimestamp());
        calendar.add(Calendar.SECOND, currentMappedFix);

        return new GpsFix(mappedLatitude, mappedLongitude, 0, 0, 0, calendar.getTime());
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
