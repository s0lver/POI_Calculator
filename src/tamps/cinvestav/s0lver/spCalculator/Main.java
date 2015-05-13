package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.spCalculator.algorithms.MontoliuAlgorithm;
import tamps.cinvestav.s0lver.spCalculator.algorithms.StayPointsDetectionAlgorithm;
import tamps.cinvestav.s0lver.spCalculator.algorithms.ZhenAlgorithm;
import tamps.cinvestav.s0lver.spCalculator.classes.GpsFix;
import tamps.cinvestav.s0lver.spCalculator.classes.StayPoint;
import tamps.cinvestav.s0lver.spCalculator.guiMontoliou.GUIMontoliouLive;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {

        GpsFix[] gpsFixes = createList();
        //writeFile("c:\\users\\cinvestav\\Desktop\\gpsFixes.csv", gpsFixes);

        ArrayList<GpsFix> gpsFixArrayList = new ArrayList<GpsFix>(Arrays.asList(gpsFixes));

        StayPointsDetectionAlgorithm zhengAlgorithm = new ZhenAlgorithm(gpsFixArrayList, 60 * 1000, 150);
        StayPointsDetectionAlgorithm montoliuAlgorithm = new MontoliuAlgorithm(gpsFixArrayList, 60 * 1000, 3600 * 1000, 150);

        ArrayList<StayPoint> stayPointsZheng = zhengAlgorithm.extractStayPoints();
        ArrayList<StayPoint> stayPointsMontoliu = montoliuAlgorithm.extractStayPoints();

        System.out.println(String.format("Zheng obtained %d points", stayPointsZheng.size()));
        System.out.println(String.format("Montoliu obtained %d points", stayPointsMontoliu.size()));

        System.out.println("Points obtained by Zheng");
        for (StayPoint stayPoint : stayPointsZheng) {
            System.out.println(stayPoint);
        }

        System.out.println("Points obtained by Montoliu");
        for (StayPoint stayPoint : stayPointsMontoliu) {
            System.out.println(stayPoint);
        }


        JFrame frame = new JFrame("GUIMontoliouLive");
        frame.setContentPane(new GUIMontoliouLive().getPnlGUIMontliouLive());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.pack();
        frame.setVisible(true);
    }

    public static GpsFix[] createList() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        GpsFix[] gpsFixes = new GpsFix[]{
                new GpsFix(24.840481f, -98.166489f, 0, simpleDateFormat.parse("Tue May 15 13:47:20 CDT 2012"), 0),
                new GpsFix(24.84123f, -98.164726f, 0, simpleDateFormat.parse("Tue May 15 13:50:20 CDT 2012"), 0),
                new GpsFix(24.841026f, -98.163269f, 0, simpleDateFormat.parse("Tue May 15 13:52:25 CDT 2012"), 0),
                new GpsFix(24.842857f, -98.156059f, 0, simpleDateFormat.parse("Tue May 15 14:02:10 CDT 2012"), 0),
                new GpsFix(24.844316f, -98.155846f, 0, simpleDateFormat.parse("Tue May 15 14:04:21 CDT 2012"), 0),
                new GpsFix(24.845079f, -98.155792f, 0, simpleDateFormat.parse("Tue May 15 14:05:33 CDT 2012"), 0),
                new GpsFix(24.845606f, -98.155815f, 0, simpleDateFormat.parse("Tue May 15 14:06:38 CDT 2012"), 0),
                new GpsFix(24.846004f, -98.155792f, 0, simpleDateFormat.parse("Tue May 15 14:14:44 CDT 2012"), 0),
                new GpsFix(24.849789f, -98.155647f, 0, simpleDateFormat.parse("Tue May 15 14:15:39 CDT 2012"), 0),
                new GpsFix(24.850178f, -98.155594f, 0, simpleDateFormat.parse("Tue May 15 14:16:32 CDT 2012"), 0)
        };

        return gpsFixes;
    }

    public static void writeFile(String fileName, GpsFix[] gpsFixes) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        for (GpsFix gpsFix: gpsFixes) {
            pw.println(gpsFix.toCsv());
        }
        pw.close();
    }
}
