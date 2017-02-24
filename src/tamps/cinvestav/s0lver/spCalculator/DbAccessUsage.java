package tamps.cinvestav.s0lver.spCalculator;

import tamps.cinvestav.s0lver.databaseaccess.conversors.fromdbtocsv.CsvFromDbCreator;
import tamps.cinvestav.s0lver.databaseaccess.dal.ActivitiesDal;
import tamps.cinvestav.s0lver.databaseaccess.dal.DatabaseConnectionManager;
import tamps.cinvestav.s0lver.databaseaccess.dto.DtoActivity;

import java.io.IOException;
import java.util.List;

public class DbAccessUsage {
    private String databaseInputFilePath;
    private String csvOutputFilePath;

    public DbAccessUsage(String databaseInputFilePath, String csvOutputFilePath) {
        this.databaseInputFilePath = databaseInputFilePath;
        this.csvOutputFilePath = csvOutputFilePath;
    }

    public void translateActivitiesInDbToCsvLocationsFile() throws IOException {
        DatabaseConnectionManager connectionManager = new DatabaseConnectionManager(databaseInputFilePath);

        ActivitiesDal activitiesDal = new ActivitiesDal(connectionManager.getConnection());
        List<DtoActivity> activities = activitiesDal.getAll();
        CsvFromDbCreator creator = new CsvFromDbCreator(csvOutputFilePath, activities);
        creator.writeFile();
    }
}
