package tamps.cinvestav.s0lver.databaseaccess.dto;

import tamps.cinvestav.s0lver.databaseaccess.QueryConstants;

import java.util.Date;

public class DtoVisit {
    private int id;
    private int idStayPoint;
    private long arrivalTime;
    private long departureTime;

    public DtoVisit(int id, int idStayPoint, long arrivalTime, long departureTime) {
        this.id = id;
        this.idStayPoint = idStayPoint;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public int getId() {
        return id;
    }

    public int getIdStayPoint() {
        return idStayPoint;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public long getDepartureTime() {
        return departureTime;
    }

    @Override
    public String toString() {
        return String.format("id: %s, idStayPoint: %s, arrival time: %s, departure time: %s, stay time: %s",
                id, idStayPoint, QueryConstants.SIMPLE_DATE_FORMAT_HUMAN.format(new Date(arrivalTime)), QueryConstants.SIMPLE_DATE_FORMAT_HUMAN.format(new Date(departureTime)),
                ((departureTime - arrivalTime) / 1000) / 60.0);
    }
}
