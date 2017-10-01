package DataController;

import model.Driver;
import model.Vehicle;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class implements the core functionality of a connection to a database
 * and implements the DataInterface
 * @see DataController.DataInterface
 */
public class DbConnector implements DataInterface {
    private final static String myUrl="jdbc:mysql://localhost:3306/projecteam9?autoReconnect=true&useSSL=false";
    private final static String user="root";
    private final static String password="91217065";


    /**
     * This method connects to the database and returns a Connection object
     * @return A Connection object
     */
    private static Connection Connect(){
        Connection connection=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(myUrl,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * This method implements the getStatus of the DataInterface method
     * @param plate The plate of the vehicle
     * @return
     */
    public int getStatus(String plate) {
        int found=0;
        LocalDate expdate=null, today=null;
        Connection connection=Connect();
        ResultSet rs=null;
        PreparedStatement statement=null;
        try {
            statement = connection.prepareStatement("select * from vehicle where plate='"+plate+"'");
            rs=statement.executeQuery();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            today = LocalDate.now();
            if(rs.next()) {
                found=1;
                String expiration = rs.getString("expiration");
                expdate = LocalDate.parse(expiration, dtf);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }
        if(found==1) {
            return expdate.compareTo(today);
        }
        else {
            return -3;
        }
    }


    /**
     * This method implements the method from interface DataInterface
     * @param date The upper limit of the time frame
     * @return An ArrayList of vehicles objects
     */
    public  ArrayList<Vehicle> getExpVehicle(Date date) {
        ArrayList<Vehicle> vehicles=new ArrayList<Vehicle>();
        Connection connection=Connect();
        ResultSet resultSet=null;
        PreparedStatement statement=null;
        try {
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String newdate=dateFormat.format(date);
            statement= connection.prepareStatement("SELECT plate,expiration From vehicle where vehicle.expiration < '"+newdate+"' and vehicle.expiration >= CURDATE() ;");
            resultSet=statement.executeQuery();
            while (resultSet.next()){
                vehicles.add(new Vehicle(resultSet.getString(1),resultSet.getDate(2)));
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try { resultSet.close(); } catch (Exception e) { /* ignored */ }
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }
        return vehicles;
    }

    /**
     * This method implements the method from DataInterface
     * @return An ArrayList of all the vehicles
     */
    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> vehicles=new ArrayList<Vehicle>();
        Connection connection=Connect();
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            statement = connection.prepareStatement("select plate,expiration,driver.last_name from vehicle,driver where driver.afm=vehicle.owner_id");
            resultSet=statement.executeQuery();
            while (resultSet.next()){
              vehicles.add(new Vehicle(resultSet.getString(1),resultSet.getDate(2)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { resultSet.close(); } catch (Exception e) { /* ignored */ }
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }
        return vehicles;
    }

    /**
     * Implementation of the method in the interface
     * @param afm The unique identifier which represents exactly one person
     * @return A driver object who has the specific AFM
     */
    public Driver getDriver(int afm) {
        Driver driver =new Driver();
        PreparedStatement statement=null;
        ResultSet rs=null;
        Connection connection=Connect();
        try {
            statement = connection.prepareStatement("select * from vehicle where owner_id='"+afm+"'");
            rs=statement.executeQuery();
            while(rs.next()) {
                Vehicle vehicle= new Vehicle( rs.getString("plate"),rs.getDate("expiration"));
                driver.getVehicles().add(vehicle);
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }
        return driver;

    }





}
