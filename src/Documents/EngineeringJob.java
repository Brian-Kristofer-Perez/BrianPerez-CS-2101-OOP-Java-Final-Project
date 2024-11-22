package Documents;
import java.util.ArrayList;

enum ProjectType{
    Software, Mechanical, Civil, Chemical
}

enum LocationType{
    onsite, remote, flexible
}

enum TravelRequirements{
    none,
    occasional,
    frequent
}

enum ContractType{
    permanent, temporary, contract
}

public class EngineeringJob extends Job{

    ProjectType projectType;
    LocationType locationType;
    TravelRequirements travelRequirements;
    ContractType contractType;

    public EngineeringJob(String title, String jobDesc, int salary, ArrayList<String> benefits, String employerName, ProjectType projectType, LocationType locationType, ContractType contractType, TravelRequirements travelRequirements){

        super(title, jobDesc, salary, benefits, employerName);
        this.projectType = projectType;
        this.locationType = locationType;
        this.travelRequirements = travelRequirements;
        this.contractType = contractType;

    }

    // Getters!
    public ProjectType getProjectType() {
        return projectType;
    }
    public LocationType getLocationType() {
        return locationType;
    }
    public TravelRequirements getTravelRequirements() {
        return travelRequirements;
    }
    public ContractType getContractType() {
        return contractType;
    }

    // Setters!
    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }
    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }
    public void setTravelRequirements(TravelRequirements travelRequirements) {
        this.travelRequirements = travelRequirements;
    }
    public void setContractType(ContractType contractType) {
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
