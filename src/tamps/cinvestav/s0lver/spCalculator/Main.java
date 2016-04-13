package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.GPSFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.LoggerReaderFixes;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.SmartphoneFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.readers.staypoints.StayPointsFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.writers.GpsFixCsvWriter;
import tamps.cinvestav.s0lver.iolocationfiles.writers.StayPointCsvWriter;
import tamps.cinvestav.s0lver.kmltranslator.translators.KmlFileCreator;
import tamps.cinvestav.s0lver.kmltranslator.translators.PinnedKmlCreator;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.locationentities.Trajectory;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.buffered.MontoliouBufferedAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.MontoliuAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.OfflineAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.ZhenAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.gui.FrmMain;
import tamps.cinvestav.s0lver.trajectoriescomparator.TrajectoryComparator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Main {
    public static final int ONE_MINUTE = 60 * 1000;

    public static void main(String[] args) throws IOException, ParseException, TransformerException, ParserConfigurationException {
//        String gpsFixesSmartphoneOne = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\registros.csv";
//        SmartphoneFixesFileReader sfr1 = new SmartphoneFixesFileReader(gpsFixesSmartphoneOne);
//        ArrayList<GpsFix> gpsFixesSmartphone1 = sfr1.readFile();
//
//        String gpsFixesSmartphoneTwo = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-2\\registros.csv";
//        SmartphoneFixesFileReader sfr2 = new SmartphoneFixesFileReader(gpsFixesSmartphoneTwo);
//        ArrayList<GpsFix> gpsFixesSmartphone2 = sfr2.readFile();
//
//        String gpsFixesLogger = "c:\\Users\\rafael\\Desktop\\tmp\\exp\\logger\\exported-20_28-10-2015.csv";
//        LoggerReaderFixes loggerReaderFixes = new LoggerReaderFixes(gpsFixesLogger);
//        ArrayList<GpsFix> gpsFixesFromLogger = loggerReaderFixes.readFile();
//
//        processSmartphoneOne(gpsFixesSmartphone1);
//        processSmartphoneTwo(gpsFixesSmartphone2);
//        processGpsLogger(gpsFixesFromLogger);

//         new FrmMain();
//        StayPointsFileReader reader = new StayPointsFileReader("C:\\Users\\Rafael\\Desktop\\staypoints_sigma-zhen_fixed-period_03-Mar-2016-16-21.csv");
//        ArrayList<StayPoint> stayPoints = reader.readFile();
//
//        PinnedKmlCreator kmlCreator = PinnedKmlCreator.createForStayPoints("C:\\Users\\Rafael\\Desktop\\staypoints_sigma-zhen_fixed-period_03-Mar-2016-16-21.kml", stayPoints);
//        kmlCreator.create();

        String groundTruthPath = "c:\\Users\\rafael\\Desktop\\tmp\\trajectories\\victoria-guemez.csv";
        LoggerReaderFixes groundTruthReader = new LoggerReaderFixes(groundTruthPath);
        ArrayList<GpsFix> groundTruthFixes = groundTruthReader.readFile();
        Trajectory groundTruthTrajectory = new Trajectory(groundTruthFixes);

        String subSampledPath = "c:\\Users\\rafael\\Desktop\\tmp\\trajectories\\victoria-guemez-sub-sampled.csv";
        LoggerReaderFixes subSampledReader = new LoggerReaderFixes(subSampledPath);
        ArrayList<GpsFix> subSampledFixes = subSampledReader.readFile();
        Trajectory subSampledTrajectory = new Trajectory(subSampledFixes);

        TrajectoryComparator comparator = new TrajectoryComparator(groundTruthTrajectory, subSampledTrajectory);
//        double euclideanDistance = comparator.compareEuclidean();
//        System.out.println("Distance is " + euclideanDistance);
//        ArrayList<GpsFix> synthetic = comparator.compareEuclidean();
//        PinnedKmlCreator.createForGpsFixes("C:\\Users\\rafael\\Desktop\\tmp\\trajectories\\synthetic-fixes.kml", synthetic).create();
//        PinnedKmlCreator.createForGpsFixes("C:\\Users\\rafael\\Desktop\\tmp\\trajectories\\originalFixes.kml", groundTruthFixes).create();
//        ArrayList<GpsFix> syntheticFixes = comparator.getSyntheticFixes();
//        GpsFixCsvWriter writer = new GpsFixCsvWriter("c:\\Users\\rafael\\Desktop\\tmp\\trajectories\\synthetic-fixes.csv", syntheticFixes);
//        writer.writeFile();


        String syntheticFixesPath = "c:\\Users\\rafael\\Desktop\\tmp\\trajectories\\synthetic-fixes.csv";
        SmartphoneFixesFileReader reader = new SmartphoneFixesFileReader(syntheticFixesPath);
        ArrayList<GpsFix> syntheticFixes = reader.readFile();
    // TODO something is wrong with the calculateEuclideanly, see that below the result is more believable

        double distanceSum = 0;
        for (int i = 0; i < syntheticFixes.size(); i++) {
            GpsFix syntheticFix = syntheticFixes.get(i);
            GpsFix groundTruthFix = groundTruthFixes.get(i);
            double distance = syntheticFix.distanceTo(groundTruthFix);
            System.out.println(syntheticFix + " ->VS<- " + groundTruthFix + "\t = " + distance);
            distanceSum += distance;
        }
        System.out.println("Sum of distances is " + distanceSum);
    }
}
