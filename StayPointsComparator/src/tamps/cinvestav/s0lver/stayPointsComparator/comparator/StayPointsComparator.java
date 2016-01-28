package tamps.cinvestav.s0lver.stayPointsComparator.comparator;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsComparator.comparatorResults.StayPointsComparatorResult;

public class StayPointsComparator {
    private StayPoint stayPointA;
    private StayPoint stayPointB;

    public StayPointsComparator(StayPoint stayPointA, StayPoint stayPointB) {
        this.stayPointA = stayPointA;
        this.stayPointB = stayPointB;
    }

    // TODO this might be embedded on the StayPoint class, however I think it is out of the scope of that entity
    public StayPointsComparatorResult compareStayPoints() {
        long arrivalTimeDifference = stayPointB.getArrivalTime().getTime() - stayPointA.getArrivalTime().getTime();
        long departureTimeDifference = stayPointB.getDepartureTime().getTime() - stayPointA.getDepartureTime().getTime();

        long stayTimeStayPointA = stayPointA.getDepartureTime().getTime() - stayPointA.getArrivalTime().getTime();
        long stayTimeStayPointB = stayPointB.getDepartureTime().getTime() - stayPointB.getArrivalTime().getTime();
        long stayTimeDifference = stayTimeStayPointB - stayTimeStayPointA;

        double distanceDifference = computeDistanceBetweenStayPoints();

        return new StayPointsComparatorResult(arrivalTimeDifference, departureTimeDifference, stayTimeDifference, distanceDifference);
    }

    private double computeDistanceBetweenStayPoints() {
        GpsFix gpsFixA = new GpsFix(stayPointA.getLatitude(), stayPointA.getLongitude(), 0, 0, 0, stayPointA.getArrivalTime());
        GpsFix gpsFixB = new GpsFix(stayPointB.getLatitude(), stayPointB.getLongitude(), 0, 0, 0, stayPointB.getArrivalTime());

        return gpsFixA.distanceTo(gpsFixB);
    }
}
