package tamps.cinvestav.s0lver.jsonparser.sensorentities;

/***
 * Represents a record of an accelerometer sample
 */
public class AccelerometerSample{
    private float x, y, z;
    private long timestamp;
    private byte type;

    public AccelerometerSample(float x, float y, float z, long timestamp) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z + "," + timestamp;
    }
}
