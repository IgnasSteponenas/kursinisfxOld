package financeManagementSystem.utils;

import financeManagementSystem.model.Category;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CategoryDataBaseManagement {
    public static void deleteCategory(int id){
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();

            String deleteString = "delete from categories where catId = ?";
            PreparedStatement delete = connection.prepareStatement(deleteString);
            delete.setInt(1,id);
            delete.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
    }

    public static int getCategoryIdCount(){
        Connection connection = null;
        Statement statement = null;

        int idNow = 0;

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();

            String sql = "SELECT nexIdForCategory FROM nextId";// + insertedId;
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                idNow = resultSet.getInt("nexIdForCategory");
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

    public static void addCategoryIdCount(){
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();

            String updateString = "update nextId set nexIdForCategory = ? where nexIdForCategory = ?";
            PreparedStatement updateCat = connection.prepareStatement(updateString);
            updateCat.setInt(1, getCategoryIdCount()+1);
            updateCat.setInt(2, getCategoryIdCount());
            updateCat.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
    }

    public static void updateCategoryName(String newName, String catId){

        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();

            String updateString = "update categories set name = ?, dateModified = ? where catId = ?";
            PreparedStatement updateCat = connection.prepareStatement(updateString);
            updateCat.setString(1, newName);
            updateCat.setDate(2, Date.valueOf(LocalDate.now()));
            updateCat.setString(3, catId);
            updateCat.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
    }

    public static void updateCategoryDescription(String newDescription, String catId){

        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();

            String updateString = "update categories set description = ?, dateModified = ? where catId = ?";
            PreparedStatement updateCat = connection.prepareStatement(updateString);
            updateCat.setString(1, newDescription);
            updateCat.setDate(2, Date.valueOf(LocalDate.now()));
            updateCat.setString(3, catId);
            updateCat.executeUpdate();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
    }

    public static Category getCategoryById(String insertedId) {

        Category category = null;
        Connection connection = null;
        Statement statement = null;
        ArrayList<Category> arrayList = new ArrayList<Category>();

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();
            String sql = "SELECT * FROM categories c WHERE c.catId = " + insertedId;

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int catId = resultSet.getInt("catId");
                Date dateCreated = resultSet.getDate("dateCreated");
                Date dateModified = resultSet.getDate("dateModified");
                String parentID = resultSet.getString("parentID");

                category = new Category(name, description, catId, dateModified.toLocalDate(), dateCreated.toLocalDate(), parentID);

            }
            return category;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
        return category;
    }

    public static ArrayList<Category> getAllCategories(){

        Category category = null;
        Connection connection = null;
        Statement statement = null;
        ArrayList<Category> arrayList = new ArrayList<Category>();

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();
            String sql = "SELECT * FROM categories";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                int catId = resultSet.getInt("catId");
                Date dateCreated = resultSet.getDate("dateCreated");
                Date dateModified = resultSet.getDate("dateModified");
                String parentID = resultSet.getString("parentID");

                //System.out.println(name + bendrauthor + jurnalName + price + paid);

                category = new Category(name, description, catId, dateModified.toLocalDate(), dateCreated.toLocalDate(), parentID);
                arrayList.add(category);
            }
            return arrayList;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Jdbc.disconnectFromDb(connection, statement);
        return arrayList;
    }

    public static void insertCategory(Category category) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Jdbc.connectToDb();
            statement = connection.createStatement();
            String sql =
                    "insert into categories(`name`,`description`,`catId`,`dateCreated`,`dateModified`,`parentID`) VALUES(?,?,?,?,?,?)";
            PreparedStatement insert = connection.prepareStatement(sql);

            insert.setString(1, category.getName());
            insert.setString(2, category.getDescription());
            insert.setInt(3, category.getCatId());
            insert.setDate(4, Date.valueOf(category.getDateCreated()));
            insert.setDate(5, Date.valueOf(category.getDateModified()));
            insert.setString(6, category.getParentID());

            insert.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Jdbc.disconnectFromDb(connection, statement);
    }
}
