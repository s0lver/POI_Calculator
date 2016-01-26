package tamps.cinvestav.s0lver.spCalculator.guiMontoliou;

import tamps.cinvestav.s0lver.spCalculator.Main;
import tamps.cinvestav.s0lver.spCalculator.classes.GpsFix;
import tamps.cinvestav.s0lver.spCalculator.classes.StayPoint;
import tamps.cinvestav.s0lver.spCalculator.algorithms.live.buffered.MontoliouBufferedAlgorithm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class PresenterGUIMontoliouLive {
    private GUIMontoliouLive gui;
    private ArrayList<GpsFix> list;
    private MontoliouBufferedAlgorithm mba;

    private long maxTimeThreshold = 60 * 60 * 1000; // una hora
    private long minTimeThreshold = 10 * 60 * 1000; // 10 minutos
    private double distanceThreshold = 150;

    public PresenterGUIMontoliouLive(GUIMontoliouLive guiMontoliouLive) {
        this.gui = guiMontoliouLive;
        this.list = new ArrayList<>();
        this.mba = new MontoliouBufferedAlgorithm(maxTimeThreshold, minTimeThreshold, distanceThreshold);

    }

    public void resetList() {
        try {
            this.list = new ArrayList<GpsFix>(Arrays.asList(Main.createList()));
            this.mba = new MontoliouBufferedAlgorithm(maxTimeThreshold, minTimeThreshold, distanceThreshold);
            gui.redrawList(this.list);
            gui.clearSPList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public StayPoint processTopFix() {
        StayPoint stayPoint;
        if (list.isEmpty()) {
            stayPoint = mba.processLastPart();
        }
        else {
            GpsFix currentFix = list.get(0);
            stayPoint = mba.processFix(currentFix);
            list.remove(0);
        }

        return stayPoint;
    }
}
