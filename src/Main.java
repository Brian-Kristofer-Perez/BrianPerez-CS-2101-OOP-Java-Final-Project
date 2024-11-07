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
                    break;
                case '2':
                    employerRegister(input);
                    break;
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
        String username, password;

        // collect user input
        while (true) {
            System.out.print("Input your username (leave blank to return): ");
            username = input.nextLine();

            if(username.isEmpty()){
                return;
            }

            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if(password.isEmpty()){
                return;
            }

            // connect to database
            try {
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");

                // check if the user exists
                PreparedStatement query = db.prepareStatement("SELECT * FROM WORKERS WHERE username = ? AND password = ?;");
                query.setString(1, username);
                query.setString(2, password);
                ResultSet resultSet = query.executeQuery();

                // if user is found!
                if (resultSet.next()) {
                    System.out.println("Logged in!");

                    // add constructors here!
                    break;
                }

                // else break
                else {
                    System.out.println("No records found. username or password may be incorrect");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static void employerLogin(Scanner input){

        // initialize variables
        String username, password;

        // collect user input
        while (true) {
            System.out.print("Input your username (leave blank to return): ");
            username = input.nextLine();

            if(username.isEmpty()){
                return;
            }

            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if(password.isEmpty()){
                return;
            }

            // connect to database
            try {
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");

                // check if the user exists
                PreparedStatement query = db.prepareStatement("SELECT * FROM employers WHERE username = ? AND password = ?;");
                query.setString(1, username);
                query.setString(2, password);
                ResultSet resultSet = query.executeQuery();

                // if user is found!
                if (resultSet.next()) {
                    System.out.println("Logged in!");

                    // add constructors here!
                    break;
                }

                // else break
                else {
                    System.out.println("No records found. username or password may be incorrect");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static void workerRegister(Scanner input){
        // initialize variables
        String username, password;

        // collect user input
        while (true) {
            System.out.print("Input your username (leave blank to return): ");
            username = input.nextLine();

            if(username.isEmpty()){
                return;
            }

            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if(password.isEmpty()){
                return;
            }

            // connect to database
            try {
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");

                // check if the person's username exists in db
                PreparedStatement query = db.prepareStatement("SELECT * FROM WORKERS WHERE username = ?;");
                query.setString(1, username);
                ResultSet resultSet = query.executeQuery();

                // if user is valid (doesn't exist yet)!
                if (!resultSet.next()) {

                    // add to DB
                    PreparedStatement statement = db.prepareStatement("INSERT INTO workers (username, password) VALUES (?, ?);");
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.executeUpdate();
                    System.out.println("Added successfully!");
                    break;
                }

                // else, error
                else {
                    System.out.println("Username already exists.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static  void employerRegister(Scanner input){
        // initialize variables
        String username, password;

        // collect user input
        while (true) {
            System.out.print("Input your username (leave blank to return): ");
            username = input.nextLine();

            if(username.isEmpty()){
                return;
            }

            System.out.print("Input your password (leave blank to return): ");
            password = input.nextLine();

            if(password.isEmpty()){
                return;
            }

            // connect to database
            try {
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");

                // check if the person's username exists in db
                PreparedStatement query = db.prepareStatement("SELECT * FROM employers WHERE username = ?;");
                query.setString(1, username);
                ResultSet resultSet = query.executeQuery();

                // if user is valid (doesn't exist yet)!
                if (!resultSet.next()) {

                    // add to DB
                    PreparedStatement statement = db.prepareStatement("INSERT INTO employers (username, password) VALUES (?, ?);");
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.executeUpdate();
                    System.out.println("Added successfully!");
                    break;
                }

                // else, error
                else {
                    System.out.println("Username already exists.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
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