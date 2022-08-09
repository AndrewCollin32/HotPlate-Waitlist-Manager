package com.hotplate.hotplate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignInController {

    @FXML
    private TextField signInTextPin;

    @FXML
    void signInClosePress(ActionEvent event) throws IOException {
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("customerPortal.fxml"));
        Parent root = fxml.load();
        customerPortalController cpc = fxml.getController();
        cpc.customerPortalWaitlistLabel.setText("Current waitlist: " + HotPlateApp.waitListSize);
        HotPlateApp.stage.setScene(new Scene(root, 600, 600));
        HotPlateApp.stage.show();
    }

    @FXML
    void signInSubmitPress(ActionEvent event) throws IOException {
        if (!signInTextPin.getText().equals(HotPlateApp.pinNumber)){
            new AlertBox("Pin Error", "You've entered the wrong pin");
            return;
        }
        AdminPage.homePage();
    }

}

