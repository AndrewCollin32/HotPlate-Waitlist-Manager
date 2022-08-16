package com.hotplate.hotplate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AlertBox {
    public static Stage stage;
    public AlertBox(String title, String message) throws IOException {
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("AlertBox.fxml"));
        Scene scene = new Scene(fxml.load(), 400, 200);
        AlertBoxController abc = fxml.getController();
        abc.alertlabel.setText(message);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
