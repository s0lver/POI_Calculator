package tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes;

import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/***
 * Reader for the CSV files generated from SQLite database
 */
public class ExportedDatabaseGpsFixesReader extends GPSFixesFileReader {
    private final static int LATITUDE = 2;
    private final static int LONGITUDE = 3;
    private final static int ALTITUDE = 4;
    private final static int ACCURACY = 5;
    private final static int SPEED = 6;
    private final static int TIMESTAMP = 7;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public ExportedDatabaseGpsFixesReader(String path, boolean skipFirstLine) {
        super(path, skipFirstLine);
    }

    @Override
    protected GpsFix processLine(String line) {
        GpsFix fix = null;
        String[] slices = line.split(",");

        try { // 2016-11-22 18:00:52
            Double latitude = Double.valueOf(slices[LATITUDE]);
            Double longitude = Double.valueOf(slices[LONGITUDE]);
            Double altitude = Double.valueOf(slices[ALTITUDE]);
            Double accuracy = Double.valueOf(slices[ACCURACY]);
            Double velocity = Double.valueOf(slices[SPEED]);
            Date timestamp = simpleDateFormat.parse(slices[TIMESTAMP]);

            if (!(latitude == 0 && longitude == 0 && altitude == 0 && accuracy == 0 && velocity == 0)) {
                fix = new GpsFix(true, latitude, longitude, altitude, accuracy, velocity, timestamp);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("I couldn't parse the date, and I hate checked exceptions");
        }
        return fix;
    }
}
