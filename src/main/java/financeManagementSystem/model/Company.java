package financeManagementSystem.model;

import java.io.Serializable;

public class Company extends User implements Serializable{
    private String name;
    private String phoneNumber;
    private String email;
    private int id;
    private String reposnsibleForCategoriesId;
    //private ArrayList<Category> attachedCategories;

    public Company() {
    }

    public Company(String psw, String loginName, String name, String phoneNumber, String email, int id, String reposnsibleForCategoriesId) {
        super(psw, loginName);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.id = id;
        this.reposnsibleForCategoriesId = reposnsibleForCategoriesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReposnsibleForCategoriesId() {
        return reposnsibleForCategoriesId;
    }

    public void setReposnsibleForCategoriesId(String reposnsibleForCategoriesId) {
        this.reposnsibleForCategoriesId = reposnsibleForCategoriesId;
    }

    @Override
    public String toString() {
        return "User name and surname "
                + this.name
                + ". Contact info: "
                + this.phoneNumber + " "
                + this.email + " "
                + this.id;
    }
}
