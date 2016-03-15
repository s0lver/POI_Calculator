package tamps.cinvestav.s0lver.stayPointsCalculator.gui;

import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.GPSFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.LoggerReaderFixes;
import tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes.SmartphoneFixesFileReader;
import tamps.cinvestav.s0lver.iolocationfiles.writers.StayPointCsvWriter;
import tamps.cinvestav.s0lver.kmltranslator.translators.PinnedKmlCreator;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.MontoliuAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.OfflineAlgorithm;
import tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline.ZhenAlgorithm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FrmCalculateStayPoints extends JFrame implements ActionListener{
    private JTextField txtMinimumDistance;
    private JTextField txtMaximumTime;
    private JTextField txtMinimumTime;
    private JButton btnBrowseFile;
    private JButton btnProcessFile;
    private JRadioButton rdbSmartphone;
    private JRadioButton rdbGpsLogger;

    private JTextField txtFilePath;
    private JFileChooser fileChooser;
    private File fileInput;
    private String placeholderFile;

    private static String[] ALGORITHMS = new String[]{"Zhen", "Montoliou"};
    private JComboBox<String> cmbAlgorithms;


    private JTable outputTable;

    public FrmCalculateStayPoints() {
        super("Stay points calculation");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createGuiComponents();
        pack();
        setVisible(true);

    }

    private void createGuiComponents() {
        placeholderFile = "Select a file!";
        setLayout(new GridLayout(3, 1));
        assignGuiReferences();

        JPanel algorithmSelectionPanel = createAlgorithmSelectionPanel();
        JPanel inputPanel = createInputPanel();
        JPanel outputPanel = createOutputPanel();
        add(algorithmSelectionPanel);
        add(inputPanel);
        add(outputPanel);
    }

    private JPanel createAlgorithmSelectionPanel() {
        JPanel innerTopPanel = new JPanel();
        innerTopPanel.setLayout(new GridLayout(4, 2));
        innerTopPanel.add(new JLabel("Select algorithm:"));
        innerTopPanel.add(cmbAlgorithms);
        innerTopPanel.add(new JLabel("Minimum time (seconds): "));
        innerTopPanel.add(txtMinimumTime);
        innerTopPanel.add(new JLabel("Maximum time (seconds): "));
        innerTopPanel.add(txtMaximumTime);
        innerTopPanel.add(new JLabel("Minimum distance (meters): "));
        innerTopPanel.add(txtMinimumDistance);


        JPanel completePanel = new JPanel();
        completePanel.setBorder(BorderFactory.createTitledBorder("Algorithm selection:"));
        completePanel.setLayout(new BoxLayout(completePanel, BoxLayout.Y_AXIS));
        completePanel.add(innerTopPanel);
        return completePanel;
    }

    private JPanel createInputPanel() {
        JPanel pnlFileSelection = new JPanel();
        pnlFileSelection.add(new JLabel("File: "));
        pnlFileSelection.add(txtFilePath);
        pnlFileSelection.add(btnBrowseFile);

        JPanel pnlFileType = new JPanel();
        pnlFileType.add(rdbSmartphone);
        pnlFileType.add(rdbGpsLogger);

        JPanel mainEmbeddedPanel = new JPanel();
        mainEmbeddedPanel.setLayout(new GridLayout(3, 1));
        mainEmbeddedPanel.add(pnlFileSelection);
        mainEmbeddedPanel.add(pnlFileType);
        mainEmbeddedPanel.add(btnProcessFile);

        JPanel completePanel = new JPanel();
        completePanel.setBorder(BorderFactory.createTitledBorder("Algorithm selection:"));
        completePanel.setLayout(new BoxLayout(completePanel, BoxLayout.Y_AXIS));
        completePanel.add(mainEmbeddedPanel);

        return completePanel;
    }

    private JPanel createOutputPanel() {
        Dimension tablePreferredDimension = new Dimension(700, 150);

        outputTable = Tools.createTable();
        JScrollPane scrPanelTable = new JScrollPane(outputTable);
        scrPanelTable.setPreferredSize(tablePreferredDimension);

        JPanel completePanel = new JPanel();
        completePanel.setBorder(BorderFactory.createTitledBorder("Algorithm's output:"));
        completePanel.setLayout(new BoxLayout(completePanel, BoxLayout.Y_AXIS));
        completePanel.add(scrPanelTable);

        return completePanel;
    }

    public void assignGuiReferences() {
        cmbAlgorithms = new JComboBox<>(ALGORITHMS);
        txtMinimumDistance = new JTextField();
        txtMaximumTime = new JTextField();
        txtMinimumTime = new JTextField();

        btnBrowseFile = new JButton("Browse");
        btnBrowseFile.addActionListener(this);
        btnProcessFile = new JButton("Process");
        btnProcessFile.addActionListener(this);
        txtFilePath = new JTextField(placeholderFile, 50);
        txtFilePath.setEditable(false);


        rdbSmartphone = new JRadioButton("Smartphone", true);
        rdbGpsLogger = new JRadioButton("Gps Logger");
        ButtonGroup group = new ButtonGroup();
        group.add(rdbSmartphone);
        group.add(rdbGpsLogger);

        fileChooser = new JFileChooser();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBrowseFile){
            int returnVal = fileChooser.showOpenDialog(FrmCalculateStayPoints.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                this.fileInput = selectedFile;
                this.txtFilePath.setText(selectedFile.getAbsolutePath());
            }
        }
        if (e.getSource() == btnProcessFile) {
            if (txtFilePath.getText().equals(placeholderFile)) {
                JOptionPane.showMessageDialog(this, "Select a file first", "Alert", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ArrayList<StayPoint> stayPoints = calculateStayPoints();
            if (stayPoints.size() > 1) {
                showStayPoints(stayPoints);
                saveStayPoints(stayPoints);
                saveKmlFile(stayPoints);
            }else{
                JOptionPane.showMessageDialog(this, "No stay points found", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void saveStayPoints(ArrayList<StayPoint> stayPoints) {
        String algorithmIdentifier = getAlgorithmIdentifier();

        String outputFileName = fileInput.getName().split("\\.")[0];

        outputFileName = outputFileName + "-" + algorithmIdentifier + ".csv";
        StayPointCsvWriter writer = new StayPointCsvWriter(fileInput.getParent() + File.separator + outputFileName, stayPoints);

        try {
            writer.writeFile();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred when saving the file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showStayPoints(ArrayList<StayPoint> stayPoints) {
        DefaultTableModel model = (DefaultTableModel) outputTable.getModel();
        model.setRowCount(0);

        int i = 1;
        for (StayPoint stayPoint : stayPoints) {
            Object[] stayPointChunked = Tools.chunkStayPoint(stayPoint);
            stayPointChunked[0] = String.valueOf(i++);
            model.addRow(stayPointChunked);
        }
    }

    private void saveKmlFile(ArrayList<StayPoint> stayPoints) {
        String algorithmIdentifier = getAlgorithmIdentifier();

        String outputFileName = fileInput.getName().split("\\.")[0];

        outputFileName = outputFileName + "-" + algorithmIdentifier + ".kml";
        PinnedKmlCreator writer = PinnedKmlCreator.createForStayPoints(fileInput.getParent() + File.separator + outputFileName + outputFileName, stayPoints);

        try {
            writer.create();
        } catch (IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred when saving the file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getAlgorithmIdentifier() {
        String algorithmIdentifier = null;
        if (cmbAlgorithms.getSelectedIndex() == 0) {
            algorithmIdentifier = "zhen";
        } else if (cmbAlgorithms.getSelectedIndex() == 1) {
            algorithmIdentifier = "montoliou";
        }
        else{
            throw new RuntimeException("Algorithm type not supported");
        }
        return algorithmIdentifier;
    }

    private ArrayList<StayPoint> calculateStayPoints() {
        OfflineAlgorithm stayPointsAlgorithm;
        long minimumDistance = Long.valueOf(txtMinimumDistance.getText());
        long minimumTime = Long.valueOf(txtMinimumTime.getText()) * 1000;
        ArrayList<GpsFix> gpsFixes = readFixes();
        if (cmbAlgorithms.getSelectedIndex() == 0) {
            stayPointsAlgorithm = new ZhenAlgorithm(gpsFixes, minimumTime, minimumDistance, false);
        }
        else{
            long maximumTime = Long.valueOf(txtMaximumTime.getText()) * 1000;
            stayPointsAlgorithm = new MontoliuAlgorithm(gpsFixes, minimumTime, maximumTime, minimumDistance, false);
        }
        return stayPointsAlgorithm.extractStayPoints();
    }

    private ArrayList<GpsFix> readFixes(){
        GPSFixesFileReader reader;
        if (rdbSmartphone.isSelected()){
            reader = new SmartphoneFixesFileReader(fileInput.getAbsolutePath());
        }else{
            reader = new LoggerReaderFixes(fileInput.getAbsolutePath());
        }
        return reader.readFile();
    }
}
