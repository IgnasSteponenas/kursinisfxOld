package financeManagementSystem.utils;

import java.sql.*;

public class Jdbc {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //ArrayList<MokslinisStraipsnis> arr2 = getStrapsnisByMinMaxPrice(0, 60, true);
        //System.out.println(arr2.toString());

        //ArrayList<MokslinisStraipsnis> arr = getAllStraipsniai();
        //System.out.println(arr.toString());

        //MokslinisStraipsnis e2 = new MokslinisStraipsnis("name", 4, "yolo", (float)21231.5, true);
        //insertStraipsnis(e2);
        System.out.println("just to push");
    }





    public static Connection connectToDb() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String DB_URL = "jdbc:mysql://localhost/fms";
        String USER = "root";
        String PASS = "";
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;
    }

    public static void disconnectFromDb(Connection connection, Statement statement) {
        try {
            if (connection != null && statement != null) {
                connection.close();
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
