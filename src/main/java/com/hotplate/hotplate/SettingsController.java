package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private ToggleGroup darkLight;

    @FXML
    private RadioButton settingsDarkMode;

    @FXML
    private RadioButton settingsLightMode;

    @FXML
    private TextField settingsName;

    @FXML
    private PasswordField settingsPin;

    @FXML
    private TextField settingsRestaurant;

    @FXML
    private CheckBox loadCheckBox;

    @FXML
    void settingsBrowse(ActionEvent event) {

    }

    @FXML
    void settingsCancelButton(ActionEvent event) {
        try {
            AdminPage.homePage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void settingsSaveButton(ActionEvent event) {
        if (loadCheckBox.isSelected()){
            HotPlateApp.automaticallyLoadData = true;
        }
        else {
            HotPlateApp.automaticallyLoadData = false;
        }
        if (settingsName.getText().length() == 0 ||
        settingsPin.getText().length() == 0 ||
        settingsRestaurant.getText().length() == 0){
            try {
                new AlertBox("Error", "One of the text fields are empty");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        else if (settingsPin.getText().length() < 4){
            try {
                new AlertBox("Error", "Please make sure that the pin is at least 4 digits");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        HotPlateApp.userName = settingsName.getText();
        HotPlateApp.restaurantName = settingsRestaurant.getText();
        HotPlateApp.pinNumber = settingsPin.getText();

        try {
            AdminPage.homePage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settingsName.setText(HotPlateApp.userName);
        settingsRestaurant.setText(HotPlateApp.restaurantName);
        settingsPin.setText(HotPlateApp.pinNumber);
    }
}
