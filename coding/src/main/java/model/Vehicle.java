package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Vehicle implements Comparable<Vehicle> {
    private String plate;
    private Date expDate;

    public Vehicle(){}

    public Vehicle(String plate, Date expDate) {
        this.plate = plate;
        this.expDate = expDate;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @Override
    public int compareTo(Vehicle o) {
        //return this.plate.compareTo(o.plate);
        int result=0;
        for(int i=0; i<o.plate.length(); i++){
            if (this.plate.charAt(i)>o.plate.charAt(i)){
                result=1;
                break;
            }else if (this.plate.charAt(i)<o.plate.charAt(i)){
                result=-1;
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/YY");
        return "Vehicle Plate: "+ this.plate + " Expiration Date: " + dateFormat.format(this.expDate);
    }
}
