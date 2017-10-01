package service;

import DataController.DataInterface;
import model.Driver;
import model.Vehicle;
import utils.Utilities;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class InsuranceServices {


    /**
     * This method calls the getStatus and prints the status of the given
     * vehicle (expired, valid, and not found)
     * @param dataInterface This object is the interface declaration and we call the getStatus method
     * @param plate The plate number of the given vehicle
     */
    public static void checkPlates(DataInterface dataInterface, String plate){
        int check;
        check=dataInterface.getStatus(plate);

        if(check>= 0){
            System.out.println("Valid");
        }
        else if(check<= 0 && check!=-3 ){
            System.out.println("Expired");
        }
        else{
            System.out.println("Not found ");

        }
    }


    /**
     * This method calls getExpVehicle method and prints the plate numbers tha are going to expire
     * at given num of days
     * @param dataInterface This object is the interface declaration to call getExpVehicle method
     * @param days The number of days given by the user
     * @param flag Integer 1 or 2 to print data in console or in file, respectively
     */
    public static void checkExpVehicles(DataInterface dataInterface, int days,int flag){
        ArrayList<Vehicle> vh = dataInterface.getExpVehicle(Utilities.addDays(days));
        if (flag==1){
            Utilities.printConsole(vh);
        }else{
            Utilities.writeToFile(vh);
        }
    }

    /**
     * This method calls getAllVehicles method and prints the plate numbers sorted
     * @param dataInterface This object is the interface declaration to call getAllVehicles method
     * @param flag Integer 1 or 2 to print data in console or in file, respectively
     */
    public static void sortVehicles(DataInterface dataInterface,int flag){
        ArrayList<Vehicle> vehicles;
        vehicles=dataInterface.getAllVehicles();
        //vehicles.sort(Vehicle::compareTo);
        for(int j=0;j<vehicles.size();j++) {
            for (int i=1; (i < vehicles.size()- j); i++) {
                if (vehicles.get(i-1).compareTo(vehicles.get(i)) > 0) {
                    Vehicle temp = new Vehicle();
                    temp = vehicles.get(i-1);
                    vehicles.set(i-1,vehicles.get(i));
                    vehicles.set(i,temp);

                }
            }
        }
        if (flag==1) {
            Utilities.printConsole(vehicles);
        }else{
            Utilities.writeToFile(vehicles);
        }

    }


    /**
     * This method calls getDriver method which returns a driver type object with the given afm.
     * The object has an arraylist of driver's vehicles to find the number of expired insurances.
     * @param dataInterface This object is the interface declaration to call getAllVehicles method
     * @param afm Afm number given by the user
     * @param fee amount of fee given by the user
     */
    public static void calculateFee(DataInterface dataInterface, int afm, double fee){
            int count=0;
            Driver driver = dataInterface.getDriver(afm);
            LocalDate today = LocalDate.now();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (driver==null){
                System.out.println("AFM number was not found");
                return;
            }
            if(driver.getVehicles().size()==0){
                System.out.println( "No relative vehicles for this AFM");
            }
            else{
                for(int i=0;i<driver.getVehicles().size();i++){
                    String expiration = df.format( driver.getVehicles().get(i).getExpDate());
                    LocalDate  expdate = LocalDate.parse(expiration, dtf);
                    if(expdate.compareTo(today)<=0){
                        count++;
                    }
                }
                if(count >0 ){
                    System.out.println("Num of relative vehicles: "+driver.getVehicles().size());
                    System.out.println("Total fee: "+count*fee);
                }
                else{
                    System.out.println("Num of relative vehicles: "+driver.getVehicles().size());
                    System.out.println("Total fee: 0");
                }
            }

    }



}
