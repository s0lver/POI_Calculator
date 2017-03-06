package tamps.cinvestav.s0lver.trajectoryanalyzer;

import tamps.cinvestav.s0lver.databaseaccess.dto.DtoActivity;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoStayPoint;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoVisit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrajectoryAnalyzer {
//    private DatabaseConnectionManager connectionManager;
    private List<DtoActivity> activities;
    private List<DtoStayPoint> stayPoints;
    private List<DtoVisit> visits;
    private int amountOfFixes;
    private int amountOfFixesInStayPoint;
    private int amountOfFixesInTrajectory;
    private int amountOfStayPoints;
    private long overallTime;
    private long timeInStayPoints;
    private long timeInTrajectory;
    private int totalCommuting;
    private List<StayPointWeight> stayPointWeights;

    public TrajectoryAnalyzer(List<DtoActivity> activities, List<DtoStayPoint> stayPoints, List<DtoVisit> visits) {
        this.activities = activities;
        this.stayPoints = stayPoints;
        this.visits = visits;
//        loadActivities();
//        loadStayPoints();
//        loadVisits();
    }

    public void analyzeMobilityDatabase() {
        calculateAmountOfFixes();
        calculateFixesInStayPoint();
        calculateFixesInTrajectory();
        calculateAmountOfStayPoints();
        calculateOverallTime();
        calculateTimeInStayPoints();
        calculateTimeInTrajectory();
        calculateTotalCommuting();
        printTimeBalance();
        updateWeights();
        printVisitsInformation();
    }

    private void printVisitsInformation() {
        stayPoints.forEach(stayPoint -> {
            long stayTime = calculateTimeInStayPoint(stayPoint);
            System.out.println(String.format("Visits to stay point %s, total stay time is %s minutes", stayPoint.toString(), (stayTime / 1000) / 60));
            Stream<DtoVisit> visitStream = visits.stream().filter(visit -> visit.getIdStayPoint() == stayPoint.getId());
            visitStream.forEach(visit -> System.out.println(String.format("Visit: %s", visit.toString())));
        });
    }

    private long calculateTimeInStayPoint(DtoStayPoint stayPoint) {
        Stream<DtoVisit> visitStream = visits.stream().filter(visit -> visit.getIdStayPoint() == stayPoint.getId());
        return visitStream.mapToLong(f -> (f.getDepartureTime() - f.getArrivalTime())).sum();
    }

    private void printTimeBalance() {
        double stayPointTimePercentage = timeInStayPoints * 100.0 / overallTime;
        double trajectoryTimePercentage = timeInTrajectory * 100.0 / overallTime;
        System.out.println(String.format("Stay point time %% is %s, Trajectory time %% is %s", stayPointTimePercentage, trajectoryTimePercentage));
    }

    private void calculateTotalCommuting() {
        totalCommuting = visits.size() - 1;
        System.out.println(String.format("Total commuting interventions are %s", totalCommuting));
    }

    private void calculateTimeInTrajectory() {
        timeInTrajectory = overallTime - timeInStayPoints;
        printTime("Time in trajectory", timeInTrajectory);
    }

    private void calculateTimeInStayPoints() {
        stayPointWeights = obtainInitialWeights();
        timeInStayPoints = stayPointWeights.stream().mapToLong(w -> w.getTime()).sum();
        printTime("Time inside stay points", timeInStayPoints);
    }

    private void calculateOverallTime() {
        overallTime = activities.get(activities.size() - 1).getTimestamp() - activities.get(0).getTimestamp();
        printTime("Total running time", overallTime);
    }

    private void calculateAmountOfStayPoints() {
        amountOfStayPoints = stayPoints.size();
        System.out.println(String.format("Total stay points are %s", amountOfStayPoints));
    }

    private void calculateFixesInTrajectory() {
        amountOfFixesInTrajectory = amountOfFixes - amountOfFixesInStayPoint;
        System.out.println(String.format("Fixes in trajectory are %s, %s%%", amountOfFixesInTrajectory, (amountOfFixesInTrajectory * 100.0 / amountOfFixes)));
    }

    private void calculateFixesInStayPoint() {
        amountOfFixesInStayPoint = 0;
        for (DtoVisit visit : visits) {
            long arrivalTime = visit.getArrivalTime();
            long departureTime = visit.getDepartureTime();

            List<DtoActivity> filteredFixes = activities.parallelStream().filter(x -> x.getTimestamp() >= arrivalTime && x.getTimestamp() <= departureTime).collect(Collectors.toList());
            amountOfFixesInStayPoint += filteredFixes.size();
        }

        System.out.println(String.format("Fixes inside stay points are %s, %s%%", amountOfFixesInStayPoint, (amountOfFixesInStayPoint * 100.0 / amountOfFixes)));
    }

    private void calculateAmountOfFixes() {
//        List<DtoActivity> filteredActivities = activities.parallelStream().filter(x -> x.getLatitude() == 0 && x.getLongitude() == 0).collect(Collectors.toList());
//        System.out.println(String.format("Filtered activities are %s", filteredActivities.size()));

        amountOfFixes = activities.size();
        System.out.println(String.format("Total fixes are %s", amountOfFixes));
    }

//    private void loadVisits() {
//        long startTime;
//        long endTime;
//        VisitsDal visitsDal = new VisitsDal(connectionManager.getConnection());
//        startTime = System.currentTimeMillis();
//        visits = visitsDal.getAll();
//        endTime = System.currentTimeMillis();
//        System.out.println(String.format("%s visits were read from database file %s in %s milliseconds", visits.size(), connectionManager.getPathToDatabase(), (endTime - startTime)));
//    }

//    private void loadStayPoints() {
//        long startTime;
//        long endTime;
//        StayPointsDal stayPointsDal = new StayPointsDal(connectionManager.getConnection());
//        startTime = System.currentTimeMillis();
//        stayPoints = stayPointsDal.getAll();
//        endTime = System.currentTimeMillis();
//        System.out.println(String.format("%s stay points were read from database file %s in %s milliseconds", stayPoints.size(), connectionManager.getPathToDatabase(), (endTime - startTime)));
//    }

//    private void loadActivities() {
//        ActivitiesDal activitiesDal = new ActivitiesDal(connectionManager.getConnection());
//        long startTime = System.currentTimeMillis();
//        activities = activitiesDal.getAll();
//        long endTime = System.currentTimeMillis();
//        System.out.println(String.format("%s activities were read from database file %s in %s milliseconds", activities.size(), connectionManager.getPathToDatabase(), (endTime - startTime)));
//    }

    private void printTime(String label, long millisecondsTime) {
        long totalTimeSeconds = millisecondsTime / 1000;
        double totalTimeMinutes = totalTimeSeconds / 60.0;
        double totalTimeHours = totalTimeMinutes / 60.0;
        double totalTimeDays = totalTimeHours / 24.0;

        System.out.println(String.format("%s is %s milliseconds, %s seconds, %s minutes, %s hours, %s days", label, millisecondsTime, totalTimeSeconds, totalTimeMinutes, totalTimeHours, totalTimeDays));
    }

    private void updateWeights() {
        stayPointWeights.forEach(p -> {
            p.updateOverallPercentage(overallTime);
            p.updateRelativePercentage(timeInStayPoints);
        });

        for (StayPointWeight weight : stayPointWeights) {
            System.out.println(String.format("SP: #%s ABS weight is %s, REL weight is %s", weight.getIdStayPoint(), weight.getOverallPercentage(), weight.getRelativePercentage()));
        }
    }

    private List<StayPointWeight> obtainInitialWeights() {
        List<StayPointWeight> stayPointWeights = new ArrayList<>();
        for (DtoVisit visit : visits) {
            long arrivalTime = visit.getArrivalTime();
            long departureTime = visit.getDepartureTime();
            long stayTime = departureTime - arrivalTime;

            StayPointWeight currentWeight = new StayPointWeight(visit.getIdStayPoint(), stayTime);

            List<StayPointWeight> matchingWeights = stayPointWeights.stream().filter(x -> x.getIdStayPoint() == visit.getIdStayPoint()).collect(Collectors.toList());

            if (matchingWeights.size() > 0) {
                StayPointWeight storedWeight = matchingWeights.get(0);
                storedWeight.addStayTime(currentWeight.getTime());
            }
            else{
                stayPointWeights.add(currentWeight);
            }
        }

        return stayPointWeights;
    }

}