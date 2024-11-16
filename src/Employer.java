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


    // overloaded method! this one you have to manually pass the list, the other one automatically does based off of the name.
    public void viewPostings(ArrayList<Job> jobList){

        int counter = 0;

        if(!jobList.isEmpty()) {
            for (Job i : jobList) {

                System.out.println(++counter + ".  " + "Job title: " + i.getJobTitle());
                System.out.println("\tDescription: " + i.getJobDesc());
                System.out.println("\tMonthly Salary: " + i.getSalary() + " php");
                System.out.println("\tBenefits: ");

                if (i.getBenefits() != null && !i.getBenefits().isEmpty()) {
                    for (String j : i.getBenefits()) {

                        if(!(j == null) && !j.isEmpty()){
                            System.out.println("\t - " + j);
                        }
                        else{
                            System.out.println("\t\tNo benefits listed.");
                        }
                    }
                    System.out.println();
                } else {
                    System.out.println("\t\tNo benefits listed.");
                }
            }
        }
        else{
            System.out.println("No jobs listed. ");
        }
    }

    public void deleteJobMenu(Scanner input){

        String strChoice;
        int choice = 0;
        Database database = new Database();
        ArrayList<Job> jobList = database.queryJobs(this.name);

        viewPostings(jobList);

        // collect and validate user input
        while(true) {
            System.out.print("Input the number of the posting you want to delete: ");

            strChoice = input.nextLine();
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

        Job job = jobList.get(choice);
        ArrayList<Worker> applicationList = database.queryWorkersApplyingFor(job);
        int ctr = 0;

        if(applicationList.isEmpty()){
            System.out.println("No applications found.");
        }
        else {
            System.out.println("Current Application Resumes: ");
            for (Worker w : applicationList) {
                w.printResume(++ctr);
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
                    reviewPostings(input);
                    // EXTRA MENU HERE()
                    break;
                case '3':
                    break;
                case '4':
                    deleteJobMenu(input);
                    break;
                case '5':
                    return;

                default:
                    System.out.println("Please provide a valid input.");
            }
        }
    }
}
