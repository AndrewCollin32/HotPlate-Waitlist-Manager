/*
HotPlateApp Software, created by Andrew Collin Diep 08/06/2022
*/
package com.hotplate.hotplate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class HotPlateApp extends Application {

    public static Stage stage;
    public static Scene scene;
    public static int waitListSize;
    public static AdminPage AP;

    public static String pinNumber = "1111";
    public static String userName = "Jeffery";
    public static String restaurantName = "Ernie's";
    public static String warnMessage = "Hi {name}, this is {restaurant}, your table will be ready within 5 minutes.";
    public static String callMessag = "Hi {name}, this is {restaurant}, your table is ready.";
    public static String customerDataPathFile = "DefaultSave.ser";
    public static String saveSettingsPathFile = "SaveSettings.ser";
    public static Boolean automaticallyLoadData = false;

    public static ArrayList<Customer> customerData = new ArrayList<Customer>();

    //Starting the program in the customer portal scene
    @Override
    public void start(Stage stage) throws IOException {
        waitListSize = 0;
        FXMLLoader fxmlLoader = new FXMLLoader(HotPlateApp.class.getResource("customerPortal.fxml"));

        scene = new Scene(fxmlLoader.load(), 600, 600);
        this.stage = stage;

        SaveSettings ss = (SaveSettings) ResourceManager.load(HotPlateApp.saveSettingsPathFile);
        HotPlateApp.userName = ss.userName;
        HotPlateApp.restaurantName = ss.restaurantName;
        HotPlateApp.automaticallyLoadData = ss.autoLoadData;
        HotPlateApp.pinNumber = ss.pin;
        HotPlateApp.warnMessage = ss.warnMessage;
        HotPlateApp.callMessag = ss.callMessage;
        System.out.println(ss.warnMessage + " " + ss.callMessage);

        SaveData sd = new SaveData(new ArrayList<Customer>());
        try {
            sd = (SaveData) ResourceManager.load(HotPlateApp.customerDataPathFile);
        } catch (Exception e){
            e.printStackTrace();
        }

        if (HotPlateApp.automaticallyLoadData && sd != null){

            HotPlateApp.waitListSize = sd.customersData.size();
            HotPlateApp.customerData = sd.customersData;
        }

        stage.setResizable(false);
        stage.setTitle("HotPlate");
        stage.setScene(scene);
        ((customerPortalController) fxmlLoader.getController()).customerPortalWaitlistLabel.setText("WaitList Size: " + waitListSize);
        stage.show();
    }

    public static void backToCustomer(){
        FXMLLoader fxmlLoader = new FXMLLoader(HotPlateApp.class.getResource("customerPortal.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        customerPortalController cpc = fxmlLoader.getController();
        cpc.customerPortalWaitlistLabel.setText("Current waitlist: " + HotPlateApp.waitListSize);
        scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void debugPrint(){
        for (int i = 0; i < customerData.size(); i++){
            Customer customer = customerData.get(i);
            System.out.println(customer.getName() + " " + customer.getPartySize() + " " + customer.getTimeWaited() + " " + customer.getPhoneNumber());
        }
    }

}