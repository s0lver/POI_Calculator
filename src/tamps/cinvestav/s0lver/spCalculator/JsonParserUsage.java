package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.jsonparser.deserializers.JsonDeserializer;
import tamps.cinvestav.s0lver.jsonparser.parserentities.SensingUnit;
import tamps.cinvestav.s0lver.jsonparser.readers.LocationsAsSensingUnitsReader;
import tamps.cinvestav.s0lver.jsonparser.serializers.JsonSerializer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class JsonParserUsage {
    private JsonDeserializer jsonDeserializer;
    private JsonSerializer jsonSerializer;
    private LocationsAsSensingUnitsReader sensingUnitsReader;
    private String outputJsonFilePath;

    public JsonParserUsage(String rawCsvLocationsFile, String outputJsonFilePath) throws FileNotFoundException {
        sensingUnitsReader = new LocationsAsSensingUnitsReader(rawCsvLocationsFile);
        jsonSerializer = new JsonSerializer(outputJsonFilePath, true);
        this.outputJsonFilePath = outputJsonFilePath;
    }

    public void generateJsonFileFromSingleLocationFile() throws FileNotFoundException {
        List<SensingUnit> sensingUnits = sensingUnitsReader.readFileAsSensorUnits();
        jsonSerializer.serialize(sensingUnits);
    }

    public void playWithSerializeAndDeserialize() throws FileNotFoundException {
        serializeFile();
        jsonDeserializer = new JsonDeserializer(outputJsonFilePath);
        deserializeWholeFile();
        deserializeByUnit();
    }

    private void serializeFile() throws FileNotFoundException {
        List<SensingUnit> sensingUnits = sensingUnitsReader.readFileAsSensorUnits();
        jsonSerializer.serialize(sensingUnits);
    }

    private void deserializeByUnit() throws FileNotFoundException {
        List<SensingUnit> sensingUnitList = new ArrayList<>();

        SensingUnit sensingUnit = jsonDeserializer.readSensingUnit();
        while (sensingUnit != null) {
            sensingUnitList.add(sensingUnit);
            sensingUnit = jsonDeserializer.readSensingUnit();
        }

        printUnits(sensingUnitList);
    }

    private void printUnits(List<SensingUnit> sensingUnitList) {
        for (SensingUnit su : sensingUnitList) {
            su.printUnit();
        }
    }

    private void deserializeWholeFile() throws FileNotFoundException {
        List<SensingUnit> sensingUnitList = jsonDeserializer.deserializeWholeFile();
        if (sensingUnitList != null) {
            printUnits(sensingUnitList);
        } else {
            System.out.println("No sensing units reported by deserializer");
        }
    }
}
