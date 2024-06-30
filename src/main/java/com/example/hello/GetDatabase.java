package com.example.hello;
import java.sql.*;
import java.util.ArrayList;

class GetDatabase {
    private static Statement statement;
    private static final String url = "jdbc:sqlite:C://sql-lite//customer.db";
    int grandTotal = 0;

    public static void main(String[] args) {
        GetDatabase getDatabase = new GetDatabase();
    }

    GetDatabase() {
        getConnection();
        getTotalSales();
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

    ArrayList<String> getTabs() {
        ArrayList<String> tabs = new ArrayList<>();
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

    ArrayList<String> getSupplier() {
        ArrayList<String> supplier = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select distinct supplier from product");

            while (resultSet.next()) {
                supplier.add(resultSet.getString(1));
            }
            connection.close();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return supplier;
    }

    ArrayList<Products> allItems(String ...tab) {
        ArrayList<Products> item = new ArrayList<>();
        String query = "select * from product";
        if(tab.length > 0)
           query = tab[0].equals("All") ? "select * from product" : "select * from product where category = '" + tab[0] + "'";
        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                int quantity = resultSet.getInt(2);
                int price = resultSet.getInt(3);
                String supplier = resultSet.getString(4);
                String category = resultSet.getString(5);
                int id = resultSet.getInt(6);
                String desc = resultSet.getString(7);
                item.add(new Products(name, quantity, price, supplier, category, id,desc));
            }

            connection.close();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return item;
    }


    void addProduct(String name, int quantity, int price, String supplier, String category, int id,String description) {
        String sql = "INSERT INTO product(name, quantity, price, supplier, category, id,description) VALUES(?, ?, ?, ?, ?, ?,?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, price);
            pstmt.setString(4, supplier);
            pstmt.setString(5, category);
            pstmt.setInt(6, id);
            pstmt.setString(7,description);
            pstmt.executeUpdate();

            System.out.println("data has been added successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void editProduct(String name, int quantity, int price, String supplier, String category, int id,String description) {
        String sql = "UPDATE product SET name = ?, quantity = ?, price = ?, supplier = ?, category = ?, description = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setInt(3, price);
            pstmt.setString(4, supplier);
            pstmt.setString(5, category);
            pstmt.setInt(6, id);
            pstmt.setString(7,description);
            pstmt.executeUpdate();

            System.out.println("data has been updated successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void deleteProduct(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            System.out.println("data has been deleted successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void transactions(ArrayList<Products> products,int[] addedCount) {
        try (Connection connection = DriverManager.getConnection(url)) {
            connection.setAutoCommit(false);
            String sql = "insert into transactions(name,quantity,price,id,supplier,category) values(?,?,?,?,?,?)";
            String sql1 = "select quantity from transactions where id = ?";
            String sql2 = "update product set quantity = quantity - ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);

            for ( int i=0;i<products.size();i++) {
                Products product = products.get(i);
                int count = addedCount[i];
                preparedStatement.setInt(1, product.id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    int quantity = resultSet.getInt(1);
                    PreparedStatement update = connection.prepareStatement("update transactions set quantity = ? where id = ?");
                    update.setInt(1,quantity + count);
                    update.setInt(2,product.id);
                    update.executeUpdate();
                }else{
                    preparedStatement1.setString(1, product.name);
                    preparedStatement1.setInt(2, count);
                    preparedStatement1.setInt(3, product.price);
                    preparedStatement1.setInt(4, product.id);
                    preparedStatement1.setString(5, product.supplier);
                    preparedStatement1.setString(6, product.category);
                    preparedStatement1.executeUpdate();
                }

                preparedStatement2.setInt(1,count);
                preparedStatement2.setInt(2, product.id);
                preparedStatement2.executeUpdate();

            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ArrayList<Products> getAllTransactions(){
        ArrayList<Products> products = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from transactions")) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");
                int price = resultSet.getInt("price");
                int id = resultSet.getInt("id");
                String supplier = resultSet.getString("supplier");
                String category = resultSet.getString("category");
                products.add(new Products(name, quantity, price, supplier, category, id,""));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public static ArrayList<CategorySales> getCategorySales() {
        ArrayList<CategorySales> categorySalesList = new ArrayList<>();

        String sql = "SELECT category, SUM(quantity * price) as total_sales FROM transactions GROUP BY category";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String category = resultSet.getString("category");
                int totalSales = resultSet.getInt("total_sales");
                categorySalesList.add(new CategorySales(category, totalSales));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categorySalesList;
    }

    ArrayList<Total> getTotalSales(){
        ArrayList<Total> totals = new ArrayList<>();
        String sql = "SELECT name ,quantity,price, SUM(quantity * price) as total_sales FROM product GROUP BY name";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                int quantity = resultSet.getInt(2);
                int price = resultSet.getInt(3);
                int total = resultSet.getInt(4);

                totals.add(new Total(total,name,quantity,price));
                grandTotal += total;
                System.out.println("Product name : " + name + " total sales : " + total);
            }
            System.out.println("Total sales of all products: " + grandTotal);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totals;
    }

}