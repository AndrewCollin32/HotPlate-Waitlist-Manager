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

public final class AdminEditItemController implements Initializable {

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
            Pattern timePattern = Pattern.compile((HotPlateApp.britishTime)? "^(2[0123]|[01]\\d):[012345]\\d$":"^[012]\\d:[012345]\\d\\s[P|A]M$");
            Matcher timeMatch = timePattern.matcher(adminCustomTimeText.getText());
            if (!timeMatch.matches()){
                AlertBox.createAlertBox("Time Error", "Please enter a time in this format (hh:mm [PM or AM])");
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
            HotPlateApp.log.warning("[Fail] User failed to enter the correct party size: " +e );
            AlertBox.createAlertBox("Number Error", "Please provide a party size for \n this reservation");
            return;
        }
        HotPlateApp.log.info("[Success] Party size successfully loaded");
        if (!match.matches()){
            HotPlateApp.log.warning("[Fail] User failed to enter the correct phone number format");
            AlertBox.createAlertBox("Phone Number Error", "Please enter a valid phone number");
        }
        else if (adminAddName.getText().length() == 0){
            HotPlateApp.log.warning("[Fail] User didn't enter a name");
            AlertBox.createAlertBox("Name Error", "Please provide a name for \n this reservation");

        }
        else if (adminAddPartySize.getText().length() == 0) {
            HotPlateApp.log.warning("[Fail] User failed to enter party size");
            AlertBox.createAlertBox("Party Size Error", "Please provide a valid integer for \n the party size");
        }
        else{
            HotPlateApp.customerData.remove(HotPlateApp.selectedCustomer);
            String phoneNumber = match.group(1) + match.group(2) + match.group(3);
            HotPlateApp.customerData.add(new Customer(adminAddName.getText(), adminAddPartySize.getText(),
                    timeSelected, phoneNumber));
            HotPlateApp.waitListSize++;
            HotPlateApp.adminEditToTableStage.close();

            HotPlateApp.endTime=true;
            HotPlateApp.log.info("[Success] Customer's data was successfully saved");
            HotPlateApp.launchAdminPortal();
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
