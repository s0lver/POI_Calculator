package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.LoggerReaderFixes;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.SmartphoneFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.writers.GpsFixCsvWriter;
import tamps.cinvestav.s0lver.kmltranslator.translators.PinnedKmlCreator;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.Trajectory;
import tamps.cinvestav.s0lver.trajectoriescomparator.TrajectoryComparator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Main {
    public static final int ONE_MINUTE = 60 * 1000;

    public static void main(String[] args) throws IOException, ParseException, TransformerException, ParserConfigurationException {
//         new FrmMain();
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
//        ArrayList<GpsFix> syntheticFixes = comparator.getSyntheticFixesEuclideanly();
//        GpsFixCsvWriter writer = new GpsFixCsvWriter("c:\\Users\\rafael\\Desktop\\tmp\\trajectories\\synthetic-fixes.csv", syntheticFixes);
//        writer.writeFile();


//        String syntheticFixedEuclideanPath = "c:\\Users\\rafael\\Desktop\\tmp\\trajectories\\synthetic-fixes-euclid.csv";
//        SmartphoneFixesFileReader readerEuclid = new SmartphoneFixesFileReader(syntheticFixedEuclideanPath);
//        ArrayList<GpsFix> syntheticEuclidFixes = readerEuclid.readFile();
//        double distanceSum = calculateDistance(groundTruthFixes, syntheticEuclidFixes);
//        System.out.println("Sum of distances is " + distanceSum);
//
//        String syntheticFixesSyncPath = "c:\\Users\\rafael\\Desktop\\tmp\\trajectories\\synthetic-fixes-synch.csv";
//        SmartphoneFixesFileReader readerSync = new SmartphoneFixesFileReader(syntheticFixesSyncPath);
//        ArrayList<GpsFix> syntheticSyncFixes = readerSync.readFile();
//        double distanceSumSync = calculateDistance(groundTruthFixes, syntheticSyncFixes);
//        System.out.println("Sum of distances is " + distanceSumSync);
//
//        System.out.println("Ok now pay attention here...");
//        for (int i = 0; i < syntheticEuclidFixes.size() - 1; i++) {
//            GpsFix gpsFix = syntheticEuclidFixes.get(i);
//            GpsFix nextFix = syntheticEuclidFixes.get(i + 1);
//            double distance = gpsFix.distanceTo(nextFix);
//            System.out.println(gpsFix + " --> " + nextFix + "  === " + distance);
//        }

//        ArrayList<GpsFix> syntheticFixes = comparator.getSyntheticFixesSynchronizedly();
//        GpsFixCsvWriter writer = new GpsFixCsvWriter("c:\\Users\\rafael\\Desktop\\tmp\\trajectories\\synthetic-fixes-synch.csv", syntheticFixes);
//        writer.writeFile();
//        PinnedKmlCreator.createForGpsFixes("C:\\Users\\rafael\\Desktop\\tmp\\trajectories\\synthetic-fixes-synch.kml", syntheticFixes).create();
//        PinnedKmlCreator.createForGpsFixes("C:\\Users\\rafael\\Desktop\\tmp\\trajectories\\original-fixes.kml",
//                getSubArrayList(groundTruthFixes, syntheticFixes.size())).create();

        ArrayList<GpsFix> syntheticEuclidFixes = comparator.getSyntheticFixesEuclideanly();
                System.out.println("Ok now pay attention here...");
        for (int i = 0; i < syntheticEuclidFixes.size() - 1; i++) {
            GpsFix gpsFix = syntheticEuclidFixes.get(i);
            GpsFix nextFix = syntheticEuclidFixes.get(i + 1);
            double distance = gpsFix.distanceTo(nextFix);
            System.out.println(i + " " + gpsFix + " --> " + nextFix + "  === " + distance);
        }
        //GpsFixCsvWriter writer = new GpsFixCsvWriter("c:\\Users\\rafael\\Desktop\\tmp\\trajectories\\synthetic-fixes-euclid.csv", syntheticEuclidFixes);
        //writer.writeFile();

    }

    private static double calculateDistance(ArrayList<GpsFix> groundTruthFixes, ArrayList<GpsFix> syntheticFixes) {
        double distanceSum = 0;
        for (int i = 0; i < syntheticFixes.size(); i++) {
            GpsFix syntheticFix = syntheticFixes.get(i);
            GpsFix groundTruthFix = groundTruthFixes.get(i);
            double distance = syntheticFix.distanceTo(groundTruthFix);
            System.out.println(syntheticFix + " ->VS<- " + groundTruthFix + "\t = " + distance);
            distanceSum += distance;
        }
        return distanceSum;
    }

    private static ArrayList<GpsFix> getSubArrayList(ArrayList<GpsFix> original, int amount) {
        ArrayList<GpsFix> newArrayList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            newArrayList.add(original.get(i));
        }
        return newArrayList;
    }
}
