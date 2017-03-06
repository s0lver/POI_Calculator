package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.databaseaccess.dal.ActivitiesDal;
import tamps.cinvestav.s0lver.databaseaccess.dal.DatabaseConnectionManager;
import tamps.cinvestav.s0lver.databaseaccess.dal.StayPointsDal;
import tamps.cinvestav.s0lver.databaseaccess.dal.VisitsDal;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoActivity;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoStayPoint;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoVisit;
import tamps.cinvestav.s0lver.trajectoryanalyzer.TrajectoryAnalyzer;

import java.util.List;

public class TrajectoryAnalyzerUsage {
    private DatabaseConnectionManager connectionManager;

    public TrajectoryAnalyzerUsage(String databaseInputFilePath) {
        this.connectionManager = new DatabaseConnectionManager(databaseInputFilePath);
    }

    public void doWork() {
        List<DtoVisit> visits = new VisitsDal(connectionManager.getConnection()).getAll();
        List<DtoStayPoint> stayPoints = new StayPointsDal(connectionManager.getConnection()).getAll();
        List<DtoActivity> activities = new ActivitiesDal(connectionManager.getConnection()).getAll();

        TrajectoryAnalyzer trajectoryAnalyzer = new TrajectoryAnalyzer(activities, stayPoints, visits);
        trajectoryAnalyzer.analyzeMobilityDatabase();
    }
}
