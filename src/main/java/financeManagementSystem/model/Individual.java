package financeManagementSystem.model;

import java.io.Serializable;

public class Individual extends User implements Serializable{
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private int id;
    private String reposnsibleForCategoriesId;
    //private ArrayList<Category> attachedCategories;

    public Individual() {
    }

    public Individual(String psw, String loginName, String name, String surname, String phoneNumber, String email, int id, String reposnsibleForCategoriesId) {
        super(psw, loginName);
        this.name = name;
        this.surname = surname;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
        return "User name: "
                + this.name
                + "\nSurname: "
                + this.surname
                + "\nPhone number: "
                + this.phoneNumber
                + "\nemail: "
                + this.email
                + "\nid: "
                + this.id;
    }
}
