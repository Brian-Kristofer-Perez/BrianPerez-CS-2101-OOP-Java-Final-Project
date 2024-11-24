package Database;

import Jobs.*;
import Users.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ManagementDAO extends JobDAO{

    private Connection connection;

    // This is the Data Access Object (DAO) of the ManagementJob class.
    // This should handle the database operations related to the said class

    // connect to database!
    public ManagementDAO(){
        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Job to Database
    public void addJob(ManagementJob job, int employerID){
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO managementjobs (title, description, salary, idEmployer, teamSize, leadershipLevel, department) VALUES (?, ?, ?, ?, ?, ?, ?);");
            statement.setString(1, job.getJobTitle());
            statement.setString(2, job.getJobDesc());
            statement.setInt(3, job.getSalary());
            statement.setInt(4, employerID);
            statement.setInt(5, job.getTeamSize());
            statement.setString(6, job.getLeadershipLevel());
            statement.setString(7, job.getDepartment());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    // Add benefit to Database
    public void addBenefit(int idJob, String benefit){
        try {
            PreparedStatement addToBenefits = this.connection.prepareStatement("INSERT INTO managementbenefits (idManagementJob, benefit) VALUES (?, ?);");
            addToBenefits.setInt(1, idJob);
            addToBenefits.setString(2, benefit);
            addToBenefits.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteJob(ManagementJob job){

        try {
            // get jobID from traits
            int jobID = queryJobID(job);

            // delete from jobs table
            PreparedStatement statement = connection.prepareStatement("DELETE FROM managementjobs WHERE idManagementJob = ?");
            statement.setInt(1, jobID);
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int queryJobID(ManagementJob job){

        int idJob = 0;

        // get job id
        try {
            PreparedStatement getJobID = connection.prepareStatement("SELECT * FROM managementjobs WHERE title = ? AND description = ? AND salary = ? AND teamSize = ? AND leadershipLevel = ? AND department = ?");
            getJobID.setString(1, job.getJobTitle());
            getJobID.setString(2, job.getJobDesc());
            getJobID.setInt(3, job.getSalary());
            getJobID.setInt(4, job.getTeamSize());
            getJobID.setString(5, job.getLeadershipLevel());
            getJobID.setString(6, job.getDepartment());
            ResultSet jobSet = getJobID.executeQuery();
            jobSet.next();
            idJob = jobSet.getInt("idManagementJob");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return idJob;
    }

    public void addPosting(String name, ManagementJob job){

        EmployerDAO employerDAO = new EmployerDAO();

        int employerID = employerDAO.queryEmployerID(name);
        addJob(job, employerID);

        int jobID = queryJobID(job);

        // add benefits to benefits table, and link to jobs
        for(String benefit: job.getBenefits()) {
            addBenefit(jobID, benefit);
        }

    }

    // view ALL jobs, both occupied and not (management only)
    public ArrayList<Job> queryAllPostings(){

        HashMap<Integer, ManagementJob> jobMap = new HashMap<>();

        try {

            // get all jobID's
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM managementjobs LEFT JOIN managementbenefits on managementjobs.idManagementJob = managementbenefits.idManagementJob;");
            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer idJob = resultSet.getInt("idManagementJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(idJob)){

                    ManagementJob job = jobMap.get(idJob);
                    job.addBenefits(resultSet.getString("benefit"));
                    jobMap.replace(idJob, job);
                }

                // else, add it to the hashmap
                else{
                    EmployerDAO employerDAO = new EmployerDAO();

                    int idEmployer = resultSet.getInt("idEmployer");
                    Employer employer = employerDAO.queryEmployerFromID(idEmployer);

                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int salary = resultSet.getInt("salary");
                    int teamSize = resultSet.getInt("teamSize");
                    String leadershipLevel = resultSet.getString("leadershipLevel");
                    String department = resultSet.getString("department");


                    ArrayList<String> benefits = new ArrayList<>();
                    ManagementJob job = new ManagementJob(title, description, salary, benefits, employer.getName(), teamSize, department, leadershipLevel);
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

    // get the list of workers that are applying for this job
    public ArrayList<Worker> queryApplicants(ManagementJob job){

        WorkerDAO workerDAO = new WorkerDAO();
        int idJob = queryJobID(job);
        ArrayList<Worker> applications = new ArrayList<>();
        ArrayList<Integer> workerIDList = new ArrayList<>();

        try {

            // query all applications for that specific job ID
            PreparedStatement getAllApps = connection.prepareStatement("SELECT * FROM managementapplications WHERE idManagementJob = ?");
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

    public ManagementJob jobFromID(int idJob){

        EmployerDAO employerDAO = new EmployerDAO();
        ManagementJob job = null;
        try {
            PreparedStatement getJob = connection.prepareStatement("SELECT * FROM managementjobs WHERE idManagementJob = ?");
            getJob.setInt(1, idJob);

            ResultSet jobRS = getJob.executeQuery();
            jobRS.next();

            String title = jobRS.getString("title");
            String description = jobRS.getString("description");
            int salary = jobRS.getInt("salary");
            int idEmployer = jobRS.getInt("idEmployer");
            Employer employer = employerDAO.queryEmployerFromID(idEmployer);
            ArrayList<String> benefits = new ArrayList<>();
            int teamSize = jobRS.getInt("teamSize");
            String department = jobRS.getString("department");
            String leadershipLevel = jobRS.getString("leadershipLevel");

            job = new ManagementJob(title, description, salary, benefits, employer.getName(), teamSize, department, leadershipLevel);

            PreparedStatement getBenefits = connection.prepareStatement("SELECT * FROM managementbenefits WHERE idManagementJob = ?");
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

    // query ALL open postings only.
    public ArrayList<Job> queryOpenPostings(String employerName){

        EmployerDAO employerDAO = new EmployerDAO();

        // get employer id
        int employerID = employerDAO.queryEmployerID(employerName);
        HashMap<Integer, ManagementJob> jobMap = new HashMap<>();

        // query all jobs that have employerID as employer
        try {

            // get all jobID's that belong to that employer, and filter out the ones that are occupied already
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM managementjobs LEFT JOIN managementbenefits ON managementjobs.idManagementJob = managementbenefits.idManagementJob WHERE idEmployer = ? AND NOT EXISTS (SELECT * FROM managementoccupations WHERE managementoccupations.idManagementJob = managementjobs.idManagementJob)");
            statement.setInt(1, employerID);

            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer id = resultSet.getInt("idManagementJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(id)){

                    ManagementJob job = jobMap.get(id);
                    job.addBenefits(resultSet.getString("benefit"));
                    jobMap.replace(id, job);
                }

                // else, add it to the hashmap
                else{

                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int salary = resultSet.getInt("salary");
                    int teamSize = resultSet.getInt("teamSize");
                    String leadershipLevel = resultSet.getString("leadershipLevel");
                    String department = resultSet.getString("department");
                    ArrayList<String> benefits = new ArrayList<>();

                    ManagementJob job = new ManagementJob(title, description, salary, benefits, employerName, teamSize, department, leadershipLevel);
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
