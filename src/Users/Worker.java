package Users;
import java.util.ArrayList;
import java.util.Scanner;
import Database.*;
import Jobs.*;

public class Worker extends User {
    private Resume resume;
    private Job occupation;

    // Constructor, loading details from DB as well
    public Worker(String name){
        super(name);
        this.resume = new Resume();

        WorkerDAO database = new WorkerDAO();
        this.setResume(database.loadResume(name));
        this.setEmail(database.queryWorkerEmail(name));
        this.setContactNumber(database.queryWorkerContactNo(name));
        this.setOccupation(database.loadOccupation(name));
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

    public void setOccupation(Job job){
        this.occupation = job;
    }

    public void login(Scanner input){
        // user log-in menu

        // preload stuff, load resume, and job
        WorkerDAO database = new WorkerDAO();
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
            System.out.println("3. View Contact details");
            System.out.println("4. Edit Contact details");
            System.out.println("5. Apply for a Job");
            System.out.println("6. View Current Job");
            System.out.println("7. Log out");

            System.out.print("Please enter your choice (1-7): ");

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
                    printContactDetails();
                    break;
                case '4':
                    editContactDetails(input);
                    break;
                case '5':
                    applyForJob(input);
                    break;
                case '6':
                    displayCurrentJob(input);
                    break;
                case '7':
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
        if (this.name.isBlank()) {
            System.out.println("No name listed.");
        } else {
            System.out.println(this.name);
        }
        System.out.println();


        // Print Summary
        System.out.println("Summary:");
        if (this.resume.getSummary().isBlank()) {
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
        if (this.name.isBlank()) {
            System.out.println("No name listed.");
        } else {
            System.out.println(this.name);
        }
        System.out.println();


        // Print Summary
        System.out.println("Summary:");
        if (this.resume.getSummary().isBlank()) {
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

    // takes a preset manually queried job order, used in cases where indexing is needed
    private void printAllPostings(ArrayList<Job> jobList) {

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
            job.print(++counter);
        }

        System.out.println("\n========================================");
        System.out.println();
    }

    private void editResume(Scanner input){

        String newSummary, certification, experience;
        ArrayList<String> newExperience = new ArrayList<>();
        ArrayList<String> newCertifications = new ArrayList<>();

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

            if (certification.isBlank()) {
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

            if (experience.isBlank()) {
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
        WorkerDAO database = new WorkerDAO();
        database.updateWorkerResume(this.name, newSummary, newExperience, newCertifications);

        // Confirm that the resume has been updated
        System.out.println("========================================");
        System.out.println("     Resume Updated Successfully!       ");
        System.out.println("========================================");
    }

    private JobDAO selectPostingType(Scanner input){

        String jobTypeChoice;
        char jobType;

        while(true) {
            System.out.println();
            System.out.println("1. Engineering");
            System.out.println("2. Medical");
            System.out.println("3. Management");
            System.out.println("4. General");
            System.out.print("Input the number of the desired job type: ");

            jobTypeChoice = input.nextLine();

            if(jobTypeChoice.isBlank()){
                System.out.println("Input a valid choice!");
                continue;
            }

            jobType = jobTypeChoice.charAt(0);

            switch (jobType){
                case '1':
                    return new EngineeringDAO();
                case '2':
                    return new MedicalDAO();
                case '3':
                    return new ManagementDAO();
                case '4':
                    return new JobDAO();
                default:
                    System.out.println("Input a valid choice!");
            }
        }
    }

    private void applyForJob(Scanner input){
        WorkerDAO database = new WorkerDAO();
        int idWorker = database.queryWorkerID(this.name);

        JobDAO jobDB = selectPostingType(input);
        ArrayList<Job> jobList = jobDB.queryAllOpenPostings();

        String strChoice;
        int choice, jobID;
        Job job;
        printAllPostings(jobList);

        if(jobList.isEmpty()){
            return;
        }

        while(true){
            System.out.print("Input the number of the job you want to apply for (leave empty to return): ");
            strChoice = input.nextLine();

            if(strChoice.isBlank()){
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
                jobID = jobDB.queryJobID(job);
            }

            if(jobDB.applicationExists(idWorker, jobID)){
                System.out.println("Application already exists!");
            }
            else if (database.alreadyEmployed(idWorker)) {
                System.out.println("You are already employed. Don't overwork yourself!");
            }
            else {
                jobDB.createApplication(this.name, job);
                System.out.println("Application submitted!");
                return;
            }
        }
    }

    private void displayCurrentJob(Scanner input){

        // fully check if the current job is actually the default "empty" one (i.e. unemployed)
        boolean noTitle = this.occupation.getJobTitle().isBlank();
        boolean noDesc = this.occupation.getJobDesc().isBlank();
        boolean noSalary = this.occupation.getSalary() == 0;
        boolean noBenefits = this.occupation.getBenefits().isEmpty();

        if(noTitle && noDesc && noSalary && noBenefits){
            System.out.println("You are currently unemployed. Find a job now!");
        }

        else {

            System.out.println("====================================================");
            System.out.println("                CURRENT JOB DETAILS                ");
            System.out.println("====================================================");

            this.occupation.print();

            String choiceStr;

            System.out.println("====================================================");
            while(true) {
                System.out.print("Do you wish to resign? (y/n): ");
                choiceStr = input.nextLine();

                if(choiceStr.isBlank()){
                    return;
                }

                else if(choiceStr.equalsIgnoreCase("y")){

                    // auto find the DAO based on what the jobtype of the occupation is
                    JobDAO DAO = new JobDAO();

                    if(this.occupation instanceof EngineeringJob){
                        DAO = new EngineeringDAO();
                    }
                    if(this.occupation instanceof MedicalJob){
                        DAO = new MedicalDAO();
                    }
                    if(this.occupation instanceof ManagementJob){
                        DAO = new ManagementDAO();
                    }

                    DAO.fireWorker(this.name);
                    this.occupation = new Job();
                    System.out.println("You have successfully resigned!");
                    System.out.println();
                    return;
                }

                else if(choiceStr.equalsIgnoreCase("n")){
                    System.out.println();
                    return;
                }

                else{
                    System.out.println("Input a valid choice!");
                }
            }
        }
    }

    protected void editContactDetails(Scanner input){

        printContactDetails();
        String newEmail, newContact;

        System.out.print("Input the new email: ");
        newEmail = input.nextLine();

        System.out.print("Input the new contact number: ");
        newContact = input.nextLine();

        WorkerDAO database = new WorkerDAO();
        database.setWorkerContactDetails(newEmail, newContact, this.name);

        this.setEmail(newEmail);
        this.setContactNumber(newContact);
    }
}

