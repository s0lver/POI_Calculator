package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.SmartphoneFixesFileReader;
import tamps.cinvestav.s0lver.kmltranslator.translators.KmlFileTranslator;
import tamps.cinvestav.s0lver.kmltranslator.translators.LinedKmlCreator;
import tamps.cinvestav.s0lver.kmltranslator.translators.PinnedKmlCreator;
import tamps.cinvestav.s0lver.kmltranslator.translators.TimePinnedKmlCreator;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.spCalculator.algorithms.offline.MontoliuAlgorithm;
import tamps.cinvestav.s0lver.spCalculator.algorithms.offline.OfflineAlgorithm;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class Main {
    public static final int ONE_MINUTE = 60 * 1000;

    public static void main(String[] args) throws IOException, ParseException, TransformerException, ParserConfigurationException {
        String sourceGpsFixes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\registros.csv";
        String kmlPinnedOutput = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\registros-pinned.kml";
        String kmlTimePinnedOutput = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\registros-time-pinned.kml";
        String kmlLinedOutput = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\registros-lined.kml";

        String kmlSpPinnedOutput = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\stay-points-pinned.kml";

        SmartphoneFixesFileReader sfr = new SmartphoneFixesFileReader(sourceGpsFixes);
        ArrayList<GpsFix> gpsFixes = sfr.readFile();

        KmlFileTranslator pinnedTranslator = PinnedKmlCreator.createForGpsFixes(kmlPinnedOutput, gpsFixes);
        pinnedTranslator.translate();

        KmlFileTranslator timepinnedTranslator = TimePinnedKmlCreator.createForGpsFixes(kmlTimePinnedOutput, gpsFixes);
        timepinnedTranslator.translate();

        KmlFileTranslator linedTranslator = LinedKmlCreator.createForGpsFixes(kmlLinedOutput, gpsFixes);
        linedTranslator.translate();

        OfflineAlgorithm montoliuAlgorithm = new MontoliuAlgorithm(gpsFixes, 10 * ONE_MINUTE, 60 * ONE_MINUTE, 150);
        ArrayList<StayPoint> stayPoints = montoliuAlgorithm.extractStayPoints();

        KmlFileTranslator pinnedSpKmlCreator = PinnedKmlCreator.createForStaypoints(kmlSpPinnedOutput, stayPoints);
        pinnedSpKmlCreator.translate();

//        String outputCalculatedStayPoints = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\stay-points-calculated.csv";
//        StayPointCsvWriter stayPointsWriter = new StayPointCsvWriter(outputCalculatedStayPoints, stayPoints);
//        stayPointsWriter.writeFile();

//        String outputReadFixes = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\read-fixes.csv";
//        GpsFixCsvWriter gpsFixesWriter = new GpsFixCsvWriter(outputReadFixes, gpsFixes);
//        gpsFixesWriter.writeFile();
    }
}
