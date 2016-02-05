package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.LoggerReaderFixes;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.SmartphoneFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.writers.StayPointCsvWriter;
import tamps.cinvestav.s0lver.kmltranslator.translators.KmlFileCreator;
import tamps.cinvestav.s0lver.kmltranslator.translators.PinnedKmlCreator;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.LiveAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.buffered.MontoliouBufferedAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.MontoliuAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.OfflineAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.ZhenAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.gui.FrmMain;
import tamps.cinvestav.s0lver.stayPointsCalculator.gui.FrmStayPointComparator;

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

        // new FrmMain();
        testServerResults();
    }

    private static void testServerResults() {
        String csvOrigin = "C:\\Users\\rafael\\desktop\\tmp\\test-server-side\\registros.csv";
        SmartphoneFixesFileReader reader = new SmartphoneFixesFileReader(csvOrigin);
        ArrayList<GpsFix> gpsFixes = reader.readFile();
        MontoliouBufferedAlgorithm mba = new MontoliouBufferedAlgorithm(60 * ONE_MINUTE, 10 * ONE_MINUTE, 50);

        ArrayList<StayPoint> stayPoints = new ArrayList<>();
        for (GpsFix gpsFix : gpsFixes) {
            StayPoint stayPoint = mba.processFix(gpsFix);
            if (stayPoint != null) {
                stayPoints.add(stayPoint);
            }
        }

        StayPoint stayPoint = mba.processLastPart();
        if (stayPoint != null) {
            stayPoints.add(stayPoint);
        }

        for (StayPoint point : stayPoints) {
            System.out.println(point);
        }


    }

    private static void processSmartphoneOne(ArrayList<GpsFix> gpsFixes) throws IOException, TransformerException, ParserConfigurationException {
        String kmlSp10Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\sp1-stay-points-10-pinned.kml";
        String kmlSp15Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\sp1-stay-points-15-pinned.kml";

        String csvSp10Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\sp1-stay-points-10-pinned.csv";
        String csvSp15Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\sp1-stay-points-15-pinned.csv";

        OfflineAlgorithm montoliuAlgorithmTenMinutes = new MontoliuAlgorithm(gpsFixes, 10 * ONE_MINUTE, 60 * ONE_MINUTE, 150, true);
        ArrayList<StayPoint> stayPointsTenMinutes = montoliuAlgorithmTenMinutes.extractStayPoints();
        KmlFileCreator creatorFromSP = PinnedKmlCreator.createForStaypoints(kmlSp10Minutes, stayPointsTenMinutes);
        creatorFromSP.create();
        StayPointCsvWriter spcsv10minutes = new StayPointCsvWriter(csvSp10Minutes, stayPointsTenMinutes);
        spcsv10minutes.writeFile();

        OfflineAlgorithm montoliouAlgorithmFifteenMinutes = new MontoliuAlgorithm(gpsFixes, 15 * ONE_MINUTE, 60 * ONE_MINUTE, 150, true);
        ArrayList<StayPoint> stayPointsFiftenMinutes = montoliouAlgorithmFifteenMinutes.extractStayPoints();
        creatorFromSP = PinnedKmlCreator.createForStaypoints(kmlSp15Minutes, stayPointsFiftenMinutes);
        creatorFromSP.create();
        StayPointCsvWriter spcsv15minutes = new StayPointCsvWriter(csvSp15Minutes, stayPointsFiftenMinutes);
        spcsv15minutes.writeFile();

    }

    private static void processSmartphoneTwo(ArrayList<GpsFix> gpsFixes) throws IOException, TransformerException, ParserConfigurationException {
        String kmlSp10Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-2\\sp2-stay-points-10-pinned.kml";
        String kmlSp15Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-2\\sp2-stay-points-15-pinned.kml";

        String csvSp10Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-2\\sp2-stay-points-10-pinned.csv";
        String csvSp15Minutes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-2\\sp2-stay-points-15-pinned.csv";

        OfflineAlgorithm montoliuAlgorithmTenMinutes = new MontoliuAlgorithm(gpsFixes, 10 * ONE_MINUTE, 60 * ONE_MINUTE, 150, true);
        ArrayList<StayPoint> stayPointsTenMinutes = montoliuAlgorithmTenMinutes.extractStayPoints();
        KmlFileCreator creatorFromSP = PinnedKmlCreator.createForStaypoints(kmlSp10Minutes, stayPointsTenMinutes);
        creatorFromSP.create();
        StayPointCsvWriter spcsv10minutes = new StayPointCsvWriter(csvSp10Minutes, stayPointsTenMinutes);
        spcsv10minutes.writeFile();

        OfflineAlgorithm montoliouAlgorithmFifteenMinutes = new MontoliuAlgorithm(gpsFixes, 15 * ONE_MINUTE, 60 * ONE_MINUTE, 150, true);
        ArrayList<StayPoint> stayPointsFiftenMinutes = montoliouAlgorithmFifteenMinutes.extractStayPoints();
        creatorFromSP = PinnedKmlCreator.createForStaypoints(kmlSp15Minutes, stayPointsFiftenMinutes);
        creatorFromSP.create();
        StayPointCsvWriter spcsv15minutes = new StayPointCsvWriter(csvSp15Minutes, stayPointsFiftenMinutes);
        spcsv15minutes.writeFile();
    }

    private static void processGpsLogger(ArrayList<GpsFix> gpsFixesFromLogger) throws IOException, TransformerException, ParserConfigurationException {
        String kml10minutesZheng = "c:\\Users\\rafael\\Desktop\\tmp\\exp\\logger\\logger-sp-zheng-10-minutes.kml";
        String kml15minutesZheng = "c:\\Users\\rafael\\Desktop\\tmp\\exp\\logger\\logger-sp-zheng-15-minutes.kml";

        String csv10minutesZheng = "c:\\Users\\rafael\\Desktop\\tmp\\exp\\logger\\logger-sp-zheng-10-minutes.csv";
        String csv15minutesZheng = "c:\\Users\\rafael\\Desktop\\tmp\\exp\\logger\\logger-sp-zheng-15-minutes.csv";

        OfflineAlgorithm zhenLoggerTenMinutes = new ZhenAlgorithm(gpsFixesFromLogger, 10 * ONE_MINUTE, 150);
        ArrayList<StayPoint> stayPoints10MinZhen = zhenLoggerTenMinutes.extractStayPoints();
        KmlFileCreator spKmlCreator = PinnedKmlCreator.createForStaypoints(kml10minutesZheng, stayPoints10MinZhen);
        spKmlCreator.create();
        StayPointCsvWriter spcsv10minutesZhen = new StayPointCsvWriter(csv10minutesZheng, stayPoints10MinZhen);
        spcsv10minutesZhen.writeFile();

        OfflineAlgorithm zhenLoggerFifteenMinutes = new ZhenAlgorithm(gpsFixesFromLogger, 15 * ONE_MINUTE, 150);
        ArrayList<StayPoint> stayPoints15MinZhen = zhenLoggerFifteenMinutes.extractStayPoints();
        spKmlCreator = PinnedKmlCreator.createForStaypoints(kml15minutesZheng, stayPoints10MinZhen);
        spKmlCreator.create();
        StayPointCsvWriter spcsv15minutesZhen = new StayPointCsvWriter(csv15minutesZheng, stayPoints15MinZhen);
        spcsv15minutesZhen.writeFile();


        String kml10minutesMontoliou = "c:\\Users\\rafael\\Desktop\\tmp\\exp\\logger\\logger-sp-montoliou-10-minutes.kml";
        String kml15minutesMontoliou = "c:\\Users\\rafael\\Desktop\\tmp\\exp\\logger\\logger-sp-montoliou-15-minutes.kml";
        String csv10minutesMontoliou = "c:\\Users\\rafael\\Desktop\\tmp\\exp\\logger\\logger-sp-montoliou-10-minutes.csv";
        String csv15minutesMontoliou = "c:\\Users\\rafael\\Desktop\\tmp\\exp\\logger\\logger-sp-montoliou-15-minutes.csv";

        OfflineAlgorithm montoliouLoggerTenMinutes = new MontoliuAlgorithm(gpsFixesFromLogger, 10 * ONE_MINUTE, 60 * ONE_MINUTE, 150, true);
        ArrayList<StayPoint> stayPoints10MinMontoliou = montoliouLoggerTenMinutes.extractStayPoints();
        spKmlCreator = PinnedKmlCreator.createForStaypoints(kml10minutesMontoliou, stayPoints10MinMontoliou);
        spKmlCreator.create();
        StayPointCsvWriter spcsv10minutesMontoliou = new StayPointCsvWriter(csv10minutesMontoliou, stayPoints10MinMontoliou);
        spcsv10minutesMontoliou.writeFile();

        OfflineAlgorithm montoliouLoggerFifteenMinutes = new MontoliuAlgorithm(gpsFixesFromLogger, 15 * ONE_MINUTE, 60 * ONE_MINUTE, 150, true);
        ArrayList<StayPoint> stayPoints15MinMontoliou = montoliouLoggerFifteenMinutes.extractStayPoints();
        spKmlCreator = PinnedKmlCreator.createForStaypoints(kml15minutesMontoliou, stayPoints15MinZhen);
        spKmlCreator.create();
        StayPointCsvWriter spcsv15minutesMontoliou = new StayPointCsvWriter(csv15minutesMontoliou, stayPoints15MinMontoliou);
        spcsv15minutesMontoliou.writeFile();
    }
}
