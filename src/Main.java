import Database.*;
import Users.Employer;
import Users.Worker;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        mainMenu(input);
        input.close();
    }

    private static void mainMenu(Scanner input){
        char choice;

        while(true){
            System.out.println();
            System.out.println("=================================");
            System.out.println("      Welcome to InsideJob!     ");
            System.out.println("=================================");
            System.out.println("1. Log in to your account");
            System.out.println("2. Create a new account");
            System.out.println("3. Exit the application");
            System.out.println();
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
            System.out.println("====================================");
            System.out.println("    Registration - Choose Role     ");
            System.out.println("====================================");
            System.out.println("Are you a Worker or an Employer?");
            System.out.println("1. Worker");
            System.out.println("2. Employer");
            System.out.println();
            System.out.print("Please input your choice (0 to return): ");


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
            System.out.println("====================================");
            System.out.println("       Log In - Choose Role         ");
            System.out.println("====================================");
            System.out.println("Are you a Worker or an Employer?");
            System.out.println("1. Worker");
            System.out.println("2. Employer");
            System.out.println();
            System.out.print("Please input your choice (0 to return): ");

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
            System.out.println("=================================");
            System.out.println("      Worker Login Process       ");
            System.out.println("=================================");

            // Prompt for name input
            System.out.print("Input your name (leave blank to return): ");
            name = input.nextLine();

            if (name.isBlank()) {
                System.out.println("Returning to the previous menu...\n");
                return;
            }

            // Prompt for password input
            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if (password.isBlank()) {
                System.out.println("Returning to the previous menu...\n");
                return;
            }

            // connect to database
            WorkerDAO database = new WorkerDAO();

            boolean valid = database.searchWorker(name, password);

                // if user is found!
                if (valid) {
                    System.out.println("Logged in!");
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
            System.out.println("=================================");
            System.out.println("     Employer Login Process      ");
            System.out.println("=================================");

            // Prompt for name input
            System.out.print("Input your name (leave blank to return): ");
            name = input.nextLine();

            if (name.isBlank()) {
                System.out.println("Returning to the previous menu...\n");
                return;
            }

            // Prompt for password input
            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if (password.isBlank()) {
                System.out.println("Returning to the previous menu...\n");
                return;
            }

            // connect to database
            EmployerDAO database = new EmployerDAO();
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
            System.out.println("=================================");
            System.out.println("   Worker Registration Process   ");
            System.out.println("=================================");

            // Prompt for name input
            System.out.print("Input your name (leave blank to return): ");
            name = input.nextLine();

            if (name.isBlank()) {
                System.out.println("Returning to the previous menu...\n");
                return;
            }

            // Prompt for password input
            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if (password.isBlank()) {
                System.out.println("Returning to the previous menu...\n");
                return;
            }

            WorkerDAO database = new WorkerDAO();
            boolean found = database.searchWorker(name);

            if (!found) {
                database.addWorker(name, password);
                System.out.println("\nRegistration successful! Welcome aboard, " + name + "!\n");
                break;
            }

            else {
                System.out.println("\nName already exists. Please try a different name.");
            }
        }
    }

    private static void employerRegister(Scanner input){
        // initialize variables
        String name, password;

        // collect user input
        while (true) {
            System.out.print("Input your name (leave blank to return): ");
            name = input.nextLine();

            if(name.isBlank()){
                return;
            }

            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if(password.isBlank()){
                return;
            }

            // connect to database
            EmployerDAO database = new EmployerDAO();
            boolean exists = database.searchEmployer(name);

            // if user is valid (doesn't exist yet)!
            if (!exists) {

                // add to DB
                database.addEmployer(name, password);
                System.out.println("\nRegistration successful! Welcome aboard, " + name + "!\n");
                break;
            }

            // else, error
            else {
                System.out.println("\nName already exists. Please try a different name.");
            }
        }
    }
}