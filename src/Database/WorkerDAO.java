package Database;

import Jobs.Job;
import Jobs.Resume;
import Users.Worker;

import java.sql.*;
import java.util.ArrayList;

public class WorkerDAO {

    private Connection connection;

    // This is the Data Access Object (DAO) of the Worker class.
    // This should handle the database operations related to the worker class,
    public WorkerDAO(){
        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int queryWorkerID(String name){

        ResultSet resultSet;
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
    }

    public Worker getWorkerFromID(int idWorker){

        Worker worker = null;
        String workerName;
        try{

            PreparedStatement getWorkerDetails = connection.prepareStatement("SELECT * FROM workers WHERE idWorker = ?");
            getWorkerDetails.setInt(1, idWorker);

            ResultSet workerRS = getWorkerDetails.executeQuery();
            workerRS.next();

            workerName = workerRS.getString("name");
            worker = new Worker(workerName);

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return worker;
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

    // version used for login
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
    // version used for sign up
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
            }

            while(experienceSet.next()){
                resume.addExperience(experienceSet.getString("experience"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resume;
    }

    public boolean applicationExists(int idWorker, int idJob){
        // check if the current application (idWorker, idJob) already exists
        try {
            PreparedStatement getApplicationSet = connection.prepareStatement("SELECT * FROM applications WHERE idWorker = ? AND idJob = ?");
            getApplicationSet.setInt(1, idWorker);
            getApplicationSet.setInt(2, idJob);
            ResultSet applicationSet = getApplicationSet.executeQuery();

            // if applications exists,
            return applicationSet.next();

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean alreadyEmployed(int idWorker){

        try{
            PreparedStatement preparedStatement = connection.prepareStatement( "SELECT * FROM occupations WHERE idWorker = ?");
            preparedStatement.setInt(1, idWorker);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public String queryWorkerContactNo(String workerName) {

        int idWorker = queryWorkerID(workerName);
        String strNo = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM workers WHERE idWorker = ?");
            preparedStatement.setInt(1, idWorker);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                strNo = resultSet.getString("contactno");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return strNo;
    }

    public void setWorkerContactDetails(String newEmail, String newContact, String workerName){

        int idWorker = queryWorkerID(workerName);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE workers SET email = ?, contactno = ? WHERE idWorker = ?");
            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, newContact);
            preparedStatement.setInt(3, idWorker);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public String queryWorkerEmail(String workerName){

        int idWorker = queryWorkerID(workerName);
        String email = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM workers WHERE idWorker = ?");
            preparedStatement.setInt(1, idWorker);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                email = resultSet.getString("email");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return email;
    }


    public void hireWorker(Worker worker, Job job){

        JobDAO jobDAO = new JobDAO();
        int idWorker = queryWorkerID(worker.getName());
        int idJob = jobDAO.queryJobID(job);

        try{
            // hiring the worker
            PreparedStatement hireWorker = connection.prepareStatement("INSERT INTO occupations (idWorker, idJob) VALUES (?, ?)");
            hireWorker.setInt(1, idWorker);
            hireWorker.setInt(2, idJob);

            hireWorker.executeUpdate();

            // clearing ALL other applications once hired
            PreparedStatement clearApps = connection.prepareStatement("DELETE FROM applications WHERE idJob = ?");
            clearApps.setInt(1, idJob);
            clearApps.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateWorkerResume(String workerName, String newSummary, ArrayList<String> newExperience, ArrayList<String> newCertifications){

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

    public void createApplication(String workerName, Job job){

        JobDAO jobDAO = new JobDAO();
        int idWorker = queryWorkerID(workerName);
        int idJob = jobDAO.queryJobID(job);

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

    public Job loadOccupation(String workerName){

        int idWorker = queryWorkerID(workerName);
        Job outputJob = new Job();
        try {
            PreparedStatement isJob = connection.prepareStatement("SELECT * FROM occupations WHERE idWorker = ?");
            isJob.setInt(1, idWorker);
            ResultSet job = isJob.executeQuery();

            PreparedStatement isEngineering = connection.prepareStatement("SELECT * FROM engineeringoccupations WHERE idWorker = ?");
            isEngineering.setInt(1, idWorker);
            ResultSet engineering = isEngineering.executeQuery();

            PreparedStatement isMedical = connection.prepareStatement("SELECT * FROM medicaloccupations WHERE idWorker = ?");
            isMedical.setInt(1, idWorker);
            ResultSet medical = isMedical.executeQuery();

            PreparedStatement isManagement = connection.prepareStatement("SELECT * FROM managementoccupations WHERE idWorker = ?");
            isManagement.setInt(1, idWorker);
            ResultSet management = isManagement.executeQuery();

            if(management.next()){
                int idManagementJob = management.getInt("idManagementJob");
                ManagementDAO managementDAO = new ManagementDAO();
                outputJob = managementDAO.jobFromID(idManagementJob);
            }
            if(medical.next()){
                int idMedicalJob = medical.getInt("idMedicalJob");
                MedicalDAO medicalDAO = new MedicalDAO();
                outputJob = medicalDAO.jobFromID(idMedicalJob);
            }
            if(engineering.next()){
                int idEngineeringJob = engineering.getInt("idEngineeringJob");
                EngineeringDAO engineeringDAO = new EngineeringDAO();
                outputJob = engineeringDAO.jobFromID(idEngineeringJob);
            }

            if(job.next()){
                int idJob = job.getInt("idJob");
                JobDAO jobDAO = new JobDAO();
                outputJob = jobDAO.jobFromID(idJob);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return outputJob;
    }
}
