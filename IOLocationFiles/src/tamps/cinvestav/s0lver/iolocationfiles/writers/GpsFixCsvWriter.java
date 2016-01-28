package tamps.cinvestav.s0lver.iolocationfiles.writers;

import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GpsFixCsvWriter {
    private String filepath;
    private ArrayList<GpsFix> gpsFixes;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    public GpsFixCsvWriter(String filepath, ArrayList<GpsFix> gpsFixes) {
        this.filepath = filepath;
        this.gpsFixes = gpsFixes;
    }

    public void writeFile() throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(filepath));
        for (GpsFix gpsFix : gpsFixes) {
            pw.println(translateGpsFixToCsv(gpsFix));
        }
        pw.close();
    }

    private String translateGpsFixToCsv(GpsFix gpsFix) {
        return String.format("%f,%f,%f,%f,%s",
                gpsFix.getLatitude(), gpsFix.getLongitude(),
                gpsFix.getAltitude(), gpsFix.getAccuracy(), simpleDateFormat.format(gpsFix.getTimestamp()));
    }
}
