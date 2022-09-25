package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class CustomMessageController implements Initializable {

    @FXML
    private TextArea callMessage;

    @FXML
    private TextArea warnMessage;

    @FXML
    void messageEditorCancel(ActionEvent event) throws IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.launchAdminPortal();
    }

    @FXML
    void messageEditorSave(ActionEvent event) throws IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.log.info("[Starting] Saving custom messages");
        HotPlateApp.warnMessage = warnMessage.getText();
        HotPlateApp.callMessage = callMessage.getText();

        if (HotPlateApp.useSQL) {
            HotPlateApp.loadSQL.saveSQLSettings();
        }
        else {
            SaveSettings ss = new SaveSettings(HotPlateApp.userUsername, HotPlateApp.restaurantName, HotPlateApp.userUsername, HotPlateApp.userPassword, HotPlateApp.automaticallyLoadData, HotPlateApp.warnMessage, HotPlateApp.callMessage, HotPlateApp.britishTime);
            ResourceManager.save(ss, HotPlateApp.saveSettingsPathFile);
        }
        HotPlateApp.log.info("[Success] Saving custom messages");
        HotPlateApp.launchAdminPortal();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HotPlateApp.log.info("[Loading] Loading up messages");
        warnMessage.setText(HotPlateApp.warnMessage);
        callMessage.setText(HotPlateApp.callMessage);
    }
}
