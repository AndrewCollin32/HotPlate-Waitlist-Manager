package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class registerController {

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField restaurantNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField yourNameTextField;

    @FXML
    void cancelButton(ActionEvent event) {
        HotPlateApp.launchCustomerPortal();
    }

    @FXML
    void createAccountButton(ActionEvent event) throws IOException {
        if (usernameTextField.getText().length() == 0||passwordTextField.getText().length() == 0|| yourNameTextField.getText().length() == 0|| restaurantNameTextField.getText().length() == 0){
            AlertBox.createAlertBox("Empty Textfield", "Please enter for every textfield");
            return;
        }
        if (HotPlateApp.loadSQL.userNameExist(usernameTextField.getText())){
            AlertBox.createAlertBox("Username Exist", "The username already exists");
            return;
        }
        if (passwordTextField.getText().length() < 4){
            AlertBox.createAlertBox("Password Error", "Please choose a password that is at least 4 characters");
            return;
        }

        HotPlateApp.loadSQL.createNewAccount(usernameTextField.getText(), passwordTextField.getText(), yourNameTextField.getText(), restaurantNameTextField.getText());
        HotPlateApp.launchSignInPage();
        AlertBox.createAlertBox("Success", "Your account was created successfully");

    }

}
