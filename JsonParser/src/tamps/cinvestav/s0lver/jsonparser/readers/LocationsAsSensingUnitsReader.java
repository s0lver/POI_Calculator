package tamps.cinvestav.s0lver.jsonparser.readers;

import tamps.cinvestav.s0lver.jsonparser.parserentities.SensingUnit;
import tamps.cinvestav.s0lver.jsonparser.parserentities.SensorDataBlock;
import tamps.cinvestav.s0lver.jsonparser.sensorentities.SimpleLocation;
import tamps.cinvestav.s0lver.jsonparser.sensorentities.AccelerometerSample;
import tamps.cinvestav.s0lver.jsonparser.sensorentities.Sensors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * Reads a CSV location file produced by other versions of the platform into a {@link SensingUnit} list.
 */
public class LocationsAsSensingUnitsReader {
    private File fileInput;

    public LocationsAsSensingUnitsReader(String fileInputPath) {
        this.fileInput = new File(fileInputPath);
    }

    /***
     * Reads the file as a list of {@link SensingUnit}
     * @return The content of file as a list of {@link SensingUnit}
     */
    public List<SensingUnit> readFileAsSensorUnits() {
        LocationFileReader reader = new LocationFileReader(fileInput.getAbsolutePath());
        SimpleLocation location = reader.readLine();

        List<SensingUnit> sensingUnitList = new ArrayList<>();
        while (location != null) {

            SensorDataBlock sensorDataBlockLocation = buildLocationDataBlock(location);
            SensorDataBlock sensorDataBlockAccelerometer = buildAccelerometerDataBlock(location.getTime());

            List<SensorDataBlock> sensorDataBlockList = new ArrayList<>();
            sensorDataBlockList.add(sensorDataBlockLocation);
            sensorDataBlockList.add(sensorDataBlockAccelerometer);
            SensingUnit sensingUnit = new SensingUnit(sensorDataBlockList);
            sensingUnitList.add(sensingUnit);

            location = reader.readLine();
        }
        return sensingUnitList;
    }

    /***
     * Internal method: Builds the data block corresponding to the specified location
     * @param location The location from which data block will be built
     * @return The {@link SensorDataBlock} corresponding to the specified location.
     */
    private SensorDataBlock buildLocationDataBlock(SimpleLocation location) {
        HashMap<String, Object> valuesMap = new HashMap<>();
        valuesMap.put("values", location);
        return new SensorDataBlock(Sensors.GPS, location.getTime(), valuesMap);
    }

    /***
     * Internal method: Builds a dummy data block for accelerations.
     * Recall that this platform version's files do not integrated accelerometer readings
     * @param timestamp The timestamp to assign to fake accelerometer readings
     * @return The {@link SensorDataBlock} corresponding to accelerometer readings
     */
    private SensorDataBlock buildAccelerometerDataBlock(long timestamp) {
        HashMap<String, Object> mapValues = new HashMap<>();
        List<AccelerometerSample> accelerometerSamples = buildAccelerometerSamples();
        mapValues.put("values", accelerometerSamples);
        SensorDataBlock accelerometerSensorDataBlock = new SensorDataBlock(Sensors.ACCELEROMETER, timestamp, mapValues);
        return accelerometerSensorDataBlock;

    }

    private List<AccelerometerSample> buildAccelerometerSamples() {
        List<AccelerometerSample> samples = new ArrayList<>();
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));
        samples.add(new AccelerometerSample(0, 0, 0, 0));

        return samples;
    }
}
