package com.hotplate.hotplate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public final class AlertBox {
    public static Stage stage;
    public static void createAlertBox(String title, String message){
        HotPlateApp.log.info("[Alert] Alert box was called!");
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("AlertBox.fxml"));
        Scene scene = null;
        try{
            scene = new Scene(fxml.load(), 400, 200);
        }
        catch (Exception e){
            StringBuilder strBuilder = new StringBuilder("[Fail] Can't launch AlertBox: ");
            HotPlateApp.log.severe(strBuilder.append(e).toString());
            HotPlateApp.launchLogError(strBuilder.append(e).toString());
        }
        AlertBoxController abc = fxml.getController();
        abc.alertlabel.setText(message);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
