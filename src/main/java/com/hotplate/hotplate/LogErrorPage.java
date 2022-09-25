package com.hotplate.hotplate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

public final class LogErrorPage implements Initializable {

    @FXML
    private Label errorLabel;

    @FXML
    private TextArea logTextArea;


    @FXML
    void onClickExit(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.logErrorStage.close();
    }

    @FXML
    void onClickSendReport(ActionEvent event) {
        HotPlateApp.log.info("[Button] Clicked: " + event);
        HotPlateApp.logErrorStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HotPlateApp.log.info("[Starting] Loading error log file");
        errorLabel.setText("Error: " + HotPlateApp.errorLogMessage);
        StringBuilder total = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("Logs/logFile.log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                total.append(line).append("\n");
            }
        } catch(Exception e) {HotPlateApp.log.severe("Couldn't read file: " + e);}
        logTextArea.setText(total.toString());
        logTextArea.selectPositionCaret(logTextArea.getLength());
        logTextArea.deselect();
        HotPlateApp.log.info("[Success] Loading error log file");
    }
}
