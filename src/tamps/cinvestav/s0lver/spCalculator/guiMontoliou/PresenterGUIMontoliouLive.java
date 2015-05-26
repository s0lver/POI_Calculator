package tamps.cinvestav.s0lver.spCalculator.guiMontoliou;

import tamps.cinvestav.s0lver.spCalculator.Main;
import tamps.cinvestav.s0lver.spCalculator.algorithms.MontoliouLiveAlgorithm;
import tamps.cinvestav.s0lver.spCalculator.classes.GpsFix;
import tamps.cinvestav.s0lver.spCalculator.classes.StayPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class PresenterGUIMontoliouLive {
    private GUIMontoliouLive gui;
    private ArrayList<GpsFix> list;
    private MontoliouLiveAlgorithm montoliouLiveAlgorithm;

    private long maxTimeThreshold = 60 * 60 * 1000; // una hora
    private long minTimeThreshold = 10 * 60 * 1000; // 10 minutos
    private double distanceThreshold = 150;

    public PresenterGUIMontoliouLive(GUIMontoliouLive guiMontoliouLive) {
        this.gui = guiMontoliouLive;
        this.list = new ArrayList<>();
        this.montoliouLiveAlgorithm = new MontoliouLiveAlgorithm(maxTimeThreshold, minTimeThreshold, distanceThreshold);

    }

    public void resetList() {
        try {
            this.list = new ArrayList<GpsFix>(Arrays.asList(createList()));
            this.montoliouLiveAlgorithm = new MontoliouLiveAlgorithm(maxTimeThreshold, minTimeThreshold, distanceThreshold);
            gui.redrawList(this.list);
            gui.clearSPList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private GpsFix[] createList() throws ParseException {
        return Main.createList();
    }

    public StayPoint processTopFix() {
        StayPoint stayPoint;
        if (list.isEmpty()) {
            stayPoint = montoliouLiveAlgorithm.processLastPart();
        }
        else {
            GpsFix currentFix = list.get(0);
            stayPoint = montoliouLiveAlgorithm.processFix(currentFix);
            list.remove(0);
        }

        return stayPoint;
    }
}
