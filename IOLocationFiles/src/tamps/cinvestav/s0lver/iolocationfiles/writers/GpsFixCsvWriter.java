package tamps.cinvestav.s0lver.iolocationfiles.writers;

import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GpsFixCsvWriter {
    private final static String OBTAINED_YES = "Si";
    private final static String OBTAINED_NO = "No";
    public final static SimpleDateFormat SIMPLE_DATE_FORMAT_HUMAN = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
//    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_FOR_SQLITE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private String filepath;
    private List<GpsFix> gpsFixes;

    public GpsFixCsvWriter(String filepath, List<GpsFix> gpsFixes) {
        this.filepath = filepath;
        this.gpsFixes = gpsFixes;
    }

    public void writeFile() throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(filepath));
        pw.println("Obtained,Latitude,Longitude,Altitude,Accuracy,Velocity,Timestamp");
        for (GpsFix gpsFix : gpsFixes) {
            pw.println(translateGpsFixToCsv(gpsFix));
        }
        pw.close();
    }

    private String translateGpsFixToCsv(GpsFix gpsFix) {
        String strObtained = gpsFix.isObtained() ? OBTAINED_YES : OBTAINED_NO;
        return strObtained + "," + gpsFix.getLatitude() + "," + gpsFix.getLongitude() + "," + gpsFix.getAltitude()
                + "," + gpsFix.getAccuracy() + "," + gpsFix.getVelocity() + ","
                + SIMPLE_DATE_FORMAT_HUMAN.format(gpsFix.getTimestamp());
    }
}
