package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.spCalculator.classes.GpsFix;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        new AlgorithmsInvoker().invokeAlgorithms();
    }

    /***
     * Dummmy GPS fixes list
     * @return
     * @throws ParseException
     */
    public static GpsFix[] createList() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        GpsFix[] gpsFixes = new GpsFix[]{
                new GpsFix(24.840481, -98.166489, 0, 0, 0, simpleDateFormat.parse("Tue May 15 13:47:20 CDT 2012")),
                new GpsFix(24.84123, -98.164726, 0, 0, 0, simpleDateFormat.parse("Tue May 15 13:50:20 CDT 2012")),
                new GpsFix(24.841026, -98.163269, 0, 0, 0, simpleDateFormat.parse("Tue May 15 13:52:25 CDT 2012")),
                new GpsFix(24.842857, -98.156059, 0, 0, 0, simpleDateFormat.parse("Tue May 15 14:02:10 CDT 2012")),
                new GpsFix(24.844316, -98.155846, 0, 0, 0, simpleDateFormat.parse("Tue May 15 14:04:21 CDT 2012")),
                new GpsFix(24.845079, -98.155792, 0, 0, 0, simpleDateFormat.parse("Tue May 15 14:05:33 CDT 2012")),
                new GpsFix(24.845606, -98.155815, 0, 0, 0, simpleDateFormat.parse("Tue May 15 14:06:38 CDT 2012")),
                new GpsFix(24.846004, -98.155792, 0, 0, 0, simpleDateFormat.parse("Tue May 15 14:14:44 CDT 2012")),
                new GpsFix(24.849789, -98.155647, 0, 0, 0, simpleDateFormat.parse("Tue May 15 14:15:39 CDT 2012")),
                new GpsFix(24.850178, -98.155594, 0, 0, 0, simpleDateFormat.parse("Tue May 15 14:16:32 CDT 2012"))
        };

        return gpsFixes;
    }
}
