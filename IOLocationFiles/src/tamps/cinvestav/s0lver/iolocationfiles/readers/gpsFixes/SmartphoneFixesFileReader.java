package tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes;

import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SmartphoneFixesFileReader extends GPSFixesFileReader {
    private final static int LATITUDE = 1;
    private final static int LONGITUDE = 2;
    private final static int ALTITUDE = 3;
    private final static int ACCURACY = 4;
    private final static int SPEED = 5;
    private final static int TIMESTAMP = 6;

    // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    public SmartphoneFixesFileReader(String path) {
        super(path, false);
    }

    @Override
    protected GpsFix processLine(String line) {
        GpsFix fix = null;
        String[] slices = line.split(",");
        if (slices[0].equals("Si")) {
            try {
                fix = new GpsFix(
                        true,
                        Double.valueOf(slices[LATITUDE]),
                        Double.valueOf(slices[LONGITUDE]),
                        Double.valueOf(slices[ALTITUDE]),
                        Double.valueOf(slices[ACCURACY]),
                        Double.valueOf(slices[SPEED]),
                        simpleDateFormat.parse(slices[TIMESTAMP])
                );
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException("I couldn't parse the date, and I hate checked exceptions");
            }
        }
        return fix;
    }
}
