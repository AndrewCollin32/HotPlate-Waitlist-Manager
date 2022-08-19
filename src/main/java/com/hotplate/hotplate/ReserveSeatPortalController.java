package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReserveSeatPortalController implements Initializable {

    @FXML
    public TextField reserveSeatNameInput;

    @FXML
    public ChoiceBox<String> reserveSeatPartySizeChoice;

    @FXML
    public TextField reserveSeatPhoneInput;

    @FXML
    public ImageView reserveSeatPortalIcon;

    @FXML
    public Label reserveSeatPortalWelcomeLabel;

    @FXML
    public VBox reserveSeatVBox;

    @FXML
    void reserveSeatCancelPress(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        reserveSeatNameInput.setText("");
        reserveSeatPhoneInput.setText("");
        HotPlateApp.launchCustomerPortal();

    }

    @FXML
    void reserveSeatSubmitPress(ActionEvent event) throws IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.log.info("[Start] Saving customer's data");
        Pattern pattern = Pattern.compile("^\\(?(\\d{3})\\)?-?(\\d{3})-?(\\d{4})$");
        Matcher match = pattern.matcher(reserveSeatPhoneInput.getText());
        if (!match.matches()){
            HotPlateApp.log.warning("[Fail] Customer failed to enter the correct phone number" );
            AlertBox.createAlertBox("Phone Number Error", "Please enter a valid phone number");
        }
        else if (reserveSeatNameInput.getText().length() == 0){
            HotPlateApp.log.warning("[Fail] Customer failed to enter the correct name");
            AlertBox.createAlertBox("Name Error", "Please provide a name for \n this reservation");
        }
        else{
            HotPlateApp.waitListSize++;
            SimpleDateFormat sdf = new SimpleDateFormat((HotPlateApp.britishTime)? "HH:mm": "hh:mm a");
            String phoneNumber = match.group(1) + match.group(2) + match.group(3);
            HotPlateApp.customerData.add(new Customer(reserveSeatNameInput.getText(), reserveSeatPartySizeChoice.getSelectionModel().getSelectedItem(),
                    sdf.format(new Date()), phoneNumber));
            HotPlateApp.log.info("[Success] Saving customer's data");
            HotPlateApp.launchConfirmationPage();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HotPlateApp.log.info("[Start] Loading party size choices");
        for (int i = 1; i < 13; i++){
            reserveSeatPartySizeChoice.getItems().add(i + "");
        }
        reserveSeatPartySizeChoice.getSelectionModel().selectFirst();
        reserveSeatPortalWelcomeLabel.setText("Thank you for choosing " + HotPlateApp.restaurantName + "!");
        HotPlateApp.log.info("[Success] Loading party size choices");
    }
}
