package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.LoggerReaderFixes;
import tamps.cinvestav.s0lver.kmltranslator.translators.KmlFileCreator;
import tamps.cinvestav.s0lver.kmltranslator.translators.PinnedKmlCreator;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.MontoliuAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.OfflineAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.ZhenAlgorithm;
import tamps.cinvestav.s0lver.stayPointsComparator.comparator.StayPointsComparator;
import tamps.cinvestav.s0lver.stayPointsComparator.comparatorResults.StayPointsComparatorResult;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Main {
    public static final int ONE_MINUTE = 60 * 1000;

    public static void main(String[] args) throws IOException, ParseException, TransformerException, ParserConfigurationException {
//        String gpsFixesSmartphoneOne = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\registros.csv";
//        String gpsFixesSmartphoneTwo = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-2\\registros.csv";

//        SmartphoneFixesFileReader sfr1 = new SmartphoneFixesFileReader(gpsFixesSmartphoneOne);
//        ArrayList<GpsFix> gpsFixesSmartphone1 = sfr1.readFile();
//
//        SmartphoneFixesFileReader sfr2 = new SmartphoneFixesFileReader(gpsFixesSmartphoneTwo);
//        ArrayList<GpsFix> gpsFixesSmartphone2 = sfr2.readFile();

//        OfflineAlgorithm montoliouAlgorithmFifteenMinutes = new MontoliuAlgorithm(gpsFixesSmartphone2, 15 * ONE_MINUTE, 60 * ONE_MINUTE, 150);
//        ArrayList<StayPoint> stayPointsFiftenMinutes = montoliouAlgorithmFifteenMinutes.extractStayPoints();

//
//        String inputFull = "c:\\Users\\rafael\\Desktop\\tmp\\exported\\exported-20_28-10-2015.csv";
//        LoggerReaderFixes loggerReaderFixes = new LoggerReaderFixes(inputFull);
//
//        ArrayList<GpsFix> gpsFixesFromLogger = loggerReaderFixes.readFile();
//        OfflineAlgorithm zhenLoggerFifteenMinutes = new ZhenAlgorithm(gpsFixesFromLogger, 15 * ONE_MINUTE, 150);
//        ArrayList<StayPoint> spZhen = zhenLoggerFifteenMinutes.extractStayPoints();
//
//        compareStaypoints(spZhen.get(6), stayPointsFiftenMinutes.get(16));
        new FrmStayPointComparator();
    }

    private static void processSmartphoneOne(ArrayList<GpsFix> gpsFixes) throws FileNotFoundException, TransformerException, ParserConfigurationException {
        String kmlSp10Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\stay-points-10-pinned.kml";
        String kmlSp15Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\stay-points-15-pinned.kml";

        OfflineAlgorithm montoliuAlgorithmTenMinutes = new MontoliuAlgorithm(gpsFixes, 10 * ONE_MINUTE, 60 * ONE_MINUTE, 150);
        ArrayList<StayPoint> stayPointsTenMinutes = montoliuAlgorithmTenMinutes.extractStayPoints();
        KmlFileCreator creatorFromSP = PinnedKmlCreator.createForStaypoints(kmlSp10Minutes, stayPointsTenMinutes);
        creatorFromSP.create();

        OfflineAlgorithm montoliouAlgorithmFifteenMinutes = new MontoliuAlgorithm(gpsFixes, 15 * ONE_MINUTE, 60 * ONE_MINUTE, 150);
        ArrayList<StayPoint> stayPointsFiftenMinutes = montoliouAlgorithmFifteenMinutes.extractStayPoints();
        creatorFromSP = PinnedKmlCreator.createForStaypoints(kmlSp15Minutes, stayPointsFiftenMinutes);
        creatorFromSP.create();

    }

    private static void processSmartphoneTwo(ArrayList<GpsFix> gpsFixes) throws FileNotFoundException, TransformerException, ParserConfigurationException {
        String kmlSp10Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-2\\stay-points-10-pinned.kml";
        String kmlSp15Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-2\\stay-points-15-pinned.kml";

        OfflineAlgorithm montoliuAlgorithmTenMinutes = new MontoliuAlgorithm(gpsFixes, 10 * ONE_MINUTE, 60 * ONE_MINUTE, 150);
        ArrayList<StayPoint> stayPointsTenMinutes = montoliuAlgorithmTenMinutes.extractStayPoints();
        KmlFileCreator creatorFromSP = PinnedKmlCreator.createForStaypoints(kmlSp10Minutes, stayPointsTenMinutes);
        creatorFromSP.create();

        OfflineAlgorithm montoliouAlgorithmFifteenMinutes = new MontoliuAlgorithm(gpsFixes, 15 * ONE_MINUTE, 60 * ONE_MINUTE, 150);
        ArrayList<StayPoint> stayPointsFiftenMinutes = montoliouAlgorithmFifteenMinutes.extractStayPoints();
        creatorFromSP = PinnedKmlCreator.createForStaypoints(kmlSp15Minutes, stayPointsFiftenMinutes);
        creatorFromSP.create();

    }

    private static void processGpsLogger() throws FileNotFoundException, TransformerException, ParserConfigurationException {
        String inputFull = "c:\\Users\\rafael\\Desktop\\tmp\\exported\\exported-20_28-10-2015.csv";
        String output15minutes = "c:\\Users\\rafael\\Desktop\\tmp\\exported\\sp-zheng-15-minutes.kml";
        String output10minutes = "c:\\Users\\rafael\\Desktop\\tmp\\exported\\sp-zheng-10-minutes.kml";
        LoggerReaderFixes loggerReaderFixes = new LoggerReaderFixes(inputFull);
        ArrayList<GpsFix> gpsFixesFromLogger = loggerReaderFixes.readFile();

        OfflineAlgorithm zhenLoggerFifteenMinutes = new ZhenAlgorithm(gpsFixesFromLogger, 15 * ONE_MINUTE, 150);
        ArrayList<StayPoint> spZhen = zhenLoggerFifteenMinutes.extractStayPoints();
        KmlFileCreator spKmlCreator = PinnedKmlCreator.createForStaypoints(output15minutes, spZhen);
        spKmlCreator.create();

        OfflineAlgorithm zhenLoggerTenMinutes = new ZhenAlgorithm(gpsFixesFromLogger, 10 * ONE_MINUTE, 150);
        spZhen = zhenLoggerTenMinutes.extractStayPoints();
        spKmlCreator = PinnedKmlCreator.createForStaypoints(output10minutes, spZhen);
        spKmlCreator.create();
    }

    private static void compareStaypoints(StayPoint stayPointA, StayPoint stayPointB) {
        long UN_SEGUNDO = 1000;
        StayPointsComparator comparator = new StayPointsComparator(stayPointA, stayPointB);
        StayPointsComparatorResult comparisonResult = comparator.compareStayPoints();
        System.out.println("Comparator output");
        System.out.println("Arrival time = " + comparisonResult.getArrivalTimeDifference() / UN_SEGUNDO);
        System.out.println("Departure time = " + comparisonResult.getDepartureTimeDifference() / UN_SEGUNDO);
        System.out.println("Stay time = " + comparisonResult.getStayTimeDifference() / UN_SEGUNDO + " min, = " + (comparisonResult.getStayTimeDifference() / UN_SEGUNDO) / 60 + " hours");
        System.out.println("Distance = " + comparisonResult.getDistanceDifference());
    }
}
