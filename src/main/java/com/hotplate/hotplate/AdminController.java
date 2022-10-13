package com.hotplate.hotplate;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public final class AdminController implements Initializable {

    @FXML
    public TableColumn<Customer, String> name;

    @FXML
    public TableColumn<Customer, String> partySize;

    @FXML
    public TableColumn<Customer, String> timeWaited;

    @FXML
    public TableColumn<Customer, String> phoneNumber;

    @FXML
    public TableView<Customer> AdminTable;

    @FXML
    public BorderPane adminBorderPane;

    @FXML
    public Label adminNameLabel;

    @FXML
    public Label adminTimeLabel;

    @FXML
    public MenuItem transferToHotPlateHelp;


    @FXML
    public void transferToGitHub(ActionEvent event) throws URISyntaxException, IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        java.awt.Desktop.getDesktop().browse(new URI("https://github.com/AndrewCollin32/HotPlate-Waitlist-Manager"));
    }

    @FXML
    void adminCustomMessage(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        HotPlateApp.endTime = true;
        HotPlateApp.launchCustomMessagePage();
    }

    @FXML
    void addToTable(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        HotPlateApp.launchAdminAddToTable();
    }

    @FXML
    void callToTable(ActionEvent event) throws IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        Customer selectedCustomer = AdminTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null){
            HotPlateApp.log.warning("[Fail] Customer failed to make a selection: " + event);
            AlertBox.createAlertBox("Call Error", "Please select the customer you want to message to");
            return;
        }
        HotPlateApp.launchCallCustomer(true, selectedCustomer);
        //twilioClass.warnPerson(selectedCustomer, textMessage);
    }

    @FXML
    void deleteToTable(ActionEvent event) throws IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        Customer customer = AdminTable.getSelectionModel().getSelectedItem();
        if (customer == null){
            HotPlateApp.log.warning("[Fail] Customer failed to make a selection: " + event);
            AlertBox.createAlertBox("Delete Error", "Please select the customer you want to remove");
            return;
        }
        AdminTable.getItems().remove(customer);
        HotPlateApp.customerData.remove(customer);
        HotPlateApp.waitListSize--;
    }

    @FXML
    void transferToAbout(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        HotPlateApp.endTime = true;
        HotPlateApp.launchAboutMePage();
        HotPlateApp.aboutMeCustomerOrigin = false;
    }

    @FXML
    void transferToCustomer(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        if (YesNoBox.createAlert("Customer Portal", "Are you sure you want to leave?")){
            HotPlateApp.endTime = true;
            HotPlateApp.launchCustomerPortal();
        }
    }

    @FXML
    void transferToExit(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        if (YesNoBox.createAlert("Exit", "Are you sure you want to exit?")){
            HotPlateApp.endTime = true;
            HotPlateApp.log.info("[Closing] SOFTWARE IS CLOSING");
            HotPlateApp.stage.close();
        }
    }

    @FXML
    public void transferToLoad(ActionEvent event) throws IOException{

        HotPlateApp.log.info("[Button] Clicked: " + event.toString());

        if (HotPlateApp.useSQL){
            HotPlateApp.loadSQL.loadCustomerData();
        }
        else {
            SaveData sd;
            try {
                sd = (SaveData) ResourceManager.load(HotPlateApp.customerDataPathFile);
                assert sd != null;
                HotPlateApp.waitListSize = sd.customersData.size();
                HotPlateApp.customerData = sd.customersData;

                if (sd.customersData.size() != 0) {
                    SimpleDateFormat britishTime = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat americanTime = new SimpleDateFormat("hh:mm a");

                    boolean isBritishTime = false;

                    try {
                        americanTime.parse(HotPlateApp.customerData.get(0).getTimeWaited());
                    } catch (Exception e) {
                        isBritishTime = true;
                    }

                    if (HotPlateApp.britishTime && !isBritishTime || !HotPlateApp.britishTime && isBritishTime) {
                        int size = HotPlateApp.customerData.size();
                        for (int i = 0; i < size; i++) {
                            Customer customer = HotPlateApp.customerData.get(i);
                            String time = customer.getTimeWaited();
                            Date date = ((HotPlateApp.britishTime) ? americanTime : britishTime).parse(time);
                            String newDate = ((HotPlateApp.britishTime) ? britishTime : americanTime).format(date);
                            customer.setTimeWaited(newDate);
                        }
                    }
                }
            } catch (Exception e) {
                HotPlateApp.log.severe("[Fail] Couldn't load customer data: " + e);
                HotPlateApp.launchLogError("[Fail] Couldn't load customer data: " + e);
            }
        }
        HotPlateApp.log.info("[Success] Customer data successfully loaded");
        HotPlateApp.endTime = true;
        HotPlateApp.launchAdminPortal();
    }

    @FXML
    void transferToSave(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event.toString());
        if (HotPlateApp.useSQL){
            HotPlateApp.loadSQL.saveCustomerData();
        }
        else {
            SaveData sd = new SaveData(HotPlateApp.customerData);
            ResourceManager.save(sd, HotPlateApp.customerDataPathFile);
        }
    }

    @FXML
    void transferToSaveAs(ActionEvent event) {

    }

    @FXML
    void editTable(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        ObservableList<TablePosition> oblist = AdminTable.getSelectionModel().getSelectedCells();
        if (oblist.size() == 0){
            HotPlateApp.log.warning("[Fail] User failed to make a selection");
            AlertBox.createAlertBox("Error", "Please select the row you want to edit");
            return;
        }
        HotPlateApp.selectedCustomer = AdminTable.getItems().get(oblist.get(0).getRow());
        HotPlateApp.launchAdminEditToTable();
    }

    @FXML
    void transferToSettings(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.endTime = true;
        HotPlateApp.launchSettings();
    }

    @FXML
    void warnToTable(ActionEvent event) throws IOException {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        Customer selectedCustomer = AdminTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null){
            HotPlateApp.log.warning("[Fail] User failed to make a selection");
            AlertBox.createAlertBox("Warn Error", "Please select the customer you want to message to");
            return;
        }
        HotPlateApp.launchCallCustomer(false, selectedCustomer);
        //twilioClass.warnPerson(selectedCustomer, textMessage);
    }

    public void setTime() throws InterruptedException {
        HotPlateApp.log.info("Starting time");
        Thread thread = new Thread(() -> {
           SimpleDateFormat timeFormat = new SimpleDateFormat((HotPlateApp.britishTime)? "HH:mm": "hh:mm a");
           while(!HotPlateApp.endTime){
               try{
                   Thread.sleep(1000);
               }
               catch (Exception e){
                   HotPlateApp.log.severe("[Fail] Time: " + e);
                   HotPlateApp.launchLogError("[Fail] Time: " +e);
               }
               final String timeNow = timeFormat.format(new Date());
               Platform.runLater(() -> adminTimeLabel.setText(timeNow));
           }
        });
        thread.start();
    }

    @FXML
    public void transferToClearTable(ActionEvent event){
        HotPlateApp.log.info("[Button] Clicked: " + event);
        if (YesNoBox.createAlert("Clear Table", "Are you sure you want to clear the table?")){
            AdminTable.getItems().clear();
            HotPlateApp.customerData.clear();
            HotPlateApp.waitListSize = 0;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HotPlateApp.log.info("[Start] Loading Customer's data to table");
        adminNameLabel.setText("Welcome, " + HotPlateApp.userName);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        partySize.setCellValueFactory(new PropertyValueFactory<>("partySize"));
        timeWaited.setCellValueFactory(new PropertyValueFactory<>("timeWaited"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        customerList.addAll(HotPlateApp.customerData);
        AdminTable.setItems(customerList);

        HotPlateApp.stage.setOnCloseRequest(closeEvent -> {
            HotPlateApp.log.info("[Closing] Program is closing");
            HotPlateApp.endTime = true;
        });

        try {
            HotPlateApp.endTime = false;
            setTime();
        } catch (InterruptedException e) {
            HotPlateApp.log.severe("[Fail] Failed to set time: " + e);
            HotPlateApp.launchLogError("[Fail] Failed to set time: " + e);
        }

        HotPlateApp.log.info("[Success] Customer's data is successfully loaded");
    }
}
