package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    private RadioButton britishTimeSelection;

    @FXML
    private RadioButton americanTimeSelection;

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
    void settingsSaveButton(ActionEvent event) throws ParseException {
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

        if (HotPlateApp.customerData.size() != 0){

            SimpleDateFormat britishTime = new SimpleDateFormat("HH:mm");
            SimpleDateFormat americanTime = new SimpleDateFormat("hh:mm a");

            boolean isBritishTime = false;

            try{
                americanTime.parse(HotPlateApp.customerData.get(0).getTimeWaited());
            }
            catch(Exception e){
                isBritishTime = true;
            }

            if (britishTimeSelection.isSelected() && !isBritishTime || !britishTimeSelection.isSelected() && isBritishTime){
                for (int i = 0; i < HotPlateApp.customerData.size(); i++){
                    Customer customer = HotPlateApp.customerData.get(i);
                    String time = customer.getTimeWaited();
                    Date date = ((britishTimeSelection.isSelected())?americanTime:britishTime).parse(time);
                    String newDate = ((britishTimeSelection.isSelected())?britishTime:americanTime).format(date);
                    customer.setTimeWaited(newDate);
                }
            }
        }

        HotPlateApp.userName = settingsName.getText();
        HotPlateApp.restaurantName = settingsRestaurant.getText();
        HotPlateApp.pinNumber = settingsPin.getText();
        HotPlateApp.automaticallyLoadData = loadCheckBox.isSelected();
        HotPlateApp.britishTime = britishTimeSelection.isSelected();

        SaveSettings ss = new SaveSettings(HotPlateApp.userName, HotPlateApp.restaurantName, HotPlateApp.pinNumber, HotPlateApp.automaticallyLoadData, HotPlateApp.warnMessage, HotPlateApp.callMessag, HotPlateApp.britishTime);
        ResourceManager.save(ss, HotPlateApp.saveSettingsPathFile);

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
        loadCheckBox.setSelected(HotPlateApp.automaticallyLoadData);
        if (HotPlateApp.britishTime){
            britishTimeSelection.setSelected(true);
        }
    }
}
