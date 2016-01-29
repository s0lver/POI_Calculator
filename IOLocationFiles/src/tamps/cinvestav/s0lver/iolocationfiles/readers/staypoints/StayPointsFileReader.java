package tamps.cinvestav.s0lver.iolocationfiles.readers.staypoints;

import tamps.cinvestav.s0lver.locationentities.StayPoint;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class StayPointsFileReader {
    private String filepath;
    private String dateFormat = "dd/MM/yyyy HH:mm:ss";
    private SimpleDateFormat simpleDateFormat;

    private final int LATITUDE = 0;
    private final int LONGITUDE = 1;
    private final int ARRIVAL_TIME = 2;
    private final int DEPARTURE_TIME = 3;
    private final int AMOUNT_FIXES = 4;

    public StayPointsFileReader(String filepath) {
        this.filepath = filepath;
        this.simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
    }

    public ArrayList<StayPoint> readFile() {
        ArrayList<StayPoint> stayPoints = new ArrayList<>();
        Scanner scanner;

        try {
            scanner = new Scanner(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("I couldn't open the file. Additionally I hate checked exceptions");
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            StayPoint stayPoint = processLine(line);
            stayPoints.add(stayPoint);
        }

        scanner.close();
        return stayPoints;
    }

    private StayPoint processLine(String line) {
        String[] slices = line.split(",");
        try {
            double latitude = Double.valueOf(slices[LATITUDE]);
            double longitude = Double.valueOf(slices[LONGITUDE]);
            Date arrivalTime = simpleDateFormat.parse(slices[ARRIVAL_TIME]);
            Date departureTime = simpleDateFormat.parse(slices[DEPARTURE_TIME]);
            int amountFixes = Integer.valueOf(slices[AMOUNT_FIXES]);


            StayPoint stayPoint = new StayPoint(latitude, longitude, arrivalTime, departureTime, amountFixes);
            return stayPoint;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("I couldn't parse the date, and I hate checked exceptions");
        }
    }


}
