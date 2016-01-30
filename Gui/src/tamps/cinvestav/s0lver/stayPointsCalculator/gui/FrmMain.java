package tamps.cinvestav.s0lver.stayPointsCalculator.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmMain extends JFrame {
    public FrmMain() {
        super("Main menu");
        prepareGuiComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void prepareGuiComponents() {
        createMenu();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu mnuFile = new JMenu("File");

        JMenu mnuGpsFixes = new JMenu("Gps fixes");
        JMenuItem mnuGpsGenerateKml = new JMenuItem("Generate KML");
        mnuGpsGenerateKml.addActionListener(e -> menuGpsGenerateKmlSelected());
        JMenuItem mnuGpsCalculateStayPoints = new JMenuItem("Calculate Stay Points");
        mnuGpsCalculateStayPoints.addActionListener(e -> menuGpsCalculateStayPointsSelected());
        mnuGpsFixes.add(mnuGpsGenerateKml);
        mnuGpsFixes.add(mnuGpsCalculateStayPoints);


        JMenu mnuStayPoints = new JMenu("Stay points");
        JMenuItem mnuStayPointsGenerateKml = new JMenuItem("Generate KML");
        mnuStayPointsGenerateKml.addActionListener(e -> menuStayPointsGenerateKmlSelected());
        mnuStayPoints.add(mnuStayPointsGenerateKml);
        JMenuItem mnuStayPointsCompare = new JMenuItem("Compare");
        mnuStayPointsCompare.addActionListener(e -> menuStayPointsCompareSelected());
        mnuStayPoints.add(mnuStayPointsCompare);

        JMenuItem mnuExit = new JMenuItem("Exit");
        mnuExit.addActionListener(e -> menuExitSelected());

        mnuFile.add(mnuGpsFixes);
        mnuFile.addSeparator();
        mnuFile.add(mnuStayPoints);
        mnuFile.addSeparator();
        mnuFile.add(mnuExit);

        menuBar.add(mnuFile);
        setJMenuBar(menuBar);
    }

    private void menuGpsCalculateStayPointsSelected() {
        JOptionPane.showMessageDialog(this, "We are going to CALCULATE stay points from GPS fixes", "Hey!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void menuGpsGenerateKmlSelected() {
        JOptionPane.showMessageDialog(this, "We are going to GENERATE KML for GPS fixes", "Hey!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void menuStayPointsCompareSelected() {
        JOptionPane.showMessageDialog(this, "We are going to COMPARE stay points", "Hey!", JOptionPane.INFORMATION_MESSAGE);
        this.setEnabled(false);
        new FrmStayPointComparator();
    }

    private void menuStayPointsGenerateKmlSelected() {
        JOptionPane.showMessageDialog(this, "We are going to GENERATE KML for stay points", "Hey!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void menuExitSelected() {
        System.exit(0);
    }

}
