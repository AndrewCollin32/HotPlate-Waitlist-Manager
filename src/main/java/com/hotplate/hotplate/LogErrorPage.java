package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

public class LogErrorPage implements Initializable {

    @FXML
    private Label errorLabel;

    @FXML
    private TextArea logTextArea;

    @FXML
    private TextField commentTextField;

    @FXML
    void onClickExit(ActionEvent event) {
        HotPlateApp.logErrorStage.close();
    }

    @FXML
    void onClickSendReport(ActionEvent event) {
        HotPlateApp.logErrorStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("Error: " + HotPlateApp.errorLogMessage);
        String total = "";
        try (BufferedReader br = new BufferedReader(new FileReader("Logs/logFile.log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                total += line + "\n";
            }
        } catch(Exception e) {HotPlateApp.log.severe("Couldn't read file: " + e);}
        logTextArea.setText(total);
        logTextArea.selectPositionCaret(logTextArea.getLength());
        logTextArea.deselect();
    }
}
