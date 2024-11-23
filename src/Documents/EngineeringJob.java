package Documents;
import java.util.ArrayList;

//enum ProjectType{
//    Software, Mechanical, Civil, Chemical
//}
//
//enum LocationType{
//    onsite, remote, flexible
//}
//
//enum TravelRequirements{
//    none,
//    occasional,
//    frequent
//}
//
//enum ContractType{
//    permanent, temporary, contract
//}

public class EngineeringJob extends Job{

    String projectType;
    String locationType;
    String travelRequirements;
    String contractType;

    // Pass all the inputs manually
    public EngineeringJob(String title, String jobDesc, int salary, ArrayList<String> benefits, String employerName, String projectType, String locationType, String travelRequirements, String contractType){

        super(title, jobDesc, salary, benefits, employerName);
        this.projectType = projectType;
        this.locationType = locationType;
        this.travelRequirements = travelRequirements;
        this.contractType = contractType;

    }

    // Or "aggregate" them into one job object, and use it instead!
    public EngineeringJob(Job job, String projectType, String locationType, String travelRequirements, String contractType){

        super(job.getJobTitle(), job.getJobDesc(), job.getSalary(), job.getBenefits(), job.getEmployer().getName());
        this.projectType = projectType;
        this.locationType = locationType;
        this.travelRequirements = travelRequirements;
        this.contractType = contractType;
    }

    // Getters!
    public String getProjectType() {
        return projectType;
    }
    public String getLocationType() {
        return locationType;
    }
    public String getTravelRequirements() {
        return travelRequirements;
    }
    public String getContractType() {
        return contractType;
    }

    // Setters!
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }
    public void setTravelRequirements(String travelRequirements) {
        this.travelRequirements = travelRequirements;
    }
    public void setContractType(String contractType) {
        this.contractType = contractType;
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
        System.out.println("Project Type: " + this.projectType);
        System.out.println("Location Type: " + this.locationType);
        System.out.println("Travel Requirements: " + this.travelRequirements);
        System.out.println("Contract Type: " + this.contractType);
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
        System.out.println("Job #" + (++counter) + " (Engineering)");
        System.out.println("----------------------------------------------------");
        System.out.println("Job Title       : " + this.jobTitle);
        System.out.println("Employer        : " + this.jobTitle);
        System.out.println("Job Description : " + this.jobDesc);
        System.out.println("Salary          : PHP " + this.salary);
        System.out.println("Benefits        : ");
        printBenefits();
        System.out.println();

        System.out.println("Miscellaneous Details");
        System.out.println("\t- Project Type: " + this.projectType);
        System.out.println("\t- Location Type: " + this.locationType);
        System.out.println("\t- Travel Requirements: " + this.travelRequirements);
        System.out.println("\t- Contract Type: " + this.contractType);
        System.out.println();
        System.out.println("----------------------------------------------------\n");
    }
}
