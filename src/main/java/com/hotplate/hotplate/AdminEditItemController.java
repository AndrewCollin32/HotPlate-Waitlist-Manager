package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminEditItemController implements Initializable {

    @FXML
    public TextField adminAddName;

    @FXML
    public TextField adminAddPartySize;

    @FXML
    public TextField adminAddPhone;

    @FXML
    public CheckBox adminCheckBox;

    @FXML
    public TextField adminCustomTimeText;

    @FXML
    void adminCancelButton(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.adminEditToTableStage.close();
    }

    boolean checkBox = false;

    @FXML
    void adminCurrentTimeCheckBox(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        if (!checkBox){
            adminCustomTimeText.setText("");
            adminCustomTimeText.setVisible(false);
            checkBox = true;
        }
        else{
            adminCustomTimeText.setVisible(true);
            checkBox = false;
        }
    }

    @FXML
    void adminSaveButton(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.log.info("[Starting] Saving Customer Data");
        String timeSelected;
        if (adminCheckBox.isSelected()){
            SimpleDateFormat sdf = new SimpleDateFormat((HotPlateApp.britishTime)? "HH:mm": "hh:mm a");
            timeSelected = sdf.format(new Date());
        }
        else{
            timeSelected = adminCustomTimeText.getText();
            Pattern timePattern = Pattern.compile((HotPlateApp.britishTime)? "^(2[0123]|[01]\\d):[012345]\\d$":"^[012]\\d:[012345]\\d\\s[PM|AM]$");
            Matcher timeMatch = timePattern.matcher(adminCustomTimeText.getText());
            if (!timeMatch.matches()){
                try {
                    AlertBox ab = new AlertBox("Time Error", "Please enter a time in this format (hh:mm [PM or AM])");
                } catch (IOException e) {
                    HotPlateApp.log.severe("[Fail] Couldn't open alert box" + e);
                    HotPlateApp.launchLogError("[Fail] Couldn't open alert box: " + e);
                }
                return;
            }
        }
        HotPlateApp.log.info("[Success] Time successfully saved");
        Pattern pattern = Pattern.compile("^\\(?(\\d{3})\\)?-?(\\d{3})-?(\\d{4})$");
        Matcher match = pattern.matcher(adminAddPhone.getText());

        try{
            Integer.parseInt(adminAddPartySize.getText());
        }
        catch(Exception e){
            try {
                HotPlateApp.log.warning("[Fail] User failed to enter the correct party size: " +e );
                AlertBox ab = new AlertBox("Number Error", "Please provide a party size for \n this reservation");
                return;
            } catch (IOException j) {
                HotPlateApp.log.severe("[Fail] Couldn't open alert box" + j);
                HotPlateApp.launchLogError("[Fail] Couldn't open alert box: " + j);
            }
            return;
        }
        HotPlateApp.log.info("[Success] Party size successfully loaded");
        if (!match.matches()){
            try {
                HotPlateApp.log.warning("[Fail] User failed to enter the correct phone number format");
                AlertBox ab = new AlertBox("Phone Number Error", "Please enter a valid phone number");
            } catch (IOException e) {
                HotPlateApp.log.severe("[Fail] Couldn't open alert box" + e);
                HotPlateApp.launchLogError("[Fail] Couldn't open alert box: " + e);
            }
            return;
        }
        else if (adminAddName.getText().length() == 0){
            try {
                HotPlateApp.log.warning("[Fail] User didn't enter a name");
                AlertBox ab = new AlertBox("Name Error", "Please provide a name for \n this reservation");
            } catch (IOException e) {
                HotPlateApp.log.severe("[Fail] Couldn't open alert box" + e);
                HotPlateApp.launchLogError("[Fail] Couldn't open alert box: " + e);
            }
            return;
        }
        else if (adminAddPartySize.getText().length() == 0) {
            try {
                HotPlateApp.log.warning("[Fail] User failed to enter party size");
                AlertBox ab = new AlertBox("Party Size Error", "Please provide a valid integer for \n the party size");
            } catch (IOException e) {
                HotPlateApp.log.severe("[Fail] Couldn't open alert box" + e);
                HotPlateApp.launchLogError("[Fail] Couldn't open alert box: " + e);
            }
            return;
        }
        else{
            HotPlateApp.customerData.remove(HotPlateApp.selectedCustomer);
            String phoneNumber = match.group(1) + match.group(2) + match.group(3);
            HotPlateApp.customerData.add(new Customer(adminAddName.getText(), adminAddPartySize.getText(),
                    timeSelected, phoneNumber));
            HotPlateApp.waitListSize++;
            HotPlateApp.adminEditToTableStage.close();

            try {
                HotPlateApp.endTime=true;
                HotPlateApp.log.info("[Success] Customer's data was successfully saved");
                HotPlateApp.launchAdminPortal(true);
            } catch (IOException e) {
                HotPlateApp.log.severe("[Fail] Couldn't open Admin Page" + e);
                HotPlateApp.launchLogError("[Fail] Couldn't open Admin Page: " + e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HotPlateApp.log.info("[Start] Loading customer's data to edit page");
        adminAddName.setText(HotPlateApp.selectedCustomer.getName());
        adminAddPartySize.setText(HotPlateApp.selectedCustomer.getPartySize());
        adminAddPhone.setText(HotPlateApp.selectedCustomer.getPhoneNumber());
        adminCustomTimeText.setText(HotPlateApp.selectedCustomer.getTimeWaited());
        HotPlateApp.log.info("[Success] Customer's data successfully loaded");
    }
}
