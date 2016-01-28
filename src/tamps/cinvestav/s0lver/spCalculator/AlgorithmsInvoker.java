package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.GPSFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.readers.LoggerReaderFixes;
import tamps.cinvestav.s0lver.iolocationfiles.readers.SmartphoneFixesFileReader;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.spCalculatorDOS.algorithms.live.LiveAlgorithm;
import tamps.cinvestav.s0lver.spCalculatorDOS.algorithms.live.buffered.MontoliouBufferedAlgorithm;
import tamps.cinvestav.s0lver.spCalculatorDOS.algorithms.live.sigma.MontoliouSigmaAlgorithm;
import tamps.cinvestav.s0lver.spCalculatorDOS.algorithms.offline.MontoliuAlgorithm;
import tamps.cinvestav.s0lver.spCalculatorDOS.algorithms.offline.OfflineAlgorithm;
import tamps.cinvestav.s0lver.spCalculatorDOS.algorithms.offline.ZhenAlgorithm;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AlgorithmsInvoker {
    public static final int ONE_MINUTE = 60 * 1000;

    public static void invokeAlgorithms(){
        String pathPolicyOne = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\registros.csv";
        String pathPolicyTwo = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-2\\registros.csv";
        String pathGpsLogger = "C:\\Users\\rafael\\desktop\\tmp\\exp\\logger\\exported-20_28-10-2015.csv";

        GPSFixesFileReader sfrPolicyOne = new SmartphoneFixesFileReader(pathPolicyOne);
        GPSFixesFileReader sfrPolicyTwo = new SmartphoneFixesFileReader(pathPolicyTwo);
        GPSFixesFileReader glfr = new LoggerReaderFixes(pathGpsLogger);

        ArrayList<GpsFix> gpsFixesPolicyOne = sfrPolicyOne.readFile();
        ArrayList<GpsFix> gpsFixesPolicyTwo = sfrPolicyTwo.readFile();
        ArrayList<GpsFix> gpsFixesGpsLogger = glfr.readFile();

        ArrayList<StayPoint> stayPointsPolicyOne = executeMontoliouLive(gpsFixesPolicyOne);
        ArrayList<StayPoint> stayPointsPolicyTwo = executeMontoliouLive(gpsFixesPolicyTwo);
        ArrayList<StayPoint> stayPointsGpsLogger = executeMontoliouLive(gpsFixesGpsLogger);

        stayPointsPolicyOne.forEach(System.out::println);
        stayPointsPolicyTwo.forEach(System.out::println);
        stayPointsGpsLogger.forEach(System.out::println);
    }

    public static ArrayList<StayPoint> executeZhen(ArrayList<GpsFix> gpsFixArrayList) {
        OfflineAlgorithm zhengAlgorithm = new ZhenAlgorithm(gpsFixArrayList, 60 * 1000, 150);
        return zhengAlgorithm.extractStayPoints();
    }

    public static ArrayList<StayPoint> executeMontoliou(ArrayList<GpsFix> gpsFixArrayList) {
        OfflineAlgorithm montoliuAlgorithm = new MontoliuAlgorithm(gpsFixArrayList, 10 * ONE_MINUTE, 60 * ONE_MINUTE, 150);
        return montoliuAlgorithm.extractStayPoints();
    }

    public static ArrayList<StayPoint> executeMontoliouLive(ArrayList<GpsFix> gpsFixes) {
        LiveAlgorithm mla = new MontoliouBufferedAlgorithm(60 * ONE_MINUTE, 10 * ONE_MINUTE, 150);
        ArrayList<StayPoint> stayPointsMLA = new ArrayList<>();

        for (int i = 0; i < gpsFixes.size(); i++) {
            StayPoint sp = mla.processFix(gpsFixes.get(i));
            if (sp != null) {
                stayPointsMLA.add(sp);
            }
        }

        StayPoint sp = mla.processLastPart();
        if (sp != null) {
            stayPointsMLA.add(sp);
        }

        return stayPointsMLA;
    }

    public static ArrayList<StayPoint> executeMontoliouLiveSigma(ArrayList<GpsFix> gpsFixes) {
        LiveAlgorithm mlas = new MontoliouSigmaAlgorithm(60 * ONE_MINUTE, 10 * ONE_MINUTE, 150);
        ArrayList<StayPoint> staypoints = new ArrayList<>();

        for (int i = 0; i < gpsFixes.size(); i++) {
            StayPoint sp = mlas.processFix(gpsFixes.get(i));
            if (sp != null) {
                staypoints.add(sp);
            }
        }

        StayPoint sp = mlas.processLastPart();
        if (sp != null) {
            staypoints.add(sp);
        }
        return staypoints;
    }

    public static ArrayList<GpsFix> readFile(String filename) throws IOException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        ArrayList<GpsFix> fixes = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        while (line != null) {
            String[] slices = line.split(",");
            // You have to verify what was read here to modify it
            // GpsFix fix = new GpsFix(Float.valueOf(slices[0]), Float.valueOf(slices[1]), 0, 0, 0, simpleDateFormat.parse(slices[3]), Float.valueOf(slices[2]));
            GpsFix fix = null;
            fixes.add(fix);
            line = br.readLine();
        }

        return fixes;
    }


}
