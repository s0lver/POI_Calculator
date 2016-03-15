package tamps.cinvestav.s0lver.stayPointsCalculator.gui;

import javax.swing.*;

public class FrmMain extends JFrame {
    public FrmMain() {
        super("Main menu");
        prepareGuiComponents();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

    private void prepareGuiComponents() {
        createMenu();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu mnuFile = new JMenu("File");


        JMenuItem mnuCalculateStayPoints = new JMenuItem("Calculate Stay Points");
        mnuCalculateStayPoints.addActionListener(e -> menuGpsCalculateStayPointsSelected());

        JMenuItem mnuCompareStayPoints = new JMenuItem("Compare Stay Points");
        mnuCompareStayPoints.addActionListener(e -> menuCompareStayPointsSelected());

        JMenuItem mnuGenerateKml = new JMenuItem("Generate KML");
        mnuGenerateKml.addActionListener(e -> menuStayPointsGenerateKmlSelected());

        JMenuItem mnuExit = new JMenuItem("Exit");
        mnuExit.addActionListener(e -> menuExitSelected());

        mnuFile.add(mnuCalculateStayPoints);
        mnuFile.add(mnuCompareStayPoints);
        mnuFile.add(mnuGenerateKml);
        mnuFile.add(mnuExit);

        menuBar.add(mnuFile);
        setJMenuBar(menuBar);
    }

    private void menuGpsCalculateStayPointsSelected() {
        new FrmCalculateStayPoints();
    }

    private void menuCompareStayPointsSelected() {
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
