package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
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
        HotPlateApp.adminAddToTableStage.close();
    }

    boolean checkBox = false;

    @FXML
    void adminCurrentTimeCheckBox(ActionEvent event) {
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
                    throw new RuntimeException(e);
                }
                return;
            }
        }
        Pattern pattern = Pattern.compile("^\\(?(\\d{3})\\)?-?(\\d{3})-?(\\d{4})$");
        Matcher match = pattern.matcher(adminAddPhone.getText());

        try{
            Integer.parseInt(adminAddPartySize.getText());
        }
        catch(Exception e){
            try {
                AlertBox ab = new AlertBox("Name Error", "Please provide a name for \n this reservation");
            } catch (IOException j) {
                throw new RuntimeException(e);
            }
            return;
        }
        if (!match.matches()){
            try {
                AlertBox ab = new AlertBox("Phone Number Error", "Please enter a valid phone number");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        else if (adminAddName.getText().length() == 0){
            try {
                AlertBox ab = new AlertBox("Name Error", "Please provide a name for \n this reservation");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        else if (adminAddPartySize.getText().length() == 0) {
            try {
                AlertBox ab = new AlertBox("Party Size Error", "Please provide a valid integer for \n the party size");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        else{
            String phoneNumber = match.group(1) + match.group(2) + match.group(3);
            HotPlateApp.customerData.add(new Customer(adminAddName.getText(), adminAddPartySize.getText(),
                    timeSelected, phoneNumber));
            HotPlateApp.waitListSize++;
            HotPlateApp.adminAddToTableStage.close();

            try {
                AdminController.endTime=true;
                HotPlateApp.launchAdminPortal(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
