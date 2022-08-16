package com.hotplate.hotplate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPortalController implements Initializable {

    @FXML
    private ImageView customerPortalPhoto;

    @FXML
    private Button customerPortalReserveButton;

    @FXML
    public Label customerPortalWaitlistLabel;

    @FXML
    public BorderPane bp;


    public void pressReserveButton() throws IOException {
        HotPlateApp.launchReserveSeatPortal();
    }

    public void pressSignInButton() throws IOException {
        HotPlateApp.launchSignInPage();
    }

    public void pressAboutButton() throws IOException {
        HotPlateApp.aboutMeCustomerOrgin = true;
        HotPlateApp.launchAboutMePage();
    }

    public void pressCallWaiterButton() throws IOException {
        AlertBox AB = new AlertBox("Service Disabled", "This function is disabled");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerPortalWaitlistLabel.setText("Waitlist Size: " + HotPlateApp.waitListSize);
    }
}