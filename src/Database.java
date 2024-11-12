import java.sql.*;
import java.util.ArrayList;

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

    public ResultSet queryWorker(String name){

        ResultSet resultSet = null;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM workers WHERE name = ?;");
            query.setString(1, name);
            resultSet = query.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
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

    public ResultSet queryEmployer(String name){

        ResultSet resultSet = null;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM employers WHERE name = ?;");
            query.setString(1, name);
            resultSet = query.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
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

    public int getWorkerID(String name){

        int idWorker = 0;

        try {
            ResultSet resultSet = queryWorker(name);

            // set the ResultSet pointer to the variable in the result set!
            resultSet.next();
            idWorker = resultSet.getInt("idWorker");
        }

        catch(SQLException e){
            e.printStackTrace();
        }

        return idWorker;
    };

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




            //printing the resume
            /*
            do{
                String cert = resultSet.getString("certificationName");
                String exp = resultSet.getString("experience");

                if (!certifications.contains(cert)) {
                    certifications.add(cert);
                }

                if(!workExperience.contains(exp)) {
                    workExperience.add(exp);
                }
            } while(resultSet.next());

            System.out.println(name);
            System.out.println(password);
            System.out.println(idWorker);
            System.out.println(resumeDesc);

            System.out.println("Certifications: ");
            for(String i : certifications){
                System.out.println(i);
            }

            System.out.println("Experience: ");
            for(String i : workExperience){
                System.out.println(i);
            }
            */

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addPosting(int employerID, Job job){

        try {

            // add the job to jobs table
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO jobs (salary, description, idEmployer) VALUES (?, ?, ?);");
            statement.setInt(1, job.getSalary());
            statement.setString(2, job.getJobDesc());
            statement.setInt(3, employerID);
            statement.executeUpdate();

            // get the job ID of the newly added job.
            PreparedStatement getID = this.connection.prepareStatement("SELECT * FROM jobs WHERE salary = ? AND description = ? AND idEmployer = ?;");
            getID.setInt(1, job.getSalary());
            getID.setString(2, job.getJobDesc());
            getID.setInt(3, employerID);
            ResultSet resultSet = getID.executeQuery();

            // move the pointer forward, and get the job ID
            resultSet.next();
            int jobID = resultSet.getInt("idJob");

            // add benefits to benefits table, and link to jobs
            for(String benefit: job.getBenefits()) {
                PreparedStatement addToBenefits = this.connection.prepareStatement("INSERT INTO benefits (idJob, benefit) VALUES (?, ?);");
                statement.setString(2, job.getJobDesc());
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }



    }
}

