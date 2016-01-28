package tamps.cinvestav.s0lver.iolocationfiles.readers;


import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class GPSFixesFileReader {
    protected String path;
    private boolean skipFirstLine;

    public GPSFixesFileReader(String path, boolean skipFirstLine) {
        this.path = path;
        this.skipFirstLine = skipFirstLine;
    }

    protected abstract GpsFix processLine(String line);

    public ArrayList<GpsFix> readFile() {
        ArrayList<GpsFix> gpsFixes = new ArrayList<>();
        Scanner scanner;

        try {
            scanner = new Scanner(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("I couldn't open the file. Additionally I hate checked exceptions");
        }

        // Skip the header as needed
        if (skipFirstLine) {
            scanner.nextLine();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            GpsFix gpsFix = processLine(line);
            if (gpsFix != null)
                gpsFixes.add(gpsFix);
        }

        scanner.close();
        return gpsFixes;
    }

}
