package Users;
import Database.*;
import Jobs.EngineeringJob;
import Jobs.Job;
import Jobs.MedicalJob;

import java.util.ArrayList;
import java.util.Scanner;

public class Employer extends User {

    public Employer(String name){
        super(name);
        EmployerDAO database = new EmployerDAO();
        setEmail(database.queryEmployerEmail(name));
        setContactNumber(database.queryEmployerContactNo(name));
    }

    private void createJobMenu(Scanner input){

        String jobTypeChoice;
        char jobType;

        while(true) {
            System.out.println("1. Engineering");
            System.out.println("2. Medical");
            System.out.println("3. Management");
            System.out.println("4. General");
            System.out.print("Input the number of your job type: ");

            jobTypeChoice = input.nextLine();

            if(jobTypeChoice.isBlank()){
                System.out.println("Input a valid choice!");
                continue;
            }

            jobType = jobTypeChoice.charAt(0);

            switch (jobType){
                case '1':
                    createEngineeringJob(input);
                    break;
                case '2':
                    createMedicalJob(input);
                    break;
                case '3':

                    break;
                case '4':
                    createJob(input);
                    break;
                default:
                    System.out.println("Input a valid choice!");
                    continue;
            }
            break;
        }
    }

    private Job getJobCreationInputs(Scanner input){

        String jobTitle, jobDesc, benefit, strSalary;
        int salary;
        ArrayList<String> benefits = new ArrayList<>();
        Job newPosting;

        System.out.println("====================================================");
        System.out.println("            CREATE NEW JOB POSTING                 ");
        System.out.println("====================================================");

        // collect job title
        System.out.print("Input the job title (leave empty to return): ");
        jobTitle = input.nextLine();

        if (jobTitle.isBlank()) {
            return null;
        }

        // collect job description
        System.out.print("Input the job description (leave empty to return): ");
        jobDesc = input.nextLine();

        if (jobDesc.isBlank()) {
            return null;
        }

        // collect salary
        while(true) {
            try {
                System.out.print("Input the job's monthly salary (leave empty to return): ");
                strSalary = input.nextLine();

                if (strSalary.isBlank()) {
                    return null;
                }

                salary = Integer.parseInt(strSalary);
                if (salary <= 0) {
                    System.out.println("Please provide a proper value");
                }

                else{
                    break;
                }

            } catch (RuntimeException e) {
                System.out.println("Please input a valid number!");
            }
        }

        // benefits input!
        while(true) {
            System.out.print("List down the other job benefits (leave empty to end): ");
            benefit = input.nextLine();

            if (benefit.isBlank()) {
                break;
            } else {
                benefits.add(benefit);
            }
        }

        // create the posting object, and add to database
        newPosting = new Job(jobTitle, jobDesc, salary, benefits, this.name);

        return newPosting;
    }

    private void createJob(Scanner input){

        Job newPosting;

        newPosting = getJobCreationInputs(input);

        if(newPosting == null){
            System.out.println("Returning to menu...");
            return;
        }

        JobDAO database = new JobDAO();
        database.addPosting(this.name, newPosting);

        System.out.println("Job successfully created!");
        System.out.println("====================================================");
    }

    private void createEngineeringJob(Scanner input){

        Job newPosting = getJobCreationInputs(input);
        int locationChoice;
        int travelChoice;
        int contractChoice;
        String location, locationType;
        String travel, travelRequirement;
        String contract, contractType;
        String projectType;

        while(true) {
            System.out.print("Input the job's project type: ");
            projectType = input.nextLine();

            // get project type!
            if (projectType.isBlank()) {
                System.out.println("Project must have a type!");
                continue;
            }
            break;
        }

        // get location type!
        while(true) {
            System.out.println("1. Onsite");
            System.out.println("2. Remote");
            System.out.println("3. Flexible");
            System.out.print("Input the number of the job's location type: "); // onsite, remote, flexible

            try {
                location = input.nextLine();
                locationChoice = Integer.parseInt(location);
            } catch (RuntimeException e) {
                System.out.println("Input a valid number!");
                continue;
            }

            switch (locationChoice){
                case 1:
                    locationType = "Onsite";
                    break;
                case 2:
                    locationType = "Remote";
                    break;
                case 3:
                    locationType = "Flexible";
                    break;
                default:
                    System.out.println("Input a valid choice!");
                    continue;
            }
            break;
        }

        // get travel requirement!
        while(true) {
            System.out.println("1. Frequent");
            System.out.println("2. Occasional");
            System.out.println("3. Never");
            System.out.print("Input the number of the job's travel requirement: ");
            try {
                travel = input.nextLine();
                travelChoice = Integer.parseInt(travel);
            } catch (RuntimeException e) {
                System.out.println("Input a valid number!");
                continue;
            }

            switch (travelChoice){
                case 1:
                    travelRequirement = "Frequent";
                    break;
                case 2:
                    travelRequirement = "Occasional";
                    break;
                case 3:
                    travelRequirement = "Never";
                    break;
                default:
                    System.out.println("Input a valid choice!");
                    continue;
            }
            break;
        }

        // get contract type!
        while(true) {
            System.out.println("1. Permanent");
            System.out.println("2. Temporary");
            System.out.println("3. Contract");
            System.out.print("Input the number of the job's contract type: ");
            try {
                contract = input.nextLine();
                contractChoice = Integer.parseInt(contract);
            } catch (RuntimeException e) {
                System.out.println("Input a valid number!");
                continue;
            }

            switch (contractChoice){
                case 1:
                    contractType = "Permanent";
                    break;
                case 2:
                    contractType = "Temporary";
                    break;
                case 3:
                    contractType = "Contract";
                    break;
                default:
                    System.out.println("Input a valid choice!");
                    continue;
            }
            break;
        }

        EngineeringJob newJob = new EngineeringJob(newPosting, projectType, locationType, travelRequirement, contractType);

        EngineeringDAO database = new EngineeringDAO();

        database.addPosting(this.name, newJob);

        System.out.println("Job successfully created!");
        System.out.println("====================================================");

    };

