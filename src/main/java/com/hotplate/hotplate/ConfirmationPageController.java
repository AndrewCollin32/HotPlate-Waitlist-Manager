package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConfirmationPageController {

    @FXML
    public Label confirmationWelcomeLabel;

    @FXML
    void confirmationPageOkButton(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.launchCustomerPortal();
    }

}
