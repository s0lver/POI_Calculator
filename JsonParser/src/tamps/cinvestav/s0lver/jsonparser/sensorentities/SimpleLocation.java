package tamps.cinvestav.s0lver.jsonparser.sensorentities;

public class SimpleLocation {
    private String provider;

    private double latitude;
    private double longitude;
    private double altitude;
    private double accuracy;
    private double speed;
    private long time;
    public SimpleLocation(String provider, double latitude, double longitude, double altitude, double accuracy, double speed, long time) {
        this.provider = provider;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.accuracy = accuracy;
        this.speed = speed;
        this.time = time;
    }

    public SimpleLocation(double latitude, double longitude, double altitude, double accuracy, double speed, long time) {
        this(null, latitude, longitude, altitude, accuracy, speed, time);
    }

    public SimpleLocation(String locationProvider) {
        this.provider = locationProvider;
    }

    public String toString() {
        return String.format("lat:%s, long:%s, alt:%s, acc:%s, speed:%s, time:%s", latitude, longitude, altitude, accuracy, speed, time);
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
