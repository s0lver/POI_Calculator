package tamps.cinvestav.s0lver.jsonparser.sensorentities;

/**
 * Defines the sensors supported by the platform and helpful to identify the target sensor when scheduling samples.
 */
public class Sensors {
    public static final byte UNKNOWN = 0x0;
    public static final byte GPS = 0x1;
    public static final byte ACCELEROMETER = 0x2;

    /***
     * Indicates whether the specified sensor type is recognized by the platform
     * @param sensorType The sensor type to validate
     * @return true if the sensor is recognized, false otherwise
     */
    public static boolean isRecognized(byte sensorType){
        switch (sensorType) {
            case GPS:
            case ACCELEROMETER:
                return true;
            default:
                return false;
        }
    }


    /***
     * Obtains a String representation of the given sensor type
     * @param sensorType The sensor type recognized by the classifier
     * @return A String representation of the sensor type
     */
    public static String getAsString(byte sensorType) {
        if (!isRecognized(sensorType)) return "Unknown";
        switch (sensorType) {
            case GPS:
                return "GPS";
            case ACCELEROMETER:
                return "Accelerometer";
            default:
                return "Unknown";
        }
    }
}
