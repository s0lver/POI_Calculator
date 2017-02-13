package tamps.cinvestav.s0lver.jsonparser.deserializers;

import tamps.cinvestav.s0lver.jsonparser.parserentities.SensingUnit;
import tamps.cinvestav.s0lver.jsonparser.parserentities.SensorDataBlock;
import tamps.cinvestav.s0lver.jsonparser.sensorentities.SimpleLocation;
import tamps.cinvestav.s0lver.jsonparser.sensorentities.AccelerometerSample;
import tamps.cinvestav.s0lver.jsonparser.sensorentities.Sensors;

import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static tamps.cinvestav.s0lver.jsonparser.readers.LocationFileReader.TIMED_OUT_LOCATION_PROVIDER;

/***
 * Takes a JSON compatible file and deserializes it, in a single run or block by block.
 * There must be an implementation of a deserializeXBlock for any X sensor type.
 */
public class JsonDeserializer {
    public final static String CUSTOM_PROVIDER = "CustomProvider";
    private final JsonParser jsonParser;
    private boolean fileDone;

    public JsonDeserializer(String filePath) throws FileNotFoundException {
        File fileInput = new File(filePath);
        this.fileDone = false;
        this.jsonParser = Json.createParser(new FileInputStream(fileInput));
    }

    /***
     * Deserializes the whole specified file in a single step.
     * @return The list of {@link SensingUnit} existing on file.
     */
    public List<SensingUnit> deserializeWholeFile() {
        if (fileDone) {
            return null;
        }

        List<SensingUnit> sensingUnitList = new ArrayList<>();
        while (true) {
            JsonParser.Event event = jsonParser.next();
            if (event == JsonParser.Event.START_ARRAY) {
                // Start of file, the root [
                continue;
            }
            else if (event == JsonParser.Event.END_ARRAY) {
                // EOF found
                jsonParser.close();
                break;
            } else {
                SensingUnit sensingUnit = acquireNextSensingUnit();
                sensingUnitList.add(sensingUnit);
            }
        }

        fileDone = true;
        return sensingUnitList;
    }

    /***
     * Reads the next {@link SensingUnit} in specified file
     * @return The next SensingUnit or null if EOF has been reached.
     */
    public SensingUnit readSensingUnit() {
        if (fileDone) {
            return null;
        }

        JsonParser.Event event = jsonParser.next();
        // Start of file, the root [
        if (event == JsonParser.Event.START_ARRAY) {
            // Then read another one
            jsonParser.next();
        }

        if (event == JsonParser.Event.END_ARRAY) {
            // EOF found
            fileDone = true;
            jsonParser.close();
            return null;
        }

        return acquireNextSensingUnit();
    }

    /***
     * Internal method. Given the current cursor (place) of parser, it reads the next sensing unit
     * @return The next {@link SensingUnit} on file or null if EOF has been reached.
     */
    private SensingUnit acquireNextSensingUnit() {
        List<SensorDataBlock> sensorDataBlockList = new ArrayList<>();
        SensorDataBlock sensorDataBlock;

        // Reading all sensor data blocks
        while (true) {
            // Reading the type
            JsonParser.Event event = jsonParser.next();

            // Checking end of sensing unit
            if (event == JsonParser.Event.END_OBJECT) {
                break;
            }

            byte type = Byte.valueOf(jsonParser.getString());

            switch (type) {
                case Sensors.GPS:
                    sensorDataBlock = deserializeLocationBlock(jsonParser);
                    sensorDataBlockList.add(sensorDataBlock);
                    break;
                case Sensors.ACCELEROMETER:
                    sensorDataBlock = deserializeAccelerometerBlock(jsonParser);
                    sensorDataBlockList.add(sensorDataBlock);
                    break;
            }
        }

        return new SensingUnit(sensorDataBlockList);
    }

