package com.hotplate.hotplate;


import javafx.fxml.FXMLLoader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreLaunchCheck {
    public static void checkSpecs(){

        final String[] total = {""};
        System.getProperties().forEach((e,a) -> total[0] += "\n" + e + " " + a);
        HotPlateApp.log.info(
                total[0] +
                "\nFree Memory: " + Runtime.getRuntime().freeMemory() +
                "\nTotal Memory: " + Runtime.getRuntime().totalMemory() +
                "\nMax Memory: " + Runtime.getRuntime().maxMemory() +
                "\nAvailable Cores: " + Runtime.getRuntime().availableProcessors());
        Matcher matcher = Pattern.compile("^(\\d+)\\.\\d+\\.\\d+$").matcher(System.getProperty("java.version"));
        matcher.find();
        int javaVersion = Integer.parseInt(matcher.group(1));
        if (javaVersion < 15){
            HotPlateApp.log.warning("[Warning] Running Outdated Java Version: " + System.getProperty("java.version") + " HotPlate requires: 15.0.2 and up");
        }
        else {
            HotPlateApp.log.info("[Success] Java version is up to date");
        }
    }

    public static boolean checkFiles(){
        String[] fxmlList = new String[]{
                "aboutMePage.fxml",
                "adminAddItem.fxml",
                "adminEditItem.fxml",
                "adminPage.fxml",
                "AlertBox.fxml",
                "callPage.fxml",
                "confirmationPage.fxml",
                "customerPortal.fxml",
                "customMessagePage.fxml",
                "errorLogPage.fxml",
                "reserveSeatPortal.fxml",
                "settings.fxml",
                "signInPage.fxml",
                "yesNoBox.fxml"
        };
        HotPlateApp.log.info("[Start] Scanning all required FXML Files");
        for (String str:fxmlList) {
            if (HotPlateApp.class.getResource(str) == null) {
                HotPlateApp.log.severe("Can not find file: " + str);
                HotPlateApp.launchLogError("Can not find file: " + str);
                return true;
            }
        }
        HotPlateApp.log.info("[Success] All FXML files are present");
        HotPlateApp.log.info("[Start] Scanning all required Java class files");

        String[] classList = new String[]{
                "AboutMeController",
                "AdminAddItemController",
                "AdminController",
                "AdminEditItemController",
                "AlertBox" ,
                "AlertBoxController",
                "ConfirmationPageController",
                "Customer",
                "CustomerPortalController",
                "CustomMessageController",
                "HotPLateApp",
                "LogErrorPage",
                "PreLaunchCheck",
                "ReserveSeatPortalController",
                "ResourceManager",
                "SaveData",
                "SaveSettings",
                "SignInController",
                "TextMessageWindow",
                "YesNoBox"
        };

        for (String str : classList){
            if(!(new File("src/main/java/com/hotplate/hotplate/" + str + ".java").exists())){
                HotPlateApp.log.severe("[Fail] Unable to find file: " + str + ".java");
                HotPlateApp.launchLogError("[Fail] Unable to find file: " + str + ".java");
                return true;
            }
        }
        HotPlateApp.log.info("[Success] All Java class files are present");
        return false;
    }

}
