package Database;

import Jobs.*;
import Users.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MedicalDAO extends JobDAO{

    private Connection connection;

    // This is the Data Access Object (DAO) of the MedicalJob class.
    // This should handle the database operations related to the said class

    // connect to database!
    public MedicalDAO(){
        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add Job to Database
    public void addJob(Job genericJob, int employerID){

        MedicalJob job = (MedicalJob) genericJob;
        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO medicaljobs (salary, description, idEmployer, title, department, shift) VALUES (?, ?, ?, ?, ?, ?);");
            statement.setInt(1, job.getSalary());
            statement.setString(2, job.getJobDesc());
            statement.setInt(3, employerID);
            statement.setString(4, job.getJobTitle());
            statement.setString(5, job.getDepartment());
            statement.setString(6, job.getShiftType());
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    // Add benefit to Database
    public void addBenefit(int idJob, String benefit){
        try {
            PreparedStatement addToBenefits = this.connection.prepareStatement("INSERT INTO medicalbenefits (idMedicalJob, benefit) VALUES (?, ?);");
            addToBenefits.setInt(1, idJob);
            addToBenefits.setString(2, benefit);
            addToBenefits.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteJob(Job genericJob){

        // We take a generic type to override the parent function,
        // but we will immediately downcast it to the sub type for specific implementation
        MedicalJob job = (MedicalJob) genericJob;

        try {
            // get jobID from traits
            int jobID = queryJobID(job);

            // delete from jobs table
            PreparedStatement statement = connection.prepareStatement("DELETE FROM medicaljobs WHERE idMedicalJob = ?");
            statement.setInt(1, jobID);
            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int queryJobID(Job genericJob){

        int idJob = 0;

        // We take a generic type to override the parent function,
        // but we will immediately downcast it to the sub type for specific implementation
        MedicalJob job = (MedicalJob) genericJob;

        // get job id
        try {
            PreparedStatement getJobID = connection.prepareStatement("SELECT * FROM medicaljobs WHERE title = ? AND description = ? AND salary = ? AND shift = ? AND department = ?");
            getJobID.setString(1, job.getJobTitle());
            getJobID.setString(2, job.getJobDesc());
            getJobID.setInt(3, job.getSalary());
            getJobID.setString(4, job.getShiftType());
            getJobID.setString(5, job.getDepartment());
            ResultSet jobSet = getJobID.executeQuery();
            jobSet.next();
            idJob = jobSet.getInt("idMedicalJob");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return idJob;
    }

    public void addPosting(String name, MedicalJob job){

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
            PreparedStatement getAllApps = connection.prepareStatement("SELECT * FROM medicalapplications WHERE idMedicalJob = ?");
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

    public MedicalJob jobFromID(int idJob){

        EmployerDAO employerDAO = new EmployerDAO();
        MedicalJob job = null;
        try {
            PreparedStatement getJob = connection.prepareStatement("SELECT * FROM medicaljobs WHERE idMedicalJob = ?");
            getJob.setInt(1, idJob);

            ResultSet jobRS = getJob.executeQuery();
            jobRS.next();

            String title = jobRS.getString("title");
            String description = jobRS.getString("description");
            int salary = jobRS.getInt("salary");
            int idEmployer = jobRS.getInt("idEmployer");
            Employer employer = employerDAO.queryEmployerFromID(idEmployer);
            ArrayList<String> benefits = new ArrayList<>();

            String shift = jobRS.getString("shift");
            String department = jobRS.getString("department");

            job = new MedicalJob(title, description, salary, benefits, employer.getName(), department, shift);

            PreparedStatement getBenefits = connection.prepareStatement("SELECT * FROM medicalbenefits WHERE idMedicalJob = ?");
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


    public ArrayList<Job> queryAllOpenPostings(){

        HashMap<Integer, MedicalJob> jobMap = new HashMap<>();

        try {

            // get all jobID's
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicaljobs LEFT JOIN medicalbenefits on medicaljobs.idMedicalJob = medicalbenefits.idMedicalJob WHERE NOT EXISTS (SELECT * FROM medicaloccupations WHERE medicaloccupations.idMedicalJob = medicaljobs.idMedicalJob);");
            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer idJob = resultSet.getInt("idMedicalJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(idJob)){

                    MedicalJob job = jobMap.get(idJob);
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
                    String department = resultSet.getString("department");
                    String shift = resultSet.getString("shift");


                    ArrayList<String> benefits = new ArrayList<>();
                    MedicalJob job = new MedicalJob(title, description, salary, benefits, employer.getName(), department, shift);
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

    public ArrayList<Job> queryOwnedOpenPostings(String employerName){

        EmployerDAO employerDAO = new EmployerDAO();

        // get employer id
        int employerID = employerDAO.queryEmployerID(employerName);
        HashMap<Integer, MedicalJob> jobMap = new HashMap<>();

        // query all jobs that have employerID as employer
        try {

            // get all jobID's that belong to that employer, and filter out the ones that are occupied already
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicaljobs LEFT JOIN medicalbenefits ON medicaljobs.idMedicalJob = medicalbenefits.idMedicalBenefits WHERE idEmployer = ? AND NOT EXISTS (SELECT * FROM medicaloccupations WHERE medicaloccupations.idMedicalJob = medicaljobs.idMedicalJob)");
            statement.setInt(1, employerID);

            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer id = resultSet.getInt("idMedicalJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(id)){

                    MedicalJob job = jobMap.get(id);
                    job.addBenefits(resultSet.getString("benefit"));
                    jobMap.replace(id, job);
                }

                // else, add it to the hashmap
                else{

                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int salary = resultSet.getInt("salary");
                    String department = resultSet.getString("department");
                    String shift = resultSet.getString("shift");
                    ArrayList<String> benefits = new ArrayList<>();
                    MedicalJob job = new MedicalJob(title, description, salary, benefits, employerName, department, shift);
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

    public ArrayList<Job> queryAllOwnedPostings(String employerName){

        EmployerDAO employerDAO = new EmployerDAO();

        // get employer id
        int employerID = employerDAO.queryEmployerID(employerName);

        HashMap<Integer, MedicalJob> jobMap = new HashMap<>();

        try {

            // get all jobID's
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicaljobs LEFT JOIN medicalbenefits on medicaljobs.idMedicalJob = medicalbenefits.idMedicalJob where idEmployer = ?;");
            statement.setInt(1, employerID);
            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer idJob = resultSet.getInt("idMedicalJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(idJob)){

                    MedicalJob job = jobMap.get(idJob);
                    job.addBenefits(resultSet.getString("benefit"));
                    jobMap.replace(idJob, job);
                }

                // else, add it to the hashmap
                else{
                    int idEmployer = resultSet.getInt("idEmployer");
                    Employer employer = employerDAO.queryEmployerFromID(idEmployer);

                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int salary = resultSet.getInt("salary");
                    String department = resultSet.getString("department");
                    String shift = resultSet.getString("shift");


                    ArrayList<String> benefits = new ArrayList<>();
                    MedicalJob job = new MedicalJob(title, description, salary, benefits, employer.getName(), department, shift);
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

    public ArrayList<Worker> queryEmployees(String employerName){

        EmployerDAO employerDAO = new EmployerDAO();

        ArrayList<Worker> workerList = new ArrayList<>();
        int employerID = employerDAO.queryEmployerID(employerName);

        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicaloccupations join medicaljobs ON medicaloccupations.idMedicalJob = medicaljobs.idMedicalJob join workers ON medicaloccupations.idWorker = workers.idWorker where idEmployer = ?;");
            statement.setInt(1, employerID);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){

                String workerName = resultSet.getString("name");
                Worker newWorker = new Worker(workerName);
                workerList.add(newWorker);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return workerList;
    }

    public void fireWorker(String workerName){

        WorkerDAO workerDAO = new WorkerDAO();
        int idWorker = workerDAO.queryWorkerID(workerName);

        try{
            PreparedStatement fireEmployee = connection.prepareStatement("DELETE FROM medicaloccupations WHERE idWorker = ?");
            fireEmployee.setInt(1, idWorker);
            fireEmployee.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean applicationExists(int idWorker, int idJob){
        // check if the current application (idWorker, idJob) already exists
        try {
            PreparedStatement getApplicationSet = connection.prepareStatement("SELECT * FROM medicalapplications WHERE idWorker = ? AND idMedicalJob = ?");
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

    public void createApplication(String workerName, Job job){

        WorkerDAO workerDAO = new WorkerDAO();
        int idWorker = workerDAO.queryWorkerID(workerName);
        int idJob = queryJobID(job);

        try {
            // insert into applications
            PreparedStatement addApplication = connection.prepareStatement("INSERT INTO medicalapplications (idWorker, idMedicalJob) VALUES (?, ?)");
            addApplication.setInt(1, idWorker);
            addApplication.setInt(2, idJob);
            addApplication.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void hireWorker(Worker worker, Job job){

        WorkerDAO workerDAO = new WorkerDAO();
        int idWorker = workerDAO.queryWorkerID(worker.getName());
        int idJob = queryJobID(job);

        try{
            // hiring the worker
            PreparedStatement hireWorker = connection.prepareStatement("INSERT INTO medicaloccupations (idWorker, idMedicalJob) VALUES (?, ?)");
            hireWorker.setInt(1, idWorker);
            hireWorker.setInt(2, idJob);

            hireWorker.executeUpdate();

            // clearing ALL other applications once hired
            PreparedStatement clearApps = connection.prepareStatement("DELETE FROM medicalapplications WHERE idMedicalJob = ?");
            clearApps.setInt(1, idJob);
            clearApps.executeUpdate();

            // clear the worker's applications toward other jobs once hired
            PreparedStatement clearApps2 = connection.prepareStatement("DELETE FROM engineeringapplications WHERE idWorker = ?");
            clearApps2.setInt(1, idWorker);
            clearApps2.executeUpdate();

            PreparedStatement clearApps3 = connection.prepareStatement("DELETE FROM applications WHERE idWorker = ?");
            clearApps3.setInt(1, idWorker);
            clearApps3.executeUpdate();

            PreparedStatement clearApps4 = connection.prepareStatement("DELETE FROM managementapplications WHERE idWorker = ?");
            clearApps4.setInt(1, idWorker);
            clearApps4.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
