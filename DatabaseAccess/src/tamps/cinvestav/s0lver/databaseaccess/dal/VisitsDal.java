package tamps.cinvestav.s0lver.databaseaccess.dal;

import tamps.cinvestav.s0lver.databaseaccess.QueryConstants;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoVisit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static tamps.cinvestav.s0lver.databaseaccess.QueryConstants.SIMPLE_DATE_FORMAT_FOR_SQLITE;

/***
 * Data Access Layer to the Visits information.
 * It dissects all of the time parameters for conducting the proper queries that represent moving a time window over the
 * learned information memory of the platform.
 */
public class VisitsDal {
    private Connection connection;
    private final static String TABLE_NAME = "visits";

    public VisitsDal(Connection connection) {
        this.connection = connection;
    }

    /***
     * Get all visits stored in database
     * @return The full list of visits
     */
    public List<DtoVisit> getAll() {
        try {
            String query = String.format(QueryConstants.QUERY_GET_ALL_VISITS, TABLE_NAME);
            return executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Could not read table %s contents", TABLE_NAME));
        }
    }

    private List<DtoVisit> executeQuery(String query) throws SQLException, ParseException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<DtoVisit> visits = buildResultsList(resultSet);
        resultSet.close();
        statement.close();
        return visits;
    }

    private List<DtoVisit> buildResultsList(ResultSet resultSet) throws SQLException, ParseException {
        List<DtoVisit> visits = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("_id");
            int idStayPoint = resultSet.getInt("idStayPoint");
            String strArrivalTime = resultSet.getString("arrivalTime");
            String strDepartureTime = resultSet.getString("departureTime");

            Date arrivalTime = SIMPLE_DATE_FORMAT_FOR_SQLITE.parse(strArrivalTime);
            Date departureTime = SIMPLE_DATE_FORMAT_FOR_SQLITE.parse(strDepartureTime);
            DtoVisit dtoVisit = new DtoVisit(id, idStayPoint, arrivalTime.getTime(), departureTime.getTime());
            visits.add(dtoVisit);
        }
        return visits;
    }
}
