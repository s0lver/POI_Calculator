package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.ExportedDatabaseGpsFixesReader;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.GPSFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.LoggerReaderFixes;
import tamps.cinvestav.s0lver.iolocationfiles.writers.GpsFixCsvWriter;
import tamps.cinvestav.s0lver.iolocationfiles.writers.StayPointCsvWriter;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.buffered.ZhengBufferedAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.ZhenAlgorithm;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static final int ONE_MINUTE = 60 * 1000;

    public static void main(String[] args) throws IOException, ParseException, TransformerException, ParserConfigurationException {
//         new FrmMain();

//        String rawCsvLocationsFile = "/home/rafael/Documents/experiments/three/ground-truth-fixes.csv";
//        String outputJsonFilePath = "/home/rafael/Desktop/file.json";


//        String databaseInputFilePath = "C:\\Users\\Rafael\\Documents\\experiments\\five\\nexus-1.db";
//        String csvOutputFilePath = "C:\\Users\\Rafael\\Desktop\\nexus-1.csv";
//        new DbAccessUsage(databaseInputFilePath, csvOutputFilePath).translateActivitiesInDbToCsvLocationsFile();
//
//        String rawCsvLocationsFile = "C:\\Users\\Rafael\\Desktop\\nexus-1.csv";
//        String outputJsonFilePath = "C:\\Users\\Rafael\\Desktop\\json-based-sensing.json";
//        boolean generateFakeAccelerometerSamples = false;
//        new JsonParserUsage(rawCsvLocationsFile, outputJsonFilePath).generateJsonFileFromSingleLocationFile(generateFakeAccelerometerSamples);
//        new JsonParserUsage(rawCsvLocationsFile, outputJsonFilePath).playWithSerializeAndDeserialize(false);

        //String databaseInputFilePath = "C:\\Users\\LTI\\Desktop\\nexus-2.db";
        //new TrajectoryAnalyzerUsage(databaseInputFilePath).doWork();


//        GpsFix fix_1 = new GpsFix(true, 23.72108268, -99.07762772, 0, 0, 0, new Date(System.currentTimeMillis()));
//        GpsFix fix_2 = new GpsFix(true, 24.72108268, -99.07762772, 0, 0, 0, new Date(System.currentTimeMillis()));
//        System.out.println("distance_to " + fix_1.distanceTo(fix_2));

        GPSFixesFileReader reader = new ExportedDatabaseGpsFixesReader("c:\\Users\\rafael\\Documents\\experiments\\csv-auc-creator\\2\\nexus-1.csv", true);
        ArrayList<GpsFix> gpsFixes = reader.readFile();
        ZhenAlgorithm algorithm = new ZhenAlgorithm(gpsFixes, 40 * 60 * 1000, 500, false);
        ArrayList<StayPoint> stayPoints = algorithm.extractStayPoints();
//        System.out.println("Fixes are " + gpsFixes.size());
//        ArrayList<StayPoint> stayPoints = new ArrayList<>();
//        for (GpsFix fix : gpsFixes) {
//            StayPoint stayPoint = algorithm.processFix(fix);
//            if (stayPoint != null) {
//                stayPoints.add(stayPoint);
//            }
//        }
//
//        StayPoint stayPoint = algorithm.processLastPart();
//        if (stayPoint != null) {
//            stayPoints.add(stayPoint);
//        }

        for (StayPoint stayPoint : stayPoints) {
            System.out.println(stayPoint);
        }

        StayPointCsvWriter writer = new StayPointCsvWriter("c:\\Users\\rafael\\Desktop\\stay-points-offline-testing-nexus-2.csv", stayPoints);
        writer.writeFile();

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
