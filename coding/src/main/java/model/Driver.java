package model;

import java.util.ArrayList;

public class Driver {
    private String lastName;
    private String firstName;
    private int afm;
    private ArrayList<Vehicle> vehicles=new ArrayList<Vehicle>();


    public Driver(){}

    public Driver(String firstName,String lastName, int afm) {
        this.lastName = lastName;
        this.firstName=firstName;
        this.afm = afm;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAfm() {
        return afm;
    }

    public void setAfm(int afm) {
        this.afm = afm;
    }
}
