package tamps.cinvestav.s0lver.stayPointsCalculator.gui;

import tamps.cinvestav.s0lver.locationentities.StayPoint;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Tools {
    private static final String[] COLUMN_NAMES = {
            "#",
            "Latitude",
            "Longitude",
            "Arrival Time",
            "Departure Time",
            "Stay Time",
            "Involved fixes"
    };

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private final static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);

    public static JTable createTable() {
        DefaultTableModel modelTable = new DefaultTableModel();
        JTable table = new JTable(modelTable);

        for (String column : COLUMN_NAMES) {
            modelTable.addColumn(column);
        }

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(false);
        TableColumn column = table.getColumnModel().getColumn(0);
        column.setPreferredWidth(1);

        column = table.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
        column = table.getColumnModel().getColumn(4);
        column.setPreferredWidth(100);

        table.setFillsViewportHeight(true);

        return table;
    }

    public static Object[] chunkStayPoint(StayPoint stayPoint) {
        long timeDifferenceMs = stayPoint.getDepartureTime().getTime() - stayPoint.getArrivalTime().getTime();
        String timeDifferenceMinutes = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMs));

        return new Object[]{
                "0",
                stayPoint.getLatitude(),
                stayPoint.getLongitude(),
                sdf.format(stayPoint.getArrivalTime()),
                sdf.format(stayPoint.getDepartureTime()),
                timeDifferenceMinutes + " min",
                stayPoint.getAmountFixes()
        };
    }
}
