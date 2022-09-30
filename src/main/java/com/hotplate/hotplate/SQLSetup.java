package com.hotplate.hotplate;

import java.sql.SQLException;
import java.util.ArrayList;

public class SQLSetup{

    // -------------------------------------------------------------------------------------------------

    // If you want to use the SQL database, you must enable useSQL to true. You will also need to type
    // down your username, password and database url. Make sure that you have the class path to your
    // connector in your library. Please see https://github.com/AndrewCollin32/HotPlate-Waitlist-Manager
    // for more information.

    // Enable to true if you want to use SQL to store customer and user data
    public static boolean useSQL = false;
    // Put down your databaseURL. It should be in this format: jdbc:mysql://localhost:{PortNumber}/{databaseName}
    // if you are using local host and mySQL
    protected static String databaseURL = "";
    // Type down the sqlUsername and password to log in to your database
    protected static String sqlUsername = "";
    protected static String sqlPassword = "";
    // At the time of this writing, I am using mySQL to store data for this program.

    // Instructions: After entering those 3 information, run main (below) one time. This will generate
    // the necessary tables for you. It will also give you a sample of users and customers.
    // To remove all the data including the 2 tables, just call the deleteEverything() method.

    LoadSQL loadSQL = LoadSQL.getInstances();

    // -------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws SQLException {
        SQLSetup sqlSetup = new SQLSetup();
        sqlSetup.createDatabaseTables();
        sqlSetup.createSampleSettingData();
        sqlSetup.createSampleCustomerData();
        //sqlSetup.deleteEverything();
    }


    //Creates two tables for you. CustomerData and UserSettings
    public void createDatabaseTables() throws SQLException {
        loadSQL.statement.execute("CREATE TABLE `customerdata` (\n" +
                "  `customerName` varchar(40) NOT NULL,\n" +
                "  `customerPartySize` varchar(2) NOT NULL,\n" +
                "  `customerTime` varchar(10) NOT NULL,\n" +
                "  `customerPhoneNumber` varchar(10) NOT NULL\n" +
                ");");

        loadSQL.statement.execute("CREATE TABLE `usersettings` (\n" +
                "  `username` varchar(45) NOT NULL,\n" +
                "  `password` varchar(45) NOT NULL,\n" +
                "  `ownername` varchar(45) NOT NULL,\n" +
                "  `restaurantname` varchar(80) NOT NULL,\n" +
                "  `autoloaddata` tinyint NOT NULL,\n" +
                "  `britishTime` tinyint NOT NULL,\n" +
                "  `warnmessage` varchar(500) NOT NULL,\n" +
                "  `callmessage` varchar(500) NOT NULL,\n" +
                "  PRIMARY KEY (`username`)\n" +
                ");");
    }

    // Creates a sample of user data.
    public void createSampleSettingData(){
        loadSQL.createNewAccount("Andrew", "greaterGood123", "Andrew", "Ernies");
        loadSQL.createNewAccount("Jacob", "password432!", "Jacob Blake", "Ernies");
        loadSQL.createNewAccount("Gordan12", "Ramsey32", "Gordan Ramsey", "Prime Steak");
    }

    // Creates a sample of customer data
    public void createSampleCustomerData(){
        ArrayList<Customer> customerdata = new ArrayList<>();
        customerdata.add(new Customer("Steven", "12","09:23 PM", "6198182234"));
        customerdata.add(new Customer("Maverick", "1","10:00 PM", "6198182123"));
        customerdata.add(new Customer("Nguyen", "3","10:12 PM", "6198183342"));
        customerdata.add(new Customer("Frank", "1","10:13 PM", "6198184542"));
        customerdata.add(new Customer("Peter", "2","10:15 PM", "6198183342"));
        customerdata.add(new Customer("Ryan", "3","10:32 PM", "6196423332"));
        customerdata.add(new Customer("Gustavo", "2","10:34 PM", "6193943342"));
        HotPlateApp.customerData = customerdata;
        loadSQL.saveCustomerData();
    }

    // Deletes everything in the database including the two tables that was created.
    public void deleteEverything() throws SQLException {
        loadSQL.deleteAllUsers();
        loadSQL.deleteAllCustomers();
        loadSQL.statement.execute("DROP TABLE `customerdata`;");
        loadSQL.statement.execute("DROP TABLE `usersettings`;");
    }
}
