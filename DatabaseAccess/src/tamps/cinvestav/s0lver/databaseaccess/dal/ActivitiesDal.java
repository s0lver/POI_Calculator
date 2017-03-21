package tamps.cinvestav.s0lver.databaseaccess.dal;

import tamps.cinvestav.s0lver.databaseaccess.QueryConstants;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivitiesDal {
    private Connection connection;
    private final static String TABLE_NAME = "activities";

    public ActivitiesDal(Connection connection) {
        this.connection = connection;
    }

    public List<DtoActivity> getAll() {
        List<DtoActivity> activities = new ArrayList<>();
        try {
            connection.setAutoCommit(false);

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM %s;", TABLE_NAME));
            while (rs.next()) {
                int id = rs.getInt("_id");
                int idDetectedActivity = rs.getInt("detectedActivity");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");
                double altitude = rs.getDouble("altitude");
                double accuracy = rs.getDouble("accuracy");
                double speed = rs.getDouble("speed");
                String strTimestamp = rs.getString("timestamp");
                double batteryLevel = rs.getDouble("batteryLevel");
                int chargingType = rs.getInt("chargingType");

                Date timestamp = QueryConstants.SIMPLE_DATE_FORMAT_FOR_SQLITE.parse(strTimestamp);
//                Date timestamp = QueryConstants.SIMPLE_DATE_FORMAT_HUMAN.parse(strTimestamp);
                DtoActivity dtoActivity = new DtoActivity(id, idDetectedActivity, latitude, longitude, altitude, accuracy, speed, timestamp.getTime(), batteryLevel, chargingType);
                activities.add(dtoActivity);

            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(String.format("Could not read table %s contents", TABLE_NAME));
        }
        return activities;
    }
}
