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
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("customerPortal.fxml"));
        Parent root = fxml.load();
        HotPlateApp.waitListSize++;
        CustomerPortalController cpc = fxml.getController();
        cpc.customerPortalWaitlistLabel.setText("Current waitlist: " + HotPlateApp.waitListSize);
        HotPlateApp.stage.setScene(new Scene(root, 600, 600));
        HotPlateApp.stage.show();
    }

}
