package financeManagementSystem.model;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String psw;
    protected String loginName;

    public User() {
    }

    public User(String psw, String loginName) {
        this.psw = psw;
        this.loginName = loginName;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void greetUser(String name) {
        System.out.println("Hello dear user");
    }
}