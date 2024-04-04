package com.example.hello;
import java.sql.*;
import java.util.ArrayList;

class GetDatabase {
    private static Statement statement;
    private final String url = "jdbc:sqlite:C://sql-lite//customer.db";

    public static void main(String[] args) {
        GetDatabase getDatabase = new GetDatabase();
    }

    GetDatabase() {
        getConnection();
    }

    void getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    boolean isValid(String email, String password) {
        boolean isValid = false;
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customerDetails where email = ? and password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            isValid = resultSet.next();
            connection.close();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return isValid;
    }

    ArrayList<Object> getTabs() {
        ArrayList<Object> tabs = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select distinct category from product");

            while (resultSet.next()) {
                tabs.add(resultSet.getString(1));
            }
            connection.close();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return tabs;
    }

    ArrayList<Products> allItems() {
        ArrayList<Products> item = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select *  from product");
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                int quantity = resultSet.getInt(2);
                int price = resultSet.getInt(3);
                String supplier = resultSet.getString(4);
                String category = resultSet.getString(5);
                int id = resultSet.getInt(6);
                item.add(new Products(name, quantity, price, supplier, category, id));
            }

            connection.close();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return item;
    }


    void addProduct(String name, int quantity, int price, String supplier, String category, int id) {
        String sql = "INSERT INTO product(name, quantity, price, supplier, category, id) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, price);
            pstmt.setString(4, supplier);
            pstmt.setString(5, category);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();

            System.out.println("data has been added successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}