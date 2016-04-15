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

        ArrayList<GpsFix> syntheticFixesEuclideanly = comparator.getSyntheticFixesEuclideanly();
        double distance = calculateDistance(groundTruthFixes, syntheticFixesEuclideanly);
        System.out.println("Distance is " + distance);

        ArrayList<GpsFix> syntheticFixesSynchronizedly = comparator.getSyntheticFixesSynchronizedly();
        distance = calculateDistance(groundTruthFixes, syntheticFixesSynchronizedly);
        System.out.println("Distance is " + distance);

    }

    private static double calculateDistance(ArrayList<GpsFix> groundTruthFixes, ArrayList<GpsFix> syntheticFixes) {
        double distanceSum = 0;
        for (int i = 0; i < syntheticFixes.size(); i++) {
            GpsFix syntheticFix = syntheticFixes.get(i);
            GpsFix groundTruthFix = groundTruthFixes.get(i);
            double distance = syntheticFix.distanceTo(groundTruthFix);
//            System.out.println(syntheticFix + " ->VS<- " + groundTruthFix + "\t = " + distance);
//            System.out.println(distance);
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
