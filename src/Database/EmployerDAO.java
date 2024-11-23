package Database;

import Users.Employer;
import Users.Worker;

import java.sql.*;
import java.util.ArrayList;

public class EmployerDAO {

    private Connection connection;

    // This is the Data Access Object (DAO) of the Employer class.
    // This should handle the database operations related to the said class

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

    public String queryEmployerEmail(String employerName){

        int idEmployer = queryEmployerID(employerName);
        String email = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employers WHERE idEmployer = ?");
            preparedStatement.setInt(1, idEmployer);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            email = resultSet.getString("email");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return email;
    }

    public String queryEmployerContactNo(String employerName){

        int idEmployer = queryEmployerID(employerName);
        String contact = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employers WHERE idEmployer = ?");
            preparedStatement.setInt(1, idEmployer);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            contact = resultSet.getString("contactno");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contact;
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
}



