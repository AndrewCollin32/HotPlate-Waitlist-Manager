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

public class customerPortalController implements Initializable {

    @FXML
    private ImageView customerPortalPhoto;

    @FXML
    private Button customerPortalReserveButton;

    @FXML
    public Label customerPortalWaitlistLabel;

    @FXML
    public BorderPane bp;


    public void pressReserveButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(HotPlateApp.class.getResource("reserveSeatPortal.fxml"));
       HotPlateApp.stage.setScene(new Scene(loader.load(), 600, 600));
        ReserveSeatPortalController rspc = loader.getController();
        for (int i = 1; i < 13; i++){
            rspc.reserveSeatPartySizeChoice.getItems().add(i + "");
        }
        rspc.reserveSeatPartySizeChoice.getSelectionModel().selectFirst();
        rspc.reserveSeatPortalWelcomeLabel.setText("Thank you for choosing " + HotPlateApp.restaurantName + "!");
    }

    public void pressSignInButton() throws IOException {
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("signInPage.fxml"));
        HotPlateApp.stage.setScene(new Scene(fxml.load(),600,600));
        //DEMO REMOVE IN FINAL VERSION
        AlertBox ab = new AlertBox("Your Pin number", "Your pin number is: " + HotPlateApp.pinNumber );
    }

    public void pressAboutButton() throws IOException {
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("aboutMePage.fxml"));
        HotPlateApp.stage.setScene(new Scene(fxml.load(), 600, 600));
        AdminPage.aboutMeSectionOrg = false;
    }

    public void pressCallWaiterButton() throws IOException {
        AlertBox AB = new AlertBox("Service Disabled", "This function is disabled");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerPortalWaitlistLabel.setText("Waitlist Size: " + HotPlateApp.waitListSize);
    }
}