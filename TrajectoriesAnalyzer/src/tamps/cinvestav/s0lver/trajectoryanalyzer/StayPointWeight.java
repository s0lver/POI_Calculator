package tamps.cinvestav.s0lver.trajectoryanalyzer;

/***
 * Models the weight of a stay point in a user trajectory.
 */
public class StayPointWeight {
    private int idStayPoint;
    private long time;
    private double overallPercentage;
    private double relativePercentage;

    public StayPointWeight(int idStayPoint, long time) {
        this.idStayPoint = idStayPoint;
        this.time = time;
    }

    public int getIdStayPoint() {
        return idStayPoint;
    }

    public long getTime() {
        return time;
    }

    public double getOverallPercentage() {
        return overallPercentage;
    }

    public double getRelativePercentage() {
        return relativePercentage;
    }

    /***
     * Increases the stay time of the stay point with the specified time value.
     * @param time The time value to add to the stay time of the stay point. In milliseconds.
     */
    public void addStayTime(long time) {
        this.time += time;
    }

    /***
     * Updates the overall percentage of the stay point.
     * The stay point is assumed to have a  valid time value, so that the comparison with the overall time can be performed
     * and obtain realistic values.
     * @param overallTime The overall time duration of the trajectory this stay point is embedded in.
     */
    public void updateOverallPercentage(long overallTime) {
        this.overallPercentage = time * 100.0 / overallTime;
    }

    /***
     * Updates the percentage of this stay point's stay time in consideration to the overall stay time of the trajectory.
     * @param overallStayTime The overall stay time of the trajectory this stay point is embedded in.
     */
    public void updateRelativePercentage(long overallStayTime) {
        this.relativePercentage = time * 100.0 / overallStayTime;
    }

    @Override
    public String toString() {
        return String.format("SP: #%s ABS weight is %s, REL weight is %s", getIdStayPoint(), getOverallPercentage(), getRelativePercentage());
    }
}
