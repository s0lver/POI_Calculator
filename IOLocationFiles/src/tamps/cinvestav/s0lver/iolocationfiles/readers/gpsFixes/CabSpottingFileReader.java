package tamps.cinvestav.s0lver.iolocationfiles.readers.gpsFixes;

import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.util.Date;

public class CabSpottingFileReader extends GPSFixesFileReader {
    private final static int LATITUDE = 0;
    private final static int LONGITUDE = 1;
    private final static int OCCUPIED = 2;
    private final static int TIMESTAMP = 3;

    public CabSpottingFileReader(String path, boolean skipFirstLine) {
        super(path, skipFirstLine);
    }

    @Override
    protected GpsFix processLine(String line) {
        String tokens[] = line.split(" ");
        Date ts = new Date((Long.valueOf(tokens[TIMESTAMP])) * 1000);
        return new GpsFix(true, Double.valueOf(tokens[LATITUDE]), Double.valueOf(tokens[LONGITUDE]),
                0, 0, 0, ts);
    }

}
