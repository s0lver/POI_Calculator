package tamps.cinvestav.s0lver.databaseaccess.dal;

import tamps.cinvestav.s0lver.databaseaccess.dto.DtoStayPoint;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StayPointsDal {
    private Connection connection;
    private final static String TABLE_NAME = "stayPoints";

    public StayPointsDal(Connection connection) {
        this.connection = connection;
    }

    public List<DtoStayPoint> getAll() {
        List<DtoStayPoint> stayPoints = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s;", TABLE_NAME));
            while (rs.next()) {
                int id = rs.getInt("_id");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                int visitCount = rs.getInt("visitCount");

                DtoStayPoint dtoStayPoint = new DtoStayPoint(id, latitude, longitude, visitCount);
                stayPoints.add(dtoStayPoint);

            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Could not read table %s contents", TABLE_NAME));
        }
        return stayPoints;
    }
}
