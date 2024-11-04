import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Create a scanner, we'll use this scanner for all menus by passing it as argument!
        Scanner scanner = new Scanner(System.in);

        mainMenu(scanner);
    }


    private static void mainMenu(Scanner input){
        char choice;

        while(true){
            System.out.print("Welcome to InsideJob!\n");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");

            System.out.print("Input your choice: ");
            choice = input.nextLine().charAt(0);

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
                    clearScreen();
                    System.out.println("Please enter a valid input");
                    continue;
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

            System.out.print("Input your choice: ");
            choice = input.nextLine().charAt(0);

            switch (choice) {
                case '1':
                    // workerRegister()
                    break;
                case '2':
                    // employerRegister()
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
            choice = input.nextLine().charAt(0);

            switch (choice) {
                case '0':
                    clearScreen();
                    return;
                case '1':
                    // workerLogin()
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

        while(true){





        }

        /*Input email
        * input password
        * if email and password IN database:
        *   error
        * else:
        *   login, construct user object
        * */
    }

    private static void employerLogin(){
        /*Input email
         * input password
         * if email and password IN database:
         *   error
         * else:
         *   login, construct user object
         * */
    }

    // simulate clearing a screen, since im not sure how java does it.
    private static void clearScreen(){
        for(int i = 0; i<50; i++){
            System.out.print('\n');
        }
    }

    /*
    * Row, row, row your boat
    * Gently down the stream,
    * Merrily merrily merrily merrily
    * Life is but a dream :)
    * */

}