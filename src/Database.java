import java.sql.*;

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

    public ResultSet queryWorker(String username, String password){

        ResultSet resultSet = null;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM workers WHERE username = ? AND password = ?;");
            query.setString(1, username);
            query.setString(2, password);
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



    public ResultSet queryEmployer(String username, String password){

        ResultSet resultSet = null;

        try {
            PreparedStatement query = connection.prepareStatement("SELECT * FROM employers WHERE username = ? AND password = ?;");
            query.setString(1, username);
            query.setString(2, password);
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

}

