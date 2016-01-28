package tamps.cinvestav.s0lver.spCalculator.dbtools;

import tamps.cinvestav.s0lver.locationentities.GpsFix;

import java.sql.*;

public class MySQLConnector {
    private Connection conexion = null;
    private String dbServerURL;
    private String username;
    private String password;

    public Connection getConexion() {
        return conexion;
    }

    /***
     * Parameterized constructor
     *
     * @param dbServerURL
     *            URL of the database server
     * @param username
     *            Credential to be used to establish the connection
     * @param password
     *            Credential password.
     */
    public MySQLConnector(String dbServerURL, String username, String password) {
        super();
        this.dbServerURL = dbServerURL;
        this.username = username;
        this.password = password;
    }

    /***
     * Opens the connection
     */
    public void prepare() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection(dbServerURL, username,
                    password);
            // System.out.println("Conexion establecida");
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /***
     * Closes the connection
     */
    public void finalise() {
        try {
            conexion.close();
            // System.out.println("Conexion cerrada");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * Add a register of type GpsFix to the database
     *
     * @param register The fix register to add
     * @throws java.sql.SQLException
     */
    public void add(GpsFix register) throws SQLException {
        PreparedStatement s;
        s = conexion
                .prepareStatement("INSERT INTO tbl_geo (id_usuario, latitud, longitud, timestamp) VALUES(?,?,?,?)");

        //s.setInt(1, register.getUserId()); <- re-adapt when needed!
        s.setDouble(2, register.getLatitude());
        s.setDouble(3, register.getLongitude());

        s.setTimestamp(4, new Timestamp(register.getTimestamp().getTime()));
        // int count = s.executeUpdate();
        s.close();

        // System.out.println(count + " rows were inserted");
    }

    public ResultSet doQuery(int userId, String tbl_name) throws SQLException {
        ResultSet rs = null;
            Statement s = conexion.createStatement();
            rs = s.executeQuery("SELECT * FROM " + tbl_name
                    + " WHERE id_usuario = " + userId);
            // System.out.println("Query executed succesfully");
        return rs;
    }

}
