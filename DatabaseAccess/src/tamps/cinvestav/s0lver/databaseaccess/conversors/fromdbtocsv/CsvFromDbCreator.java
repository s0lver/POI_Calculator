package tamps.cinvestav.s0lver.databaseaccess.conversors.fromdbtocsv;

import tamps.cinvestav.s0lver.databaseaccess.dto.DtoActivity;
import tamps.cinvestav.s0lver.iolocationfiles.writers.GpsFixCsvWriter;
import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvFromDbCreator {
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

    private List<GpsFix> buildListFromActivities() {
        List<GpsFix> fixes = new ArrayList<>();

        for (int i = 0; i < activities.size(); i++) {
            DtoActivity activity = activities.get(i);

            boolean obtained = (activity.getLatitude() == 0 && activity.getLongitude() == 0 && activity.getAltitude() == 0 && activity.getAccuracy() == 0) ? false : true;
            GpsFix fix = new GpsFix(obtained, activity.getLatitude(), activity.getLongitude(), activity.getAltitude(),
                    activity.getAccuracy(), activity.getSpeed(), new Date(activity.getTimestamp()));
            fixes.add(fix);
        }

        return fixes;
    }
}
