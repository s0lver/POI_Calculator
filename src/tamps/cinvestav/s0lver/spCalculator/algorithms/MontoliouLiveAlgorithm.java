package tamps.cinvestav.s0lver.spCalculator.algorithms;

import tamps.cinvestav.s0lver.spCalculator.classes.GpsFix;
import tamps.cinvestav.s0lver.spCalculator.classes.StayPoint;

import java.util.ArrayList;

public class MontoliouLiveAlgorithm {
    private ArrayList<GpsFix> list;
    private long maxTimeThreshold;
    private double distanceThreshold;
    private long minTimeThreshold;

    public MontoliouLiveAlgorithm(long maxTimeThreshold, long minTimeThreshold, double distanceThreshold) {
        this.maxTimeThreshold = maxTimeThreshold;
        this.minTimeThreshold = minTimeThreshold;
        this.distanceThreshold = distanceThreshold;
        this.list = new ArrayList<>();
    }

    public StayPoint processFix(GpsFix fix) {
        list.add(fix);
        int size = list.size();
        if (size == 1) {
            return null;
        }
        else{
            return processLive();
        }
    }

    private StayPoint processLive() {
        int n = list.size();

        GpsFix pi = list.get(0);
        GpsFix pj = list.get(n - 1);
        GpsFix pjMinus = list.get(n - 2);

        long timespan = timeDifference(pjMinus, pj);
        if (timespan > maxTimeThreshold) {
            cleanList(pj);
            return null;
        }

        double distance = distance(pi, pj);

        if (distance > distanceThreshold) {
            timespan = timeDifference(pi, pj);

            if (timespan > minTimeThreshold) {
                StayPoint sp = StayPoint.createStayPoint(list, 0, n - 1);
                cleanList(pj);
                return sp;
            }
            cleanList(pj);
        }

        return null;
    }

    public StayPoint processLastPart() {
        int n = list.size();
        StayPoint sp = StayPoint.createStayPoint(list, 0, n - 1);
        list.clear();
        return sp;
    }

    private void cleanList(GpsFix pj) {
        list = new ArrayList<>();
        list.add(pj);
    }

    /***
     * Calculates the time span between two Points (GPSFix)
     *
     * @param p
     *            Origin point
     * @param q
     *            Destination point
     * @return Time span between the p and q points
     */
    protected long timeDifference(GpsFix p, GpsFix q) {
        if (p == null || q == null) {
            return Long.MIN_VALUE;
        }
        long timespan = q.getTimestamp().getTime() - p.getTimestamp().getTime();
        return timespan;
    }

    private float distance(GpsFix p, GpsFix q) {
        return computeDistanceAndBearing(p.getLatitude(), p.getLongitude(), q.getLatitude(), q.getLongitude());
    }

    /***
     * Official Android Way to calculate the distance
     * http://grepcode.com/file_/repository.grepcode.com/java/ext/com.google.android/android/4.0.1_r1/android/location/Location.java/?v=source
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    private static float computeDistanceAndBearing(double lat1, double lon1, double lat2, double lon2) {
       // float[] results = new float[2];
        int MAXITERS = 20;

        lat1 *= Math.PI / 180.0;
        lat2 *= Math.PI / 180.0;
        lon1 *= Math.PI / 180.0;
        lon2 *= Math.PI / 180.0;

        double a = 6378137.0; // WGS84 major axis
        double b = 6356752.3142; // WGS84 semi-major axis
        double f = (a - b) / a;
        double aSqMinusBSqOverBSq = (a * a - b * b) / (b * b);

        double L = lon2 - lon1;
        double A = 0.0;
        double U1 = Math.atan((1.0 - f) * Math.tan(lat1));
        double U2 = Math.atan((1.0 - f) * Math.tan(lat2));

        double cosU1 = Math.cos(U1);
        double cosU2 = Math.cos(U2);
        double sinU1 = Math.sin(U1);
        double sinU2 = Math.sin(U2);
        double cosU1cosU2 = cosU1 * cosU2;
        double sinU1sinU2 = sinU1 * sinU2;

        double sigma = 0.0;
        double deltaSigma = 0.0;
        double cosSqAlpha = 0.0;
        double cos2SM = 0.0;
        double cosSigma = 0.0;
        double sinSigma = 0.0;
        double cosLambda = 0.0;
        double sinLambda = 0.0;

        double lambda = L; // initial guess
        for (int iter = 0; iter < MAXITERS; iter++) {
            double lambdaOrig = lambda;
            cosLambda = Math.cos(lambda);
            sinLambda = Math.sin(lambda);
            double t1 = cosU2 * sinLambda;
            double t2 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda;
            double sinSqSigma = t1 * t1 + t2 * t2; // (14)
            sinSigma = Math.sqrt(sinSqSigma);
            cosSigma = sinU1sinU2 + cosU1cosU2 * cosLambda; // (15)
            sigma = Math.atan2(sinSigma, cosSigma); // (16)
            double sinAlpha = (sinSigma == 0) ? 0.0 :
                    cosU1cosU2 * sinLambda / sinSigma; // (17)
            cosSqAlpha = 1.0 - sinAlpha * sinAlpha;
            cos2SM = (cosSqAlpha == 0) ? 0.0 :
                    cosSigma - 2.0 * sinU1sinU2 / cosSqAlpha; // (18)

            double uSquared = cosSqAlpha * aSqMinusBSqOverBSq; // defn
            A = 1 + (uSquared / 16384.0) * // (3)
                    (4096.0 + uSquared *
                            (-768 + uSquared * (320.0 - 175.0 * uSquared)));
            double B = (uSquared / 1024.0) * // (4)
                    (256.0 + uSquared *
                            (-128.0 + uSquared * (74.0 - 47.0 * uSquared)));
            double C = (f / 16.0) *
                    cosSqAlpha *
                    (4.0 + f * (4.0 - 3.0 * cosSqAlpha)); // (10)
            double cos2SMSq = cos2SM * cos2SM;
            deltaSigma = B * sinSigma * // (6)
                    (cos2SM + (B / 4.0) *
                            (cosSigma * (-1.0 + 2.0 * cos2SMSq) -
                                    (B / 6.0) * cos2SM *
                                            (-3.0 + 4.0 * sinSigma * sinSigma) *
                                            (-3.0 + 4.0 * cos2SMSq)));

            lambda = L +
                    (1.0 - C) * f * sinAlpha *
                            (sigma + C * sinSigma *
                                    (cos2SM + C * cosSigma *
                                            (-1.0 + 2.0 * cos2SM * cos2SM))); // (11)

            double delta = (lambda - lambdaOrig) / lambda;
            if (Math.abs(delta) < 1.0e-12) {
                break;
            }
        }

        float distance = (float) (b * A * (sigma - deltaSigma));
        return        distance;
    }
}
