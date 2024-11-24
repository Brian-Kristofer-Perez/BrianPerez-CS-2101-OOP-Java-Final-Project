package Database;

import Jobs.EngineeringJob;
import Jobs.Job;
import Jobs.MedicalJob;
import Users.Employer;
import Users.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EngineeringDAO extends JobDAO{

    private Connection connection;

    // This is the Data Access Object (DAO) of the EngineeringJob class.
    // This should handle the database operations related to the said class

    // connect to database!
    public EngineeringDAO(){
        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Job to Database
    public void addJob(EngineeringJob job, int employerID){
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO engineeringjobs (salary, description, idEmployer, title, projectType, locationType, travelRequirements, contractType) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            statement.setInt(1, job.getSalary());
            statement.setString(2, job.getJobDesc());
            statement.setInt(3, employerID);
            statement.setString(4, job.getJobTitle());
            statement.setString(5, job.getProjectType());
            statement.setString(6, job.getLocationType());
            statement.setString(7, job.getTravelRequirements());
            statement.setString(8, job.getContractType());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    // Add benefit to Database
    public void addBenefit(int idJob, String benefit){
        try {
            PreparedStatement addToBenefits = this.connection.prepareStatement("INSERT INTO engineeringbenefits (idEngineeringJob, benefit) VALUES (?, ?);");
            addToBenefits.setInt(1, idJob);
            addToBenefits.setString(2, benefit);
            addToBenefits.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteJob(Job job){

        try {
            // We take a generic type to override the parent function,
            // but we will immediately downcast it to the sub type for specific implementation
            EngineeringJob engineeringJob = (EngineeringJob) job;

            // get jobID from traits
            int jobID = queryJobID(engineeringJob);

            // delete from jobs table
            PreparedStatement statement = connection.prepareStatement("DELETE FROM engineeringjobs WHERE idEngineeringJob = ?");
            statement.setInt(1, jobID);
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int queryJobID(Job genericJob){

        // We take a generic type to override the parent function,
        // but we will immediately downcast it to the sub type for specific implementation
        EngineeringJob job = (EngineeringJob) genericJob;

        int idJob = 0;

        // get job id
        try {
            PreparedStatement getJobID = connection.prepareStatement("SELECT * FROM engineeringjobs WHERE title = ? AND description = ? AND salary = ? AND projectType = ? AND locationType = ? AND travelRequirements = ? AND contractType = ?");
            getJobID.setString(1, job.getJobTitle());
            getJobID.setString(2, job.getJobDesc());
            getJobID.setInt(3, job.getSalary());
            getJobID.setString(4, job.getProjectType());
            getJobID.setString(5, job.getLocationType());
            getJobID.setString(6, job.getTravelRequirements());
            getJobID.setString(7, job.getContractType());
            ResultSet jobSet = getJobID.executeQuery();
            jobSet.next();
            idJob = jobSet.getInt("idEngineeringJob");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return idJob;
    }

    public void addPosting(String name, EngineeringJob job){

        EmployerDAO employerDAO = new EmployerDAO();

        int employerID = employerDAO.queryEmployerID(name);
        addJob(job, employerID);

        int jobID = queryJobID(job);

        // add benefits to benefits table, and link to jobs
        for(String benefit: job.getBenefits()) {
            addBenefit(jobID, benefit);
        }

    }

    // get the list of workers that are applying for this job
    public ArrayList<Worker> queryApplicants(Job job){

        WorkerDAO workerDAO = new WorkerDAO();
        int idJob = queryJobID(job);
        ArrayList<Worker> applications = new ArrayList<>();
        ArrayList<Integer> workerIDList = new ArrayList<>();

        try {

            // query all applications for that specific job ID
            PreparedStatement getAllApps = connection.prepareStatement("SELECT * FROM engineeringapplications WHERE idEngineeringJob = ?");
            getAllApps.setInt(1, idJob);
            ResultSet workerIDSet = getAllApps.executeQuery();

            while(workerIDSet.next()){
                Integer workerID = workerIDSet.getInt("idWorker");
                workerIDList.add(workerID);
            }

            // get necessary details per workerID (worker name, and resume), and construct an application
            for(int i: workerIDList){
                Worker newWorker = workerDAO.getWorkerFromID(i);
                applications.add(newWorker);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return applications;
    }

    public EngineeringJob jobFromID(int idJob){

        EmployerDAO employerDAO = new EmployerDAO();
        EngineeringJob job = null;
        try {
            PreparedStatement getJob = connection.prepareStatement("SELECT * FROM engineeringjobs WHERE idEngineeringJob = ?");
            getJob.setInt(1, idJob);

            ResultSet jobRS = getJob.executeQuery();
            jobRS.next();

            String title = jobRS.getString("title");
            String description = jobRS.getString("description");
            int salary = jobRS.getInt("salary");
            int idEmployer = jobRS.getInt("idEmployer");
            Employer employer = employerDAO.queryEmployerFromID(idEmployer);
            ArrayList<String> benefits = new ArrayList<>();

            String projectType = jobRS.getString("projectType");
            String locationType = jobRS.getString("locationType");
            String travelRequirements = jobRS.getString("travelRequirements");
            String contractType = jobRS.getString("contractType");

            job = new EngineeringJob(title, description, salary, benefits, employer.getName(), projectType, locationType, travelRequirements, contractType);

            PreparedStatement getBenefits = connection.prepareStatement("SELECT * FROM engineeringbenefits WHERE idEngineeringJob = ?");
            getBenefits.setInt(1, idJob);
            ResultSet benefitsRS = getBenefits.executeQuery();

            while(benefitsRS.next()){
                job.addBenefits(benefitsRS.getString("benefit"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return job;
    }

    // view ALL owned jobs, open or not
    public ArrayList<Job> queryAllOwnedPostings(String employerName){

        HashMap<Integer, EngineeringJob> jobMap = new HashMap<>();

        EmployerDAO employerDAO = new EmployerDAO();
        int idEmployer = employerDAO.queryEmployerID(employerName);

        try {

            // get all jobID's
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM engineeringjobs LEFT JOIN engineeringbenefits on engineeringjobs.idEngineeringJob = engineeringbenefits.idEngineeringJob WHERE idEmployer = ?;");
            statement.setInt(1, idEmployer);
            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer idJob = resultSet.getInt("idEngineeringJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(idJob)){

                    EngineeringJob job = jobMap.get(idJob);
                    job.addBenefits(resultSet.getString("benefit"));
                    jobMap.replace(idJob, job);
                }

                // else, add it to the hashmap
                else{

                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int salary = resultSet.getInt("salary");
                    String projectType = resultSet.getString("projectType");
                    String locationType = resultSet.getString("locationType");
                    String travelRequirements = resultSet.getString("travelRequirements");
                    String contractType = resultSet.getString("contractType");

                    ArrayList<String> benefits = new ArrayList<>();
                    EngineeringJob job = new EngineeringJob(title, description, salary, benefits, employerName, projectType, locationType, travelRequirements, contractType);
                    job.addBenefits(resultSet.getString("benefit"));

                    jobMap.put(idJob, job);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // pass it to a return arraylist
        return new ArrayList<>(jobMap.values());
    }

    // query All open postings, regardless of ownership
    public ArrayList<Job> queryAllOpenPostings(){

        EmployerDAO employerDAO = new EmployerDAO();
        HashMap<Integer, EngineeringJob> jobMap = new HashMap<>();

        // query all engineering jobs
        try {

            // get all jobID's that belong in engineering, and filter out the ones that are occupied already
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM engineeringjobs LEFT JOIN engineeringbenefits ON engineeringjobs.idEngineeringJob = engineeringbenefits.idEngineeringJob WHERE NOT EXISTS (SELECT * FROM engineeringoccupations WHERE engineeringoccupations.idEngineeringJob = engineeringjobs.idEngineeringJob)");
            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer id = resultSet.getInt("idEngineeringJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(id)){

                    EngineeringJob job = jobMap.get(id);
                    job.addBenefits(resultSet.getString("benefit"));
                    jobMap.replace(id, job);
                }

                // else, add it to the hashmap
                else{

                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int salary = resultSet.getInt("salary");
                    String projectType = resultSet.getString("projectType");
                    String locationType = resultSet.getString("locationType");
                    String travelRequirements = resultSet.getString("travelRequirements");
                    String contractType = resultSet.getString("contractType");
                    ArrayList<String> benefits = new ArrayList<>();

                    // slightly more complex logic, to query the employerName
                    int idEmployer = resultSet.getInt("idEmployer");
                    String employerName = employerDAO.queryEmployerFromID(idEmployer).getName();


                    EngineeringJob job = new EngineeringJob(title, description, salary, benefits, employerName, projectType,locationType, travelRequirements,contractType);
                    job.addBenefits(resultSet.getString("benefit"));

                    jobMap.put(id, job);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // pass it to a return arraylist
        return new ArrayList<>(jobMap.values());
    }

    // query owned open
    public ArrayList<Job> queryOwnedOpenPostings(String employerName){

        EmployerDAO employerDAO = new EmployerDAO();

        // get employer id
        int employerID = employerDAO.queryEmployerID(employerName);
        HashMap<Integer, EngineeringJob> jobMap = new HashMap<>();

        // query all jobs that have employerID as employer
        try {

            // get all jobID's that belong to that employer, and filter out the ones that are occupied already
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM engineeringjobs LEFT JOIN engineeringbenefits ON engineeringjobs.idEngineeringJob = engineeringbenefits.idEngineeringJob WHERE idEmployer = ? AND NOT EXISTS (SELECT * FROM engineeringoccupations WHERE engineeringoccupations.idEngineeringJob = engineeringjobs.idEngineeringJob)");
            statement.setInt(1, employerID);

            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer id = resultSet.getInt("idEngineeringJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(id)){

                    EngineeringJob job = jobMap.get(id);
                    job.addBenefits(resultSet.getString("benefit"));
                    jobMap.replace(id, job);
                }

                // else, add it to the hashmap
                else{

                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int salary = resultSet.getInt("salary");
                    String projectType = resultSet.getString("projectType");
                    String locationType = resultSet.getString("locationType");
                    String travelRequirements = resultSet.getString("travelRequirements");
                    String contractType = resultSet.getString("contractType");
                    ArrayList<String> benefits = new ArrayList<>();
                    EngineeringJob job = new EngineeringJob(title, description, salary, benefits, employerName, projectType,locationType, travelRequirements,contractType);
                    job.addBenefits(resultSet.getString("benefit"));

                    jobMap.put(id, job);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // pass it to a return arraylist
        return new ArrayList<>(jobMap.values());
    }
}
