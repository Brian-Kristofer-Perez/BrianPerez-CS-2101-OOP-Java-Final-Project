import java.util.ArrayList;
import java.util.Scanner;

public class Employer extends User {

    public Employer(String name){
        this.name = name;
    }

    // Getters and setters
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
            System.out.println("====================================================");
            System.out.println("            CREATE NEW JOB POSTING                 ");
            System.out.println("====================================================");
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
        System.out.println("====================================================");
    }


    public void viewPostings(ArrayList<Job> jobList){

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
                        if (benefit != null && !benefit.isEmpty()) {
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

    public void deleteJobMenu(Scanner input){

        String strChoice;
        int choice = 0;
        Database database = new Database();
        ArrayList<Job> jobList = database.queryJobs(this.name);

        if(jobList.isEmpty()){
            return;
        }

        viewPostings(jobList);

        // collect and validate user input
        while(true) {
            System.out.print("Input the number of the posting you want to delete (leave empty to return): ");

            strChoice = input.nextLine();

            if(strChoice.isEmpty()){
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
        return;

    }

    public void reviewPostings(Scanner input){

        Database database = new Database();
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
            return;
        }

    }

    public void workerHireMenu(Scanner input, Job job, ArrayList<Worker> applicationList, Database database){

        String stringChoice;
        int choice;

        while(true){
            System.out.print("Input the number of the applicant you want to hire (leave empty to return): ");
            stringChoice = input.nextLine();

            if(stringChoice.isEmpty()){
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
                continue;
            }

            else{
                Worker worker = applicationList.get(choice);
                database.hireWorker(worker, job);
                System.out.println("Worker successfully hired!");

                return;
            }

        }
    }


    public void manageEmployees(Scanner input){

        Database database = new Database();
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

            if(choiceStr.isEmpty()){
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

            if (strChoice.isEmpty()) {
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

    public void login(Scanner input) {
        // user log-in menu here
        char choice;
        while (true) {
            System.out.println(String.format("Welcome, %s!", this.name));
            System.out.println("1. Create Job postings");
            System.out.println("2. Review Job postings");
            System.out.println("3. Manage Employees");
            System.out.println("4. Delete Job postings");
            System.out.println("5. Log out");

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
                    manageEmployees(input);
                    break;
                case '4':
                    deleteJobMenu(input);
                    break;
                case '5':
                    System.out.println("Logging out. Goodbye!W");
                    return;

                default:
                    System.out.println("Please provide a valid input.");
            }
        }
    }
}
