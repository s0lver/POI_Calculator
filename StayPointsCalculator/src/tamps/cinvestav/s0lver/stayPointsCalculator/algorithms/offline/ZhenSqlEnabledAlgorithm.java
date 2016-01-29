package tamps.cinvestav.s0lver.stayPointsCalculator.algorithms.offline;

import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;
import tamps.cinvestav.s0lver.stayPointsCalculator.dbtools.MySQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ZhenSqlEnabledAlgorithm extends OfflineAlgorithm {
    private String urlDbServer = "jdbc:mysql://localhost/bd_geo";
    private String dbUsername = "root";
    private String dbPassword = "root";
    private String dbTableName = "tbl_geo";


    public ZhenSqlEnabledAlgorithm(ArrayList<GpsFix> gpsFixes, long minTimeTreshold, double distanceTreshold, boolean verbose,
                                   String urlDbServer, String dbUsername, String dbPass, String dbTableName) {
        super(gpsFixes, minTimeTreshold, distanceTreshold, verbose);
        this.urlDbServer = urlDbServer;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPass;
        this.dbTableName = dbTableName;
    }

    @Override
    public ArrayList<StayPoint> extractStayPoints() {
        ArrayList<StayPoint> result = new ArrayList<>();
        try {
            MySQLConnector connector = new MySQLConnector(urlDbServer,
                    dbUsername, dbPassword);

            connector.prepare();

            long startTimeQuery = System.currentTimeMillis();
            if (verbose) {
                System.out.println("Trying to do the query");
            }
            ResultSet rs = connector.doQuery(1, dbTableName);
            long endTimeQuery = System.currentTimeMillis();
            if (verbose) {
                System.out.println("Query obtained in "
                        + (endTimeQuery - startTimeQuery)
                        + " ms, starting algorithm execution");
            }
            GpsFix pi = null, pj;
            double distance = 0.0;
            long timespan = 0L;
            ArrayList<GpsFix> tmpList = new ArrayList<>();
            boolean listHasBeenDepurated = false, weAreDone = false;
            long startTimeAlgorithm = System.currentTimeMillis();
            while (true) {
                // Read point Pi
                if (!listHasBeenDepurated) {
                    if (!rs.next()) {
                        // There are no more Gps Fixes
                        // sout("No Gps Fixes left. Outter loop");
                        break;
                    }
                    // Fix this when needed
                    pi = new GpsFix(rs.getDouble("latitud"), rs.getDouble("longitud"), 0, rs.getFloat("precision"), 0, rs.getTimestamp("timestamp"));
                    tmpList.add(pi);
                }
                while (true) {
                    // Leer el registro Pj
                    if (!rs.next()) {
                        // Se acabaron los registros
                        if (verbose) System.out.println("No more records in the inner loop");
                        weAreDone = true;
                        break;
                    }
                    pj = new GpsFix(rs.getDouble("latitud"), rs.getDouble("longitud"), 0, rs.getFloat("precision"), 0, rs.getTimestamp("timestamp"));
                    tmpList.add(pj);

                    // Obtener la distancia entre ambos
                    distance = pi.distanceTo(pj);

                    if (distance > distanceTreshold) {
                        timespan = pi.timeDifference(pj);
                        if (timespan > minTimeTreshold) {
                            StayPoint sp = StayPoint.createStayPoint(tmpList);

                            result.add(sp);
                            if (verbose) {
                                System.out.println("New SP generated Fixes involved:");
                                for (GpsFix gpsFix : tmpList) {
                                    System.out.println(gpsFix);
                                }
                            }
                        }
                        pi = pj;
                        depurateList(tmpList);
                        listHasBeenDepurated = true;
                    }
                }
                if (weAreDone)
                    break;
            }
            if (tmpList.size() != 0) {
                if (verbose) {
                    System.out.println("There still are fixes to process...");
                    for (GpsFix gpsFix : tmpList) {
                        System.out.println("LEFT: " + gpsFix);
                    }

                    System.out.println(".........................");
                }
                StayPoint sp = StayPoint.createStayPoint(tmpList);
                result.add(sp);
            }
            long endTimeAlgorithm = System.currentTimeMillis();
            if (verbose) {
                System.out.println("Algorithm execution time: " + (endTimeAlgorithm - startTimeAlgorithm) + " ms");

                System.out.println("Total time execution time is " +
                        ((endTimeQuery - startTimeQuery) + (endTimeAlgorithm - startTimeAlgorithm))
                        + " ms");

            }
        }catch(SQLException sqlExc){

        }
        return result;
    }

    public void depurateList(ArrayList<GpsFix> list){
        // Esto es una adaptación del método inferior.
        // Al parecer, solamente estaba eliminando size-1 elementos
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            list.remove(list.size() - 1);
        }
    }

    /***
     * Depurates a list by erasing all the elements before a specific one
     *
     * @param list
     *            List of GPS fixes
     * @param pivot
     *            The element which will be the reference. All the fixes before
     *            this one will de deleted.
     */
    public void depurateList(java.util.LinkedList<GpsFix> list, GpsFix pivot) {
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            list.remove();
        }
    }
}