    /***
     * Deserializes (reads) a data block corresponding to a Location in the current position of parser
     * @return The {@link SensorDataBlock} corresponding to a Location
     */
    private SensorDataBlock deserializeLocationBlock(JsonParser jsonParser) {
        // Reading the beginning of object
        jsonParser.next();
        jsonParser.next();
        jsonParser.next();
        long timestamp = jsonParser.getLong();

        // Reading the beginning of "v"
        jsonParser.next();

        SimpleLocation tmpLocation;

        JsonParser.Event next = jsonParser.next();
        if (next == JsonParser.Event.VALUE_NULL) {
            tmpLocation = new SimpleLocation(TIMED_OUT_LOCATION_PROVIDER);
            tmpLocation.setTime(timestamp);
        }
        else{
            tmpLocation = new SimpleLocation(CUSTOM_PROVIDER);

            jsonParser.next();
            jsonParser.next();
            double latitude = jsonParser.getBigDecimal().doubleValue();

            jsonParser.next();
            jsonParser.next();
            double longitude = jsonParser.getBigDecimal().doubleValue();

            jsonParser.next();
            jsonParser.next();
            double altitude = jsonParser.getBigDecimal().doubleValue();

            jsonParser.next();
            jsonParser.next();
            double accuracy = jsonParser.getBigDecimal().doubleValue();

            jsonParser.next();
            jsonParser.next();
            double speed = jsonParser.getBigDecimal().doubleValue();

            tmpLocation.setLatitude(latitude);
            tmpLocation.setLongitude(longitude);
            tmpLocation.setAltitude(altitude);
            tmpLocation.setAccuracy((float) accuracy);
            tmpLocation.setSpeed((float) speed);
            tmpLocation.setTime(timestamp);

            // Read closing }
            jsonParser.next();
        }

        HashMap<String, Object> values = new HashMap<>();
        values.put("values", tmpLocation);

        SensorDataBlock sensorDataBlock = new SensorDataBlock(Sensors.GPS, timestamp, values);

        // Read closing }
        jsonParser.next();

        return sensorDataBlock;
    }

    /***
     * Deserializes (reads) a data block corresponding to a set of accelerometer records in the current position of parser
     * @return The {@link SensorDataBlock} corresponding to a list of accelerometer records.
     */
    private SensorDataBlock deserializeAccelerometerBlock(JsonParser jsonParser) {
        // Reading the beginning of object
        jsonParser.next();
        jsonParser.next();
        jsonParser.next();
        long timestamp = jsonParser.getLong();

        // Reading the beginning of "v"
        jsonParser.next();

        // Reading the beginning of array
        jsonParser.next();

        List<AccelerometerSample> accelerometerSamples = new ArrayList<>();

        while (true) {
            // Reading token
            JsonParser.Event next = jsonParser.next();

            // Is it the end of array? then exit!
            if (next == JsonParser.Event.END_ARRAY) {
                break;
            }else{
                // Then it is the beginning of an accelerometer object
                // Reading key "x"
                jsonParser.next();
                // Reading value x
                jsonParser.next();
                float x = jsonParser.getBigDecimal().floatValue();

                // Reading key "y"
                jsonParser.next();
                // Reading value y
                jsonParser.next();
                float y = jsonParser.getBigDecimal().floatValue();

                // Reading key "z"
                jsonParser.next();
                // Reading value z
                jsonParser.next();
                float z = jsonParser.getBigDecimal().floatValue();

                accelerometerSamples.add(new AccelerometerSample(x, y, z, timestamp));

                // End of current object
                jsonParser.next();
            }
        }

        // We are currently at ], we need to read closing }
        jsonParser.next();

        HashMap<String, Object> values = new HashMap<>();
        values.put("values", accelerometerSamples);

        return new SensorDataBlock(Sensors.ACCELEROMETER, timestamp, values);
    }

    private void printEventType(JsonParser.Event next) {
        switch (next) {
            case END_ARRAY:
                System.out.println("end array");
                break;
            case END_OBJECT:
                System.out.println("End object");
                break;
            case KEY_NAME:
                System.out.println("Key name");
                break;
            case START_ARRAY:
                System.out.println("Start array");
                break;
            case START_OBJECT:
                System.out.println("Start object");
                break;
            case VALUE_FALSE:
                System.out.println("Value false");
                break;
            case VALUE_NULL:
                System.out.println("Value null");
                break;
            case VALUE_NUMBER:
                System.out.println("Value number");
                break;
            case VALUE_STRING:
                System.out.println("Value string");
                break;
            case VALUE_TRUE:
                System.out.println("Value true");
                break;
        }
    }
}
