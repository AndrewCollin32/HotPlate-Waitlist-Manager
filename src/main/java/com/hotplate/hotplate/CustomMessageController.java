package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomMessageController implements Initializable {

    @FXML
    private TextArea callMessage;

    @FXML
    private TextArea warnMessage;

    @FXML
    void messageEditorCancel(ActionEvent event) throws IOException {
        HotPlateApp.launchAdminPortal(false);
    }

    @FXML
    void messageEditorSave(ActionEvent event) throws IOException {
        HotPlateApp.warnMessage = warnMessage.getText();
        HotPlateApp.callMessage = callMessage.getText();

        SaveSettings ss = new SaveSettings(HotPlateApp.userName, HotPlateApp.restaurantName, HotPlateApp.pinNumber, HotPlateApp.automaticallyLoadData, HotPlateApp.warnMessage, HotPlateApp.callMessage, HotPlateApp.britishTime);
        ResourceManager.save(ss, HotPlateApp.saveSettingsPathFile);
        HotPlateApp.launchAdminPortal(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warnMessage.setText(HotPlateApp.warnMessage);
        callMessage.setText(HotPlateApp.callMessage);
    }
}
