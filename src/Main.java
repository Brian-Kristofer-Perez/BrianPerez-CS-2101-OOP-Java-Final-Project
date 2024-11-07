import java.util.Scanner;
import java.sql.*;

public class Main {

    public static void main(String[] args) {

        mainMenu();
    }


    private static void mainMenu(){
        char choice;
        Scanner input = new Scanner(System.in);

        while(true){
            System.out.print("Welcome to InsideJob!\n");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");

            System.out.print("Input your choice: ");
            choice = input.nextLine().charAt(0);

            switch(choice) {
                case '1':
                    loginChoice();
                    break;
                case '2':
                    registerChoice();
                    break;
                case '3':
                    return;
                default:
                    clearScreen();
                    System.out.println("Please enter a valid input");
                    continue;
        }
        }
    }

    //are you a worker or an employer?
    private static void registerChoice(){
        char choice;
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Are you a worker or an employer?");
            System.out.println("1. Worker");
            System.out.println("2. Employer");

            System.out.print("Input your choice (0 to return): ");
            choice = input.nextLine().charAt(0);

            switch (choice) {
                case '0':
                    return;

                case '1':
                    workerRegister();
                    break;
                case '2':
                    // employerRegister()
                    break;
                default:
                    System.out.println("Please provide a valid input.");
                    continue;
            }
        }
    }

    private static void loginChoice(){
        Scanner input = new Scanner(System.in);
        char choice;

        while (true) {
            System.out.println("Are you a worker or an employer?");
            System.out.println("1. Worker");
            System.out.println("2. Employer");

            System.out.print("Input your choice (0 to return): ");
            choice = input.nextLine().charAt(0);

            switch (choice) {
                case '0':
                    return;
                case '1':
                    workerLogin();
                    break;
                case '2':
                    // employerLogin()
                default:
                    clearScreen();
                    System.out.println("Please provide a valid input.");
                    continue;
            }
        }
    }

    private static void workerLogin(){

        // initialize variables
        String email, password;
        Scanner input = new Scanner(System.in);

        // collect user input
        while (true) {
            System.out.print("Input your email: ");
            email = input.nextLine();
            System.out.print("Input your password: ");
            password = input.nextLine();

            // isValid(email, password);

            // connect to database
            try {
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");

                // check if the user exists
                PreparedStatement query = db.prepareStatement("SELECT * FROM WORKERS WHERE email = ? AND password = ?;");
                query.setString(1, email);
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
                    System.out.println("No records found. Email or password may be incorrect");
                    continue;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private static void employerLogin(){
        /*Input email
         * input password
         * if email and password IN database:
         *   login, construct object
         * else:
         *   error!
         * */
    }

    // simulate clearing a screen, since im not sure how java does it.
    private static void clearScreen(){
        for(int i = 0; i<50; i++){
            System.out.print('\n');
        }
    }


    private static void workerRegister(){
        // initialize variables
        String email, password;
        Scanner input = new Scanner(System.in);

        // collect user input
        while (true) {
            System.out.print("Input your email: ");
            email = input.nextLine();
            System.out.print("Input your password: ");
            password = input.nextLine();

            // connect to database
            try {
                Connection db = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");

                // check if the person's email exists in db
                PreparedStatement query = db.prepareStatement("SELECT * FROM WORKERS WHERE email = ?;");
                query.setString(1, email);
                ResultSet resultSet = query.executeQuery();

                // if user is valid (doesn't exist yet)!
                if (!resultSet.next()) {

                    // add to DB
                    PreparedStatement statement = db.prepareStatement("INSERT INTO workers (email, password) VALUES (?, ?);");
                    statement.setString(1, email);
                    statement.setString(2, password);
                    statement.executeUpdate();
                    System.out.println("Added successfully!");
                    break;
                }

                // else, error
                else {
                    System.out.println("Email already exists. Please log in.");
                    continue;
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