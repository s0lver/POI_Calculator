package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.SmartphoneFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.readers.staypoints.StayPointsFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.writers.StayPointCsvWriter;
import tamps.cinvestav.s0lver.kmltranslator.translators.KmlFileCreator;
import tamps.cinvestav.s0lver.kmltranslator.translators.PinnedKmlCreator;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.live.buffered.MontoliouBufferedAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.MontoliuAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.OfflineAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.ZhenAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.gui.FrmMain;

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

         new FrmMain();
//        StayPointsFileReader reader = new StayPointsFileReader("C:\\Users\\Rafael\\Desktop\\staypoints_sigma-zhen_fixed-period_03-Mar-2016-16-21.csv");
//        ArrayList<StayPoint> stayPoints = reader.readFile();
//
//        PinnedKmlCreator kmlCreator = PinnedKmlCreator.createForStayPoints("C:\\Users\\Rafael\\Desktop\\staypoints_sigma-zhen_fixed-period_03-Mar-2016-16-21.kml", stayPoints);
//        kmlCreator.create();

    }
}
