package financeManagementSystem.model;

import java.io.Serializable;
import java.util.ArrayList;

public class FinanceManagementSystem implements Serializable{
    int idCount=0;
    private ArrayList<Individual> allIndividuals = new ArrayList<Individual>();
    private ArrayList<Company> allCompanies = new ArrayList<Company>();
    private ArrayList<Category> allCategories = new ArrayList<Category>();

    public FinanceManagementSystem() {
    }

    public int getIdCount(){
        return idCount;
    }

    public void addIdCount(){
        int temp=1;
        this.idCount = idCount+temp;
    }

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(ArrayList<Category> allCategories) {
        this.allCategories = allCategories;
    }

    public boolean checkIfIndividualExists(int userId) {
        //return allIndividuals.stream().anyMatch((individual -> individual.getId() == userId));
        /*for (Individual r : allIndividuals) {
            if (r.getId().equals(String.valueOf(userId))) return true;
        }*/
        return false;
    }

    public void checkIfCompanyExists(String userId) {
        //return allCompanies.stream().anyMatch((company -> company.getId().equals(userId)));
        /*for (Reader r : allReaders) {
            if (r.getReaderId().equals(userId)) return true;
        }*/
    }

    /*public Individual getIndividualData(String userId, String psw, String login) {
        return allIndividuals.stream().filter(individual -> individual.getId().equals(userId)
                && individual.getPsw().equals(psw) && individual.getLoginName().equals(login)).findFirst().orElse(null);
    }*/
    public void getCompanyData(String userId, String psw, String login) {
        /*return allCompanies.stream().filter(company -> company.getId().equals(userId)
                && company.getPsw().equals(psw) && company.getLoginName().equals(login)).findFirst().orElse(null);
    */}

    /*public Company returnCompany(String userId){
        return allCompanies.stream().filter(company -> company.getId().equals(userId)).findFirst().orElse(null);
    }*/

    public ArrayList<Individual> getAllIndividuals() {
        return allIndividuals;
    }

    public void setAllIndividuals(ArrayList<Individual> allIndividuals) {
        this.allIndividuals = allIndividuals;
    }

    public ArrayList<Company> getAllCompanies() {
        return allCompanies;
    }

    public void setAllCompanies(ArrayList<Company> allCompanies) {
        this.allCompanies = allCompanies;
    }
}
