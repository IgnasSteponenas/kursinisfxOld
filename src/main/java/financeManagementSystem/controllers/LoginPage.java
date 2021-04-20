package financeManagementSystem.controllers;

import financeManagementSystem.model.Company;
import financeManagementSystem.model.FinanceManagementSystem;
import financeManagementSystem.model.Individual;
import financeManagementSystem.utils.UserDataBaseManagement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPage implements Initializable {

    @FXML
    public PasswordField password;
    @FXML
    public TextField login;
    @FXML
    public Button signinButton;
    @FXML
    public ChoiceBox choicebox1;
    @FXML
    public ChoiceBox regChoisebox;
    @FXML
    public TextField regName;
    @FXML
    public TextField regSurname;
    @FXML
    public TextField regPhone;
    @FXML
    public TextField regEmail;
    @FXML
    public TextField regLogin;
    @FXML
    public TextField regPass;
    @FXML
    public Button registerBtn;

    private FinanceManagementSystem fms;
    private static Company company;
    private static Individual individual;
    private static boolean isItIndividual = true;

    public static boolean getIsItIndividual(){
        return isItIndividual;
    }

    public static Company getCompany() {
        return company;
    }

    public static Individual getIndividual() {
        return individual;
    }

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
    }

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        choicebox1.getItems().add("Individual");
        choicebox1.getItems().add("Company");

        regChoisebox.getItems().add("Individual");
        regChoisebox.getItems().add("Company");

        regChoisebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue == (Number)1)
                    regSurname.setDisable(true);
                else
                    regSurname.setDisable(false);
            }
        });
    }


    public void registerFunction(ActionEvent actionEvent) throws IOException {
        if(regChoisebox.getValue()=="Individual"){
            if(!regLogin.getText().isEmpty() && !regPass.getText().isEmpty() && !regName.getText().isEmpty() && !regSurname.getText().isEmpty() && !regPhone.getText().isEmpty() && !regEmail.getText().isEmpty()){
                registerIndividual();
                isItIndividual = true;
                loadMainWindow();
            }
            else{
                alertRegister();
            }

        }else if(regChoisebox.getValue()=="Company"){
            if(!regLogin.getText().isEmpty() && !regPass.getText().isEmpty() && !regName.getText().isEmpty() && !regPhone.getText().isEmpty() && !regEmail.getText().isEmpty()){
                registerCompany();
                isItIndividual = false;
                loadMainWindow();
            }
            else{
                alertRegister();
            }
        }else{
            alertChooseUser();
        }
    }

    private void registerIndividual(){
        individual = new Individual(regPass.getText(),regLogin.getText(),regName.getText(),regSurname.getText(),regPhone.getText(),regEmail.getText(), UserDataBaseManagement.getUserIdCount(), "null");
        UserDataBaseManagement.addUserIdCount();
        UserDataBaseManagement.insertIndividual(individual);
    }

    private void registerCompany(){
        company = new Company(regPass.getText(),regLogin.getText(),regName.getText(),regPhone.getText(),regEmail.getText(), UserDataBaseManagement.getUserIdCount(), "null");
        UserDataBaseManagement.addUserIdCount();
        UserDataBaseManagement.insertCompany(company);
    }

    public void LoginFunction(ActionEvent actionEvent) throws IOException {

        if(choicebox1.getValue()=="Individual"){
            individualLoginCheck();
        }else if(choicebox1.getValue()=="Company"){
            companyLoginCheck();
        }else{
            alertChooseUser();
        }
    }

    private void alertChooseUser(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("User type is not chosen");
        alert.setContentText("Choose user type!");

        alert.showAndWait();
    }

    private void alertRegister(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Some fields are not filled!");
        alert.setContentText("Fill in all of the fields");

        alert.showAndWait();
    }

    private void alertWrongCredentials(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Wrong Username or Password!");
        alert.setContentText("Try again!");

        alert.showAndWait();
    }

    private void individualLoginCheck() throws IOException {
        individual = UserDataBaseManagement.findIndividual(login.getText(), password.getText());
        if(individual == null){
            alertWrongCredentials();
        }
        else{
            isItIndividual = true;
            loadMainWindow();
        }
    }

    private void companyLoginCheck() throws IOException {
       company = UserDataBaseManagement.findCompany(login.getText(), password.getText());
        if(company == null){
            alertWrongCredentials();
        }
        else{
            isItIndividual = false;
            loadMainWindow();
        }
    }

    private void loadMainWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFMSWindow.fxml"));
        Parent root = loader.load();

        MainFMSWindow mainFMSWindow = loader.getController();
        mainFMSWindow.setFms(fms);

        Stage stage = (Stage) signinButton.getScene().getWindow();

        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }



}
