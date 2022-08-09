package com.hotplate.hotplate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class YesNoBox {
    public static Stage stage;
    public static boolean boxChoice;

    public boolean createAlert(String title, String message){
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("yesNoBox.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxml.load(), 400, 200);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AlertBoxController abc = fxml.getController();
        abc.YesNoBoxLabel.setText(message);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.showAndWait();
        return boxChoice;
    }
}