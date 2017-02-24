package tamps.cinvestav.s0lver.databaseaccess.dto;

public class DtoActivity {
    private int id;
    private int idDetectedActivity;
    private double latitude;
    private double longitude;
    private double altitude;
    private double accuracy;
    private double speed;
    private long timestamp;
    private double batteryLevel;
    private int chargingType;

    public DtoActivity(int id, int idDetectedActivity, double latitude, double longitude, double altitude, double accuracy, double speed, long timestamp, double batteryLevel, int chargingType) {
        this.id = id;
        this.idDetectedActivity = idDetectedActivity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.accuracy = accuracy;
        this.speed = speed;
        this.timestamp = timestamp;
        this.batteryLevel = batteryLevel;
        this.chargingType = chargingType;
    }

    public int getId() {
        return id;
    }

    public int getIdDetectedActivity() {
        return idDetectedActivity;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getSpeed() {
        return speed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public int getChargingType() {
        return chargingType;
    }
}
