package com.hotplate.hotplate;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

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

    public static Stage addToTableStage;

    public static Stage callStage;


    @FXML
    public void transferToGitHub() throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("https://github.com/AndrewCollin/HotPlate"));
    }

    @FXML
    void adminCustomMessage() throws IOException {
        endTime = true;
        HotPlateApp.launchCustomMessagePage();
    }

    @FXML
    void addToTable(ActionEvent event) throws IOException {
        HotPlateApp.launchAdminAddToTable();
    }

    @FXML
    void callToTable(ActionEvent event) throws IOException {
        Customer selectedCustomer = AdminTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null){
            AlertBox ab = new AlertBox("Call Error", "Please select the customer you want to message to");
            return;
        }
        HotPlateApp.launchCallCustomer(true, selectedCustomer);
        //twilioClass.warnPerson(selectedCustomer, textMessage);
    }

    @FXML
    void deleteToTable(ActionEvent event) throws IOException {
        Customer customer = AdminTable.getSelectionModel().getSelectedItem();
        if (customer == null){
            AlertBox ab = new AlertBox("Delete Error", "Please select the customer you want to remove");
            return;
        }
        AdminTable.getItems().remove(customer);
        HotPlateApp.customerData.remove(customer);
        HotPlateApp.waitListSize--;
    }

    @FXML
    void tarnsferToAbout(ActionEvent event) throws IOException {
        endTime = true;
        HotPlateApp.launchAboutMePage();
        HotPlateApp.aboutMeCustomerOrgin = false;
    }

    @FXML
    void transferToCustomer(ActionEvent event) throws IOException {
        if (YesNoBox.createAlert("Customer Portal", "Are you sure you want to leave?")){
            endTime = true;
            HotPlateApp.launchCustomerPortal();
        }
    }

    @FXML
    void transferToExit(ActionEvent event) {
        if (YesNoBox.createAlert("Exit", "Are you sure you want to exit?")){
            endTime = true;
            HotPlateApp.stage.close();
        }
    }

    @FXML
    public void transferToLoad(ActionEvent event) throws InterruptedException, InvocationTargetException, IOException, ParseException {

        SaveData sd = (SaveData) ResourceManager.load(HotPlateApp.customerDataPathFile);
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
                for (int i = 0; i < HotPlateApp.customerData.size(); i++) {
                    Customer customer = HotPlateApp.customerData.get(i);
                    String time = customer.getTimeWaited();
                    Date date = ((HotPlateApp.britishTime) ? americanTime : britishTime).parse(time);
                    String newDate = ((HotPlateApp.britishTime) ? britishTime : americanTime).format(date);
                    customer.setTimeWaited(newDate);
                }
            }
        }
        endTime = true;
        HotPlateApp.launchAdminPortal(true);
    }

    @FXML
    void transferToSave(ActionEvent event) {

        SaveData sd = new SaveData(HotPlateApp.customerData);
        ResourceManager.save(sd, HotPlateApp.customerDataPathFile);
    }

    @FXML
    void transferToSaveAs(ActionEvent event) {

    }

    @FXML
    void editTable(ActionEvent event) throws IOException {
        ObservableList<TablePosition> oblist = AdminTable.getSelectionModel().getSelectedCells();
        if (oblist.size() == 0){
            try {
                AlertBox ab = new AlertBox("Error", "Please select the row you want to edit");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        Customer customer = AdminTable.getItems().get(oblist.get(0).getRow());
        HotPlateApp.selectedCustomer = customer;
        HotPlateApp.launchAdminEditToTable();
    }

    @FXML
    void transferToSettings(ActionEvent event) throws IOException {
        endTime = true;
        HotPlateApp.launchSettings();
    }

    @FXML
    void warnToTable(ActionEvent event) throws IOException {
        Customer selectedCustomer = AdminTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null){
            AlertBox ab = new AlertBox("Warn Error", "Please select the customer you want to message to");
            return;
        }
        HotPlateApp.launchCallCustomer(false, selectedCustomer);
        //twilioClass.warnPerson(selectedCustomer, textMessage);
    }

    volatile static boolean endTime;

    public void setTime() throws InterruptedException {
        Thread thread = new Thread(() -> {
           SimpleDateFormat timeFormat = new SimpleDateFormat((HotPlateApp.britishTime)? "HH:mm": "hh:mm a");
           while(!endTime){
               try{
                   Thread.sleep(1000);
               }
               catch (Exception e){
                   e.printStackTrace();
               }
               final String timeNow = timeFormat.format(new Date());
               Platform.runLater(() -> {
                   adminTimeLabel.setText(timeNow);
               });
           }
        });
        thread.start();
    }

    @FXML
    public void transferToClearTable(){
        YesNoBox YN = new YesNoBox();
        if (YN.createAlert("Clear Table", "Are you sure you want to clear the table?")){
            AdminTable.getItems().clear();
            HotPlateApp.customerData.clear();
            HotPlateApp.waitListSize = 0;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminNameLabel.setText("Welcome, " + HotPlateApp.userName);
        name.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        partySize.setCellValueFactory(new PropertyValueFactory<Customer, String>("partySize"));
        timeWaited.setCellValueFactory(new PropertyValueFactory<Customer, String>("timeWaited"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));

        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        for (int i = 0; i < HotPlateApp.customerData.size(); i++){
            Customer customer = HotPlateApp.customerData.get(i);
            customerList.add(customer);
        }
        AdminTable.setItems(customerList);

        HotPlateApp.stage.setOnCloseRequest(closeEvent -> {
            endTime = true;
        });

        try {
            endTime = false;
            setTime();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
