package Database;

import Documents.Job;
import Users.Employer;
import Users.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployerDAO {

    private Connection connection;

    // connect to database!
    public EmployerDAO() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testschema", "root", "12345678");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setEmployerContactDetails(String newEmail, String newContact, String employerName) {

        int idEmployer = queryEmployerID(employerName);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employers SET email = ?, contactno = ? WHERE idEmployer = ?");
            preparedStatement.setString(1, newEmail);
            preparedStatement.setString(2, newContact);
            preparedStatement.setInt(3, idEmployer);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmployer(String name, String password) {

        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO employers (name, password) VALUES (?, ?);");
            statement.setString(1, name);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int queryEmployerID(String name) {

        ResultSet resultSet;
        int employerID = 0;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM employers WHERE name = ?;");
            query.setString(1, name);
            resultSet = query.executeQuery();
            resultSet.next();

            employerID = resultSet.getInt("idEmployer");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employerID;
    }


    public Employer queryEmployerFromName(String employerName) {

        Employer employer = null;

        int idEmployer = queryEmployerID(employerName);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employers WHERE idEmployer = ?");
            preparedStatement.setInt(1, idEmployer);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String contactNumber = resultSet.getString("contactno");
            String email = resultSet.getString("email");

            employer = new Employer(employerName);
            employer.setContactNumber(contactNumber);
            employer.setEmail(email);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employer;
    }


    public Employer queryEmployerFromID(int idEmployer) {
        Employer employer = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employers WHERE idEmployer = ?");
            preparedStatement.setInt(1, idEmployer);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String employerName = resultSet.getString("name");
            String contactNumber = resultSet.getString("contactno");
            String email = resultSet.getString("email");

            employer = new Employer(employerName);
            employer.setContactNumber(contactNumber);
            employer.setEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employer;
    }


    public void fireWorker(String workerName){

        WorkerDAO workerDAO = new WorkerDAO();
        int idWorker = workerDAO.queryWorkerID(workerName);

        try{
            PreparedStatement fireEmployee = connection.prepareStatement("DELETE FROM occupations WHERE idWorker = ?");
            fireEmployee.setInt(1, idWorker);
            fireEmployee.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
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


    public ArrayList<Worker> queryEmployees(String employerName){

        ArrayList<Worker> workerList = new ArrayList<>();
        int employerID = queryEmployerID(employerName);

        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM occupations join jobs ON occupations.idJob = jobs.idJob join workers ON occupations.idWorker = workers.idWorker where idEmployer = ?;");
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


    public ArrayList<Job> queryAllPostings(){

        HashMap<Integer, Job> jobMap = new HashMap<>();

        try {

            // get all jobID's
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM jobs LEFT JOIN benefits on jobs.idJob = benefits.idJob;");
            ResultSet resultSet = statement.executeQuery();

            // loop through resultSet
            while(resultSet.next()){

                // get their jobID
                Integer idJob = resultSet.getInt("idJob");

                // if it's already in the hashmap, get it, add a new benefit, put it back.
                if(jobMap.containsKey(idJob)){

                    Job job = jobMap.get(idJob);
                    job.addBenefits(resultSet.getString("benefit"));
                    jobMap.replace(idJob, job);
                }

                // else, add it to the hashmap
                else{

                    int idEmployer = resultSet.getInt("idEmployer");
                    Employer employer = queryEmployerFromID(idEmployer);

                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");
                    int salary = resultSet.getInt("salary");
                    ArrayList<String> benefits = new ArrayList<>();
                    Job job = new Job(title, description, salary, benefits, employer.getName());
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

    // EmployerService
    public ArrayList<Job> queryJobs(String employerName){

        // get employer id
        int employerID = queryEmployerID(employerName);
        HashMap<Integer, Job> jobMap = new HashMap<>();

        // query all jobs that have employerID as employer
        try {

            // get all jobID's that belong to that employer
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM jobs LEFT JOIN benefits ON jobs.idJob = benefits.idJob WHERE idEmployer = ? AND NOT EXISTS (SELECT * FROM occupations WHERE occupations.idJob = jobs.idJob)");
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
                    ArrayList<String> benefits = new ArrayList<>();
                    Job job = new Job(title, description, salary, benefits, employerName);
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



