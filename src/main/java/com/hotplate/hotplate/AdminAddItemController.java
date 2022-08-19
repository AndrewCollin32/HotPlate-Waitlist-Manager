package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminAddItemController {

    @FXML
    private TextField adminAddName;

    @FXML
    private TextField adminAddPartySize;

    @FXML
    private TextField adminAddPhone;

    @FXML
    private CheckBox adminCheckBox;

    @FXML
    private TextField adminCustomTimeText;

    @FXML
    void adminCancelButton(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        HotPlateApp.adminAddToTableStage.close();
    }

    boolean checkBox = false;

    @FXML
    void adminCurrentTimeCheckBox(ActionEvent event) {
        HotPlateApp.log.info("[CheckBox] Clicked: " + event.toString());
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
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        HotPlateApp.log.info("[Saving] Data: " + adminAddName.getText() + " " + adminAddPhone.getText() + " " + adminAddPartySize.getText() + " Checkbox:" + adminCheckBox.isSelected());
        HotPlateApp.log.info("[Saving] Starting to save data");
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
                try {
                    AlertBox.createAlertBox("Time Error", "Please enter a time in this format (hh:mm [PM or AM])");
                    HotPlateApp.log.warning("[Saving] [Warning] because of wrong time format");
                } catch (Exception e) {
                    HotPlateApp.log.severe("[Fail] Can't launch AlertBox: " + e);
                    HotPlateApp.launchLogError("[Fail] Can't launch AlertBox: " + e);
                }
                return;
            }
        }
        HotPlateApp.log.info("[Saving] [Success] Time is in the correct format");
        Pattern pattern = Pattern.compile("^\\(?(\\d{3})\\)?-?(\\d{3})-?(\\d{4})$");
        Matcher match = pattern.matcher(adminAddPhone.getText());

        try{
            Integer.parseInt(adminAddPartySize.getText());
        }
        catch(Exception e){
            try {
                AlertBox.createAlertBox("Name Error", "Please enter an integer for party size for \n this reservation");
                HotPlateApp.log.warning("[Saving] [Warning] User didn't enter correct party size");
            } catch (Exception j) {
                HotPlateApp.log.severe("[Fail] Can't launch AlertBox: " + e);
                HotPlateApp.launchLogError("[Fail] Can't launch AlertBox: " + e);
            }
            return;
        }
        if (!match.matches()){
            try {
                AlertBox.createAlertBox("Phone Number Error", "Please enter a valid phone number");
                HotPlateApp.log.warning("[Saving] [Warning] User didn't enter correct phone number");
            } catch (Exception e) {
                HotPlateApp.log.severe("[Fail] Can't launch AlertBox: " + e);
                HotPlateApp.launchLogError("[Fail] Can't launch AlertBox: " + e);
            }
        }
        else if (adminAddName.getText().length() == 0){
            try {
                AlertBox.createAlertBox("Name Error", "Please provide a name for \n this reservation");
                HotPlateApp.log.warning("[Saving] [Warning] User didn't enter a name");
            } catch (Exception e) {
                HotPlateApp.log.severe("[Fail] Can't launch AlertBox: " + e);
                HotPlateApp.launchLogError("[Fail] Can't launch AlertBox: " + e);
            }
        }
        else if (adminAddPartySize.getText().length() == 0) {
            try {
                AlertBox.createAlertBox("Party Size Error", "Please provide a valid integer for \n the party size");
                HotPlateApp.log.warning("[Saving] [Warning] User didn't enter a valid party size");
            } catch (Exception e) {
                HotPlateApp.log.severe("[Fail] Can't launch AlertBox: " + e);
                HotPlateApp.launchLogError("[Fail] Can't launch AlertBox: " + e);
            }
        }
        else{
            String phoneNumber = match.group(1) + match.group(2) + match.group(3);
            HotPlateApp.customerData.add(new Customer(adminAddName.getText(), adminAddPartySize.getText(),
                    timeSelected, phoneNumber));
            HotPlateApp.waitListSize++;
            HotPlateApp.adminAddToTableStage.close();

            try {
                HotPlateApp.endTime=true;
                HotPlateApp.launchAdminPortal();
            } catch (Exception e) {
                HotPlateApp.log.severe("[Fail] Can't launch AdminPortal: " + e);
                HotPlateApp.launchLogError("[Fail] Can't launch AdminPortal: " + e);
            } finally{
            HotPlateApp.log.info("[Saving] [Success] All data was saved correctly");}
        }
    }

}
