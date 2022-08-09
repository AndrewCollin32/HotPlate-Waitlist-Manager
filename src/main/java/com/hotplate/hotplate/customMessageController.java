package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class customMessageController implements Initializable {

    @FXML
    private TextArea callMessage;

    @FXML
    private TextArea warnMessage;

    @FXML
    void messageEditorCancel(ActionEvent event) throws IOException {
        AdminPage.homePage();
    }

    @FXML
    void messageEditorSave(ActionEvent event) throws IOException {
        HotPlateApp.warnMessage = warnMessage.getText();
        HotPlateApp.callMessag = callMessage.getText();
        AdminPage.homePage();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warnMessage.setText(HotPlateApp.warnMessage);
        callMessage.setText(HotPlateApp.callMessag);
    }
}
