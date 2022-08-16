/*
HotPlateApp Software, created by Andrew Collin Diep 08/06/2022
HotPlate is a wait-list restaurant manager
Visit https://github.com/AndrewCollin/HotPlate for more info.
*/
package com.hotplate.hotplate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HotPlateApp extends Application {

    public static Stage stage;
    public static int waitListSize = 0;
    public static String pinNumber = "1111";
    public static String userName = "Jeffery";
    public static String restaurantName = "Ernie's";
    public static String warnMessage = "Hi {name}, this is {restaurant}, your table will be ready within 5 minutes.";
    public static String callMessage = "Hi {name}, this is {restaurant}, your table is ready.";
    public static String customerDataPathFile = "DefaultSave.ser";
    public static String saveSettingsPathFile = "SaveSettings.ser";
    public static Boolean automaticallyLoadData = false;
    public static Boolean britishTime = false;
    public static ArrayList<Customer> customerData = new ArrayList<Customer>();
    public boolean bypassSaveSettingsDeBug = false;
    public boolean bypassSaveCustomersDebug = false;


    //Starting the program in the customer portal scene
    @Override
    public void start(Stage stage) throws IOException, ParseException {
        this.stage = stage;

        //Loading the user's settings into HotPlateApp
        if (!bypassSaveSettingsDeBug) {
            SaveSettings ss = (SaveSettings) ResourceManager.load(HotPlateApp.saveSettingsPathFile);
            HotPlateApp.userName = ss.userName;
            HotPlateApp.restaurantName = ss.restaurantName;
            HotPlateApp.automaticallyLoadData = ss.autoLoadData;
            HotPlateApp.pinNumber = ss.pin;
            HotPlateApp.warnMessage = ss.warnMessage;
            HotPlateApp.callMessage = ss.callMessage;
            HotPlateApp.britishTime = ss.britishTime;
        }
        //Loading the customer's data into HotPlateApp if Auto-Load is turned on.
        if (!bypassSaveCustomersDebug) {
            SaveData sd = new SaveData(new ArrayList<Customer>());
            try {
                sd = (SaveData) ResourceManager.load(HotPlateApp.customerDataPathFile);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (HotPlateApp.automaticallyLoadData && sd != null) {

                HotPlateApp.waitListSize = sd.customersData.size();
                HotPlateApp.customerData = sd.customersData;

                SimpleDateFormat britishTime = new SimpleDateFormat("HH:mm");
                SimpleDateFormat americanTime = new SimpleDateFormat("hh:mm a");

                boolean isBritishTime = false;

                try{
                    americanTime.parse(HotPlateApp.customerData.get(0).getTimeWaited());
                }
                catch(Exception e){
                    isBritishTime = true;
                }

                if (HotPlateApp.britishTime && !isBritishTime || !HotPlateApp.britishTime && isBritishTime){
                    for (int i = 0; i < HotPlateApp.customerData.size(); i++){
                        Customer customer = HotPlateApp.customerData.get(i);
                        String time = customer.getTimeWaited();
                        Date date = ((HotPlateApp.britishTime)?americanTime:britishTime).parse(time);
                        String newDate = ((HotPlateApp.britishTime)?britishTime:americanTime).format(date);
                        customer.setTimeWaited(newDate);
                    }
                }
            }
        }

        stage.setResizable(false);
        stage.setTitle("HotPlate");
        stage.getIcons().add(new Image(HotPlateApp.class.getResourceAsStream("HotPlateLogo.png")));
        launchCustomerPortal();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    /* SCENE AND CONTROLLER CREATION */

    public static Scene customerPortalScene;
    public static void launchCustomerPortal() throws IOException {
        if (customerPortalScene == null){
            FXMLLoader fxmlLoader = new FXMLLoader(HotPlateApp.class.getResource("customerPortal.fxml"));
            customerPortalScene = new Scene(fxmlLoader.load(), 600, 600);
        }
        stage.setScene(customerPortalScene);
    }

    public static Scene reserveSeatScene;
    public static void launchReserveSeatPortal() throws IOException {
        if (reserveSeatScene == null){
            FXMLLoader fxmlLoader = new FXMLLoader(HotPlateApp.class.getResource("reserveSeatPortal.fxml"));
            reserveSeatScene = new Scene(fxmlLoader.load(), 600, 600);
        }
        stage.setScene(reserveSeatScene);
    }

    public static Scene confirmationPageScene;
    public static void launchConfirmationPage() throws IOException {
        if (confirmationPageScene == null){
            FXMLLoader fxmlLoader = new FXMLLoader(HotPlateApp.class.getResource("confirmationPage.fxml"));
            confirmationPageScene = new Scene(fxmlLoader.load(), 600, 600);
        }
        stage.setScene(confirmationPageScene);
    }

    public static Scene signInScene;
    public static void launchSignInPage() throws IOException {
        if (signInScene == null){
            FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("signInPage.fxml"));
            signInScene = new Scene(fxml.load(), 600, 600);
        }
        stage.setScene(signInScene);
    }

    public static Scene aboutMeScene;
    public static boolean aboutMeCustomerOrgin;
    public static void launchAboutMePage() throws IOException {
        if (aboutMeScene == null){
            FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("aboutMePage.fxml"));
            aboutMeScene = new Scene(fxml.load(), 600, 600);
        }
        stage.setScene(aboutMeScene);
    }

    public static Scene adminPortalScene;
    public static void launchAdminPortal(boolean updatePage) throws IOException {
        if (adminPortalScene == null || updatePage){
            FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("adminPage.fxml"));
            adminPortalScene = new Scene(fxml.load(), 600, 600);
        }
        stage.setScene(adminPortalScene);
    }

    public static Scene customMessagePageScene;
    public static void launchCustomMessagePage() throws IOException {
        if(customMessagePageScene == null){
            FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("customMessagePage.fxml"));
            customMessagePageScene = new Scene(fxml.load(), 600, 600);
        }
        stage.setScene(customMessagePageScene);
    }

    public static Scene adminAddToTableScene;
    public static Stage adminAddToTableStage;
    public static void launchAdminAddToTable() throws IOException {
        if (adminAddToTableScene == null){
            FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("adminAddItem.fxml"));
            adminAddToTableScene = new Scene(fxml.load(), 600, 400);
        }
        adminAddToTableStage = new Stage();
        adminAddToTableStage.setScene(adminAddToTableScene);
        adminAddToTableStage.setTitle("Add Item");
        adminAddToTableStage.show();
    }

    public static Scene adminEditToTableScene;
    public static Stage adminEditToTableStage;
    public static Customer selectedCustomer;
    public static void launchAdminEditToTable() throws IOException {
        if (adminEditToTableScene == null){
            FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("adminEditItem.fxml"));
            adminEditToTableScene = new Scene(fxml.load(), 600, 400);
        }
        adminEditToTableStage = new Stage();
        adminEditToTableStage.setScene(adminEditToTableScene);
        adminEditToTableStage.setTitle("Edit Item");
        adminEditToTableStage.show();
    }

    public static Scene settingsScene;
    public static void launchSettings() throws IOException {
        if (settingsScene == null){
            FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("settings.fxml"));
            settingsScene = new Scene(fxml.load(), 600,600);
        }
        stage.setScene(settingsScene);
    }

}