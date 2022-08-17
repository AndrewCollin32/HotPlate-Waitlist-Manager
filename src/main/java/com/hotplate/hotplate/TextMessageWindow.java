package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TextMessageWindow implements Initializable {

    @FXML
    public Label textMessage;

    @FXML
    public Label textMessageRecieveLabel;

    @FXML
    void okButton(ActionEvent event) {
        HotPlateApp.callCustomerStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String TM;
        if (HotPlateApp.callCustomerBoolean){
            TM = HotPlateApp.callMessage;
        }
        else{
            TM = HotPlateApp.warnMessage;
        }
        Customer selectedCustomer = HotPlateApp.callCustomerClass;
        TM = TM.replaceAll("(?i)\\{name\\}", selectedCustomer.getName());
        TM = TM.replaceAll("(?i)\\{restaurant\\}", HotPlateApp.restaurantName);

        textMessageRecieveLabel.setText("This is the text message " + selectedCustomer.getName() + " will recieve.");
        textMessage.setText(TM);
    }
}
