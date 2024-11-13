import java.sql.Connection;
import java.util.ArrayList;

public class Resume {

    // Personal Information
//    private String name;
//    private String jobTitle;
//    private String email;
//    private String phone;

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

    public void display(){

        System.out.print("");

        /*
        ==========================
              Your Name
            Software Developer
        ==========================

        Email: your.email@example.com
        Phone: (123) 456-7890
        Location: City, State
        GitHub: github.com/yourusername
        LinkedIn: linkedin.com/in/yourusername

        --------------------------
          SUMMARY
        --------------------------

        Passionate software developer with experience in building web applications,
        back-end services, and optimizing database performance. Skilled in Java, Python,
        and SQL. Looking to contribute to innovative projects and collaborate in dynamic teams.

        --------------------------
          TECHNICAL SKILLS
        --------------------------

        Languages: Java, Python, SQL, JavaScript
        Frameworks: Spring Boot, Django, React
        Databases: MySQL, PostgreSQL, MongoDB
        Tools: Git, Docker, Jenkins
        Cloud: AWS, Google Cloud

        --------------------------
          EXPERIENCE
        --------------------------

        Software Developer | Company Name, City, State | Jan 2022 - Present
        - Developed and optimized back-end services using Java and Spring Boot.
        - Built RESTful APIs for mobile app integrations, increasing efficiency by 25%.
        - Managed SQL database performance, reducing query load times by 30%.

        Junior Developer | Another Company, City, State | Jun 2020 - Dec 2021
        - Worked on full-stack development, focusing on front-end design with React.
        - Built and maintained database schemas for customer data management.

        --------------------------
          PROJECTS
        --------------------------

        Employee Management System | GitHub: github.com/yourusername/project
        - Full-stack web application to manage employee data.
        - Backend: Java (Spring Boot), Frontend: React, Database: MySQL.

        Task Manager App | GitHub: github.com/yourusername/project
        - Web app for task tracking, implemented user authentication and data syncing.
        - Technologies used: Node.js, Express, MongoDB.

        --------------------------
          EDUCATION
        --------------------------

        B.S. in Computer Science | University Name | Graduated: May 2020
        - Relevant Coursework: Data Structures, Algorithms, Database Management

        --------------------------
          CERTIFICATIONS
        --------------------------

        AWS Certified Developer | Amazon Web Services | 2023

        ==========================
        */


    }


}
