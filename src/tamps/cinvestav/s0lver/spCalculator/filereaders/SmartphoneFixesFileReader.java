package tamps.cinvestav.s0lver.spCalculator.filereaders;

import tamps.cinvestav.s0lver.spCalculator.classes.GpsFix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SmartphoneFixesFileReader extends GPSFixesFileReader {

    public SmartphoneFixesFileReader(String path) {
        super(path, false);
    }

    @Override
    protected GpsFix processLine(String line) {
        GpsFix fix = null;
        // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        String[] slices = line.split(",");
        if (slices[0].equals("Si")) {
            try {
                fix = new GpsFix(
                        Double.valueOf(slices[1]),
                        Double.valueOf(slices[2]),
                        Double.valueOf(slices[3]),
                        Double.valueOf(slices[4]),
                        Double.valueOf(slices[5]),
                        simpleDateFormat.parse(slices[6])
                );
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException("I couldn't parse the date, and I hate checked exceptions");
            }
        }
        return fix;
    }
}
