package com.hotplate.hotplate;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoadSQL {

    public static Statement statement;
    public Connection connection;

    private static LoadSQL loadSQL = new LoadSQL();

    public static LoadSQL getInstances(){
        return loadSQL;
    }
    private LoadSQL(){
        HotPlateApp.log.info("[Starting] SQL Connection");
        Connection conn;
        Statement state;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(SQLSetup.databaseURL, SQLSetup.sqlUsername, SQLSetup.sqlPassword);
            state = conn.createStatement();
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "You've entered the wrong URL, Username or Password", "Login Failure", JOptionPane.INFORMATION_MESSAGE);
            HotPlateApp.log.severe("[Fail] Loading SQL Data: " + e);
            HotPlateApp.launchLogError("[Fail] Loading SQL Data: " + e);
            return;
        }
        connection = conn;
        statement = state;
    }

    public boolean loadSQLSettings(String username, String password) {
        ResultSet rs = null;
        if (!userNameExist(username)){
            return false;
        }
        try {
            rs = statement.executeQuery("SELECT * FROM userSettings WHERE username = '" + username.toLowerCase() + "'");
        }
        catch (Exception e){
            HotPlateApp.log.warning(e.toString());
            return false;
        }
        try {
            rs.next();
            if (!rs.getString(2).equals(password)) {
                return false;
            }
            HotPlateApp.log.info("[Success] User has entered the username and password successfully");

            HotPlateApp.userUsername = rs.getString(1);
            HotPlateApp.userPassword = rs.getString(2);
            HotPlateApp.userName = rs.getString(3);
            HotPlateApp.restaurantName = rs.getString(4);
            HotPlateApp.automaticallyLoadData = rs.getBoolean(5);
            HotPlateApp.britishTime = rs.getBoolean(6);
            HotPlateApp.warnMessage = rs.getString(7);
            HotPlateApp.callMessage = rs.getString(8);


            HotPlateApp.log.info("[Success] Loaded SQL Settings Data");
        } catch (Exception e){
            HotPlateApp.log.severe("[Fail] Can't load SQL settings data: " + e);
            HotPlateApp.launchLogError("[Fail] Can't load SQL settings data: " + e);
        }
        return true;
    }

    public void saveSQLSettings(){
        HotPlateApp.log.info("[Starting] Saving Data To SQL");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE usersettings SET " +
                    "username = ?, " +
                    "password = ?,  " +
                    "ownername = ?, " +
                    "restaurantname = ?, " +
                    "autoloaddata = ?, " +
                    "britishtime = ?, " +
                    "warnmessage = ?, " +
                    "callmessage = ? " +
                    "WHERE username = ?");
            preparedStatement.setString(1, HotPlateApp.userUsername);
            preparedStatement.setString(2, HotPlateApp.userPassword);
            preparedStatement.setString(3, HotPlateApp.userName);
            preparedStatement.setString(4, HotPlateApp.restaurantName);
            preparedStatement.setBoolean(5, HotPlateApp.automaticallyLoadData);
            preparedStatement.setBoolean(6, HotPlateApp.britishTime);
            preparedStatement.setString(7, HotPlateApp.warnMessage);
            preparedStatement.setString(8, HotPlateApp.callMessage);
            preparedStatement.setString(9, HotPlateApp.userUsername);
            preparedStatement.executeUpdate();

            HotPlateApp.log.info("[Success] Saving Data To SQL");

        } catch (Exception e){
            HotPlateApp.log.severe("[Fail] Couldn't update SQL settings: " + e);
            HotPlateApp.launchLogError("[Fail] Couldn't update SQL settings: " + e);
        }

    }

    public void createNewAccount(String username, String password, String ownername, String resturantname){
        try {
            HotPlateApp.log.info("[Starting] Creating a new account");

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO usersettings VALUE (?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, ownername);
            preparedStatement.setString(4, resturantname);
            preparedStatement.setBoolean(5, true);
            preparedStatement.setBoolean(6, false);
            preparedStatement.setString(7, "Hi {name}, this is {restaurant}, your table will be ready within 5 minutes.");
            preparedStatement.setString(8, "Hi {name}, this is {restaurant}, your table is ready.");
            preparedStatement.executeUpdate();

            HotPlateApp.log.info("[Success] Creating a new account");

        } catch (Exception e){
            HotPlateApp.log.severe("[Fail] Couldn't create SQL user: " + e);
            HotPlateApp.launchLogError("[Fail] Couldn't create SQL user: " + e);
        }
    }

    public boolean userNameExist(String name){
        try{
            HotPlateApp.log.info("Searching for username (Primary key)");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usersettings WHERE username = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch(Exception e){
            HotPlateApp.log.severe("[Fail] Couldn't execute SQL: " + e);
            HotPlateApp.launchLogError("[Fail] Couldn't execute SQL: " + e);
            return false;
        }
    }

    public boolean removeUser(String name){
        HotPlateApp.log.info("[Starting] removing user: " + name);
        if (!userNameExist(name)){
            HotPlateApp.log.severe("[Fail] removing user: " + name + " because username doesn't exist");
            HotPlateApp.launchLogError("[Fail] removing user: " + name + " because username doesn't exist");
            return false;
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM usersettings WHERE username = ?");
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            HotPlateApp.log.info("[Success] removing user: " + name);
            return true;
        } catch (Exception e){
            HotPlateApp.log.severe("[Fail] Couldn't execute SQL: " + e);
            HotPlateApp.launchLogError("[Fail] Couldn't execute SQL: " + e);
            return false;
        }
    }

    public void loadCustomerData(){
        HotPlateApp.log.info("[Starting] loading SQL customer data");
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM customerdata");
        }
        catch (Exception e){
            HotPlateApp.log.severe("[Fail] Couldn't execute SQL: " + e);
            HotPlateApp.launchLogError("[Fail] Couldn't execute SQL: " + e);
        }
        ArrayList<Customer> customerData = new ArrayList<>();
        try {
            while(rs.next()){
                Customer customer = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                customerData.add(customer);
            }
            HotPlateApp.customerData = customerData;
            HotPlateApp.log.info("[Success] Loading SQL customer data");
        } catch (Exception e){
            HotPlateApp.log.severe("[Fail] Can't load SQL customer data");
            HotPlateApp.launchLogError("[Fail] Can't load SQL customer data");
        }

        HotPlateApp.waitListSize = HotPlateApp.customerData.size();


        SimpleDateFormat britishTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat americanTime = new SimpleDateFormat("hh:mm a");

        boolean isBritishTime = false;
        try{
            americanTime.parse(HotPlateApp.customerData.get(0).getTimeWaited());
        }
        catch(Exception e){
            isBritishTime = true;
        }

        try {

            if (HotPlateApp.britishTime && !isBritishTime || !HotPlateApp.britishTime && isBritishTime) {
                for (int i = 0; i < HotPlateApp.customerData.size(); i++) {
                    Customer customer = HotPlateApp.customerData.get(i);
                    String time = customer.getTimeWaited();
                    Date date = ((HotPlateApp.britishTime) ? americanTime : britishTime).parse(time);
                    String newDate = ((HotPlateApp.britishTime) ? britishTime : americanTime).format(date);
                    customer.setTimeWaited(newDate);
                }
            }
        } catch (Exception e){
            HotPlateApp.log.severe("[Fail] Can't load convert time: " + e);
            HotPlateApp.launchLogError("[Fail] Can't load convert time: " + e);
        }

        HotPlateApp.log.info("[Success] SQL Connection");
    }

    public void saveCustomerData(){
        HotPlateApp.log.info("[Starting] Saving customer data SQL");
        try{
            ArrayList<Customer> customerdata = HotPlateApp.customerData;
            deleteAllCustomers();
            for (int i = 0; i < customerdata.size(); i++){
                Customer customer = customerdata.get(i);

                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customerdata VALUE(?,?,?,?)");
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2,customer.getPartySize());
                preparedStatement.setString(3, customer.getTimeWaited());
                preparedStatement.setString(4, customer.getPhoneNumber());
                System.out.println(preparedStatement.executeUpdate());
            }
            HotPlateApp.log.info("[Success] Saving customer data SQL");
        } catch (Exception e){
            HotPlateApp.log.severe("[Fail] Can't save SQL customer data: " + e);
            HotPlateApp.launchLogError("[Fail] Can't save SQL customer data: " + e);
        }
    }

    public static void deleteAllCustomers(){
        try{
            statement.execute("DELETE FROM customerdata");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteAllUsers(){
        try{
            statement.execute("DELETE FROM usersettings");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void finalize() throws SQLException {
        connection.close();
    }

}
