package tamps.cinvestav.s0lver.iolocationfiles.writers;

import tamps.cinvestav.s0lver.locationentities.StayPoint;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class StayPointCsvWriter {
    private String filepath;
    private ArrayList<StayPoint> stayPoints;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    public StayPointCsvWriter(String filepath, ArrayList<StayPoint> stayPoints) {
        this.filepath = filepath;
        this.stayPoints = stayPoints;
    }

    public void writeFile() throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileWriter(filepath));
        for (StayPoint stayPoint : stayPoints) {
            String line = translateStayPointToCsv(stayPoint);
            printWriter.println(line);
        }

        printWriter.close();
    }

    private String translateStayPointToCsv(StayPoint stayPoint) {
        StringBuilder sb = new StringBuilder();
        sb.append(stayPoint.getLatitude())
                .append(',')
                .append(stayPoint.getLongitude())
                .append(',')
                .append(simpleDateFormat.format(stayPoint.getArrivalTime()))
                .append(',')
                .append(simpleDateFormat.format(stayPoint.getDepartureTime()))
                .append(',')
                .append(stayPoint.getAmountFixes());

        return sb.toString();
    }
}
