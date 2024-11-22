package Database;

import Documents.Job;
import Users.Employer;
import Users.Worker;

import java.sql.*;
import java.util.ArrayList;


public class JobDAO {
    private Connection connection;

    // connect to database!
    public JobDAO(){
        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Job to Database
    public void addJob(Job job, int employerID){
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO jobs (salary, description, idEmployer, title) VALUES (?, ?, ?,?);");
            statement.setInt(1, job.getSalary());
            statement.setString(2, job.getJobDesc());
            statement.setInt(3, employerID);
            statement.setString(4, job.getJobTitle());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    // Add benefit to Database
    public void addBenefit(int idJob, String benefit){
        try {
            PreparedStatement addToBenefits = this.connection.prepareStatement("INSERT INTO benefits (idJob, benefit) VALUES (?, ?);");
            addToBenefits.setInt(1, idJob);
            addToBenefits.setString(2, benefit);
            addToBenefits.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteJob(Job job){

        try {
            // get jobID from traits
            int jobID = queryJobID(job);

            // delete from jobs table
            PreparedStatement statement = connection.prepareStatement("DELETE FROM jobs WHERE idJob = ?");
            statement.setInt(1, jobID);
            statement.executeUpdate();
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


    // For JobService
    public void addPosting(String name, Job job){

        EmployerDAO employerDAO = new EmployerDAO();

        int employerID = employerDAO.queryEmployerID(name);
        addJob(job, employerID);

        int jobID = queryJobID(job);

        // add benefits to benefits table, and link to jobs
        for(String benefit: job.getBenefits()) {
            addBenefit(jobID, benefit);
        }

    }

    // get the list of workers tha are applying
    public ArrayList<Worker> queryApplications(Job job){

        WorkerDAO workerDAO = new WorkerDAO();
        int idJob = queryJobID(job);
        ArrayList<Worker> applications = new ArrayList<>();
        ArrayList<Integer> workerIDList = new ArrayList<>();

        try {

            // query all applications for that specific job ID
            PreparedStatement getAllApps = connection.prepareStatement("SELECT * FROM APPLICATIONS WHERE idJob = ?");
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

    public Job jobFromID(int idJob){

        EmployerDAO employerDAO = new EmployerDAO();
        Job job = null;
        try {
            PreparedStatement getJob = connection.prepareStatement("SELECT * FROM jobs WHERE idJob = ?");
            getJob.setInt(1, idJob);

            ResultSet jobRS = getJob.executeQuery();
            jobRS.next();

            String title = jobRS.getString("title");
            String description = jobRS.getString("description");
            int salary = jobRS.getInt("salary");
            int idEmployer = jobRS.getInt("idEmployer");
            Employer employer = employerDAO.queryEmployerFromID(idEmployer);
            ArrayList<String> benefits = new ArrayList<>();

            job = new Job(title, description, salary, benefits, employer.getName());

            PreparedStatement getBenefits = connection.prepareStatement("SELECT * FROM benefits WHERE idJob = ?");
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

}
