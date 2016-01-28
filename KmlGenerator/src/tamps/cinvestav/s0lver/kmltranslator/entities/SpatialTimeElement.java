package tamps.cinvestav.s0lver.kmltranslator.entities;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

import java.util.Date;

public class SpatialTimeElement {
    private double longitude;
    private double latitude;
    private double altitude;
    private double accuracy;
    private Date arrivalTime;
    private Date departureTime;

    public SpatialTimeElement(GpsFix gpsFix) {
        this.longitude = gpsFix.getLongitude();
        this.latitude = gpsFix.getLatitude();
        this.altitude = gpsFix.getAltitude();
        this.accuracy = gpsFix.getAccuracy();
        this.arrivalTime = gpsFix.getTimestamp();
        this.departureTime = gpsFix.getTimestamp();
    }

    public SpatialTimeElement(StayPoint stayPoint) {
        this.longitude = stayPoint.getLongitude();
        this.latitude = stayPoint.getLatitude();
        this.altitude = 0;
        this.accuracy = 0;
        this.arrivalTime = stayPoint.getArrivalTime();
        this.departureTime = stayPoint.getDepartureTime();
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public boolean isStayPoint(){
        return (getArrivalTime() != getDepartureTime());
    }
}
