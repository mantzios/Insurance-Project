package mainpackage;

import DataController.DataInterface;
import DataController.DbConnector;
import DataController.FileOperations;
import service.InsuranceServices;
import utils.Utilities;

public class Main {

    private static final String ERROR_IMPORT="Invalid input. Please provide a valid number between 1.DataBase or 2.File";
    private static final String ERROR_MENU="Invalid input. Please provide a valid number between 1 to 4";
    private static final String ERROR_SUB_MENU="Invalid input. Please provide a valid number between 1.Console or 2.File";
    private static final String ERROR_AFM="Invalid input. Please provide a valid 9-digit number";
    private static final String ERROR_DAYS="Invalid input. Please provide a valid number of days";
    private static final String ERROR_PLATE="Invalid input. Please provide a valid plate number (ABC-1234)";
    private static final String ERROR_FEE="Invalid input. Please provide a valid fee";

    private static final String ONE_OR_TWO="[1,2]";
    private static final String PATTERN_MENU="[1,2,3,4]";
    private static final String PATTERN_PLATE="([A-Z])([A-Z])([A-Z])-\\d\\d\\d\\d";
    private static final String PATTERN_AFM="\\d\\d\\d\\d\\d\\d\\d\\d\\d";
    private static final String PATTERN_DAYS="\\d+";
    private static final String PATTERN_FEE="\\d+.*| \\.\\d+";

    private static DataInterface object;


    public static void main(String[] args) {
        //DataInterface fileop=new FileOperations("C:\\Users\\Angeliki\\Desktop\\db.csv");
       // DataInterface con =new DbConnector();
        // InsuranceServices.checkPlates(fileop,"Aaa-5378");
        // InsuranceServices.calculateFee(con,729166206,20); //den xrwstaei kai exei dio amaksia
        // InsuranceServices.calculateFee(con,233493998,20);// exei dio amaksia ligmena
        //InsuranceServices.calculateFee(con,111493998,20);// den exei amaksia
        // Utilities.validate(PATTERN_PLATE, ERROR_PLATE);
        // valid plate today input LAV-9333
        // invalid NUE-8822
        object=printFirstMenu();
        printMenu();
    }

    private static void printMenu(){
        System.out.println("---Select Functionality to perform :" );
        System.out.println("1. Vehicle Insurance status");
        System.out.println("2. Forecoming Expiries");
        System.out.println("3. Expiries by plate");
        System.out.println("4. Calculate fee");
        String input= Utilities.validate(PATTERN_MENU,ERROR_MENU);
        int inputnum=Integer.parseInt(input);
        switch (inputnum){
            case 1:
                System.out.println("Please enter a plate (ABC-1234)");
                String plate = Utilities.validate(PATTERN_PLATE,ERROR_PLATE);
                InsuranceServices.checkPlates(object,plate);
                break;
            case 2:
                System.out.println("Please give the number of days");
                String days = Utilities.validate(PATTERN_DAYS,ERROR_DAYS);
                int inputdays=Integer.parseInt(days);
                int out=output();
                InsuranceServices.checkExpVehicles(object,inputdays,out);

                break;

            case 3:
                int flag =output();
                InsuranceServices.sortVehicles(object,flag);
                break;

            case 4:
                System.out.println("Please enter the AFM you want to search");
                String afm = Utilities.validate(PATTERN_AFM,ERROR_AFM);
                System.out.println("Please enter the fee");
                String fee = Utilities.validate(PATTERN_FEE,ERROR_FEE);
                int inputafm=Integer.parseInt(afm);
                while (fee.contains(",")){
                    System.out.println("Error input..Please give the integer to this format 20.5");

                    fee=Utilities.validate(PATTERN_FEE,ERROR_FEE);
                }
                InsuranceServices.calculateFee(object,inputafm,Double.parseDouble(fee));
                break;
        }
    }

    private static int output(){
        System.out.println("Choose your output option.");
        System.out.println("1. Console");
        System.out.println("2. File");
        int choice=Integer.parseInt(Utilities.validate(ONE_OR_TWO,ERROR_SUB_MENU));
        return choice;
    }

     private static DataInterface printFirstMenu() {

        System.out.println("---Select type for import :");
        System.out.println("1. Use database");
        System.out.println("2. Use file");
        String input = Utilities.validate(ONE_OR_TWO, ERROR_IMPORT);
        int inputnum = Integer.parseInt(input);
        switch (inputnum) {
            case 1:
                return new DbConnector();
            case 2:
                return new FileOperations("db.csv");
        }
        return null;
    }

}