    private void createMedicalJob(Scanner input){
        Job newPosting = getJobCreationInputs(input);
        int shiftChoice;
        String department, shiftType, shift;

        while(true) {
            System.out.print("Input the job department: ");
            department = input.nextLine();

            if (department.isBlank()) {
                System.out.println("There must be a valid department!");
                continue;
            }
            break;
        }

        // get the shift!
        while(true) {
            System.out.println("1. Day");
            System.out.println("2. Night");
            System.out.println("3. Rotating");
            System.out.print("Input the corresponding number of the job shift: ");

            try {
                shift = input.nextLine();
                shiftChoice = Integer.parseInt(shift);
            } catch (RuntimeException e) {
                System.out.println("Input a valid number!");
                continue;
            }

            switch (shiftChoice){
                case 1:
                    shiftType = "Day";
                    break;
                case 2:
                    shiftType = "Night";
                    break;
                case 3:
                    shiftType = "Rotating";
                    break;
                default:
                    System.out.println("Input a valid choice!");
                    continue;
            }
            break;
        }

        MedicalJob newJob = new MedicalJob(newPosting, department, shiftType);

        MedicalDAO database = new MedicalDAO();

        database.addPosting(this.name, newJob);

        System.out.println("Job successfully created!");
        System.out.println("====================================================");

    };

    private void createManagementJob(Scanner input){};


    private void viewPostings(ArrayList<Job> jobList){

        int counter = 0;

        if(!jobList.isEmpty()) {

            System.out.println("====================================================");
            System.out.println("                 JOB POSTINGS LIST                 ");
            System.out.println("====================================================");

            for (Job i : jobList) {
                i.print(++counter);
            }

            System.out.println("====================================================");

        }
        else{
            System.out.println("No vacant jobs listed. Try and make one!");
        }
    }

    private void deleteJobMenu(Scanner input){

        String strChoice;
        int choice;
        JobDAO database = new JobDAO();
        ArrayList<Job> jobList = database.queryJobs(this.name);

        if(jobList.isEmpty()){
            return;
        }

        viewPostings(jobList);

        // collect and validate user input
        while(true) {
            System.out.print("Input the number of the posting you want to delete (leave empty to return): ");

            strChoice = input.nextLine();

            if(strChoice.isBlank()){
                return;
            }

            try {
                choice = Integer.parseInt(strChoice);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input.");
                continue;
            }

            choice--;

            if(choice < 0){
                System.out.println("Input a valid index.");
                continue;
            }

            if(choice > jobList.size()-1){
                System.out.println("Input a valid index.");
            }
            else{
                break;
            }
        }

        database.deleteJob(jobList.get(choice));
        System.out.println("Job deleted!");

    }

    private void reviewPostings(Scanner input){

        JobDAO database = new JobDAO();
        String strChoice;
        int choice;
        ArrayList<Job> jobList = database.queryJobs(this.name);

        viewPostings(jobList);

        if(jobList.isEmpty()){
            return;
        }

        while(true){
            System.out.print("Input the number of the job you want to review (leave empty to return): ");
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
            }

            else{
                break;
            }
        }

        Job jobSelection = jobList.get(choice);
        ArrayList<Worker> applicationList = database.queryApplicants(jobSelection);
        int ctr = 0;

