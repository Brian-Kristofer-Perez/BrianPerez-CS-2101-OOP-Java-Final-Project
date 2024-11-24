package Users;
import java.util.ArrayList;

public class Resume {

    // Resume Content
    private String summary = "";
    private ArrayList<String> experience = new ArrayList<String>();
    private ArrayList<String> certifications = new ArrayList<String>();


    // constructors!
    public Resume(){}

    public Resume(String summary, ArrayList<String> experience, ArrayList<String> certifications){
        this.summary = summary;
        this.experience = experience;
        this.certifications = certifications;
    }

    // Setters, for editing!
    public void setSummary(String summary){
        this.summary = summary;
    }
    public void setExperience(ArrayList<String> experience){
        this.experience = experience;
    }
    public void setCertifications(ArrayList<String> certifications){
        this.certifications = certifications;
    }

    // other modifiers for private stuff
    public void addExperience(String experience){
        this.experience.add(experience);
    }
    public void addCertification(String certification){
        this.certifications.add(certification);
    }

    // Getters
    public String getSummary(){
        return this.summary;
    }
    public ArrayList<String> getExperience(){
        return this.experience;
    }
    public ArrayList<String> getCertifications(){
        return this.certifications;
    }

}

