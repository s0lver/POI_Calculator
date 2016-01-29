package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.staypoints.StayPointsFileReader;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FrmStayPointComparator extends JFrame {
//    private ArrayList<StayPoint> stayPointsA;
//    private ArrayList<StayPoint> stayPointsB;

    private final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private JTable topTable;
    private final String[] COLUMN_NAMES = {
            "#",
            "Latitude",
            "Longitude",
            "Arrival Time",
            "Departure Time",
            "Stay Time",
            "Involved fixes"
    };

    public FrmStayPointComparator() {
        super("Stay points comparator");
        //setSize(800, 600);

        prepareGUIComponents();
        setVisible(true);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void prepareGUIComponents() {
        setLayout(new BorderLayout(3, 3));
        JTable topTable = createTable();
        JTable bottomTable = createTable();
        ArrayList<StayPoint> stayPointsA = loadStayPointsTopTable();
        ArrayList<StayPoint> stayPointsB = loadStayPointsBottomTable();

        fillTable(topTable, stayPointsA);
        fillTable(bottomTable, stayPointsB);

        Dimension tablePreferredDimension = new Dimension(500, 150);
        JScrollPane pnlTopTable = new JScrollPane(topTable);
        pnlTopTable.setPreferredSize(tablePreferredDimension);

        JScrollPane pnlBottomTable = new JScrollPane(bottomTable);
        pnlBottomTable.setPreferredSize(tablePreferredDimension);

        JPanel pnlTop = new JPanel();
        pnlTop.setBorder(BorderFactory.createTitledBorder("First family of staypoints:"));

        JPanel pnlBottom = new JPanel();
        pnlBottom.setBorder(BorderFactory.createTitledBorder("Second family of staypoints:"));


        JPanel pnlResults = new JPanel();
        pnlResults.setBorder(BorderFactory.createTitledBorder("Results of comparison"));
        pnlResults.setPreferredSize(tablePreferredDimension);

        pnlTop.add(pnlTopTable);
        pnlBottom.add(pnlBottomTable);


        add(pnlTop, BorderLayout.NORTH);
        add(pnlBottom, BorderLayout.CENTER);
        add(pnlResults, BorderLayout.SOUTH);
    }

    private JTable createTable() {
        DefaultTableModel modelTable = new DefaultTableModel();
        JTable table = new JTable(modelTable);

        for (String column : COLUMN_NAMES) {
            modelTable.addColumn(column);
        }

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(false);
        TableColumn column = table.getColumnModel().getColumn(0);
        column.setPreferredWidth(1);

        table.setFillsViewportHeight(true);

        return table;
    }

    private void fillTable(JTable table, ArrayList<StayPoint> stayPoints) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        int i = 1;
        for (StayPoint stayPoint : stayPoints) {
            Object[] stayPointChunked = chunkStayPoint(stayPoint);
            stayPointChunked[0] = String.valueOf(i++);
            model.addRow(stayPointChunked);
        }
    }

    private ArrayList<StayPoint> loadStayPointsTopTable() {
        String pathStayPointsFile = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-1\\staypoints.csv";
        StayPointsFileReader stayPointsFileReader = new StayPointsFileReader(pathStayPointsFile);
        return stayPointsFileReader.readFile();
    }

    private ArrayList<StayPoint> loadStayPointsBottomTable() {
        String pathStayPointsFile = "C:\\Users\\rafael\\desktop\\tmp\\exp\\sp-2\\staypoints.csv";
        StayPointsFileReader stayPointsFileReader = new StayPointsFileReader(pathStayPointsFile);
        return stayPointsFileReader.readFile();
    }

    private Object[] chunkStayPoint(StayPoint stayPoint) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
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
