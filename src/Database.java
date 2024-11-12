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
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO workers (name, password) VALUES (?, ?);");
            statement.setString(1, name);
            statement.setString(2, password);
            statement.executeUpdate();
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


    public void loadResumeDetails(int workerID, Resume resume){

        String sql = "SELECT *" +
                "FROM workers w " +
                "JOIN resumes r ON w.idWorker = r.idWorker " +
                "LEFT JOIN certifications c ON r.idResume = c.idResume " +
                "LEFT JOIN workexperience we ON r.idResume = we.idResume " +
                "WHERE w.idWorker = ?";

        try {
            PreparedStatement query = connection.prepareStatement(sql);
            query.setInt(1, workerID);

            ResultSet resultSet = query.executeQuery();

            // pointer
            resultSet.next();

            // parse the result
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            int idWorker = resultSet.getInt("idWorker");
            String summary = resultSet.getString("Summary");

            ArrayList<String> certifications = new ArrayList<String>();
            ArrayList<String> workExperience = new ArrayList<String>();

        }
        catch (SQLException e) {
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


}

