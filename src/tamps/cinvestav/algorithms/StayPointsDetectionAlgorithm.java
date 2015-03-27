package tamps.cinvestav.algorithms;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import tamps.cinvestav.s0lver.poicalculator.GpsFix;
import tamps.cinvestav.s0lver.poicalculator.StayPoint;

import java.util.ArrayList;

public abstract class StayPointsDetectionAlgorithm {
    protected double minTimeTreshold;
    protected double distanceTreshold;
    protected boolean verbose;

    public StayPointsDetectionAlgorithm(double minTimeTreshold, double distanceTreshold, boolean verbose) {
        this.minTimeTreshold = minTimeTreshold;
        this.distanceTreshold = distanceTreshold;
        this.verbose = verbose;
    }

    public abstract ArrayList<StayPoint> extractPois(ArrayList<GpsFix> gpsFixes);

    /***
     * Calculates the distance between two points (GPSFix)
     *
     * @param p
     *            Origin point
     * @param q
     *            Destination point
     * @return The distance between p and q points using WGS84 ellipsoid
     */
    protected double distance(GpsFix p, GpsFix q) {
        // instantiate the calculator
        GeodeticCalculator geoCalc = new GeodeticCalculator();

        // select a reference elllipsoid
        Ellipsoid reference = Ellipsoid.WGS84;

        // set Lincoln Memorial coordinates
        GlobalCoordinates lincolnMemorial;
        lincolnMemorial = new GlobalCoordinates(p.getLatitude(),
                p.getLongitude());

        // set Eiffel Tower coordinates
        GlobalCoordinates eiffelTower;
        eiffelTower = new GlobalCoordinates(q.getLatitude(), q.getLongitude());

        // calculate the geodetic curve
        GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(reference,
                lincolnMemorial, eiffelTower);

        // System.out.printf(
        // "   Ellipsoidal Distance: %1.2f kilometers (%1.2f miles)\n",
        // ellipseKilometers, ellipseMiles);
        return geoCurve.getEllipsoidalDistance();
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
    protected long timespan(GpsFix p, GpsFix q) {
        if (p == null || q == null) {
            return Long.MIN_VALUE;
        }
        long timespan = q.getTimestamp().getTime() - p.getTimestamp().getTime();
        return timespan;
    }
}
