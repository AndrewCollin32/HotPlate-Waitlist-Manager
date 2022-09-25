package com.hotplate.hotplate;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public final class CustomerPortalController implements Initializable {

    @FXML
    public Label customerPortalWaitListLabel;

    @FXML
    public BorderPane bp;


    public void pressReserveButton() {
        HotPlateApp.log.info("[Button] Clicked: reserveButton");
        HotPlateApp.launchReserveSeatPortal();
    }

    public void pressSignInButton() {
        HotPlateApp.log.info("[Button] Clicked: Sign In Button");
        HotPlateApp.launchSignInPage();
    }

    public void pressAboutButton() {
        HotPlateApp.log.info("[Button] Clicked: About Button");
        HotPlateApp.aboutMeCustomerOrigin = true;
        HotPlateApp.launchAboutMePage();
    }
    public void pressCallWaiterButton() throws IOException {
        HotPlateApp.log.info("[Button] Clicked: Call Waiter Button");
        AlertBox.createAlertBox("Service Disabled", "This function is disabled");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HotPlateApp.log.info("[Customer Portal] Wait list Size: " + HotPlateApp.waitListSize);
        customerPortalWaitListLabel.setText("Wait list Size: " + HotPlateApp.waitListSize);
    }
}