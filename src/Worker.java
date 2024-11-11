import java.util.Scanner;

public class Worker extends User {
    //private Resume resume;
    private String occupation;

    // Sign in, with resume
    public Worker(){}

    // Sign in, without resume
    public Worker(String Resume){}





    public void apply(){}

    public void login(Scanner input){
        // user log-in menu here
        char choice;
        while (true) {
            System.out.println("Welcome, [name]!");
            System.out.println("1. View Resume");
            System.out.println("2. View Job Postings");
            System.out.println("3. View Current Job");
            System.out.println("4. Log out");

            System.out.print("Input your choice: ");


            // catch empty input errors!
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                System.out.println("Please provide a valid input.");
                continue;
            }

            switch (choice) {
                case '1':
                    // viewResume();
                    break;
                case '2':
                    // viewJobPostings();
                    break;
                default:
                    System.out.println("Please provide a valid input.");
            }
        }

    }

    public void logout(){}
    
}

