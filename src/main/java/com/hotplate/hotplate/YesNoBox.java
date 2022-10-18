package com.hotplate.hotplate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public final class YesNoBox {
    private static Stage stage;
    private static boolean boxChoice;
    private static Scene scene;

    private static FXMLLoader fxml;

    private static YesNoBox yesNoBox;
    private YesNoBox(){
        fxml = new FXMLLoader(HotPlateApp.class.getResource("yesNoBox.fxml"));
        try {
            scene = new Scene(fxml.load(), 400, 200);
        } catch (IOException e) {
            HotPlateApp.log.severe("Couldn't open yesNoBox.fxml");
            throw new RuntimeException(e);
        }
    }

    public static boolean createAlert(String title, String message){
        yesNoBox = new YesNoBox();
        AlertBoxController abc = fxml.getController();
        abc.YesNoBoxLabel.setText(message);
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle(title);
        HotPlateApp.log.info("[Success] Choice box was created");
        stage.showAndWait();
        return boxChoice;
    }
    public static void closeYesNoBox(){
        stage.close();
    }
    public static void setBoxChoice(boolean choice){
        boxChoice = choice;
    }
}