package Users;
import Database.DatabaseConnection;
import Documents.Job;
import Users.User;
import java.util.ArrayList;
import java.util.Scanner;

public class Employer extends User {

    public Employer(String name){
        super(name);
        DatabaseConnection database = new DatabaseConnection();
        setEmail(database.queryEmployerEmail(name));
        setContactNumber(database.queryEmployerContactNo(name));
    }

    // Getters and setters
    public void setName(String name) {
        this.name = name;
    }


    private void createJob(Scanner input){

        String jobTitle, jobDesc, benefit, strSalary;
        int salary;
        ArrayList<String> benefits = new ArrayList<>();
        Job newPosting;

        // collect user input
        while(true) {
            System.out.println("====================================================");
            System.out.println("            CREATE NEW JOB POSTING                 ");
            System.out.println("====================================================");
            System.out.print("Input the job title: ");
            jobTitle = input.nextLine();

            if (jobTitle.isBlank()) {
                System.out.println("Please provide a proper value");
                continue;
            }

            System.out.print("Input the job description: ");
            jobDesc = input.nextLine();

            if (jobDesc.isBlank()) {
                System.out.println("Please provide a proper value");
                continue;
            }

            // making sure the integer input is valid
            try {
                System.out.print("Input the job's monthly salary: ");
                strSalary = input.nextLine();

                if(strSalary.isBlank()){
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

                if (benefit.isBlank()) {
                    break;
                } else {
                    benefits.add(benefit);
                }
            }

            break;
        }

        // create the posting object, and add to database
        newPosting = new Job(jobTitle, jobDesc, salary,benefits, this.name);
        DatabaseConnection database = new DatabaseConnection();
        database.addPosting(this.name, newPosting);

        System.out.println("Job successfully created!");
        System.out.println("====================================================");
    }


    private void viewPostings(ArrayList<Job> jobList){

        int counter = 0;

        if(!jobList.isEmpty()) {

            System.out.println("====================================================");
            System.out.println("                 JOB POSTINGS LIST                 ");
            System.out.println("====================================================");

            for (Job i : jobList) {
                System.out.println("Job #" + (++counter));
                System.out.println("----------------------------------------------------");
                System.out.println("Job Title       : " + i.getJobTitle());
                System.out.println("Job Description : " + i.getJobDesc());
                System.out.println("Salary          : PHP " + i.getSalary());

                System.out.println("Benefits        : ");
                if (i.getBenefits() != null && !i.getBenefits().isEmpty()) {
                    for (String benefit : i.getBenefits()) {
                        if (benefit != null && !benefit.isBlank()) {
                            System.out.println("  - " + benefit);
                        }
                    }
                } else {
                    System.out.println("  No benefits listed.");
                }
                System.out.println("----------------------------------------------------\n");
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
        DatabaseConnection database = new DatabaseConnection();
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

        database.deleteJob(jobList.get(choice), this.name);
        System.out.println("Job deleted!");

    }

    private void reviewPostings(Scanner input){

        DatabaseConnection database = new DatabaseConnection();
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
        ArrayList<Worker> applicationList = database.queryWorkersApplyingFor(jobSelection);
        int ctr = 0;

        if(applicationList.isEmpty()){
            System.out.println("No applications found.");
        }
        else {
            System.out.println("Current Application Resumes: ");
            for (Worker w : applicationList) {
                w.printResume(++ctr);
            }

            workerHireMenu(input, jobSelection, applicationList, database);
        }

    }

    private void workerHireMenu(Scanner input, Job job, ArrayList<Worker> applicationList, DatabaseConnection database){

        String stringChoice;
        int choice;

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

        DatabaseConnection database = new DatabaseConnection();
        int idEmployer = database.queryEmployerID(this.name);

        ArrayList<Worker> employeeList = database.queryEmployees(idEmployer);

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

        DatabaseConnection database = new DatabaseConnection();
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
                    createJob(input);
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
