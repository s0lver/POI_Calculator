package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.iolocationfiles.readers.staypoints.StayPointsFileReader;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsComparator.comparator.StayPointsComparator;
import tamps.cinvestav.s0lver.stayPointsComparator.comparatorResults.StayPointsComparatorResult;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FrmStayPointComparator extends JFrame implements ActionListener{
//    private ArrayList<StayPoint> stayPointsA;
//    private ArrayList<StayPoint> stayPointsB;

    private final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private JTable topTable, bottomTable;
    private JButton btnLoadFileA, btnLoadFileB;
    private JButton btnBrowseForFileA, btnBrowseForFileB;
    private JTextField txtSelectedFileA, txtSelectedFileB;
    private JFileChooser fileChooser;
    private File fileA, fileB;
    private ArrayList<StayPoint> stayPointsA, stayPointsB;
    JTextField txtArrivalTimeDifference,txtDepartureTimeDifference,txtStayTimeDifference, txtDistanceDifference;


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

        prepareGUIComponents();
        setVisible(true);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void prepareGUIComponents() {
        fileChooser = new JFileChooser();
        // setLayout(new BorderLayout(3, 3));
        setLayout(new GridLayout(3, 1));


        Dimension tablePreferredDimension = new Dimension(700, 150);

        // Top table
        topTable = createTable();
        topTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                rowsSelectionChanged();
            }
        });
        JScrollPane jspTopTable = new JScrollPane(topTable);
        jspTopTable.setPreferredSize(tablePreferredDimension);
        btnBrowseForFileA = new JButton("Browse");
        btnBrowseForFileA.addActionListener(this);
        btnLoadFileA = new JButton("Load");
        btnLoadFileA.addActionListener(this);
        txtSelectedFileA = new JTextField("Select a file!", 40);
        txtSelectedFileA.setEditable(false);
        JPanel topTablePanel = createInputComponent(jspTopTable, btnBrowseForFileA, btnLoadFileA, txtSelectedFileA);

        // Bottom table
        bottomTable = createTable();
        bottomTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                rowsSelectionChanged();
            }
        });
        JScrollPane jspBottomTable = new JScrollPane(bottomTable);
        jspBottomTable.setPreferredSize(tablePreferredDimension);
        btnBrowseForFileB = new JButton("Browse");
        btnBrowseForFileB.addActionListener(this);
        btnLoadFileB = new JButton("Load");
        btnLoadFileB.addActionListener(this);
        txtSelectedFileB = new JTextField("Select a file!", 40);
        txtSelectedFileB.setEditable(false);
        JPanel bottomTablePanel = createInputComponent(jspBottomTable, btnBrowseForFileB, btnLoadFileB, txtSelectedFileB);

        // Results
        JPanel pnlResults = new JPanel();
        pnlResults.setLayout(new GridLayout(4,2));
        pnlResults.setBorder(BorderFactory.createTitledBorder("Comparison:"));
        pnlResults.setPreferredSize(tablePreferredDimension);
        JLabel lblArrivalTimeDifference = new JLabel("Arrival time: ");
        JLabel lblDepartureTimeDifference = new JLabel("Departure time: ");
        JLabel lblStayTimeDifference = new JLabel("Stay time: ");
        JLabel lblDistanceDifference = new JLabel("Distance: ");

        txtArrivalTimeDifference = new JTextField("Select two stay points!");
        txtDepartureTimeDifference = new JTextField("Select two stay points!");
        txtStayTimeDifference = new JTextField("Select two stay points!");
        txtDistanceDifference = new JTextField("Select two stay points!");
        txtDepartureTimeDifference.setEditable(false);
        txtArrivalTimeDifference.setEditable(false);
        txtStayTimeDifference.setEditable(false);
        txtDistanceDifference.setEditable(false);

        pnlResults.add(lblArrivalTimeDifference);
        pnlResults.add(txtArrivalTimeDifference);
        pnlResults.add(lblDepartureTimeDifference);
        pnlResults.add(txtDepartureTimeDifference);
        pnlResults.add(lblStayTimeDifference);
        pnlResults.add(txtStayTimeDifference);
        pnlResults.add(lblDistanceDifference);
        pnlResults.add(txtDistanceDifference);


        //add(topTablePanel, BorderLayout.NORTH);
        //add(bottomTablePanel, BorderLayout.CENTER);
        //add(pnlResults, BorderLayout.SOUTH);
        add(topTablePanel);
        add(bottomTablePanel);
        add(pnlResults);
    }

    private JPanel createInputComponent(JScrollPane pnlTable, JButton btnBrowse, JButton btnLoad, JTextField txtSelectedFile) {
        JPanel innerTopPanel = new JPanel();
        innerTopPanel.add(new JLabel("File:"));
        innerTopPanel.add(txtSelectedFile);
        innerTopPanel.add(btnBrowse);
        innerTopPanel.add(btnLoad);

        JPanel completePanel = new JPanel();
        completePanel.setBorder(BorderFactory.createTitledBorder("Stay points origin:"));
        completePanel.setLayout(new BoxLayout(completePanel, BoxLayout.Y_AXIS));
        completePanel.add(innerTopPanel);
        completePanel.add(pnlTable);

        return completePanel;
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

        column = table.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
        column = table.getColumnModel().getColumn(4);
        column.setPreferredWidth(100);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBrowseForFileA){
            int returnVal = fileChooser.showOpenDialog(FrmStayPointComparator.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                this.fileA = selectedFile;
                this.txtSelectedFileA.setText(selectedFile.getAbsolutePath());
            }
        }
        else if (e.getSource() == btnBrowseForFileB){
            int returnVal = fileChooser.showOpenDialog(FrmStayPointComparator.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                this.fileB = selectedFile;
                this.txtSelectedFileB.setText(selectedFile.getAbsolutePath());
            }
        }
        else if (e.getSource() == btnLoadFileA){
            stayPointsA = readFromFile(fileA);
            fillTable(topTable, stayPointsA);
        }
        else if (e.getSource() == btnLoadFileB){
            stayPointsB = readFromFile(fileB);
            fillTable(bottomTable, stayPointsB);
        }
    }

    public void rowsSelectionChanged(){
        int selectedRowTT = topTable.getSelectedRow();
        int selectedRowBT = bottomTable.getSelectedRow();

        if (selectedRowTT!=-1 &&selectedRowBT != -1 ){
            StayPoint stayPointA = stayPointsA.get(selectedRowTT);
            StayPoint stayPointB = stayPointsB.get(selectedRowBT);
            StayPointsComparator stayPointsComparator = new StayPointsComparator(stayPointA, stayPointB);
            StayPointsComparatorResult stayPointsComparatorResult = stayPointsComparator.compareStayPoints();
            showComparisonResults(stayPointsComparatorResult);
        }
    }

    private void showComparisonResults(StayPointsComparatorResult stayPointsComparatorResult) {
        String arrivalTimeDifference = getHumanTimeData(stayPointsComparatorResult.getArrivalTimeDifference());
        String departureTimeDifference = getHumanTimeData(stayPointsComparatorResult.getDepartureTimeDifference());
        String stayTimeDifference = getHumanTimeData(stayPointsComparatorResult.getStayTimeDifference());
        String distanceDifference = String.valueOf(stayPointsComparatorResult.getDistanceDifference()) + " meters";

        txtArrivalTimeDifference.setText(arrivalTimeDifference);
        txtDepartureTimeDifference.setText(departureTimeDifference);
        txtStayTimeDifference.setText(stayTimeDifference);
        txtDistanceDifference.setText(distanceDifference);
    }

    private String getHumanTimeData(long timeDifference) {
        StringBuilder sb = new StringBuilder();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
        long hours = TimeUnit.MILLISECONDS.toHours(timeDifference);
        sb.append(String.valueOf(seconds))
                .append(" sec, ")
                .append(String.valueOf(minutes))
                .append(" mins, ")
                .append(String.valueOf(hours))
                .append(" hrs");

        return sb.toString();
    }

    private ArrayList<StayPoint> readFromFile(File file) {
        StayPointsFileReader sfr = new StayPointsFileReader(file.getAbsolutePath());
        return sfr.readFile();
    }
}
