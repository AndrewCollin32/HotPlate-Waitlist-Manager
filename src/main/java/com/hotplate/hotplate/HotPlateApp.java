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
import java.util.logging.*;

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
    public static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static ConsoleHandler console = new ConsoleHandler();
    public static FileHandler fileHandler;

    public static boolean endTime = true;


    //Starting the program in the customer portal scene
    @Override
    public void start(Stage stage) throws IOException, ParseException {
        this.stage = stage;
        fileHandler = new FileHandler("Logs/logFile.log");
        fileHandler.setLevel(Level.INFO);
        fileHandler.setFormatter(new SimpleFormatter());
        log.setUseParentHandlers(false);
        log.addHandler(fileHandler);
        log.addHandler(console);

        PreLaunchCheck.checkSpecs();
        if (PreLaunchCheck.checkFiles()){
            return;
        }
        log.info("[Starting] Hotplate");

        //Loading the user's settings into HotPlateApp
        if (!bypassSaveSettingsDeBug) {
            try {
                SaveSettings ss = (SaveSettings) ResourceManager.load(HotPlateApp.saveSettingsPathFile);
                HotPlateApp.userName = ss.userName;
                HotPlateApp.restaurantName = ss.restaurantName;
                HotPlateApp.automaticallyLoadData = ss.autoLoadData;
                HotPlateApp.pinNumber = ss.pin;
                HotPlateApp.warnMessage = ss.warnMessage;
                HotPlateApp.callMessage = ss.callMessage;
                HotPlateApp.britishTime = ss.britishTime;}
            catch(Exception e){ log.severe("[Fail] Loading User's Settings: " + e); launchLogError("[Fail] Loading User's Settings: " + e); return;}
            finally {log.info("[Success] Loading User's Settings");}
        }
        //Loading the customer's data into HotPlateApp if Auto-Load is turned on.
        if (!bypassSaveCustomersDebug) {
            try {
                SaveData sd = (SaveData) ResourceManager.load(HotPlateApp.customerDataPathFile);
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
            } catch (Exception e) {
                log.severe("[Fail] Loading Customer's Data: " + e); launchLogError("[Fail] Loading Customer's Data: " + e); return;
            } finally {log.info("[Success] Loading Customer's Data");}
        }

        stage.setResizable(false);
        stage.setTitle("HotPlate");
        try {stage.getIcons().add(new Image(HotPlateApp.class.getResourceAsStream("HotPlateLogo.png")));} catch(Exception e) {log.warning("Failure to load the HotPlate logo: " + e);}
        launchCustomerPortal();
        stage.show();
        log.info("[Success] Starting HotPlate");
    }

    public static void main(String[] args) {
        console.setLevel(Level.SEVERE);
        launch();
    }


    /* SCENE AND CONTROLLER CREATION */

    public static void launchCustomerPortal() throws IOException {
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(HotPlateApp.class.getResource("customerPortal.fxml"));
        Scene customerPortalScene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setScene(customerPortalScene);}
        catch(Exception e) {log.severe("[Fail] Loading Customer's Screen: " + e); launchLogError("[Fail] Loading Customer's Screen: " + e); return;}
        finally {log.info("[Success] Loading Customer's Screen");}
    }


    public static void launchReserveSeatPortal() throws IOException {
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(HotPlateApp.class.getResource("reserveSeatPortal.fxml"));
        Scene reserveSeatScene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setScene(reserveSeatScene);}
        catch(Exception e){log.severe("[Fail] Loading Reservation Screen: " + e); launchLogError("[Fail] Loading Reservation Screen: " + e); return;}
        finally {log.info("[Success] Loading Reservation Screen");}
    }

    public static Scene confirmationPageScene;
    public static void launchConfirmationPage() throws IOException {
        try{
        if (confirmationPageScene == null){
            FXMLLoader fxmlLoader = new FXMLLoader(HotPlateApp.class.getResource("confirmationPage.fxml"));
            confirmationPageScene = new Scene(fxmlLoader.load(), 600, 600);
        }
        stage.setScene(confirmationPageScene);}
        catch(Exception e){log.severe("[Fail] Loading Confirmation Page: " + e); launchLogError("[Fail] Loading Confirmation Page: " + e); return;}
        finally {log.info("[Success] Loading Confirmation Page");}
    }

    public static void launchSignInPage() throws IOException {
        try{
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("signInPage.fxml"));
        Scene signInScene = new Scene(fxml.load(), 600, 600);
        stage.setScene(signInScene);}
        catch(Exception e){log.severe("[Fail] Loading Sign In Page: " + e); launchLogError("[Fail] Loading Sign In Page: " + e); return;}
        finally {log.info("[Success] Loading Sign In Page");}
    }

    public static Scene aboutMeScene;
    public static boolean aboutMeCustomerOrgin;
    public static void launchAboutMePage() throws IOException {
        try{
        if (aboutMeScene == null){
            FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("aboutMePage.fxml"));
            aboutMeScene = new Scene(fxml.load(), 600, 600);
        }
        stage.setScene(aboutMeScene);}
        catch(Exception e){log.severe("[Fail] Loading About Me Page: " + e); launchLogError("[Fail] Loading About Me Page: " + e); return;}
        finally {log.info("[Success] Loading About Me Page");}
    }

    public static void launchAdminPortal(boolean updatePage) throws IOException {
        try{
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("adminPage.fxml"));
        Scene adminPortalScene = new Scene(fxml.load(), 600, 600);
        stage.setScene(adminPortalScene);}
        catch(Exception e){log.severe("[Fail] Loading Admin Portal: " + e); launchLogError("[Fail] Loading Admin Portal: " + e); return;}
        finally {log.info("[Success] Loading Admin Portal");}
    }

    public static void launchCustomMessagePage() throws IOException {
        try{
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("customMessagePage.fxml"));
        Scene customMessagePageScene = new Scene(fxml.load(), 600, 600);
        stage.setScene(customMessagePageScene);}
        catch(Exception e){log.severe("[Fail] Loading Custom Message Settings: " + e); launchLogError("[Fail] Loading Custom Message Settings: " + e); return;}
        finally {log.info("[Success] Loading Custom Message Settings");}
    }

    public static Stage adminAddToTableStage;
    public static void launchAdminAddToTable() throws IOException {
        try{
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("adminAddItem.fxml"));
        Scene adminAddToTableScene = new Scene(fxml.load(), 600, 400);
        adminAddToTableStage = new Stage();
        adminAddToTableStage.setScene(adminAddToTableScene);
        adminAddToTableStage.setTitle("Add Item");
        adminAddToTableStage.show();}
        catch(Exception e){log.severe("[Fail] Loading Add To Table Menu: " + e); launchLogError("[Fail] Loading Add To Table Menu: " + e); return;}
        finally {log.info("[Success] Loading Add To Table Menu");}
    }

    public static Stage adminEditToTableStage;
    public static Customer selectedCustomer;
    public static void launchAdminEditToTable() throws IOException {
        try{
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("adminEditItem.fxml"));
        Scene adminEditToTableScene = new Scene(fxml.load(), 600, 400);
        adminEditToTableStage = new Stage();
        adminEditToTableStage.setScene(adminEditToTableScene);
        adminEditToTableStage.setTitle("Edit Item");
        adminEditToTableStage.show();}
        catch(Exception e){log.severe("[Fail] Loading Edit To Table Menu: " + e); launchLogError("[Fail] Loading Edit To Table Menu: " + e); return;}
        finally {log.info("[Success] Loading Edit To Table Menu");}
    }

    public static void launchSettings() throws IOException {
        try{
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("settings.fxml"));
        Scene settingsScene = new Scene(fxml.load(), 600,600);
        stage.setScene(settingsScene);}
        catch(Exception e){log.severe("[Fail] Loading Settings Page: " + e); launchLogError("[Fail] Loading Settings Page: " + e); return;}
        finally {log.info("[Success] Loading Settings Page");}
    }

    public static Stage callCustomerStage;
    public static boolean callCustomerBoolean;
    public static Customer callCustomerClass;

    public static void launchCallCustomer(boolean callCustomer, Customer customer) throws IOException {
        try{
        callCustomerBoolean = callCustomer;
        callCustomerClass = customer;
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("callPage.fxml"));
        Scene callCustomerScene = new Scene(fxml.load(), 600, 400);
        callCustomerStage = new Stage();
        if (callCustomer){
            callCustomerStage.setTitle("Call Customer");
        } else{
            callCustomerStage.setTitle("Warn Customer");
        }
        callCustomerStage.setScene(callCustomerScene);
        callCustomerStage.show();}
        catch(Exception e){log.severe("[Fail] Loading Call Customer Page: " + e); launchLogError("[Fail] Loading Call Customer Page: " + e); return;}
        finally {log.info("[Success] Loading Call Customer Page");}
    }

    public static Stage logErrorStage;
    public static String errorLogMessage;
    public static void launchLogError(String errorMessage){
        HotPlateApp.endTime = true;
        errorLogMessage = errorMessage;
        logErrorStage = new Stage();
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("errorLogPage.fxml"));
        Scene scene = null;
        if (stage != null){
            stage.close();
        }
        try {
            scene = new Scene(fxml.load(), 600, 600);
        } catch (IOException e) {
            log.severe("Can not load error log page for: " + e + "\n Message: " + errorMessage);
            return;
        }
        logErrorStage.setTitle("Error");
        logErrorStage.setScene(scene);
        logErrorStage.show();
    }

}