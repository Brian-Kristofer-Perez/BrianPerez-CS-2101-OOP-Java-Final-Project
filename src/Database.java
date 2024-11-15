import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private Connection connection;

    // connect to database!
    public Database(){
        try{
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean searchWorker(String name, String password){

        boolean isFound = false;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM workers WHERE name = ? AND password = ?;");
            query.setString(1, name);
            query.setString(2, password);
            isFound = query.executeQuery().next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return isFound;
    }


    // overloading the method! the first one takes name and password, and this one takes only the name.
    public boolean searchWorker(String name){

        boolean isFound = false;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM workers WHERE name = ?;");
            query.setString(1, name);
            isFound = query.executeQuery().next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return isFound;
    }

    // validating the login!
    public boolean searchEmployer(String name, String password){

        boolean isFound = false;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM employers WHERE name = ? AND password = ?;");
            query.setString(1, name);
            query.setString(2, password);
            isFound = query.executeQuery().next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return isFound;
    }

    // overload! first one is for validating the login, this one is just for registration, to prevent name conflict
    public boolean searchEmployer(String name){

        boolean isFound = false;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM employers WHERE name = ?");
            query.setString(1, name);
            isFound = query.executeQuery().next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return isFound;
    }

    public void addWorker(String name, String password){

        try {

            // Add the workers themselves
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO workers (name, password) VALUES (?, ?);");
            statement.setString(1, name);
            statement.setString(2, password);
            statement.executeUpdate();

            // Get workerID of the newly added worker;
            int workerID = queryWorkerID(name);

            // then ADD THEIR RESUMES AS EMPTY (sorry for screaming, I am very sleepy and need this energy to wake up!!!!)
            PreparedStatement autoResume = this.connection.prepareStatement("INSERT INTO resumes (idWorker) VALUES (?); ");
            autoResume.setInt(1, workerID);
            autoResume.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void addEmployer(String name, String password){

        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO employers (name, password) VALUES (?, ?);");
            statement.setString(1, name);
            statement.setString(2, password);
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public int queryWorkerID(String name){

        ResultSet resultSet = null;
        int workerID = 0;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM workers WHERE name = ?;");
            query.setString(1, name);
            resultSet = query.executeQuery();
            resultSet.next();

            workerID = resultSet.getInt("idWorker");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return workerID;
    };

    public int queryEmployerID(String name){

        ResultSet resultSet = null;
        int employerID = 0;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM employers WHERE name = ?;");
            query.setString(1, name);
            resultSet = query.executeQuery();
            resultSet.next();

            employerID = resultSet.getInt("idEmployer");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return employerID;
    }

    // checks if a user with a specific name has a resume in the database!
    public boolean checkResume(String workerName){

        boolean found = false;

        return true;
    }

    public Resume loadResume(String name){

        int workerID = queryWorkerID(name);
        Resume resume = new Resume();

        try {

            // get the resume of the person from resume table
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM resumes WHERE idWorker = ?");
            statement.setInt(1, workerID);

            ResultSet resultSet = statement.executeQuery();

            // get id of the queried resume, to get other related stuff.
            resultSet.next();
            int resumeID = resultSet.getInt("idResume");

            // get certifications
            PreparedStatement getCerts = connection.prepareStatement("SELECT * FROM certifications WHERE idResume = ?");
            getCerts.setInt(1, resumeID);
            ResultSet certificationSet = getCerts.executeQuery();

            // get workExperience
            PreparedStatement getExp = connection.prepareStatement("SELECT * FROM workexperience WHERE idResume = ?");
            getExp.setInt(1, resumeID);
            ResultSet experienceSet = getExp.executeQuery();


            // now set it on that resume! (and handle edge case, if the resume has no summary and summary is null)
            resume.setSummary(resultSet.getString("Summary") == null ? "" :resultSet.getString("Summary"));

            while(certificationSet.next()){
                resume.addCertification(certificationSet.getString("certificationName"));
            };

            while(experienceSet.next()){
                resume.addExperience(experienceSet.getString("experience"));
            };

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resume;
    }


    public void saveResume(Worker worker, Resume resume){

        int workerID = queryWorkerID(worker.getName());

        try {
            // insert into resumes
            PreparedStatement statement = connection.prepareStatement("INSERT INTO resumes (idWorker, summary) VALUES (?, ?)");
            statement.setInt(1, workerID);
            statement.setString(2, resume.getSummary());

            // insert into certifications
            for(String i: resume.getCertifications()){

            }

            // insert into workexperience
            for(String i: resume.getExperience()){

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void addPosting(String name, Job job){

        try {

            // get the employer's ID
            int employerID = queryEmployerID(name);

            // add the job to jobs table
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO jobs (salary, description, idEmployer, title) VALUES (?, ?, ?,?);");
            statement.setInt(1, job.getSalary());
            statement.setString(2, job.getJobDesc());
            statement.setInt(3, employerID);
            statement.setString(4, job.getJobTitle());
            statement.executeUpdate();

            // get the job ID of the newly added job.
            PreparedStatement getID = this.connection.prepareStatement("SELECT * FROM jobs WHERE salary = ? AND description = ? AND idEmployer = ? AND title = ?;");
            getID.setInt(1, job.getSalary());
            getID.setString(2, job.getJobDesc());
            getID.setInt(3, employerID);
            getID.setString(4, job.getJobTitle());
            ResultSet resultSet = getID.executeQuery();

            // move the pointer forward, and get the job ID
            resultSet.next();
            int jobID = resultSet.getInt("idJob");

            // add benefits to benefits table, and link to jobs
            for(String benefit: job.getBenefits()) {
                PreparedStatement addToBenefits = this.connection.prepareStatement("INSERT INTO benefits (idJob, benefit) VALUES (?, ?);");
                addToBenefits.setInt(1, jobID);
                addToBenefits.setString(2, benefit);
                addToBenefits.executeUpdate();
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public ArrayList<Job> queryJobs(String name){

        // get employer id
        int employerID = queryEmployerID(name);
        HashMap<Integer, Job> jobMap = new HashMap<Integer, Job>();

        // query all jobs that have employerID as employer
        try {

            // get all jobID's that belong to that employer
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM jobs LEFT JOIN benefits on jobs.idJob = benefits.idJob WHERE idEmployer = ?;");
            statement.setInt(1, employerID);

            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer id = resultSet.getInt("idJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(id)){

                    Job job = jobMap.get(id);

                    job.addBenefits(resultSet.getString("benefit"));

                    jobMap.replace(id, job);
                }

                // else, add it to the hashmap
                else{

                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int salary = resultSet.getInt("salary");
                    ArrayList<String> benefits = new ArrayList<String>();
                    Job job = new Job(title, description, salary, benefits);
                    job.addBenefits(resultSet.getString("benefit"));

                    jobMap.put(id, job);
                }

            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        // pass it to a return arraylist
        ArrayList<Job> jobs = new ArrayList<Job>(jobMap.values());

        return jobs;
    }


    public void deleteJob(Job job, String employerName){

        // get employer id
        int employerID = queryEmployerID(employerName);

        try {

            // get jobID from traits

            PreparedStatement getJobID = connection.prepareStatement("SELECT * FROM jobs WHERE title = ? AND description = ? AND salary = ? AND idEmployer = ?");
            getJobID.setString(1, job.getJobTitle());
            getJobID.setString(2, job.getJobDesc());
            getJobID.setInt(3, job.getSalary());
            getJobID.setInt(4, employerID);
            ResultSet resultSet = getJobID.executeQuery();

            resultSet.next();

            int jobID = resultSet.getInt("idJob");


            // delete from jobs table
            PreparedStatement statement = connection.prepareStatement("DELETE FROM jobs WHERE idJob = ?");
            statement.setInt(1, jobID);
            statement.executeUpdate();

            // delete from benefits table
            PreparedStatement removeFromBenefits = connection.prepareStatement("DELETE FROM benefits WHERE idJob = ?");
            removeFromBenefits.setInt(1, jobID);
            removeFromBenefits.executeUpdate();

            // delete from applications
            PreparedStatement removeFromApplications = connection.prepareStatement("DELETE FROM applications WHERE idJob = ?");
            removeFromApplications.setInt(1, jobID);
            removeFromApplications.executeUpdate();


        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }


    public ArrayList<Job> queryAllPostings(){

        HashMap<Integer, Job> jobMap = new HashMap<Integer, Job>();

        // query all jobs that have employerID as employer
        try {

            // get all jobID's
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM jobs LEFT JOIN benefits on jobs.idJob = benefits.idJob;");

            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer id = resultSet.getInt("idJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(id)){

                    Job job = jobMap.get(id);
                    job.addBenefits(resultSet.getString("benefit"));
                    jobMap.replace(id, job);
                }

                // else, add it to the hashmap
                else{

                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int salary = resultSet.getInt("salary");
                    ArrayList<String> benefits = new ArrayList<String>();
                    Job job = new Job(title, description, salary, benefits);
                    job.addBenefits(resultSet.getString("benefit"));

                    jobMap.put(id, job);
                }

            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        // pass it to a return arraylist
        ArrayList<Job> jobs = new ArrayList<Job>(jobMap.values());

        return jobs;
    }

    public void updateResume(String workerName, String newSummary, ArrayList<String> newExperience, ArrayList<String> newCertifications){

        try {
            // get the workerID, then use it to get ResumeID
            int idWorker = queryWorkerID(workerName);
            PreparedStatement getResumeSet = connection.prepareStatement("SELECT * FROM resumes WHERE idWorker = ?");
            getResumeSet.setInt(1, idWorker);

            ResultSet resumeSet = getResumeSet.executeQuery();
            resumeSet.next(); // I almost forgot! :)
            int idResume = resumeSet.getInt("idResume");


            // update the resume (summary only) that's already IN the database
            PreparedStatement changeResume = connection.prepareStatement("UPDATE resumes SET summary = ? WHERE idWorker = ?");
            changeResume.setString(1, newSummary);
            changeResume.setInt(2, idWorker);
            changeResume.executeUpdate();

            // clear all previous related certifications related to that resume
            PreparedStatement clearCert = connection.prepareStatement("DELETE FROM certifications WHERE idResume = ?");
            clearCert.setInt(1, idResume);
            clearCert.executeUpdate();

            // clear all previous related experience related to that resume
            PreparedStatement clearExp = connection.prepareStatement("DELETE FROM workexperience WHERE idResume = ?");
            clearExp.setInt(1, idResume);
            clearExp.executeUpdate();

            // insert all previous related cert
            PreparedStatement insertCert = connection.prepareStatement("INSERT INTO certifications (idResume, certificationName) VALUES (?, ?)");
            insertCert.setInt(1, idResume);
            for(String certification : newCertifications){
                insertCert.setString(2, certification);
                insertCert.executeUpdate();
            }

            // insert all previous related experience
            PreparedStatement insertExp = connection.prepareStatement("INSERT INTO workexperience (idResume, experience) VALUES (?, ?)");
            insertExp.setInt(1, idResume);
            for(String experience : newExperience){
                insertExp.setString(2, experience);
                insertExp.executeUpdate();
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int queryJobID(Job job){

        int idJob = 0;

        // get job id
        try {
            PreparedStatement getJobID = connection.prepareStatement("SELECT * FROM jobs WHERE title = ? AND description = ? AND salary = ?");
            getJobID.setString(1, job.getJobTitle());
            getJobID.setString(2, job.getJobDesc());
            getJobID.setInt(3, job.getSalary());
            ResultSet jobSet = getJobID.executeQuery();
            jobSet.next();
            idJob = jobSet.getInt("idJob");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return idJob;
    }

    public boolean applicationExists(int idWorker, int idJob){
        // check if the current application (idWorker, idJob) already exists
        try {
            PreparedStatement getApplicationSet = connection.prepareStatement("SELECT * FROM applications WHERE idWorker = ? AND idJob = ?");
            getApplicationSet.setInt(1, idWorker);
            getApplicationSet.setInt(2, idJob);
            ResultSet applicationSet = getApplicationSet.executeQuery();

            // if applications exists,
            if(applicationSet.next()){
                return true;
            }
            // else,
            else{
                return false;
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public void createApplication(String workerName, Job job){

        // get worker id
        int idWorker = queryWorkerID(workerName);
        int idJob = queryJobID(job);

        try {
            // insert into applications
            PreparedStatement addApplication = connection.prepareStatement("INSERT INTO applications (idWorker, idJob) VALUES (?, ?)");
            addApplication.setInt(1, idWorker);
            addApplication.setInt(2, idJob);
            addApplication.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
}

