package com.hotplate.hotplate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPage {

    public String name;
    public static Stage stage;
    public BorderPane bp;
    public static boolean aboutMeSectionOrg = false;

    public static void homePage() throws IOException {
        FXMLLoader fxml = new FXMLLoader(HotPlateApp.class.getResource("adminPage.fxml"));
        Scene scene = new Scene(fxml.load(), 600, 600);

        stage = HotPlateApp.stage;
        stage.setScene(scene);
        stage.show();

        AdminController ac = fxml.getController();
        ac.adminNameLabel.setText("Welcome, " + HotPlateApp.userName);
        stage.setOnCloseRequest(closeEvent -> {
            ac.endTime = true;
        });
    }

}
