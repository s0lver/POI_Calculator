package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.GPSFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.LoggerReaderFixes;
import tamps.cinvestav.s0lver.iolocationfiles.writers.GpsFixCsvWriter;
import tamps.cinvestav.s0lver.locationentities.GpsFix;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Main {
    public static final int ONE_MINUTE = 60 * 1000;

    public static void main(String[] args) throws IOException, ParseException, TransformerException, ParserConfigurationException {
//         new FrmMain();

//        String rawCsvLocationsFile = "/home/rafael/Documents/experiments/three/ground-truth-fixes.csv";
//        String outputJsonFilePath = "/home/rafael/Desktop/file.json";
        String rawCsvLocationsFile = "/home/rafael/Documents/experiments/learning-truncated/asCsv.csv";
        String outputJsonFilePath = "/home/rafael/Documents/experiments/learning-truncated/json-based-sensing.json";

        boolean generateFakeAccelerometerSamples = true;
        new JsonParserUsage(rawCsvLocationsFile, outputJsonFilePath).generateJsonFileFromSingleLocationFile(generateFakeAccelerometerSamples);
//        new JsonParserUsage(rawCsvLocationsFile, outputJsonFilePath).playWithSerializeAndDeserialize();

        String databaseInputFilePath = "/home/rafael/Documents/experiments/learning-truncated/truncated.db";
        String csvOutputFilePath = "/home/rafael/Documents/experiments/learning-truncated/asCsv.csv";
//        new DbAccessUsage(databaseInputFilePath, csvOutputFilePath).translateActivitiesInDbToCsvLocationsFile();
    }

    /***
     * Translate a Csv file produced by GPS logger software to our version of CSV, usable for all of the modules of this project
     * @throws IOException
     */
    public void howToTranslateFiles() throws IOException {
        GPSFixesFileReader fixesFileReader = new LoggerReaderFixes("/home/rafael/Documents/experiments/three/three-groundtruth.csv");
        ArrayList<GpsFix> gtFixes = fixesFileReader.readFile();

        GpsFixCsvWriter writer = new GpsFixCsvWriter("/home/rafael/Documents/experiments/three/ground-truth-fixes.csv", gtFixes);
        writer.writeFile();
    }
}
