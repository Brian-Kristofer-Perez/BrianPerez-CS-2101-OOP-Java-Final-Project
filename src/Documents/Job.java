package Documents;
import Users.*;
import Database.DatabaseConnection;
import java.util.ArrayList;

public class Job {

   private String jobTitle;
   private String jobDesc;
   private int salary;
   private ArrayList<String> benefits;
   private Employer employer;

   public Job(String title, String jobDesc, int salary, ArrayList<String> benefits, String employerName){

       DatabaseConnection database = new DatabaseConnection();

       this.employer = database.loadEmployer(employerName);
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

}
