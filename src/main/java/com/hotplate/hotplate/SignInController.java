package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class SignInController implements Initializable {


    @FXML
    private PasswordField signInPassword;

    @FXML
    private TextField signInUsername;

    @FXML
    void registerButtonPress(ActionEvent event) {
        if (HotPlateApp.useSQL) {
            HotPlateApp.launchRegister();
        }
        else {
            AlertBox.createAlertBox("Error", "Because SQL is disabled, you can not register an account.");
        }
    }

    @FXML
    void signInClosePress(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.launchCustomerPortal();
    }

    @FXML
    void signInSubmitPress(ActionEvent event) {

        HotPlateApp.log.info("[Button] Clicked: " + event);

        if (HotPlateApp.useSQL){
            if (!HotPlateApp.loadSQL.loadSQLSettings(signInUsername.getText(), signInPassword.getText())){
                AlertBox.createAlertBox("Error", "You've entered the wrong username or password");
                return;
            }
        }
        else {
            if (!signInUsername.getText().equals(HotPlateApp.userUsername) || !signInPassword.getText().equals(HotPlateApp.userPassword)) {
                AlertBox.createAlertBox("Sign In Error", "You've entered your username or password incorrectly");
                return;
            }
        }
        HotPlateApp.launchAdminPortal();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!HotPlateApp.useSQL) {
            AlertBox.createAlertBox("UserInfo", "Your current username is: " + HotPlateApp.userUsername + "\n and your password is: " + HotPlateApp.userPassword);
        }
    }
}

