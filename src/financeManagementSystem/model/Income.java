package financeManagementSystem.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Income implements Serializable {
    private String name;
    private String description;
    private double cost;
    private LocalDate date;
    private int incomeId;
    private int catId;

    public Income() {
    }

    public Income(String name, String description, double cost, LocalDate date, int incomeId, int catId) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.date = date;
        this.incomeId = incomeId;
        this.catId = catId;
    }

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
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

    @Override
    public String toString() {
        return "Name "
                + name
                + "\nDescription: "
                + description
                + "\nMoney gained: "
                + cost
                + "\nDate: "
                + date
                + "\n";
    }
}
