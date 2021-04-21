package financeManagementSystem.controllers;

import financeManagementSystem.model.Category;
import financeManagementSystem.model.Company;
import financeManagementSystem.model.FinanceManagementSystem;
import financeManagementSystem.model.Individual;
import financeManagementSystem.utils.DataRW;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class StartApp extends Application {

    public static FinanceManagementSystem fms = new FinanceManagementSystem();

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent root = loader.load();

        LoginPage loginPage = loader.getController();
        loginPage.setFms(fms);

        primaryStage.setTitle("Finance Management System");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

