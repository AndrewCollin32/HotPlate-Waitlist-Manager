package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class ConfirmationPageController {

    @FXML
    public Label confirmationWelcomeLabel;

    @FXML
    void confirmationPageOkButton(ActionEvent event) throws IOException {
        HotPlateApp.launchCustomerPortal();
    }

}
