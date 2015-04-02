package tamps.cinvestav.s0lver.spCalculator.classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GpsFix {
    private float latitude;
    private float longitude;
    private float altitude;
    private float accuracy;
    private Date timestamp;

    public GpsFix(float latitude, float longitude, float altitude, Date timestamp, float accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timestamp = timestamp;
        this.accuracy = accuracy;
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

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String toString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return String.format("Lat: %f, Long: %f, Alt: %f, Acc: %f, Date: %s", getLatitude(), getLongitude(),
                getAltitude(), getAccuracy(), simpleDateFormat.format(getTimestamp()));
    }

    public String toCsv(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return String.format("%f,%f,%f,%f,%s", getLatitude(), getLongitude(),
                getAltitude(), getAccuracy(), simpleDateFormat.format(getTimestamp()));
    }

}
