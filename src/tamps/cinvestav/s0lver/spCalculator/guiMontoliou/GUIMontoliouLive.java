package tamps.cinvestav.s0lver.spCalculator.guiMontoliou;

import tamps.cinvestav.s0lver.spCalculator.classes.GpsFix;
import tamps.cinvestav.s0lver.spCalculator.classes.StayPoint;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIMontoliouLive {
    private JList lstGpsFixes;
    private JPanel pnlGUIMontliouLive;
    private JButton btnProcessTopFix;
    private JButton btnResetList;
    private JList lstStayPoints;

    private PresenterGUIMontoliouLive presenter;

    private DefaultListModel modelLstGpsFixes;
    private DefaultListModel modelLstStayPoints;
    public GUIMontoliouLive() {
        presenter = new PresenterGUIMontoliouLive(this);

        modelLstGpsFixes = new DefaultListModel();
        modelLstStayPoints = new DefaultListModel();

        this.lstGpsFixes.setModel(modelLstGpsFixes);
        this.lstStayPoints.setModel(modelLstStayPoints);

        btnProcessTopFix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StayPoint stayPoint = presenter.processTopFix();
                if (stayPoint != null) {
                    modelLstStayPoints.addElement(stayPoint);
                }
                if (!modelLstGpsFixes.isEmpty()) {
                    modelLstGpsFixes.remove(0);
                }
            }
        });
        btnResetList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.resetList();
            }
        });
    }

    public JPanel getPnlGUIMontliouLive() {
        return pnlGUIMontliouLive;
    }

    public void redrawList(ArrayList<GpsFix> list) {
        modelLstGpsFixes.clear();

        for (int i = 0; i < list.size(); i++) {
            modelLstGpsFixes.addElement(list.get(i));
        }
    }

    public void clearSPList() {
        modelLstStayPoints.clear();
    }
}
