package com.hotplate.hotplate;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class AboutMeController {

    @FXML
    void GitHubLink(ActionEvent event) throws URISyntaxException, IOException {
        //HostServices host = getHostServices().showDocument("https://github.com/AndrewCDiep");
        java.awt.Desktop.getDesktop().browse(new URI("https://github.com/AndrewCDiep"));
    }

    @FXML
    void aboutMeOkButton(ActionEvent event) throws IOException {
        if (HotPlateApp.aboutMeCustomerOrgin){
            HotPlateApp.launchCustomerPortal();
        }
        else{
            HotPlateApp.launchAdminPortal(false);
        }
    }

    @FXML
    void facebookLink(ActionEvent event) throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("https://www.facebook.com/System32Comics"));
    }

    @FXML
    void instagramLink(ActionEvent event) throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("https://www.instagram.com/system32comics/"));
    }

    @FXML
    void twitterLink(ActionEvent event) throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("https://twitter.com/System32Comics"));
    }

}
