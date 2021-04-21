package financeManagementSystem.controllers;

import financeManagementSystem.model.FinanceManagementSystem;
import financeManagementSystem.utils.DataRW;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFMSWindow {

    @FXML
    public Button categorieButton;
    @FXML
    public Button exitApp;
    @FXML
    public Button userSettingsButton;
    @FXML
    public Button save;

    private FinanceManagementSystem fms;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    public void accessCategories(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CategoryManagement.fxml"));
        Parent root = loader.load();

        CategoryManagement categoryManagement = loader.getController();
        categoryManagement.setFms(fms);

        Stage stage = (Stage) categorieButton.getScene().getWindow();

        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void exitApplication(ActionEvent actionEvent) {
        Stage stage = (Stage) exitApp.getScene().getWindow();
        stage.close();
    }

    public void userSettings(ActionEvent actionEvent) {
    }

    public void saveToFile(ActionEvent actionEvent) {
        System.out.println("rorked");
        DataRW.writeFMSToFile(fms);
    }
}
