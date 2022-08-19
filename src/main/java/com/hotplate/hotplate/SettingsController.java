package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
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
    void settingsCancelButton(ActionEvent event) throws IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.launchAdminPortal();
    }

    @FXML
    void settingsSaveButton(ActionEvent event) throws ParseException, IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.log.info("[Start] Saving Settings Data");
        if (settingsName.getText().length() == 0 ||
        settingsPin.getText().length() == 0 ||
        settingsRestaurant.getText().length() == 0){
            try {
                HotPlateApp.log.warning("[Fail] User failed to enter for one text field");
                AlertBox.createAlertBox("Error", "One of the text fields are empty");
            } catch (IOException e) {
                HotPlateApp.log.severe("[Fail] Couldn't open alert box" + e);
                HotPlateApp.launchLogError("[Fail] Couldn't open alert box: " + e);
            }
            return;
        }
        else if (settingsPin.getText().length() < 4){
            try {
                HotPlateApp.log.warning("[Fail] User failed to enter the correct pin");
                AlertBox.createAlertBox("Error", "Please make sure that the pin is at least 4 digits");
            } catch (IOException e) {
                HotPlateApp.log.severe("[Fail] Couldn't open alert box" + e);
                HotPlateApp.launchLogError("[Fail] Couldn't open alert box: " + e);
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

        SaveSettings ss = new SaveSettings(HotPlateApp.userName, HotPlateApp.restaurantName, HotPlateApp.pinNumber, HotPlateApp.automaticallyLoadData, HotPlateApp.warnMessage, HotPlateApp.callMessage, HotPlateApp.britishTime);
        ResourceManager.save(ss, HotPlateApp.saveSettingsPathFile);

        HotPlateApp.log.info("[Success] Saving Settings Data");
        HotPlateApp.launchAdminPortal();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HotPlateApp.log.info("[Start] Loading settings data");
        settingsName.setText(HotPlateApp.userName);
        settingsRestaurant.setText(HotPlateApp.restaurantName);
        settingsPin.setText(HotPlateApp.pinNumber);
        loadCheckBox.setSelected(HotPlateApp.automaticallyLoadData);
        if (HotPlateApp.britishTime){
            britishTimeSelection.setSelected(true);
        }
        HotPlateApp.log.info("[Success] Loading settings data");
    }
}
