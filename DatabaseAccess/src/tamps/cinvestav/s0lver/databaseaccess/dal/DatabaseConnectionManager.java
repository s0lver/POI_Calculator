package tamps.cinvestav.s0lver.databaseaccess.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/***
 * Manages the connection with database. A helper already exists in Android project, so this will be discarded
 */
public class DatabaseConnectionManager {
    private String pathToDatabase;

    private Connection connection = null;
    public DatabaseConnectionManager(String pathToDatabase) {
        this.pathToDatabase = pathToDatabase;
    }

    public Connection getConnection(){
        if (connection == null) {
            try {
                establishConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not create connection to database");
            }
        }

        return connection;
    }

    private void establishConnection() throws SQLException {
        DriverManager.registerDriver(new org.sqlite.JDBC());
        connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", pathToDatabase));
        connection.setAutoCommit(false);
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Could not close the connection to database");
            }
        }
    }

    public String getPathToDatabase() {
        return pathToDatabase;
    }
}
