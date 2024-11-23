package Jobs;
import java.util.ArrayList;


enum LeadershipLevel{
    Director, TeamLeader, Manager, ChiefPosition
}

public class ManagementJob extends Job{

    int teamSize;
    LeadershipLevel leadershipLevel;
    String department;

    public ManagementJob(String title, String jobDesc, int salary, ArrayList<String> benefits, String employerName, String projectType, int teamSize, String department, LeadershipLevel leadershipLevel){

        super(title, jobDesc, salary, benefits, employerName);
        this.department = department;
        this.leadershipLevel = leadershipLevel;
        this.teamSize = teamSize;

    }

    // Getters!
    public String getDepartment() {
        return this.department;
    }
    public LeadershipLevel getLeadershipLevel() {
        return this.leadershipLevel;
    }
    public int getTeamSize() {
        return this.teamSize;
    }

    // Setters!
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setLeadershipLevel(LeadershipLevel leadershipLevel) {
        this.leadershipLevel = leadershipLevel;
    }
    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    // print, but in non-listing form. Displays the job and further details!
    @Override
    public void print(){

        System.out.println("Job Details");
        System.out.println("------------------------------------------------");
        System.out.println("Job Title: " + this.jobTitle);
        System.out.println("Description: " + this.jobDesc);
        System.out.println("Monthly Salary: " + this.salary + " PHP");
        System.out.println("Benefits: ");
        printBenefits();

        System.out.println();

        System.out.println("Miscellaneous Details");
        System.out.println("------------------------------------------------");
        System.out.println("Department: " + this.department);
        System.out.println("Leadership level: " + this.leadershipLevel);
        System.out.println("Team size: " + this.teamSize);
        System.out.println();

        System.out.println("Employer Details");
        System.out.println("------------------------------------------------");
        System.out.println("Employer: " + this.employer.getName());
        System.out.println("\t- Email: " + this.employer.getEmail());
        System.out.println("\t- Contact No: " + this.getEmployer().getContactNumber());
        System.out.println();
    }

    // If you need to print jobs, but in a listing form
    @Override
    public void print(int counter){
        System.out.println("Job #" + (++counter) + " (Management)");
        System.out.println("----------------------------------------------------");
        System.out.println("Job Title       : " + this.jobTitle);
        System.out.println("Employer        : " + this.jobTitle);
        System.out.println("Job Description : " + this.jobDesc);
        System.out.println("Salary          : PHP " + this.salary);
        System.out.println("Benefits        : ");
        printBenefits();
        System.out.println();

        System.out.println("Miscellaneous Details");
        System.out.println("\t- Department: " + this.department);
        System.out.println("\t- Leadership level: " + this.leadershipLevel);
        System.out.println("\t- Team size: " + this.teamSize);
        System.out.println();
        System.out.println("----------------------------------------------------\n");
    }
}
