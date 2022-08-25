package com.hotplate.hotplate;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoadSQL {

    public static Statement statement;
    public Connection connection;

    public LoadSQL(){
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
        try {
            statement.executeUpdate("UPDATE usersettings SET " +
                    "username = '" + HotPlateApp.userUsername + "', " +
                    "password = '" + HotPlateApp.userPassword + "', " +
                    "ownername = '" + HotPlateApp.userName + "', " +
                    "restaurantname = '" + HotPlateApp.restaurantName + "', " +
                    "autoloaddata = " + HotPlateApp.automaticallyLoadData + ", " +
                    "britishtime = " + HotPlateApp.britishTime + ", " +
                    "warnmessage = '" + HotPlateApp.warnMessage + "', " +
                    "callmessage = '" + HotPlateApp.callMessage + "' " +
                    "WHERE username = '" + HotPlateApp.userUsername + "'");

        } catch (Exception e){
            HotPlateApp.log.severe("[Fail] Couldn't update SQL settings: " + e);
            HotPlateApp.launchLogError("[Fail] Couldn't update SQL settings: " + e);
        }

    }

    public static void createNewAccount(String username, String password, String ownername, String resturantname){
        try {
            HotPlateApp.log.info("[Starting] Creating a new account");
            statement.execute("INSERT INTO usersettings VALUE " +
                    "('" + username + "','" +
                    password + "','" +
                    ownername + "','" +
                    resturantname + "'," +
                    true + "," +
                    false + ",'" +
                    "Hi {name}, this is {restaurant}, your table will be ready within 5 minutes." + "','" +
                    "Hi {name}, this is {restaurant}, your table is ready." + "')");

            HotPlateApp.log.info("[Success] Creating a new account");

        } catch (Exception e){
            HotPlateApp.log.severe("[Fail] Couldn't create SQL user: " + e);
            HotPlateApp.launchLogError("[Fail] Couldn't create SQL user: " + e);
        }
    }

    public boolean userNameExist(String name){
        System.out.println(name + " vs " + HotPlateApp.userUsername);
        try{
            HotPlateApp.log.info("Searching for username (Primary key)");
            ResultSet rs = statement.executeQuery("SELECT * FROM usersettings WHERE username = '" + name + "'");
            return rs.next();
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
            statement.execute("DELETE FROM usersettings WHERE username = '" + name + "'");
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
                statement.execute("INSERT INTO customerdata VALUE ('" +
                         customer.getName() + "','" + customer.getPartySize() +
                        "','" + customer.getTimeWaited() + "','" + customer.getPhoneNumber() +
                        "')");
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

}
