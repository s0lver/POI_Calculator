package tamps.cinvestav.s0lver.spCalculator.classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StayPoint {
    private float latitude;
    private float longitude;
    private Date arrivalTime;
    private Date departureTime;
    private int amountFixes;

    private StayPoint(){

    }

    public static StayPoint createStayPoint(ArrayList<GpsFix> list) {
        StayPoint pointOfInterest = new StayPoint();

        int sizeOfList = list.size();
        float sumLatitude = 0;
        float sumLongitude = 0;

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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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
        return String.format("Lat: %f, Long: %f, Arr: %s, Dep: %s, Fixes: %d", getLatitude(), getLongitude(),
                simpleDateFormat.format(getArrivalTime()), simpleDateFormat.format(getDepartureTime()), getAmountFixes());
    }
}
