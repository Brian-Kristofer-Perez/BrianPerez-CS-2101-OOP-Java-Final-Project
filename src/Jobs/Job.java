package Jobs;
import Database.EmployerDAO;
import Users.*;
import java.util.ArrayList;

public class Job {

   protected String jobTitle;
   protected String jobDesc;
   protected int salary;
   protected ArrayList<String> benefits;
   protected Employer employer;

   public Job(String title, String jobDesc, int salary, ArrayList<String> benefits, String employerName){

       EmployerDAO database = new EmployerDAO();

       this.employer = database.queryEmployerFromName(employerName);
       this.jobTitle = title;
       this.jobDesc = jobDesc;
       this.salary = salary;
       this.benefits = benefits;

   }

   // default, empty, constructor
   public Job(){
       this.jobTitle = "";
       this.jobDesc = "";
       this.salary = 0;
       this.benefits = new ArrayList<>();
   }

   // Getters
    public String getJobTitle() {
       return jobTitle;
   }

    public String getJobDesc() {
        return jobDesc;
    }

    public int getSalary() {
        return salary;
    }

    public ArrayList<String> getBenefits() {
        return benefits;
    }

    public Employer getEmployer(){
       return this.employer;
    }

    public void addBenefits(String benefit){
       this.benefits.add(benefit);
    }

    // print, but in non-listing form. Displays the job and employer details!
    public void print(){
        System.out.println("Job Details");
        System.out.println("------------------------------------------------");
        System.out.println("Job Title: " + this.jobTitle);
        System.out.println("Description: " + this.jobDesc);
        System.out.println("Monthly Salary: " + this.salary + " PHP");
        System.out.println("Benefits: ");
        printBenefits();
        System.out.println();

        System.out.println("Employer Details");
        System.out.println("------------------------------------------------");
        System.out.println("Employer: " + this.employer.getName());
        System.out.println("\t- Email: " + this.employer.getEmail());
        System.out.println("\t- Contact No: " + this.getEmployer().getContactNumber());
        System.out.println();
    }

    // If you need to print jobs, but in a listing form
    public void print(int counter){
        System.out.println("Job #" + (++counter));
        System.out.println("----------------------------------------------------");
        System.out.println("Job Title       : " + this.jobTitle);
        System.out.println("Employer        : " + this.jobTitle);
        System.out.println("Job Description : " + this.jobDesc);
        System.out.println("Salary          : PHP " + this.salary);
        System.out.println("Benefits        : ");
        printBenefits();

        System.out.println("----------------------------------------------------\n");
    }

    // turns out, printing benefits is a hassle!
    // Set to protected to make inheriting this easier
    protected void printBenefits(){
        if (this.benefits != null && !this.benefits.isEmpty()) {
            for (String benefit : this.benefits) {
                if (benefit != null && !benefit.isBlank()) {
                    System.out.println("  - " + benefit);
                }
            }
        } else {
            System.out.println("  No benefits listed.");
        }
    }
}
