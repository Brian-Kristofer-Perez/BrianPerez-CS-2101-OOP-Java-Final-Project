import java.sql.SQLException;
import java.util.ArrayList;
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

    public Job getOccupation() {
        return occupation;
    }

    // Setters!
    public void setResume(Resume resume){
        this.resume = resume;
    }

    public void setJob(Job job){
        this.occupation = job;
    }

    public void login(Scanner input){
        // user log-in menu

        // preload stuff, load resume, and job
        Database database = new Database();
        this.resume = database.loadResume(this.name);
        this.occupation = database.loadOccupation(this.name);

        char choice;
        while (true) {
            System.out.println("\n========================================");
            System.out.println("      Welcome to Your Worker Portal     ");
            System.out.println("========================================");
            System.out.println(String.format("Welcome, %s!", this.name));
            System.out.println("What would you like to do today?");
            System.out.println("1. View Resume");
            System.out.println("2. Edit Resume");
            System.out.println("3. View Job Postings");
            System.out.println("4. Apply for a Job");
            System.out.println("5. View Current Job");
            System.out.println("6. Log out");

            System.out.print("Please enter your choice (1-6): ");

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
                    editResume(input);
                    break;
                case '3':
                    printAllPostings();
                    break;
                case '4':
                    applyForJob(input);
                    break;
                case '5':
                    displayCurrentJob(input);
                    break;
                case '6':
                    System.out.println("Logging out... Goodbye!");
                    return;
                default:
                    System.out.println("Please provide a valid input.");
            }
        }

    }

    // resume used for personal viewing and editing
    public void printResume() {
        System.out.println("=====================================================");
        System.out.println("                    RESUME                         ");
        System.out.println("=====================================================");

        // Print Name
        System.out.println("Name:");
        if (this.name.isEmpty()) {
            System.out.println("No name listed.");
        } else {
            System.out.println(this.name);
        }
        System.out.println();


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

        System.out.println("=====================================================");
    }

    // resume used for application
    public void printResume(int ctr) {
        System.out.println("=====================================================");
        System.out.println("                Resume #" + ctr +"                ");
        System.out.println("=====================================================");

        // Print Name
        System.out.println("Name:");
        if (this.name.isEmpty()) {
            System.out.println("No name listed.");
        } else {
            System.out.println(this.name);
        }
        System.out.println();


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

        System.out.println("=====================================================");
    }


    // auto-queries the db stuff, irrespective of order
    public void printAllPostings() {

        Database database = new Database();

        ArrayList<Job> jobList = database.queryAllPostings();

        int counter = 0;

        if (jobList.isEmpty()) {
            System.out.println("\nNo job postings available at the moment.");
            System.out.println("Please check back later.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("        Available Job Postings          ");
        System.out.println("========================================");

        for (Job job : jobList) {
            System.out.println(String.format("\nJob #%d: %s", ++counter, job.getJobTitle()));
            System.out.println("\tDescription: " + job.getJobDesc());
            System.out.println("\tSalary: Php " + job.getSalary());
            System.out.println("\tBenefits:");

            if (job.getBenefits() != null && !job.getBenefits().isEmpty()) {
                for (String benefit : job.getBenefits()) {
                    if (benefit != null && !benefit.isEmpty()) {
                        System.out.println("\t - " + benefit);
                    }
                }
            } else {
                System.out.println("\t - No benefits listed.");
            }
        }

        System.out.println("\n========================================");
        System.out.println();

    }

    // takes a preset manually queried job order, used in cases where indexing is needed
    public void printAllPostings(ArrayList<Job> jobList) {

        int counter = 0;

        if (jobList.isEmpty()) {
            System.out.println("\nNo job postings available at the moment.");
            System.out.println("Please check back later.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("        Available Job Postings          ");
        System.out.println("========================================");

        for (Job job : jobList) {

            System.out.println(String.format("\nJob #%d: %s", ++counter, job.getJobTitle()));
            System.out.println("\tDescription: " + job.getJobDesc());
            System.out.println("\tSalary: Php " + job.getSalary());
            System.out.println("\tBenefits:");

            if (job.getBenefits() != null && !job.getBenefits().isEmpty()) {
                for (String benefit : job.getBenefits()) {
                    if (benefit != null && !benefit.isEmpty()) {
                        System.out.println("\t - " + benefit);
                    }
                }
            } else {
                System.out.println("\t - No benefits listed.");
            }
        }

        System.out.println("\n========================================");
        System.out.println();
    }

    public void editResume(Scanner input){

        String newSummary, certification, experience;
        ArrayList<String> newExperience = new ArrayList<String>();
        ArrayList<String> newCertifications = new ArrayList<String>();

        System.out.println("\n========================================");
        System.out.println("       Editing Your Resume           ");
        System.out.println("========================================");

        // Edit Summary
        System.out.print("Enter the new summary: ");
        newSummary = input.nextLine();

        // Edit Certifications
        System.out.println("\n========================================");
        System.out.println("       Adding Certifications         ");
        System.out.println("========================================");

        while (true) {
            System.out.print("Input the new certifications (leave empty to finish): ");
            certification = input.nextLine();

            if (certification.isEmpty()) {
                break;
            } else {
                newCertifications.add(certification);
            }
        }

        // Edit Work Experience
        System.out.println("\n========================================");
        System.out.println("        Adding Work Experience           ");
        System.out.println("========================================");

        while (true) {
            System.out.print("Input the new work experience (leave empty to finish): ");
            experience = input.nextLine();

            if (experience.isEmpty()) {
                break;
            } else {
                newExperience.add(experience);
            }
        }

        // Update the resume in the program
        this.resume.setSummary(newSummary);
        this.resume.setExperience(newExperience);
        this.resume.setCertifications(newCertifications);

        // Update the resume in the database
        Database database = new Database();
        database.updateResume(this.name, newSummary, newExperience, newCertifications);

        // Confirm that the resume has been updated
        System.out.println("========================================");
        System.out.println("     Resume Updated Successfully!       ");
        System.out.println("========================================");
    }

    public void applyForJob(Scanner input){
        Database database = new Database();
        int idWorker = database.queryWorkerID(this.name);

        ArrayList<Job> jobList = database.queryAllPostings();
        String strChoice;
        int choice, jobID = 0;
        Job job;
        printAllPostings(jobList);

        while(true){
            System.out.print("Input the number of the job you want to apply for (leave empty to return): ");
            strChoice = input.nextLine();

            if(strChoice.isEmpty()){
                return;
            }

            try{
                choice = Integer.parseInt(strChoice);
            } catch (RuntimeException e) {
                System.out.println("Input a valid number!");
                continue;
            }

            --choice;
            if(choice < 0 || choice > jobList.size()-1){
                System.out.println("Input a valid number!");
                continue;
            }

            else{
                job = jobList.get(choice);
                jobID = database.queryJobID(job);
            }

            if(database.applicationExists(idWorker, jobID)){
                System.out.println("Application already exists!");
            }
            else if (database.alreadyEmployed(idWorker)) {
                System.out.println("You are already employed. Don't overwork yourself!");
            }
            else {
                database.createApplication(this.name, job);
                System.out.println("Application submitted!");
                return;
            }
        }
    }

    void displayCurrentJob(Scanner input){

        // fully check if the current job is actually the default "empty" one (i.e. unemployed)
        boolean noTitle = this.occupation.getJobTitle().strip().isEmpty();
        boolean noDesc = this.occupation.getJobDesc().strip().isEmpty();
        boolean noSalary = this.occupation.getSalary() == 0;
        boolean noBenefits = this.occupation.getBenefits().isEmpty();

        if(noTitle && noDesc && noSalary && noBenefits){
            System.out.println("You are currently unemployed. Find a job now!");
        }

        else {
            System.out.println("====================================================");
            System.out.println("                CURRENT JOB DETAILS                ");
            System.out.println("====================================================");

            System.out.println("Job Title: " + this.occupation.getJobTitle());
            System.out.println("Description: " + this.occupation.getJobDesc());
            System.out.println("Monthly Salary: " + this.occupation.getSalary() + " PHP");
            System.out.println("Benefits: ");

            if (this.occupation.getBenefits() != null && !this.occupation.getBenefits().isEmpty()) {
                for (String j : this.occupation.getBenefits()) {

                    if (!(j == null) && !j.isEmpty()) {
                        System.out.println("\t - " + j);
                    } else {
                        System.out.println("\tNo benefits listed.");
                    }
                }
                System.out.println();
            } else {
                System.out.println("\tNo benefits listed.");
            }

            String choiceStr;
            Database database = new Database();

            while(true) {
                System.out.print("Do you wish to resign? (y/n): ");
                choiceStr = input.nextLine();

                if(choiceStr.isEmpty()){
                    return;
                }

                else if(choiceStr.equalsIgnoreCase("y")){
                    database.fireWorker(this.name);
                    this.occupation = new Job();
                    System.out.println("You have successfully resigned!");
                    System.out.println("====================================================");
                    return;
                }

                else if(choiceStr.equalsIgnoreCase("n")){
                    System.out.println("====================================================");
                    return;
                }

                else{
                    System.out.println("Input a valid choice!");
                }
            }
        }
    }
}