        if(applicationList.isEmpty()){
            System.out.println("No applications found.");
        }
        else {
            System.out.println("Current Application Resumes: ");
            for (Worker w : applicationList) {
                w.printResume(++ctr);
            }

            workerHireMenu(input, jobSelection, applicationList);
        }

    }

    private void workerHireMenu(Scanner input, Job job, ArrayList<Worker> applicationList){

        String stringChoice;
        int choice;
        WorkerDAO database = new WorkerDAO();

        while(true){
            System.out.print("Input the number of the applicant you want to hire (leave empty to return): ");
            stringChoice = input.nextLine();

            if(stringChoice.isBlank()){
                return;
            }

            try{
                choice = Integer.parseInt(stringChoice);
            } catch (RuntimeException e) {
                System.out.println("Input a valid number!");
                continue;
            }

            --choice;
            if(choice < 0 || choice > applicationList.size()-1){
                System.out.println("Input a valid number!");
            }

            else{
                Worker worker = applicationList.get(choice);
                database.hireWorker(worker, job);
                worker.setOccupation(job);
                System.out.println("Worker successfully hired!");

                return;
            }

        }
    }


    private void manageEmployees(Scanner input){

        EmployerDAO database = new EmployerDAO();
        ArrayList<Worker> employeeList = database.queryEmployees(this.name);

        System.out.println("====================================================");
        System.out.println("                    EMPLOYEE LIST                  ");
        System.out.println("====================================================");

        int ctr = 0;
        if (!employeeList.isEmpty()) {
            for (Worker i : employeeList) {
                System.out.println("Employee #" + (++ctr));
                System.out.println("----------------------------------------------------");
                System.out.println("Name          : " + i.getName());
                System.out.println("Job Position  : " + i.getOccupation().getJobTitle());
                System.out.println("Email         : " + i.getEmail());
                System.out.println("Contact No    : " + i.getContactNumber());
                System.out.println("----------------------------------------------------\n");
            }
        } else {
            System.out.println("No employees found.");
        }

        System.out.println("====================================================");

        Worker employee = selectEmployee(employeeList, input);

        if(employee == null){
            return;
        }

        String choiceStr;

        System.out.println("Worker Record: ");
        employee.printResume();

        while(true) {
            System.out.print("Fire this employee? (y/n): ");
            choiceStr = input.nextLine();

            if(choiceStr.isBlank()){
                return;
            }

            else if(choiceStr.equalsIgnoreCase("y")){
                database.fireWorker(employee.getName());
                System.out.println("Employee fired. Goodbye, " + employee.getName() +"!");
                return;
            }

            else if(choiceStr.equalsIgnoreCase("n")){
                return;
            }

            else{
                System.out.println("Input a valid choice!");
            }
        }

    }

    private Worker selectEmployee(ArrayList<Worker> employeeList, Scanner input){
        String strChoice;
        int choice = 0;

        if(employeeList.isEmpty()){
            return null;
        }

        while(true) {
            System.out.print("Select the number of the employee to review (leave empty to return): ");
            strChoice = input.nextLine();

            if (strChoice.isBlank()) {
                return null;
            }
            try {
                choice = Integer.parseInt(strChoice);
            } catch (RuntimeException e) {
                System.out.println("Input a valid number!");
            }
            --choice;
            if (choice < 0 || choice > employeeList.size() - 1) {
                System.out.println("Input a valid number!");
            } else {
                return employeeList.get(choice);
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

        EmployerDAO database = new EmployerDAO();
        database.setEmployerContactDetails(newEmail, newContact, this.name);

        this.setEmail(newEmail);
        this.setContactNumber(newContact);
    }

    public void login(Scanner input) {
        // user log-in menu here
        char choice;
        while (true) {
            System.out.println(String.format("Welcome, %s!", this.name));
            System.out.println("1. Create Job postings");
            System.out.println("2. Review Job postings");
            System.out.println("3. View contact details");
            System.out.println("4. Edit contact details");
            System.out.println("5. Manage employees");
            System.out.println("6. Delete job postings");
            System.out.println("7. Log out");

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
                    createJobMenu(input);
                    break;
                case '2':
                    reviewPostings(input);
                    break;
                case '3':
                    printContactDetails();
                    break;
                case '4':
                    editContactDetails(input);
                    break;
                case '5':
                    manageEmployees(input);
                    break;
                case '6':
                    deleteJobMenu(input);
                    break;
                case '7':
                    System.out.println("Logging out. Goodbye!W");
                    return;

                default:
                    System.out.println("Please provide a valid input.");
            }
        }
    }
}
