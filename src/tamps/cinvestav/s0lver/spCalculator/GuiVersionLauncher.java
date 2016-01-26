package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.spCalculator.guiMontoliou.GUIMontoliouLive;

import javax.swing.*;

public class GuiVersionLauncher {
    public void launchGUIMontoliouLive() {
        JFrame frame = new JFrame("Sample behavior of Montoliou Live");
        frame.setContentPane(new GUIMontoliouLive().getPnlGUIMontliouLive());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.pack();
        frame.setVisible(true);
    }
}
