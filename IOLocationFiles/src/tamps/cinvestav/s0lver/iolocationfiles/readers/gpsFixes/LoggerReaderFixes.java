package tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes;

import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoggerReaderFixes extends GPSFixesFileReader {
    private final int LATITUDE = 8;
    private final int NORTH_OR_SOUTH = 9;
    private final int LONGITUDE = 10;
    private final int EAST_OR_WEST = 11;
    private final int ALTITUDE = 12;
    private final int SPEED = 13;
    private final int DATE = 3;
    private final int TIME = 4;
    // Apparently, since we are in GMT -6 and the logger delivers in GMT 0
    // we have to apply this correction... Or maybe -5
    // private final int HOURS_DIFFERENCE = -6;
    private final int HOURS_DIFFERENCE = -5;

    public LoggerReaderFixes(String path) {
        super(path, true);
    }

    protected GpsFix processLine(String line) {
        String[] tokens = line.split(",");

        double latitude = getLatitudeValue(tokens);
        double longitude = getLongitudeValue(tokens);
        double altitude = Double.parseDouble(tokens[ALTITUDE]);
        double accuracy = 0; // The GPS logger is the big brother
        double speed = Double.parseDouble(tokens[SPEED]);
        Date timestamp = createTimestamp(tokens);

        return new GpsFix(latitude, longitude, altitude, accuracy, speed, timestamp);
    }

    private double getLongitudeValue(String[] tokens) {
        double longitudeValue = Double.parseDouble(tokens[LONGITUDE]);
        if (tokens[EAST_OR_WEST].equals("W")) {
            longitudeValue *= -1;
        }

        return longitudeValue;
    }

    private double getLatitudeValue(String[] tokens) {
        double latitudeValue = Double.parseDouble(tokens[LATITUDE]);
        if (tokens[NORTH_OR_SOUTH].equals("S")) {
            latitudeValue *= -1;
        }

        return latitudeValue;
    }

    private Date createTimestamp(String[] tokens) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/M/d h:m:s");

        StringBuilder sb = new StringBuilder();

        String strDate = tokens[DATE];
        String strTime = tokens[TIME];

        sb.append(strDate).append(" ").append(strTime);

        try {
            Date date = dateFormat.parse(sb.toString());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR, HOURS_DIFFERENCE);

            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("I am lost when calculating date");
        }
    }
}
