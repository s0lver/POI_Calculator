package tamps.cinvestav.s0lver.jsonparser.serializers;

import tamps.cinvestav.s0lver.jsonparser.ParserConstants;
import tamps.cinvestav.s0lver.jsonparser.parserentities.SensingUnit;
import tamps.cinvestav.s0lver.jsonparser.parserentities.SensorDataBlock;
import tamps.cinvestav.s0lver.jsonparser.sensorentities.SimpleLocation;
import tamps.cinvestav.s0lver.jsonparser.readers.LocationFileReader;
import tamps.cinvestav.s0lver.jsonparser.sensorentities.AccelerometerSample;
import tamps.cinvestav.s0lver.jsonparser.sensorentities.Sensors;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * http://www.journaldev.com/2315/java-json-example
 * Writes a list of {@link SensingUnit} to a JSON compatible file.
 * There must be an implementation of a writeXInJson for writing each value block of any X sensor type.
 * Pay attention to the closing events and the types of values:
 *      location is a single object {"latitude":77,"longitude":44,...} , while
 *      acceleration and others must be an array of object values [{"x":1,"y":1,"z":1},{}...]
 */
public class JsonSerializer {
    private File outputFile;
    private JsonGenerator jsonGenerator;
    private boolean prettyFormatting;

    /***
     * Basic constructor
     * @param outputFilePath The output file path
     * @param prettyFormatting Whether pretty format (tabulated style) is desirable.
     */
    public JsonSerializer(String outputFilePath, boolean prettyFormatting) {
        outputFile = new File(outputFilePath);
        this.prettyFormatting = prettyFormatting;
    }

    /***
     * Instantiates the JsonGenerator depending on whether prettyFormatting is requested
     * @throws FileNotFoundException If output stream could not be created.
     */
    private void instantiateGenerator() throws FileNotFoundException {
        if (prettyFormatting) {
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonGeneratorFactory jf = Json.createGeneratorFactory(properties);
            jsonGenerator = jf.createGenerator(new FileOutputStream(outputFile, false));
        } else {
            jsonGenerator = Json.createGenerator(new FileOutputStream(outputFile, false));
        }
    }

    /***
     * Serializes (writes to file) the list of {@link SensingUnit} specified
     * @param sensingUnitList The list of {@link SensingUnit} to write
     * @throws FileNotFoundException If Parser could not be created.
     */
    public void serialize(List<SensingUnit> sensingUnitList) throws FileNotFoundException {
        instantiateGenerator();

        jsonGenerator.writeStartArray();

        for (SensingUnit sensingUnit : sensingUnitList) {
            jsonGenerator.writeStartObject();

            List<SensorDataBlock> dataBlocks = sensingUnit.getDataBlocks();
            for (SensorDataBlock dataBlock : dataBlocks) {
                jsonGenerator.writeStartObject(String.valueOf(dataBlock.getType()));
                jsonGenerator.write("w", dataBlock.getTimestamp());

                HashMap<String, Object> values = dataBlock.getValues();
                switch (dataBlock.getType()) {
                    case Sensors.ACCELEROMETER:
                        List<AccelerometerSample> accelerationValues = (List<AccelerometerSample>) values.get("values");
                        writeAccelerationsInJson(accelerationValues);
                        break;
                    case Sensors.GPS:
                        SimpleLocation locationFix = (SimpleLocation) values.get("values");
                        writeLocationInJson(locationFix);
                        break;
                }
                jsonGenerator.writeEnd();
            }
            jsonGenerator.writeEnd();
        }

        jsonGenerator.writeEnd();
        jsonGenerator.close();
    }

    /***
     * Writes the accelerations as an array of object values
     * @param accelerationValues The accelerations to write
     */
    private void writeAccelerationsInJson(List<AccelerometerSample> accelerationValues) {
        jsonGenerator.writeStartArray("v");
        for (AccelerometerSample sample : accelerationValues) {
            jsonGenerator.writeStartObject();
            jsonGenerator.write("x", sample.getX());
            jsonGenerator.write("y", sample.getY());
            jsonGenerator.write("z", sample.getZ());
            jsonGenerator.writeEnd();
        }
        jsonGenerator.writeEnd();
    }

    /***
     * Writes the location as a simple JSON object value (i.e., not an array)
     * @param location The location to write
     */
    private void writeLocationInJson(SimpleLocation location) {
        if (location.getProvider().equals(LocationFileReader.TIMED_OUT_LOCATION_PROVIDER)) {
            jsonGenerator.writeNull("v");
        } else {
            jsonGenerator.writeStartObject("v");
            jsonGenerator.write(ParserConstants.LATITUDE, location.getLatitude());
            jsonGenerator.write(ParserConstants.LONGITUDE, location.getLongitude());
            jsonGenerator.write(ParserConstants.ALTITUDE, location.getAltitude());
            jsonGenerator.write(ParserConstants.ACCURACY, location.getAccuracy());
            jsonGenerator.write(ParserConstants.SPEED, location.getSpeed());
            jsonGenerator.writeEnd();
        }
    }
}
