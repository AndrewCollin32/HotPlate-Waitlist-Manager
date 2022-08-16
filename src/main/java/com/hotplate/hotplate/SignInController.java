package com.hotplate.hotplate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML
    private TextField signInTextPin;

    @FXML
    void signInClosePress(ActionEvent event) throws IOException {
        HotPlateApp.launchCustomerPortal();
    }

    @FXML
    void signInSubmitPress(ActionEvent event) throws IOException {
        if (!signInTextPin.getText().equals(HotPlateApp.pinNumber)){
            new AlertBox("Pin Error", "You've entered the wrong pin");
            return;
        }
        HotPlateApp.launchAdminPortal(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO IMPORTANT, Must delete this alert box for non-demo version
        try {
            AlertBox ab = new AlertBox("Your Pin number", "Your pin number is: " + HotPlateApp.pinNumber );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

