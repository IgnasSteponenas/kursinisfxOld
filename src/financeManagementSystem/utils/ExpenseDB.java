package financeManagementSystem.utils;

import financeManagementSystem.model.Expense;

import java.sql.*;
import java.util.ArrayList;

public class ExpenseDB {

    public static int getExpenseIdCount(){
        Connection connection = null;
        Statement statement = null;

        int idNow = 0;

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();

            String sql = "SELECT nexIdForExpense FROM nextId";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                idNow = resultSet.getInt("nexIdForExpense");
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

    public static void addExpenseIdCount(){
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();

            String updateString = "update nextId set nexIdForExpense = ? where nexIdForExpense = ?";
            PreparedStatement updateCat = connection.prepareStatement(updateString);
            updateCat.setInt(1, getExpenseIdCount()+1);
            updateCat.setInt(2, getExpenseIdCount());
            updateCat.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
    }

    public static ArrayList<Expense> findAllExpense(int categoryId) {

        Expense expense = null;
        Connection connection = null;
        Statement statement = null;
        ArrayList<Expense> arrayList = new ArrayList<Expense>();

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();
            String sql = "SELECT * FROM expense WHERE catId = '" + categoryId +"'";

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double cost = resultSet.getFloat("cost");
                Date date = resultSet.getDate("date");
                int expenseId = resultSet.getInt("expenseId");
                int catId = resultSet.getInt("catId");
                expense = new Expense(name, description, cost, date.toLocalDate(), expenseId, catId);
                arrayList.add(expense);
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

    public static void addExpense(Expense expense) throws ClassNotFoundException, SQLException {

        Connection connection = null;
        Statement statement = null;

        connection = Jdbc.connectToDb();
        statement = connection.createStatement();
        String sql = "insert into expense(`name`,`description`,`cost`,`date`,`expenseId`,`catId`) VALUES(?,?,?,?,?,?)";
        PreparedStatement insert = connection.prepareStatement(sql);

        insert.setString(1, expense.getName());
        insert.setString(2, expense.getDescription());
        insert.setDouble(3, expense.getCost());
        insert.setDate(4, Date.valueOf(expense.getDate()));
        insert.setInt(5, expense.getExpenseId());
        insert.setInt(6, expense.getCatId());

        insert.execute();

        Jdbc.disconnectFromDb(connection, statement);
    }

    public static void deleteExpenseByCatId(int catId){
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();

            String deleteString = "delete from expense where catId = ?";
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
