package tamps.cinvestav.s0lver.jsonparser.readers;

import tamps.cinvestav.s0lver.jsonparser.sensorentities.SimpleLocation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/***
 * Reads a file of location fixes
 */
public class LocationFileReader {
    private final int LATITUDE = 1;
    private final int LONGITUDE = 2;
    private final int ALTITUDE = 3;
    private final int ACCURACY = 4;
    private final int SPEED = 5;
    private final int TIMESTAMP = 6;

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_HUMAN = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_FOR_SQLITE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    public final static String TIMED_OUT_LOCATION_PROVIDER = "TimedOutLocation";
    private static final String CUSTOM_LOCATION_PROVIDER = "CustomProvider";

    private Scanner scanner;
    private boolean endOfFileReached;

    public LocationFileReader(String path) {
        try {
            this.scanner = new Scanner(new FileReader(path));
            this.endOfFileReached = false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("I couldn't open the file %s. Additionally I hate checked exceptions", path));
        }
    }

    private SimpleLocation processLine(String line) {
        SimpleLocation fix;
        String[] slices = line.split(",");
        Date timestamp;
        try {
//            timestamp = SIMPLE_DATE_FORMAT_HUMAN.parse(slices[TIMESTAMP]);
            timestamp = SIMPLE_DATE_FORMAT_FOR_SQLITE.parse(slices[TIMESTAMP]);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("I couldn't parse the date, and I hate checked exceptions");
        }
        if (slices[0].equals("Si")) {
            fix = new SimpleLocation(CUSTOM_LOCATION_PROVIDER);
            fix.setLatitude(Double.parseDouble(slices[LATITUDE]));
            fix.setLongitude(Double.parseDouble(slices[LONGITUDE]));
            fix.setAltitude(Double.parseDouble(slices[ALTITUDE]));
            fix.setAccuracy(Float.parseFloat(slices[ACCURACY]));
            fix.setSpeed(Float.parseFloat(slices[SPEED]));
        } else {
            fix = new SimpleLocation(TIMED_OUT_LOCATION_PROVIDER);
        }
        fix.setTime(timestamp.getTime());
        return fix;
    }

    public SimpleLocation readLine() {
        if (endOfFileReached == false) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                SimpleLocation location = processLine(line);
                return location;
            } else {
                scanner.close();
                endOfFileReached = true;
            }
        }
        return null;
    }
}
