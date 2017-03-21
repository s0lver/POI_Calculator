package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.databaseaccess.dal.ActivitiesDal;
import tamps.cinvestav.s0lver.databaseaccess.dal.DatabaseConnectionManager;
import tamps.cinvestav.s0lver.databaseaccess.dal.StayPointsDal;
import tamps.cinvestav.s0lver.databaseaccess.dal.VisitsDal;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoActivity;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoStayPoint;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoVisit;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.GPSFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.LoggerReaderFixes;
import tamps.cinvestav.s0lver.iolocationfiles.writers.GpsFixCsvWriter;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsCalculator.gui.FrmMain;
import tamps.cinvestav.s0lver.trajectoryanalyzer.TrajectoryAnalyzer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


        Date nowDate = new Date(System.currentTimeMillis());
        GpsFix fix_one = new GpsFix(true, 23.72108268, -99.07762772, 0, 0, 0, nowDate);
        GpsFix fix_two = new GpsFix(true, 24.72108268, -98.07762772, 0, 0, 0, new Date(nowDate.getTime() + 20 * 1000));
        GpsFix fix_three = new GpsFix(true, 25.72108268, -97.07762772, 0, 0, 0, new Date(nowDate.getTime() + 40 * 1000));
        ArrayList<GpsFix> list = new ArrayList<>();
        list.add(fix_one);
        list.add(fix_two);
        list.add(fix_three);

        StayPoint stayPoint = StayPoint.createStayPoint(list);
        System.out.println("Stay point is " + stayPoint);

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
