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

        if (LoginPage.getIsItIndividual()) {
            if (UserDataBaseManagement.getResponsibleCategories(LoginPage.getIndividual().getId()) != null) {
                responsible = UserDataBaseManagement.getResponsibleCategories(LoginPage.getIndividual().getId()).split(":");
                filteredCats = filterCats(category, responsible);
            }
        } else if (UserDataBaseManagement.getResponsibleCategories(LoginPage.getCompany().getId()) != null) {
            responsible = UserDataBaseManagement.getResponsibleCategories(LoginPage.getCompany().getId()).split(":");
            filteredCats = filterCats(category, responsible);
        }

        fillBoxWithCats(filteredCats);
    }

    private ArrayList<Category> filterCats(ArrayList<Category> category, String[] responsible){
        ArrayList<Category> filteredCats = new ArrayList<Category>();
        for (Category cat : category) {
            for (String res : responsible) {
                if (cat.getCatId() == Integer.parseInt(res)) {
                    filteredCats.add(cat);
                }
            }
        }
        return filteredCats;
    }

    private void fillBoxWithCats(ArrayList<Category> filteredCats){
        if (filteredCats != null) {
            filteredCats.forEach(cat -> ListText.getItems().add(cat.getName()
                    + ": " + cat.getDescription()
                    + ": " + cat.getCatId()));
        }
    }

    public void addResponsibleUser() {
        String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add responsible user");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter user id:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            UserDataBaseManagement.addResponsibleCategories(Integer.parseInt(result.get()), Integer.parseInt(catData[2]));
        }
    }

    public void updateCatFunc(ActionEvent actionEvent) {
        String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");

        List<String> choices = new ArrayList<>();
        choices.add("Name");
        choices.add("Description");

        ChoiceDialog<String> dialog1 = new ChoiceDialog<>("Name", choices);
        dialog1.setTitle("Choice Dialog");
        dialog1.setHeaderText("Choose what you want to update");
        dialog1.setContentText("Choose your option:");

        Optional<String> result1 = dialog1.showAndWait();
        if (result1.isPresent()) {
            if (result1.get().equals("Name")) {
                TextInputDialog dialog = new TextInputDialog("Name");
                catDetailUpdateBoxText(dialog, "name");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    CategoryDataBaseManagement.updateCategoryName(result.get(), catData[2]);
                }

            } else if (result1.get().equals("Description")) {
                TextInputDialog dialog2 = new TextInputDialog("Description");
                catDetailUpdateBoxText(dialog2, "description");

                Optional<String> result = dialog2.showAndWait();
                if (result.isPresent()) {
                    CategoryDataBaseManagement.updateCategoryDescription(result.get(), catData[2]);
                }
            }
        }

        if (ListText.getSelectionModel().getSelectedItem().equals(null))
            somethingNotFoundErrorAlert("categories", "category");

        fillCatWithData();
    }

    private void somethingNotFoundErrorAlert(String pluralWord, String singularWord) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("No " + pluralWord + " found!");
        alert.setContentText("Add at least one " + singularWord);
        alert.showAndWait();
    }

    private void catDetailUpdateBoxText(TextInputDialog dialog, String whatToUpdate) {
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Input the " + whatToUpdate + " you want");
        dialog.setContentText("Please enter your Description:");
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
            somethingNotFoundErrorAlert("categories", "category");
        }
    }

    public void deleteCat(ActionEvent actionEvent) {
        if (CategoryDataBaseManagement.getAllCategories().size() == 0) {
            somethingNotFoundErrorAlert("categories", "category");
        } else {
            String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");
            CategoryDataBaseManagement.deleteCategory(Integer.parseInt(catData[2]));
            ExpenseDB.deleteExpenseByCatId(Integer.parseInt(catData[2]));
            IncomeDB.deleteIncomeByCatId(Integer.parseInt(catData[2]));

            if (LoginPage.getIsItIndividual())
                UserDataBaseManagement.removeResponsibleCategories(LoginPage.getIndividual().getId(), Integer.parseInt(catData[2]));
            else
                UserDataBaseManagement.removeResponsibleCategories(LoginPage.getCompany().getId(), Integer.parseInt(catData[2]));
        }
        fillCatWithData();
    }

    public void addIncome(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");

        ArrayList<Optional<String>> dialog = income_expenseDialog("Income");

        String name = null;
        String desc = null;
        double cost = 0;

        if (dialog.get(0).isPresent())
            name = dialog.get(0).get();

        if (dialog.get(1).isPresent())
            desc = dialog.get(1).get();

        if (dialog.get(2).isPresent())
            cost = Float.parseFloat(dialog.get(2).get());

        if (!name.equals(null) && !desc.equals(null) && cost !=0) {
            Income income = new Income(name, desc, cost, LocalDate.now(), IncomeDB.getIncomeIdCount(), Integer.parseInt(catData[2]));
            IncomeDB.addIncome(income);
            IncomeDB.addIncomeIdCount();
        }
    }

    private ArrayList<Optional<String>> income_expenseDialog(String incomeOrExpense){
        TextInputDialog name = new TextInputDialog("Name");
        TextInputDialog desc = new TextInputDialog("Description");
        TextInputDialog cost = new TextInputDialog("cost");
        name.setTitle(incomeOrExpense);
        name.setHeaderText("Add " + incomeOrExpense);
        name.setContentText("Please insert name:");
        desc.setContentText("Please insert description:");
        cost.setContentText("Please insert amount:");

        ArrayList <Optional<String>> result = new ArrayList<>();
        result.add(name.showAndWait());
        result.add(desc.showAndWait());
        result.add(cost.showAndWait());

        return result;
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

            ArrayList<Income> income = IncomeDB.findAllIncome(Integer.parseInt(catData[2]));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Details");
            if (income.size() != 0) {
                alert.setContentText(income.toString());
                alert.showAndWait();
            } else {
                somethingNotFoundErrorAlert("incomes", "income");
            }
        } else {
            somethingNotFoundErrorAlert("categories", "category");
        }
    }

    public void addExpense(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");

        ArrayList<Optional<String>> dialog = income_expenseDialog("Expense");

        String name = null;
        String desc = null;
        double cost = 0;

        if (dialog.get(0).isPresent())
            name = dialog.get(0).get();

        if (dialog.get(1).isPresent())
            desc = dialog.get(1).get();

        if (dialog.get(2).isPresent())
            cost = Float.parseFloat(dialog.get(2).get());

        if (!name.equals(null) && !desc.equals(null) && cost !=0) {
            Expense expense = new Expense(name, desc, cost, LocalDate.now(), ExpenseDB.getExpenseIdCount(), Integer.parseInt(catData[2]));
            ExpenseDB.addExpense(expense);
            ExpenseDB.addExpenseIdCount();
        }
    }

    public void showExpense(ActionEvent actionEvent) {

        if (CategoryDataBaseManagement.getAllCategories().size() != 0) {
            String[] catData = ListText.getSelectionModel().getSelectedItem().toString().split(": ");

            ArrayList<Expense> expense = ExpenseDB.findAllExpense(Integer.parseInt(catData[2]));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Details");
            if (expense.size() != 0) {
                alert.setContentText(expense.toString());
                alert.showAndWait();
            } else {
                somethingNotFoundErrorAlert("expenses", "expense");
            }
        } else {
            somethingNotFoundErrorAlert("categories", "category");
        }
    }
}
