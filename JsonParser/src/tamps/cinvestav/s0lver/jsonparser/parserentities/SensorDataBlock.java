package tamps.cinvestav.s0lver.jsonparser.parserentities;

import java.util.HashMap;

public class SensorDataBlock {
    private byte type;
    private long timestamp;
    private HashMap<String, Object> values;

    public SensorDataBlock(byte type, long timestamp, HashMap<String, Object> values) {
        this.type = type;
        this.timestamp = timestamp;
        this.values = values;
    }

    public byte getType() {
        return type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public HashMap<String, Object> getValues() {
        return values;
    }
}
