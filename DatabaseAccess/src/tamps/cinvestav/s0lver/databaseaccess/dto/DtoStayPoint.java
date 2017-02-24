package tamps.cinvestav.s0lver.databaseaccess.dto;

public class DtoStayPoint {
    private int id;
    private double latitude;
    private double longitude;
    private int visitCount;

    public DtoStayPoint(int id, double latitude, double longitude, int visitCount) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.visitCount = visitCount;
    }

    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getVisitCount() {
        return visitCount;
    }

    @Override
    public String toString() {
        return String.format("#%s, %s,%s, visitCount %s", id, latitude, longitude, visitCount);
    }
}
