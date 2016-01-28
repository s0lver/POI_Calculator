package tamps.cinvestav.s0lver.stayPointsComparator.comparatorResults;

public class StayPointsComparatorResult {
    private long arrivalTimeDifference;
    private long departureTimeDifference;
    private long stayTimeDifference;
    private double distanceDifference;

    public StayPointsComparatorResult(long arrivalTimeDifference, long departureTimeDifference, long stayTimeDifference, double distanceDifference) {
        this.arrivalTimeDifference = arrivalTimeDifference;
        this.departureTimeDifference = departureTimeDifference;
        this.stayTimeDifference = stayTimeDifference;
        this.distanceDifference = distanceDifference;
    }

    public long getArrivalTimeDifference() {
        return arrivalTimeDifference;
    }

    public void setArrivalTimeDifference(long arrivalTimeDifference) {
        this.arrivalTimeDifference = arrivalTimeDifference;
    }

    public long getDepartureTimeDifference() {
        return departureTimeDifference;
    }

    public void setDepartureTimeDifference(long departureTimeDifference) {
        this.departureTimeDifference = departureTimeDifference;
    }

    public long getStayTimeDifference() {
        return stayTimeDifference;
    }

    public void setStayTimeDifference(long stayTimeDifference) {
        this.stayTimeDifference = stayTimeDifference;
    }

    public double getDistanceDifference() {
        return distanceDifference;
    }

    public void setDistanceDifference(double distanceDifference) {
        this.distanceDifference = distanceDifference;
    }
}
