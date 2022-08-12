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
    public MenuItem addToTable;

    @FXML
    public BorderPane adminBorderPane;

    @FXML
    public Label adminNameLabel;

    @FXML
    public Label adminTimeLabel;

    @FXML
    public MenuItem deleteToTable;


    @FXML
    public void transferToGitHub() throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("https://github.com/AndrewCollin/HotPlate"));
    }

    @FXML
    public MenuItem transferToHotPlateHelp;

    @FXML
    void adminCustomMessage() throws IOException {
        endTime = true;
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("customMessagePage.fxml"));
        Scene scene = new Scene(fxml.load(), 600, 600);
        HotPlateApp.stage.setScene(scene);
    }

    public static Stage addToTableStage;

    public static Stage callStage;

    @FXML
    void addToTable(ActionEvent event) {

        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("adminAddItem.fxml"));
        addToTableStage = new Stage();
        Stage stage = addToTableStage;
        stage.setTitle("Add Item");
        try {
            stage.setScene(new Scene(fxml.load(), 600, 400));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void callToTable(ActionEvent event) throws IOException {
        String textMessage = HotPlateApp.callMessag;
        Customer selectedCustomer;
        selectedCustomer = AdminTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null){
            AlertBox ab = new AlertBox("Call Error", "Please select the customer you want to message to");
            return;
        }
        textMessage = textMessage.replaceAll("(?i)\\{name\\}", selectedCustomer.getName());
        textMessage = textMessage.replaceAll("(?i)\\{restaurant\\}", HotPlateApp.restaurantName);

        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("callPage.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxml.load(), 600, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        callStage = stage;
        stage.setScene(scene);
        stage.show();

        textMessageWindow tmw = fxml.getController();
        tmw.textMessageRecieveLabel.setText("This is the text message " + selectedCustomer.getName() + " will recieve.");
        tmw.textMessage.setText(textMessage);
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
    void tarnsferToAbout(ActionEvent event) {
        endTime = true;
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("aboutMePage.fxml"));
        try {
            AdminPage.stage.setScene(new Scene(fxml.load(), 600, 600));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AdminPage.aboutMeSectionOrg = true;

    }

    @FXML
    void transferToCustomer(ActionEvent event) {
        YesNoBox ynb = new YesNoBox();
        boolean boxChoice = ynb.createAlert("Customer Portal", "Are you sure you want to leave?");
        if (boxChoice){
            endTime = true;
            HotPlateApp.backToCustomer();
        }
    }

    @FXML
    void transferToDonate(ActionEvent event) {
        endTime = true;

    }

    @FXML
    void transferToExit(ActionEvent event) {

        YesNoBox ynb = new YesNoBox();
        boolean boxChoice = ynb.createAlert("Exit", "Are you sure you want to exit?");
        if (boxChoice){
            endTime = true;
            HotPlateApp.stage.close();
        }
    }

    @FXML
    public void transferToLoad(ActionEvent event) throws InterruptedException, InvocationTargetException, IOException, ParseException {

        /*
        JFileChooser chooser = new JFileChooser();
        //FileNameExtensionFilter filter = new FileNameExtensionFilter(".ser files", ".ser");
        //chooser.setFileFilter(filter);
        int checkInput = chooser.showOpenDialog(null);

        if (checkInput == JFileChooser.APPROVE_OPTION){
            File openFile = chooser.getSelectedFile();

            System.out.println(openFile.getName());
        }*/

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
        AdminPage.homePage();
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
    void editTable(ActionEvent event){
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("adminEditItem.fxml"));
        addToTableStage = new Stage();
        Stage stage = addToTableStage;
        stage.setTitle("Edit Item");
        try {
            stage.setScene(new Scene(fxml.load(), 600, 400));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        adminEditItemController aeic = fxml.getController();
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
        aeic.adminAddName.setText(customer.getName());
        aeic.adminAddPartySize.setText(customer.getPartySize());
        aeic.adminAddPhone.setText(customer.getPhoneNumber());
        aeic.adminCustomTimeText.setText(customer.getTimeWaited());
        aeic.selectedCustomer = customer;
        stage.show();
    }

    @FXML
    void transferToSettings(ActionEvent event) {
        endTime = true;
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("settings.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxml.load(), 600, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HotPlateApp.stage.setScene(scene);
    }

    @FXML
    void warnToTable(ActionEvent event) throws IOException {
        String textMessage = HotPlateApp.warnMessage;
        Customer selectedCustomer = AdminTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null){
            AlertBox ab = new AlertBox("Warn Error", "Please select the customer you want to send a message to");
            return;
        }
        textMessage = textMessage.replaceAll("(?i)\\{name\\}", selectedCustomer.getName());
        textMessage = textMessage.replaceAll("(?i)\\{restaurant\\}", HotPlateApp.restaurantName);


        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("callPage.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxml.load(), 600, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        callStage = stage;
        stage.setScene(scene);
        stage.show();

        textMessageWindow tmw = fxml.getController();
        tmw.textMessageRecieveLabel.setText("This is the text message " + selectedCustomer.getName() + " will recieve.");
        tmw.textMessage.setText(textMessage);
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

        try {
            endTime = false;
            setTime();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
