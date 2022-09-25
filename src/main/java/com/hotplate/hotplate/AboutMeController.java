package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public final class AboutMeController {

    @FXML
    void GitHubLink(ActionEvent event) throws URISyntaxException, IOException {
        //HostServices host = getHostServices().showDocument("https://github.com/AndrewCDiep");
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        java.awt.Desktop.getDesktop().browse(new URI("https://github.com/AndrewCollin32/HotPlate-Waitlist-Manager"));
    }

    @FXML
    void aboutMeOkButton(ActionEvent event) throws IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        if (HotPlateApp.aboutMeCustomerOrigin){
            HotPlateApp.launchCustomerPortal();
        }
        else{
            HotPlateApp.launchAdminPortal();
        }
    }

    @FXML
    void facebookLink(ActionEvent event) throws URISyntaxException, IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        java.awt.Desktop.getDesktop().browse(new URI("https://www.facebook.com/System32Comics"));
    }

    @FXML
    void instagramLink(ActionEvent event) throws URISyntaxException, IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        java.awt.Desktop.getDesktop().browse(new URI("https://www.instagram.com/system32comics/"));
    }

    @FXML
    void twitterLink(ActionEvent event) throws URISyntaxException, IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        java.awt.Desktop.getDesktop().browse(new URI("https://twitter.com/System32Comics"));
    }

}
