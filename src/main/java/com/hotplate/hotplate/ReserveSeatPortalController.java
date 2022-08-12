package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReserveSeatPortalController{

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
    void reserveSeatCancelPress(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HotPlateApp.class.getResource("customerPortal.fxml"));
        reserveSeatNameInput.setText("");
        reserveSeatPhoneInput.setText("");
        HotPlateApp.scene = new Scene(fxmlLoader.load(), 600,600);
        customerPortalController cpc = fxmlLoader.getController();
        cpc.customerPortalWaitlistLabel.setText("Current waitlist: " + HotPlateApp.waitListSize);
        HotPlateApp.stage.setScene(HotPlateApp.scene);
        HotPlateApp.stage.show();
    }

    @FXML
    void reserveSeatSubmitPress(ActionEvent event) throws IOException {
        Pattern pattern = Pattern.compile("^\\(?(\\d{3})\\)?-?(\\d{3})-?(\\d{4})$");
        Matcher match = pattern.matcher(reserveSeatPhoneInput.getText());
        if (!match.matches()){
            AlertBox ab = new AlertBox("Phone Number Error", "Please enter a valid phone number");
        }
        else if (reserveSeatNameInput.getText().length() == 0){
            AlertBox ab = new AlertBox("Name Error", "Please provide a name for \n this reservation");
        }
        else{
            SimpleDateFormat sdf = new SimpleDateFormat((HotPlateApp.britishTime)? "HH:mm": "hh:mm a");
            FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("confirmationPage.fxml"));
            HotPlateApp.stage.setScene(new Scene(fxml.load(), 600, 600));
            ConfirmationPageController cpc = fxml.getController();
            String phoneNumber = match.group(1) + match.group(2) + match.group(3);
            HotPlateApp.customerData.add(new Customer(reserveSeatNameInput.getText(), reserveSeatPartySizeChoice.getSelectionModel().getSelectedItem(),
                    sdf.format(new Date()), phoneNumber));
        }

    }

}
