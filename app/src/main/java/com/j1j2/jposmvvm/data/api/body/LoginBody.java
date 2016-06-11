package com.j1j2.jposmvvm.data.api.body;

/**
 * Created by 兴昊 on 2015/12/17.
 */
public class LoginBody {

    /**
     * Account : 内部测试
     * UserName : downleaves
     * Password : 111111
     */

    private String Account;
    private String UserName;
    private String Password;


    public void setAccount(String Account) {
        this.Account = Account;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getAccount() {
        return Account;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    @Override
    public String toString() {
        return "LoginBody{" +
                "Account='" + Account + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
