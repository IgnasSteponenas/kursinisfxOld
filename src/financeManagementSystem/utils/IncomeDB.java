package financeManagementSystem.utils;

import financeManagementSystem.model.Income;

import java.sql.*;
import java.util.ArrayList;

public class IncomeDB {

    public static int getIncomeIdCount(){
        Connection connection = null;
        Statement statement = null;

        int idNow = 0;

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();

            String sql = "SELECT nexIdForIncome FROM nextId";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                idNow = resultSet.getInt("nexIdForIncome");
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

    public static void addIncomeIdCount(){
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();

            String updateString = "update nextId set nexIdForIncome = ? where nexIdForIncome = ?";
            PreparedStatement updateCat = connection.prepareStatement(updateString);
            updateCat.setInt(1, getIncomeIdCount()+1);
            updateCat.setInt(2, getIncomeIdCount());
            updateCat.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
    }

    public static ArrayList<Income> findAllIncome(int categoryId) {

        Income income = null;
        Connection connection = null;
        Statement statement = null;
        ArrayList<Income> arrayList = new ArrayList<Income>();

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();
            String sql = "SELECT * FROM income WHERE catId = '" + categoryId +"'";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double cost = resultSet.getFloat("cost");
                Date date = resultSet.getDate("date");
                int incomeId = resultSet.getInt("incomeId");
                int catId = resultSet.getInt("catId");
                income = new Income(name, description, cost, date.toLocalDate(), incomeId, catId);
                arrayList.add(income);
            }
            return arrayList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
        return null;
    }


    public static void addIncome(Income income) throws ClassNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;

        connection = Jdbc.connectToDb();
        statement = connection.createStatement();
        String sql = "insert into income(`name`,`description`,`cost`,`date`,`incomeId`,`catId`) VALUES(?,?,?,?,?,?)";
        PreparedStatement insert = connection.prepareStatement(sql);

        insert.setString(1, income.getName());
        insert.setString(2, income.getDescription());
        insert.setDouble(3, income.getCost());
        insert.setDate(4, Date.valueOf(income.getDate()));
        insert.setInt(5, income.getIncomeId());
        insert.setInt(6, income.getCatId());

        insert.execute();

        Jdbc.disconnectFromDb(connection, statement);
    }

    public static void deleteIncomeByCatId(int catId){
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();

            String deleteString = "delete from income where catId = ?";
            PreparedStatement delete = connection.prepareStatement(deleteString);
            delete.setInt(1,catId);
            delete.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
    }
}
