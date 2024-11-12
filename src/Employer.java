import java.util.ArrayList;
import java.util.Scanner;

public class Employer extends User {

    public Employer(String name){
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public void createJob(Scanner input){

        String jobTitle, jobDesc, benefit, strSalary;
        int salary = 0;
        ArrayList<String> benefits = new ArrayList<>();
        Job newPosting = null;

        // collect user input
        while(true) {
            System.out.print("Input the job title: ");
            jobTitle = input.nextLine();

            if (jobTitle.isEmpty()) {
                System.out.println("Please provide a proper value");
                continue;
            }

            System.out.print("Input the job description: ");
            jobDesc = input.nextLine();

            if (jobDesc.isEmpty()) {
                System.out.println("Please provide a proper value");
                continue;
            }

            // making sure the integer input is valid
            try {
                System.out.print("Input the job's monthly salary: ");
                strSalary = input.nextLine();

                if(strSalary.isEmpty()){
                    System.out.println("Please provide a proper value");
                    continue;
                }

                salary = Integer.parseInt(strSalary);

                if (salary <= 0) {
                    System.out.println("Please provide a proper value");
                    continue;
                }

            } catch (RuntimeException e) {
                System.out.println("Please input a valid number!");
                continue;
            }

            // benefits input!

            while(true) {
                System.out.print("List down the other job benefits (leave empty to end): ");
                benefit = input.nextLine();

                if (benefit.isEmpty()) {
                    break;
                } else {
                    benefits.add(benefit);
                }
            }

            break;
        }

        // create the posting object, and add to database
        newPosting = new Job(jobTitle, jobDesc, salary,benefits);
        Database database = new Database();
        database.addPosting(this.name, newPosting);

        System.out.println("Job successfully created!");
    }

    public void login(Scanner input) {
        // user log-in menu here
        char choice;
        while (true) {
            System.out.println(String.format("Welcome, %s!", this.name));
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
