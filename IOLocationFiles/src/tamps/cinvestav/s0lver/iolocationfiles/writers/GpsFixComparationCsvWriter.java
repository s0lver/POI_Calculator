package tamps.cinvestav.s0lver.iolocationfiles.writers;

import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GpsFixComparationCsvWriter {
    private String filepath;
    private ArrayList<GpsFix> groundTruthFixes;
    private ArrayList<GpsFix> syntheticFixes;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    public GpsFixComparationCsvWriter(String filepath, ArrayList<GpsFix> groundTruthFixes, ArrayList<GpsFix> syntheticFixes) {
        this.filepath = filepath;
        this.groundTruthFixes = groundTruthFixes;
        this.syntheticFixes = syntheticFixes;
    }

    public void writeFile() throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(filepath));
        String header = "gtLatitude,gtLongitude,gtAltitude,gtAccuracy,gtVelocity,gtTimestamp,Latitude,Longitude,Altitude,Accuracy,Velocity,Timestamp,Distance";
        pw.println(header);
        int sizeSyntheticFixes = syntheticFixes.size();
        for (int i = 0; i < sizeSyntheticFixes; i++) {
            GpsFix groundTruthFix = groundTruthFixes.get(i);
            GpsFix syntheticFix = syntheticFixes.get(i);
            double distance = syntheticFix.distanceTo(groundTruthFix);
            pw.println(translateGpsFixToCsv(syntheticFix) + "," + translateGpsFixToCsv(groundTruthFix) + "," + distance);
        }
        pw.close();
    }

    private String translateGpsFixToCsv(GpsFix gpsFix) {
        return gpsFix.getLatitude() + "," + gpsFix.getLongitude() + "," + gpsFix.getAltitude()
                + "," + gpsFix.getAccuracy() + "," + gpsFix.getVelocity() + ","
                + simpleDateFormat.format(gpsFix.getTimestamp());
    }
}
