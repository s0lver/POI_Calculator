package tamps.cinvestav.s0lver.databaseaccess.conversors.fromdbtocsv;

import tamps.cinvestav.s0lver.databaseaccess.dto.DtoActivity;
import tamps.cinvestav.s0lver.iolocationfiles.writers.GpsFixCsvWriter;
import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CsvFromDbCreator {
    private final static String OBTAINED_YES = "Si";
    private final static String OBTAINED_NO = "No";
    public final static SimpleDateFormat SIMPLE_DATE_FORMAT_HUMAN = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_FOR_SQLITE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    // private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
    private List<DtoActivity> activities;
    private String fileOutputPath;

    public CsvFromDbCreator(String fileOutputPath, List<DtoActivity> activities) {
        this.fileOutputPath = fileOutputPath;
        this.activities = activities;
    }

    public void writeFile() throws IOException {
        List<GpsFix> fixes = buildListFromActivities();
        GpsFixCsvWriter writer = new GpsFixCsvWriter(fileOutputPath, fixes);
        writer.writeFile();
    }

    public void writeFileWithBatteryLevel() throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(fileOutputPath));
            pw.println("Obtained,Latitude,Longitude,Altitude,Accuracy,Velocity,Timestamp,BatteryLevel");
            for (DtoActivity activity: activities) {
                pw.println(translateActivityToCsv(activity));
            }
            pw.close();
    }

    private String translateActivityToCsv(DtoActivity activity) {
        GpsFix gpsFix= buildFixFromActivity(activity);

        String strObtained = gpsFix.isObtained() ? OBTAINED_YES : OBTAINED_NO;
        return strObtained + "," + activity.getLatitude() + "," + activity.getLongitude() + "," + gpsFix.getAltitude()
                + "," + gpsFix.getAccuracy() + "," + gpsFix.getVelocity() + ","
                + SIMPLE_DATE_FORMAT_FOR_SQLITE.format(gpsFix.getTimestamp()) + ","
//                + SIMPLE_DATE_FORMAT_HUMAN.format(gpsFix.getTimestamp()) + ","
                + activity.getBatteryLevel()
                ;
    }

    private List<GpsFix> buildListFromActivities() {
        List<GpsFix> fixes = new ArrayList<>();

        for (int i = 0; i < activities.size(); i++) {
            DtoActivity activity = activities.get(i);

            GpsFix fix = buildFixFromActivity(activity);
            fixes.add(fix);
        }

        return fixes;
    }

    private GpsFix buildFixFromActivity(DtoActivity activity) {
        boolean obtained = (activity.getLatitude() == 0 && activity.getLongitude() == 0 && activity.getAltitude() == 0 && activity.getAccuracy() == 0) ? false : true;
        return new GpsFix(obtained, activity.getLatitude(), activity.getLongitude(), activity.getAltitude(),
                activity.getAccuracy(), activity.getSpeed(), new Date(activity.getTimestamp()));
    }
}
