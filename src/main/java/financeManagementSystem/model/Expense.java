package financeManagementSystem.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Expense implements Serializable {
    private String name;
    private String description;
    private double cost;
    private LocalDate date;
    private int expenseId;
    private int catId;

    public Expense() {
    }

    public Expense(String name, String description, double cost, LocalDate date, int expenseId, int catId) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.date = date;
        this.expenseId = expenseId;
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int incomeId) {
        this.expenseId = incomeId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    @Override
    public String toString() {
        return "Name "
                + name
                + "\nDescription: "
                + description
                + "\nMoney lost: "
                + cost
                + "\nDate: "
                + date
                + "\n";
    }
}
