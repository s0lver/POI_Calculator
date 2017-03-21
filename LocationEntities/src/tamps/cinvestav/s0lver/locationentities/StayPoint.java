package tamps.cinvestav.s0lver.locationentities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StayPoint {
    private double latitude;

    private double longitude;
    private Date arrivalTime;
    private Date departureTime;
    private int amountFixes;

    private StayPoint() {    }


    public StayPoint(double latitude, double longitude, Date arrivalTime, Date departureTime, int amountFixes) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.amountFixes = amountFixes;
    }


    public static StayPoint createStayPoint(ArrayList<GpsFix> list) {
        StayPoint pointOfInterest = new StayPoint();

        int sizeOfList = list.size();
        double sumLatitude = 0;
        double sumLongitude = 0;

        for (int i = 0; i < sizeOfList; i++) {
            sumLatitude += list.get(i).getLatitude();
            sumLongitude += list.get(i).getLongitude();
        }

        pointOfInterest.setLatitude(sumLatitude / sizeOfList);
        pointOfInterest.setLongitude(sumLongitude / sizeOfList);
        pointOfInterest.setArrivalTime(list.get(0).getTimestamp());
        pointOfInterest.setDepartureTime(list.get(sizeOfList - 1).getTimestamp());
        pointOfInterest.setAmountFixes(sizeOfList);

        return pointOfInterest;
    }

    public static StayPoint createStayPoint(ArrayList<GpsFix> list, int i, int j) {
        int sizeOfListPortion = j - i + 1;

        if (sizeOfListPortion == 0) {
            throw new RuntimeException("List provided is empty");
        }

        double sumLat = 0.0, sumLng = 0.0;
        for (int h = i; h <= j; h++) {
            sumLat += list.get(h).getLatitude();
            sumLng += list.get(h).getLongitude();
        }

        StayPoint pointOfInterest = new StayPoint();
        pointOfInterest.setLatitude((float) (sumLat / sizeOfListPortion));
        pointOfInterest.setLongitude((float) (sumLng / sizeOfListPortion));
        pointOfInterest.setArrivalTime(list.get(i).getTimestamp());
        pointOfInterest.setDepartureTime(list.get(j).getTimestamp());
        pointOfInterest.setAmountFixes(sizeOfListPortion);

        return pointOfInterest;
    }

    public static StayPoint createStayPoint(double sigmaLatitude, double sigmaLongitude, Date arrivalTime, Date departureTime, int amountFixes) {
        StayPoint stayPoint = new StayPoint();
        stayPoint.setAmountFixes(amountFixes);
        stayPoint.setLatitude((float) (sigmaLatitude / amountFixes));
        stayPoint.setLongitude((float) (sigmaLongitude / amountFixes));
        stayPoint.setArrivalTime(arrivalTime);
        stayPoint.setDepartureTime(departureTime);

        System.out.println("Creating with sigma lat " + sigmaLatitude + " and sigma lon " + sigmaLongitude + " and amnt " + amountFixes);
        return stayPoint;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public int getAmountFixes() {
        return amountFixes;
    }

    public void setAmountFixes(int amountFixes) {
        this.amountFixes = amountFixes;
    }

    public String toString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return String.format("Lat: %s, Long: %s, Arr: %s, Dep: %s, Fixes: %d", getLatitude(), getLongitude(),
                simpleDateFormat.format(getArrivalTime()), simpleDateFormat.format(getDepartureTime()), getAmountFixes());
    }
}
