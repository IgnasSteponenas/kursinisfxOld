package financeManagementSystem.utils;

import financeManagementSystem.model.Company;
import financeManagementSystem.model.Individual;

import java.sql.*;

public class UserDataBaseManagement {
    public static Individual findIndividual(String login, String pass) {

        Individual individual = null;
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();
            String sql = "SELECT * FROM users u WHERE u.Login = '" + login + "' AND u.Password = '" + pass + "'";

            ResultSet resultSet = statement.executeQuery(sql);
            //if(resultSet.next() == true)
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String loginName = resultSet.getString("Login");
                String password = resultSet.getString("Password");
                String name = resultSet.getString("Name");
                String surname = resultSet.getString("Surname");
                String phoneNumber = resultSet.getString("PhoneNumber");
                String email = resultSet.getString("Email");
                String responsible = resultSet.getString("responsibleForCategories");

                if(!surname.equals("null"))
                individual = new Individual(password, loginName, name, surname, phoneNumber, email, id, responsible);

            }
            return individual;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
        return null;
    }

    public static Company findCompany(String login, String pass) {

        Company company = null;
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();
            String sql = "SELECT * FROM users u WHERE u.Login = '" + login + "' AND u.Password = '" + pass + "' AND u.Surname = 'null'";

            ResultSet resultSet = statement.executeQuery(sql);
            //if(resultSet.next() == true)
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String loginName = resultSet.getString("Login");
                String password = resultSet.getString("Password");
                String name = resultSet.getString("Name");
                String phoneNumber = resultSet.getString("PhoneNumber");
                String email = resultSet.getString("Email");
                String responsible = resultSet.getString("responsibleForCategories");
                company = new Company(password, loginName, name, phoneNumber, email, id, responsible);

            }
            return company;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
        return null;
    }

    public static void insertIndividual(Individual individual) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();
            String sql =
                    "insert into users(`ID`,`Login`,`Password`,`Name`,`Surname`,`PhoneNumber`,`Email`,`responsibleForCategories`) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement insert = connection.prepareStatement(sql);

            insert.setInt(1, individual.getId());
            insert.setString(2, individual.getLoginName());
            insert.setString(3, individual.getPsw());
            insert.setString(4, individual.getName());
            insert.setString(5, individual.getSurname());
            insert.setString(6, individual.getPhoneNumber());
            insert.setString(7, individual.getEmail());
            insert.setString(8, "null");

            insert.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Jdbc.disconnectFromDb(connection, statement);
    }

    public static void insertCompany(Company company) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();
            String sql =
                    "insert into users(`ID`,`Login`,`Password`,`Name`, `Surname`,`PhoneNumber`,`Email`,`responsibleForCategories`) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement insert = connection.prepareStatement(sql);

            insert.setInt(1, company.getId());
            insert.setString(2, company.getLoginName());
            insert.setString(3, company.getPsw());
            insert.setString(4, company.getName());
            insert.setString(5, "null");
            insert.setString(6, company.getPhoneNumber());
            insert.setString(7, company.getEmail());
            insert.setString(8, "null");

            insert.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Jdbc.disconnectFromDb(connection, statement);
    }

    public static int getUserIdCount(){
        Connection connection = null;
        Statement statement = null;

        int idNow = 0;

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();

            String sql = "SELECT nexIdForUser FROM nextId";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                idNow = resultSet.getInt("nexIdForUser");
            }
            return idNow;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
        return idNow;
    }

    public static void addUserIdCount(){
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();

            String updateString = "update nextId set nexIdForUser = ? where nexIdForUser = ?";
            PreparedStatement updateCat = connection.prepareStatement(updateString);
            updateCat.setInt(1, getUserIdCount()+1);
            updateCat.setInt(2, getUserIdCount());
            updateCat.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
    }

    public static String getResponsibleCategories(int userId){
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();
            String sql = "SELECT responsibleForCategories FROM users WHERE ID = '" + userId + "'";

            String responsible = null;
            ResultSet resultSet = statement.executeQuery(sql);
            //if(resultSet.next() == true)
            //while (resultSet.next()) {
            resultSet.next();
            responsible = resultSet.getString("responsibleForCategories");
            //}
            if(responsible.equals("null"))
                return null;
            return responsible;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
        return null;
    }

    public static void addResponsibleCategories(int userId, int categoryToAddId){
        Connection connection = null;
        Statement statement = null;

        String responsible = getResponsibleCategories(userId);
        if(responsible==null){
            responsible = String.valueOf(categoryToAddId);
        }else
        {
            responsible = responsible + ":" + categoryToAddId;
        }
        try {
            connection = Jdbc.connectToDb();

            String updateString = "update users set responsibleForCategories = ? where ID = ?";
            PreparedStatement updateCat = connection.prepareStatement(updateString);
            updateCat.setString(1, responsible);
            updateCat.setInt(2, userId);
            updateCat.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
    }

    public static void removeResponsibleCategories(int userId, int categoryToDeleteFromId){
        Connection connection = null;
        Statement statement = null;

        String[] responsible = UserDataBaseManagement.getResponsibleCategories(userId).split(":");
        String result = null;

        if(responsible.length==1){
            result = "null";
        }else{
            boolean worked = false;
            int i=0;
            for (int j=0; j<responsible.length; j++) {
                if (!responsible[j].equals(String.valueOf(categoryToDeleteFromId))) {
                    if(worked) {
                        result = result + ":" + responsible[j];
                        i++;
                    }else{
                        result = responsible[j];
                        i++;
                        worked = true;
                    }
                }
            }
        }

        try {
            connection = Jdbc.connectToDb();



            String updateString = "update users set responsibleForCategories = ? where ID = ?";
            PreparedStatement updateCat = connection.prepareStatement(updateString);
            updateCat.setString(1, result);
            updateCat.setInt(2, userId);
            updateCat.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
    }
}
