package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TextMessageWindow {

    @FXML
    public Label textMessage;

    @FXML
    public Label textMessageRecieveLabel;

    @FXML
    void okButton(ActionEvent event) {
        AdminController.callStage.close();
    }

}
