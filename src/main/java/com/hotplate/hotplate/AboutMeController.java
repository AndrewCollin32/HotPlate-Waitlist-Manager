
// About Me Page. This page shows all the social media link of Andrew Collins

package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public final class AboutMeController {
    StringBuilder buttonClickedString = new StringBuilder("[Button] Clicked: ");
    private void browseLink(String url, ActionEvent event){
        try{
            HotPlateApp.log.info(buttonClickedString.append(event.toString()).toString());
            java.awt.Desktop.getDesktop().browse(new URI(url));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void gitHubLink(ActionEvent event) throws URISyntaxException, IOException {
        browseLink("https://github.com/AndrewCollin32/HotPlate-Waitlist-Manager", event);
    }
    @FXML
    void facebookLink(ActionEvent event) throws URISyntaxException, IOException {
        browseLink("https://www.facebook.com/System32Comics", event);
    }
    @FXML
    void instagramLink(ActionEvent event) throws URISyntaxException, IOException {
        browseLink("https://www.instagram.com/system32comics/", event);
    }
    @FXML
    void twitterLink(ActionEvent event) throws URISyntaxException, IOException {
        browseLink("https://twitter.com/System32Comics", event);
    }
    @FXML
    void aboutMeOkButton(ActionEvent event) throws IOException {
        HotPlateApp.log.info(buttonClickedString.append(event.toString()).toString());
        if (HotPlateApp.aboutMeCustomerOrigin){
            HotPlateApp.launchCustomerPortal();
        }
        else{
            HotPlateApp.launchAdminPortal();
        }
    }
}
