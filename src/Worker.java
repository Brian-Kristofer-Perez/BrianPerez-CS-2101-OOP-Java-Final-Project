import java.sql.SQLException;
import java.util.Scanner;

public class Worker extends User {
    private Resume resume;
    private Job occupation;

    // Constructor, loading details from DB will be to-follow
    public Worker(String name){

        this.name = name;
        this.resume = new Resume();

    }

    // Getters!

    public Resume getResume(){
        return this.resume;
    }

    // Setters!
    public void setResume(Resume resume){
        this.resume = resume;
    }

    public void login(Scanner input){
        // user log-in menu here
        char choice;
        while (true) {
            System.out.println(String.format("Welcome, %s!", this.name));
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
                    printResume();
                    break;
                case '2':
                    // viewJobPostings();
                    break;
                default:
                    System.out.println("Please provide a valid input.");
            }
        }

    }

    public void printResume() {
        System.out.println("----------------------------------------------------");
        System.out.println("                    RESUME                         ");
        System.out.println("----------------------------------------------------");

        // Print Summary
        System.out.println("Summary:");
        if (this.resume.getSummary().isEmpty()) {
            System.out.println("No summary listed.");
        } else {
            System.out.println(this.resume.getSummary());
        }
        System.out.println();

        // Print Experience
        System.out.println("Experience:");
        if (this.resume.getExperience().isEmpty()) {
            System.out.println("No experience listed.");
        } else {
            for (String job : this.resume.getExperience()) {
                System.out.println("- " + job);
            }
        }
        System.out.println();

        // Print Certifications
        System.out.println("Certifications:");
        if (this.resume.getCertifications().isEmpty()) {
            System.out.println("No certifications listed.");
        } else {
            for (String certification : this.resume.getCertifications()) {
                System.out.println("- " + certification);
            }
        }

        System.out.println("----------------------------------------------------");
    }

    public void logout(){}
    
}

