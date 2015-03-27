package tamps.cinvestav.s0lver.poicalculator;

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

    public String toString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return String.format("Lat: %f, Long: %f, Alt: %f, Date: %s", getLatitude(), getLongitude(),
                getAltitude(), simpleDateFormat.format(getTimestamp()));
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
}
