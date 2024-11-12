import java.util.ArrayList;
import java.util.Scanner;

public class Employer extends User {

    public Employer(String name){

    }

    public Job createJob(Scanner input){

        String jobTitle, jobDesc, benefit;
        int salary = 0;
        ArrayList<String> benefits = new ArrayList<>();
        Job newPosting = null;

        while(true) {
            System.out.print("Input the job title (Leave empty to return): ");
            jobTitle = input.nextLine();

            if (jobTitle.isEmpty()) {
                System.out.println("Please provide a proper value");
                continue;
            }

            System.out.print("Input the job description (Leave empty to return): ");
            jobDesc = input.nextLine();

            if (jobDesc.isEmpty()) {
                System.out.println("Please provide a proper value");
                continue;
            }

            // making sure the integer input is valid
            try {
                System.out.print("Input the job's monthly salary (0 to return): ");
                salary = input.nextInt();

                // clear the newline
                input.nextLine();


                if (salary <= 0) {
                    System.out.println("Please provide a proper value");
                    continue;
                }

            } catch (RuntimeException e) {
                System.out.println("Please input a valid number!");
            }

            // benefits input!
            System.out.print("List down the other job benefits (leave empty to end): ");
            benefit = input.nextLine();

            if (benefit.isEmpty()) {
                break;
            } else {
                benefits.add(benefit);
            }

        }

        newPosting = new Job(jobTitle, jobDesc, salary,benefits);
        return newPosting;
    }

    public void login(Scanner input) {
        // user log-in menu here
        char choice;
        while (true) {
            System.out.println("Welcome, [name]!");
            System.out.println("1. Create Job postings");
            System.out.println("2. View Job postings");
            System.out.println("3. Delete Job postings");
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
                    createJob(input);
                    break;
                case '2':
                    // vie
                    break;
                default:
                    System.out.println("Please provide a valid input.");
            }
        }
    }
}
