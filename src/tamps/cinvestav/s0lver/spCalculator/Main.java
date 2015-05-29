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


        //launchGUIMontoliouLive();
    }

    private static void launchGUIMontoliouLive() {
        JFrame frame = new JFrame("GUIMontoliouLive");
        frame.setContentPane(new GUIMontoliouLive().getPnlGUIMontliouLive());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.pack();
        frame.setVisible(true);
    }

    public static GpsFix[] createList() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        /*GpsFix[] gpsFixes = new GpsFix[]{
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
        };*/

        GpsFix[] gpsFixes = new GpsFix[]{
                new GpsFix(23.72015443020368f, -99.07757367259318f, 48.0f, simpleDateFormat.parse("Wed May 20 15:15:46 CDT 2015"), 0),
                new GpsFix(23.72050380055873f, -99.07756252535356f, 192.0f, simpleDateFormat.parse("Wed May 20 15:16:22 CDT 2015"), 0),
                new GpsFix(23.72031328634895f, -99.07737978112793f, 200.0f, simpleDateFormat.parse("Wed May 20 15:17:06 CDT 2015"), 0),
                new GpsFix(23.720224353380605f, -99.07754472054732f, 96.0f, simpleDateFormat.parse("Wed May 20 15:17:50 CDT 2015"), 0),
                new GpsFix(23.72022838399026f, -99.07735030538942f, 96.0f, simpleDateFormat.parse("Wed May 20 15:18:28 CDT 2015"), 0),
                new GpsFix(23.720294354445787f, -99.07749754201258f, 96.0f, simpleDateFormat.parse("Wed May 20 15:19:07 CDT 2015"), 0),
                new GpsFix(23.720325024527135f, -99.07745611104758f, 96.0f, simpleDateFormat.parse("Wed May 20 15:19:48 CDT 2015"), 0),
                new GpsFix(23.720234407645613f, -99.07758079213934f, 64.0f, simpleDateFormat.parse("Wed May 20 15:20:31 CDT 2015"), 0),
                new GpsFix(23.71975127423932f, -99.07739923293718f, 192.0f, simpleDateFormat.parse("Wed May 20 15:21:11 CDT 2015"), 0),
                new GpsFix(23.72039628705774f, -99.07774381080445f, 128.0f, simpleDateFormat.parse("Wed May 20 15:21:49 CDT 2015"), 0),
                new GpsFix(23.720260694637474f, -99.07749841485177f, 96.0f, simpleDateFormat.parse("Wed May 20 15:22:29 CDT 2015"), 0),
                new GpsFix(23.72022213516582f, -99.07744388727136f, 48.0f, simpleDateFormat.parse("Wed May 20 15:23:10 CDT 2015"), 0),
                new GpsFix(23.720364418333265f, -99.0774307781147f, 250.0f, simpleDateFormat.parse("Wed May 20 15:23:57 CDT 2015"), 0),
                new GpsFix(23.719933286139696f, -99.07764848413764f, 96.0f, simpleDateFormat.parse("Wed May 20 15:24:43 CDT 2015"), 0),
                new GpsFix(23.72042125248457f, -99.07732966615586f, 64.0f, simpleDateFormat.parse("Wed May 20 15:25:26 CDT 2015"), 0),
                new GpsFix(23.719610119936565f, -99.07762311133334f, 128.0f, simpleDateFormat.parse("Wed May 20 15:26:11 CDT 2015"), 0),
                new GpsFix(23.720630165722508f, -99.07743204290546f, 64.0f, simpleDateFormat.parse("Wed May 20 15:26:55 CDT 2015"), 0),
                new GpsFix(23.721070125231886f, -99.0774545508783f, 128.0f, simpleDateFormat.parse("Wed May 20 15:27:41 CDT 2015"), 0),
                new GpsFix(23.72028498069905f, -99.07753263823902f, 128.0f, simpleDateFormat.parse("Wed May 20 15:28:39 CDT 2015"), 0),
                new GpsFix(23.72027666079672f, -99.07729011209368f, 64.0f, simpleDateFormat.parse("Wed May 20 15:29:24 CDT 2015"), 0),
                new GpsFix(23.720423021967022f, -99.07757557527788f, 128.0f, simpleDateFormat.parse("Wed May 20 15:30:09 CDT 2015"), 0)
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
