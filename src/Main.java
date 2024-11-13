import java.util.Scanner;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        mainMenu(input);
    }


    private static void mainMenu(Scanner input){
        char choice;

        while(true){
            System.out.print("Welcome to InsideJob!\n");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
            System.out.print("Input your choice: ");

            // catch empty input errors!
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                System.out.println("Please provide a proper input.");
                continue;
            }

            switch(choice) {
                case '1':
                    loginChoice(input);
                    break;
                case '2':
                    registerChoice(input);
                    break;
                case '3':
                    return;
                default:
                    System.out.println("Please enter a valid input");
            }
        }
    }

    //are you a worker or an employer?
    private static void registerChoice(Scanner input){
        char choice;
        while (true) {
            System.out.println("Are you a worker or an employer?");
            System.out.println("1. Worker");
            System.out.println("2. Employer");

            System.out.print("Input your choice (0 to return): ");


            // catch empty input errors!
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                System.out.println("Please provide a valid input.");
                continue;
            }

            switch (choice) {
                case '0':
                    return;

                case '1':
                    workerRegister(input);
                    return;
                case '2':
                    employerRegister(input);
                    return;
                default:
                    System.out.println("Please provide a valid input.");
            }
        }
    }

    private static void loginChoice(Scanner input){

        char choice;

        while (true) {
            System.out.println("Are you a worker or an employer?");
            System.out.println("1. Worker");
            System.out.println("2. Employer");

            System.out.print("Input your choice (0 to return): ");

            // catch empty input errors!
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                System.out.println("Please provide a valid input.");
                continue;
            }

            switch (choice) {
                case '0':
                    return;
                case '1':
                    workerLogin(input);
                    return;
                case '2':
                    employerLogin(input);
                    return;
                default:
                    System.out.println("Please provide a valid input.");
            }
        }
    }

    private static void workerLogin(Scanner input){

        // initialize variables
        String name, password;

        // collect user input
        while (true) {
            System.out.print("Input your name (leave blank to return): ");
            name = input.nextLine();

            if(name.isEmpty()){
                return;
            }

            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if(password.isEmpty()){
                return;
            }

            // connect to database
            Database database = new Database();

            boolean valid = database.searchWorker(name, password);

                // if user is found!
                if (valid) {
                    System.out.println("Logged in!");
                    // add constructors here!

                    Worker user = new Worker(name);

                    user.login(input);

                    break;
                }

                // else break
                else {
                    System.out.println("No records found. name or password may be incorrect");
                }
        }

    }

    private static void employerLogin(Scanner input){

        // initialize variables
        String name, password;

        // collect user input
        while (true) {
            System.out.print("Input your name (leave blank to return): ");
            name = input.nextLine();

            if(name.isEmpty()){
                return;
            }

            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if(password.isEmpty()){
                return;
            }

            // connect to database
            Database database = new Database();
            boolean valid = database.searchEmployer(name, password);

            // if user is found!
            if (valid) {
                System.out.println("Logged in!");

                // add constructors here!
                Employer employer = new Employer(name);
                employer.login(input);

                break;
            }

            // else break
            else {
                System.out.println("No records found. name or password may be incorrect");
            }
        }

    }

    private static void workerRegister(Scanner input){
        // initialize variables
        String name, password;

        // collect user input
        while (true) {
            System.out.print("Input your name (leave blank to return): ");
            name = input.nextLine();

            if(name.isEmpty()){
                return;
            }

            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if(password.isEmpty()){
                return;
            }

            Database database = new Database();
            boolean found = database.searchWorker(name);

            if (!found) {
                database.addWorker(name, password);
                System.out.println("Added successfully!");
                break;
            }

            else {
                System.out.println("Name already exists.");
            }
        }
    }

    private static  void employerRegister(Scanner input){
        // initialize variables
        String name, password;

        // collect user input
        while (true) {
            System.out.print("Input your name (leave blank to return): ");
            name = input.nextLine();

            if(name.isEmpty()){
                return;
            }

            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if(password.isEmpty()){
                return;
            }

            // connect to database
            Database database = new Database();

            // check if the person's name exists in db
            boolean exists = database.searchEmployer(name);

            // if user is valid (doesn't exist yet)!
            if (!exists) {

                // add to DB
                database.addEmployer(name, password);
                System.out.println("Added successfully!");
                break;
            }

            // else, error
            else {
                System.out.println("Name already exists.");
            }
        }
    }

    /*
    * Row, row, row your boat
    * Gently down the stream,
    * Merrily merrily merrily merrily
    * Life is but a dream :)
    * */

}