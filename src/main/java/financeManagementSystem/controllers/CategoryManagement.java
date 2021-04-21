package financeManagementSystem.controllers;

import financeManagementSystem.model.Category;
import financeManagementSystem.model.Expense;
import financeManagementSystem.model.FinanceManagementSystem;
import financeManagementSystem.model.Income;
import financeManagementSystem.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CategoryManagement {
    @FXML
    public Button addCatBtn;
    @FXML
    public Button updateCatBtn;
    @FXML
    public Button ShowCatBtn;
    @FXML
    public Button exitBtn;
    @FXML
    public ListView ListText;
    @FXML
    public Button deleteCatbtn;
    @FXML
    public Button expenseButton;
    @FXML
    public Button incomeButton;
    @FXML
    public Button subButton;

    private FinanceManagementSystem fms;

    public void setFms(FinanceManagementSystem fms) {
        this.fms = fms;
        fillCatWithData();
    }

    public void addCatFunc(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCategory.fxml"));
        Parent root = loader.load();

        AddCategory addCategory = loader.getController();
        addCategory.setFms(fms);

        Stage stage = (Stage) addCatBtn.getScene().getWindow();

        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
        fillCatWithData();
    }


    public void fillCatWithData() {
        ListText.getItems().clear();

        ArrayList<Category> category = CategoryDataBaseManagement.getAllCategories();
        String[] responsible;
        ArrayList<Category> filteredCats = new ArrayList<Category>();

        if(LoginPage.getIsItIndividual()){
            if(UserDataBaseManagement.getResponsibleCategories(LoginPage.getIndividual().getId()) != null) {
                responsible = UserDataBaseManagement.getResponsibleCategories(LoginPage.getIndividual().getId()).split(":");
                for(Category cat: category){
                    for (String res: responsible) {
                        if (cat.getCatId()==Integer.parseInt(res)) {
                            filteredCats.add(cat);
                            //break;
                        }
                    }
                }
            }
        }else
            if(UserDataBaseManagement.getResponsibleCategories(LoginPage.getCompany().getId()) != null) {
                responsible = UserDataBaseManagement.getResponsibleCategories(LoginPage.getCompany().getId()).split(":");
                for(Category cat: category){
                    for (String res: responsible) {
                        if (cat.getCatId()==Integer.parseInt(res)) {
                            filteredCats.add(cat);
                            //break;
                        }
                    }
                }
            }


        if (filteredCats != null) {
            filteredCats.forEach(cat-> ListText.getItems().add(cat.getName()
                    + ": " + cat.getDescription()
                    + ": " + cat.getCatId()));
        }
    }

    public void addResponsibleUser(){
        String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add responsible user");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter user id:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            UserDataBaseManagement.addResponsibleCategories(Integer.parseInt(result.get()), Integer.parseInt(catData[2]));
        }
    }

    public void updateCatFunc(ActionEvent actionEvent) {
        String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");
        //Category category = fms.getAllCategories().stream().filter(c -> c.getName().equals(catData[0])).findFirst().orElse(null);
        Category category = CategoryDataBaseManagement.getCategoryById(catData[2]);//id

        List<String> choices = new ArrayList<>();
        choices.add("Name");
        choices.add("Description");

        ChoiceDialog<String> dialog1 = new ChoiceDialog<>("Name", choices);
        dialog1.setTitle("Choice Dialog");
        dialog1.setHeaderText("Look, a Choice Dialog");
        dialog1.setContentText("Choose your option:");

        Optional<String> result1 = dialog1.showAndWait();
        if (result1.isPresent()) {
            if (result1.get().equals("Name")) {
                TextInputDialog dialog = new TextInputDialog("Name");
                dialog.setTitle("Text Input Dialog");
                dialog.setHeaderText("Look, a Text Input Dialog");
                dialog.setContentText("Please enter your name:");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    CategoryDataBaseManagement.updateCategoryName(result.get(), catData[2]);
                }

            } else if (result1.get().equals("Description")) {
                TextInputDialog dialog2 = new TextInputDialog("Description");
                dialog2.setTitle("Text Input Dialog");
                dialog2.setHeaderText("Look, a Text Input Dialog");
                dialog2.setContentText("Please enter your Description:");

                Optional<String> result = dialog2.showAndWait();
                if (result.isPresent()) {
                    CategoryDataBaseManagement.updateCategoryDescription(result.get(), catData[2]);
                }
            }
        }

        if(ListText.getSelectionModel().getSelectedItem().equals(null)) {

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Error");
            alert1.setHeaderText("No categories found!");
            alert1.setContentText("Add at least one category");

            alert1.showAndWait();

        }
        fillCatWithData();
    }

    public void showCatFunc(ActionEvent actionEvent) {
        if (CategoryDataBaseManagement.getAllCategories().size() != 0) {

            String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");
            Category category = CategoryDataBaseManagement.getCategoryById(catData[2]);//id

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Details");
            alert.setContentText(category.toString());

            alert.showAndWait();
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Error");
            alert1.setHeaderText("No categories found!");
            alert1.setContentText("Add at least one category");

            alert1.showAndWait();
        }
    }

    public void deleteCat(ActionEvent actionEvent) {
        if (CategoryDataBaseManagement.getAllCategories().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("No categories found!");
            alert.setContentText("Add at least one category");
            alert.showAndWait();
        } else {
            String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");
            CategoryDataBaseManagement.deleteCategory(Integer.parseInt(catData[2]));
            ExpenseDB.deleteExpenseByCatId(Integer.parseInt(catData[2]));
            IncomeDB.deleteIncomeByCatId(Integer.parseInt(catData[2]));

            if(LoginPage.getIsItIndividual())
                UserDataBaseManagement.removeResponsibleCategories(LoginPage.getIndividual().getId(), Integer.parseInt(catData[2]));
            else
                UserDataBaseManagement.removeResponsibleCategories(LoginPage.getCompany().getId(), Integer.parseInt(catData[2]));
        }
        fillCatWithData();
    }

    public void addIncome(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");

        TextInputDialog dialog = new TextInputDialog("Name");
        TextInputDialog dialo = new TextInputDialog("Description");
        TextInputDialog dial = new TextInputDialog("cost");
        dialog.setTitle("Income");
        dialog.setHeaderText("Add Income");
        dialog.setContentText("Please insert name:");
        dialo.setContentText("Please insert description:");
        dial.setContentText("Please insert amount:");

        Optional<String> result = dialog.showAndWait();
        Optional<String> resul = dialo.showAndWait();
        Optional<String> resu = dial.showAndWait();

        String name = null;
        String desc = null;
        double cost = 0;
        if (result.isPresent()) {
            name = result.get();
        }
        if (resul.isPresent()) {
            desc = resul.get();
        }
        if (resu.isPresent()) {
            cost = Float.parseFloat(resu.get());
        }


        if (name.equals(null) || desc.equals(null) || cost == 0) {

        } else {
            Income income = new Income(name, desc, cost, LocalDate.now(), IncomeDB.getIncomeIdCount(), Integer.parseInt(catData[2]));
            IncomeDB.addIncome(income);
            IncomeDB.addIncomeIdCount();
        }


    }

    public void exit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFMSWindow.fxml"));
        Parent root = loader.load();

        MainFMSWindow mainFMSWindow = loader.getController();
        mainFMSWindow.setFms(fms);

        Stage stage = (Stage) exitBtn.getScene().getWindow();

        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void addSubCategory(ActionEvent actionEvent) throws IOException {
        String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddSubCategory.fxml"));
        Parent root = loader.load();


        AddSubCategory addSubCategory = loader.getController();
        addSubCategory.setFms(fms);
        addSubCategory.setCatParentId(Integer.parseInt(catData[2]));

        Stage stage = (Stage) subButton.getScene().getWindow();

        stage.setTitle("Finance Management System");
        stage.setScene(new Scene(root));
        stage.show();
        fillCatWithData();
    }

    public void showIncome(ActionEvent actionEvent) {

        if (CategoryDataBaseManagement.getAllCategories().size() != 0) {
            String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");
            //Category category = CategoryDataBaseManagement.getCategoryById(catData[2]);//id

            ArrayList<Income> income = IncomeDB.findAllIncome(Integer.parseInt(catData[2]));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Details");
            if (income.size() != 0) {
                //ArrayList<String> temp = income.forEach(inc -> income.toString());
                alert.setContentText(income.toString());
                alert.showAndWait();
            }
            else{
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Error");
                alert1.setHeaderText("No income found!");
                alert1.setContentText("Add at least one income report");

                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Error");
            alert1.setHeaderText("No categories found!");
            alert1.setContentText("Add at least one category");

            alert1.showAndWait();
        }
    }

    public void addExpense(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");

        TextInputDialog dialog = new TextInputDialog("Name");
        TextInputDialog dialo = new TextInputDialog("Description");
        TextInputDialog dial = new TextInputDialog("cost");
        dialog.setTitle("Expense");
        dialog.setHeaderText("Add Expense");
        dialog.setContentText("Please insert name:");
        dialo.setContentText("Please insert description:");
        dial.setContentText("Please insert amount:");

        Optional<String> result = dialog.showAndWait();
        Optional<String> resul = dialo.showAndWait();
        Optional<String> resu = dial.showAndWait();

        String name = null;
        String desc = null;
        double cost = 0;
        if (result.isPresent()) {
            name = result.get();
        }
        if (resul.isPresent()) {
            desc = resul.get();
        }
        if (resu.isPresent()) {
            cost = Float.parseFloat(resu.get());
        }


        if (name.equals(null) || desc.equals(null) || cost == 0) {

        } else {
            Expense expense = new Expense(name, desc, cost, LocalDate.now(), ExpenseDB.getExpenseIdCount(), Integer.parseInt(catData[2]));
            ExpenseDB.addExpense(expense);
            ExpenseDB.addExpenseIdCount();
        }
    }

    public void showExpense(ActionEvent actionEvent) {

        if (CategoryDataBaseManagement.getAllCategories().size() != 0) {
            String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");
            //Category category = CategoryDataBaseManagement.getCategoryById(catData[2]);//id

            ArrayList<Expense> expense = ExpenseDB.findAllExpense(Integer.parseInt(catData[2]));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Details");
            if (expense.size() != 0) {
                //ArrayList<String> temp = income.forEach(inc -> income.toString());
                alert.setContentText(expense.toString());
                alert.showAndWait();
            }
            else{
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Error");
                alert1.setHeaderText("No expenses found!");
                alert1.setContentText("Add at least one expense report");

                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Error");
            alert1.setHeaderText("No categories found!");
            alert1.setContentText("Add at least one category");

            alert1.showAndWait();
        }
    }
}
