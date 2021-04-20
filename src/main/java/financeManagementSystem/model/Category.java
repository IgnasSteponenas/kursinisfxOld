package financeManagementSystem.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Category implements Serializable {
    private String name;
    private String description;
    private Integer catId;
    private LocalDate dateCreated;
    private LocalDate dateModified;
    private String parentID;
    //private ArrayList<Category> subCategories = new ArrayList<Category>();
    //private ArrayList<String> responsibleUserIds;
    //private ArrayList<Expense> allExpenses = new ArrayList<Expense>();
    //private ArrayList<Income> allIncome = new ArrayList<Income>();


    public Category(String name, String description, Integer catId, LocalDate dateCreated, LocalDate dateModified, String parentID) {
        this.name = name;
        this.description = description;
        this.catId = catId;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.parentID = parentID;
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

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    @Override
    public String toString() {
        return "Name "
                + name
                + "\nDescription: "
                + description
                + "\nCategory id: "
                + catId
                + "\nDate created : "
                + dateCreated
                + "\nDate Modified : "
                + dateModified
                + "\nParent id : "
                + parentID
                + "\n";
    }
}
