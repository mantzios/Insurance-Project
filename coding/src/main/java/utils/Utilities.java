package utils;

import model.Vehicle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utilities {

    public static Date addDays(int days) {
        Date today = new Date();

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Date subtractDay(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    public static String validate(String pattern, String error) {
        int count = 0;
        String input;
        do {
            if (count != 0) {
                System.out.println(error);
            }
            input = readFromPromt();
            count++;
        } while (!input.matches(pattern));
        return input;
    }

    public static String readFromPromt() {
        System.out.println("Please submit an input");
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        return input;
    }

    public static void writeToFile(ArrayList<Vehicle> vehicles){
        File file=new File("output.csv");
        FileWriter fileWriter=null;
        BufferedWriter bf=null;
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/YY");
        try {
            fileWriter=new FileWriter(file);
            bf=new BufferedWriter(fileWriter);
            for(Vehicle vehicle : vehicles){
                bf.write(vehicle.getPlate());
                bf.write(';');
                bf.write(dateFormat.format(vehicle.getExpDate()));
                bf.write('\n');
            }
            bf.flush();
            System.out.println("The file has been succesfully written");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fileWriter.close();
            } catch (Exception e) {
            }
            try {
                bf.close();
            } catch (Exception e) {
            }
        }
    }

    public static void printConsole(ArrayList<Vehicle> vehicles){
        for (Vehicle vehicle : vehicles){
            System.out.println(vehicle);
        }
    }


}
