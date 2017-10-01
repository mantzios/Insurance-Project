package DataController;

import model.Driver;
import model.Vehicle;

import java.util.ArrayList;
import java.util.Date;

/**
 * This interface has the methods which are necessary to get
 * the information from the data.
 */
public interface DataInterface {

    /**
     * The purpose of this method is to check if a vehicle with the
     * specific plate has insurance
     * @param plate The plate of the vehicle
     * @return Returns -1 if the vehicle has no insurance
     * 0 if the plate is not in the record
     * and 1 if the vehicle has insurance
     *
     */
    int getStatus(String plate);

    /**
     * The purpose of this method is to find the vehicles which are about to expire in the
     * time frame specified by the user.
     * @param date The upper limit of the time frame
     * @return Returns the vehicles in an ArrayList which satisfy the condition
     */
    ArrayList<Vehicle> getExpVehicle(Date date);

    /**
     * The purpose of this method is to return all the vehicles
     * @return An ArrayList which contains object type Vehicle
     */
    ArrayList<Vehicle> getAllVehicles();

    /**
     * The purpose of this method is to return the information of the driver
     * @param afm The unique identifier which represents exactly one person
     * @return Return an object Driver
     */
    Driver getDriver(int afm);
}
