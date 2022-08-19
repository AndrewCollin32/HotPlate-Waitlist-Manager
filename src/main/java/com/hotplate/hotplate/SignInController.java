package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML
    private TextField signInTextPin;

    @FXML
    void signInClosePress(ActionEvent event) throws IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.launchCustomerPortal();
    }

    @FXML
    void signInSubmitPress(ActionEvent event) throws IOException {

        HotPlateApp.log.info("[Button] Clicked: " + event);
        if (!signInTextPin.getText().equals(HotPlateApp.pinNumber)){
            AlertBox.createAlertBox("Pin Error", "You've entered the wrong pin");
            return;
        }
        HotPlateApp.launchAdminPortal();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO IMPORTANT, Must delete this alert box for non-demo version
        HotPlateApp.log.info("Displaying alert box with the pin");
        try {
            AlertBox.createAlertBox("Your Pin number", "Your pin number is: " + HotPlateApp.pinNumber );
        } catch (IOException e) {
            HotPlateApp.log.severe("[Fail] Couldn't open alert box" + e);
            HotPlateApp.launchLogError("[Fail] Couldn't open alert box: " + e);
        }
    }
}

