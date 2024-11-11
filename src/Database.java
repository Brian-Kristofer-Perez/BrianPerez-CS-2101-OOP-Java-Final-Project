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

    public ResultSet queryWorker(String username){

        ResultSet resultSet = null;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM workers WHERE username = ?;");
            query.setString(1, username);
            resultSet = query.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }


    public boolean searchWorker(String username, String password){

        boolean isFound = false;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM workers WHERE username = ? AND password = ?;");
            query.setString(1, username);
            query.setString(2, password);
            isFound = query.executeQuery().next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return isFound;
    }


    // overloading the method! the first one takes username and password, and this one takes only the username.
    public boolean searchWorker(String username){

        boolean isFound = false;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM workers WHERE username = ?;");
            query.setString(1, username);
            isFound = query.executeQuery().next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return isFound;
    }

    public ResultSet queryEmployer(String username){

        ResultSet resultSet = null;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM employers WHERE username = ?;");
            query.setString(1, username);
            resultSet = query.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }


    public boolean searchEmployer(String username, String password){

        boolean isFound = false;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM employers WHERE username = ? AND password = ?;");
            query.setString(1, username);
            query.setString(2, password);
            isFound = query.executeQuery().next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return isFound;
    }


    // overloading the method! the first one takes username and password, and this one takes only the username.
    public boolean searchEmployer(String username){

        boolean isFound = false;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM employers WHERE username = ?;");
            query.setString(1, username);
            isFound = query.executeQuery().next();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return isFound;
    }

    public void addWorker(String username, String password){

        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO workers (username, password) VALUES (?, ?);");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            System.out.println("Added successfully!");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void addEmployer(String username, String password){

        try {
            PreparedStatement statement = this.connection.prepareStatement("INSERT INTO employers (username, password) VALUES (?, ?);");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            System.out.println("Added successfully!");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public int getWorkerID(String username){

        int idWorker = 0;

        try {
            ResultSet resultSet = queryWorker(username);

            // set the ResultSet pointer to the variable in the result set!
            resultSet.next();
            idWorker = resultSet.getInt("idWorker");
        }

        catch(SQLException e){
            e.printStackTrace();
        }

        return idWorker;
    };

    public void loadResume(int workerID){

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
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            int idWorker = resultSet.getInt("idWorker");
            String resumeDesc = resultSet.getString("Summary");

            ArrayList<String> certifications = new ArrayList<String>();
            ArrayList<String> workExperience = new ArrayList<String>();

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

            System.out.println(username);
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







        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

