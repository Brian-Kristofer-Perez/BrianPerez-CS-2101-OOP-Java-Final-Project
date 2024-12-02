package Jobs;

import java.util.ArrayList;

public class MedicalJob extends Job{

    private String department;
    private String shiftType;

    public MedicalJob(String title, String jobDesc, int salary, ArrayList<String> benefits, String employerName, String department, String shiftType){
        super(title, jobDesc, salary, benefits, employerName);
        this.department = department;
        this.shiftType = shiftType;
    }

    public MedicalJob(Job job, String department, String shiftType){
        super(job.getJobTitle(), job.getJobDesc(), job.getSalary(), job.getBenefits(), job.getEmployer().getName());
        this.department = department;
        this.shiftType = shiftType;
    }

    // Setters!
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    // Getters!
    public String getDepartment() {
        return department;
    }
    public String getShiftType() {
        return shiftType;
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
        System.out.println("Shift type: " + this.shiftType);
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
        System.out.println("Job #" + (counter) + " (Medical)");
        System.out.println("----------------------------------------------------");
        System.out.println("Job Title       : " + this.jobTitle);
        System.out.println("Employer        : " + this.employer.getName());
        System.out.println("Job Description : " + this.jobDesc);
        System.out.println("Salary          : PHP " + this.salary);
        System.out.println("Benefits        : ");
        printBenefits();
        System.out.println();

        System.out.println("Miscellaneous Details");
        System.out.println("\t- Department: " + this.department);
        System.out.println("\t- Shift type: " + this.shiftType);
        System.out.println();
        System.out.println("----------------------------------------------------\n");
    }
}
