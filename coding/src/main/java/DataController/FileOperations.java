package DataController;

import model.Driver;
import model.Vehicle;
import utils.Utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible to implement the interface
 * and to override the methods to read from csv files
 * @see DataInterface
 */
public class FileOperations implements DataInterface {
    private ArrayList<Vehicle> vehicles;
    private Map<Integer,Driver> map=new HashMap<>();

    /**
     * The constructor of this class. When you make a new object
     * it reads the data from the file
     * @param file The path of the file where the csv file is located
     */
    public FileOperations(String file){
        vehicles=new ArrayList<>();
        vehicles=readFromFile(file);
    }

    /**
     * This method reads the data and stores them in an ArrayList of objects Vehicle
     * It also make a new Map which the key is the AFM of each owner and the value
     * an object of type Owner
     * @param file The path of the csv file
     * @return An ArrayList of objects Vehicle
     */
    private ArrayList<Vehicle> readFromFile(String file) {

        BufferedReader bf=null;
        FileReader fileReader=null;
        try {
            fileReader=new FileReader(file);
            bf=new BufferedReader(fileReader);
            String currentLine=bf.readLine();
            while(currentLine!=null){
                String tokens[]=currentLine.split(";");
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yy");
                Date date=dateFormat.parse(tokens[3]);
                Vehicle vehicle=new Vehicle(tokens[2],date);
                vehicles.add(vehicle);
                currentLine=bf.readLine();
                int afm=Integer.parseInt(tokens[4]);
                if (map.containsKey(afm)){          //checks if the owner is already in the collection
                    Driver driver=map.remove(afm);  //if it is we remove the driver and add the vehicle to the driver
                    driver.setVehicles(vehicle);    //
                    map.put(afm,driver);            // and we put the Driver in the collection with the new vehicle added to the list
                }else{
                    Driver driver= new Driver(tokens[0],tokens[1],afm);
                    driver.setVehicles(vehicle);    // if the driver is not in the collection we make a new Driver object
                    map.put(afm,driver);            // and we added the object in the collection
                }
            }
            bf.close();

        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } catch (IOException e) {
            System.out.println("Error parsing the file");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            try { fileReader.close(); }catch (IOException e){}
            try { bf.close(); } catch (IOException e){}

        }
        return vehicles;
    }

    /**
     * The implementation of the method in the interface
     * @see DataInterface
     * @param plate The plate of the vehicle
     * @return
     */
    @Override
    public int getStatus(String plate) {
        LocalDate expdate=null, today=null;
        int found=0;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        today = LocalDate.now();

        for(int i=0;i<vehicles.size();i++){
            if(vehicles.get(i).getPlate().equals(plate)){   //if the two plates matches then we check the date and we break the for-loop
                found=1;
                String expiration = df.format( vehicles.get(i).getExpDate());
                expdate = LocalDate.parse(expiration, dtf);
                break;
            }

        }
        if(found==1) {
            return expdate.compareTo(today);
        }
        else {
            return -3;
        }

    }

    /**
     * The implentation of the DataInterface
     * @see DataInterface
     * @param date The upper limit of the time frame
     * @return
     */
    @Override
    public ArrayList<Vehicle> getExpVehicle(Date date)  {
        int counter = vehicles.size();
        ArrayList<Vehicle>  list=new ArrayList<>();
        Date curdate = new Date();
        curdate = Utilities.subtractDay(curdate);
        for (int i=0; i<counter; i++) {
            boolean bool = vehicles.get(i).getExpDate().before(date);  // we store in the ArrayList the vehicles which are in
            if (bool && vehicles.get(i).getExpDate().after(curdate)) { // the time frame and we return the list
                list.add(vehicles.get(i));
            }
        }
        return list;
    }

    /**
     * Implementaion of the interface
     * @see DataInterface
     * @return ArrayList of vehicles
     */
    @Override
    public ArrayList<Vehicle> getAllVehicles() {
        return vehicles;
    } // returns the ArrayList

    /**
     * Implementation of the interface DataInterface
     * @see DataInterface
     * @param afm The unique identifier which represents exactly one person
     * @return
     *
     */
    @Override
    public Driver getDriver(int afm) {
        return map.get(afm);
    } // It returns the Driver from the map with key the specific AFM
}

